package com.ls.ai.service.impl;

import com.ls.ai.manager.Aimanager;
import com.ls.ai.service.haiguitangSevice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
public class haiguitangSeviceImpl implements haiguitangSevice {
    @Resource
    private Aimanager aimanager;

    // 用于存储答案的缓存（Key: 会话 ID, Value: 答案）
    private final Map<String, String> answerCache = new HashMap<>();

    /**
     * 生成海龟汤故事，并隐藏答案
     */
    public String generateStory() {
        String systemPrompt = """
            你是一个海龟汤游戏的主持人，请你生成故事，并且生成答案
            要求
            1.故事要有逻辑性，要有悬念
            2.故事要符合海龟汤游戏的规则，要有答案
            3.在开头,输出“请你还原事情真相”
            4.不要生成多余内容
            """;

        // 生成初始故事
        String story = aimanager.doChat(systemPrompt, "");

        // 分离问题和答案
        String[] parts = story.split("答案：");
        String question = parts[0].trim(); // 问题部分
        String answer = "答案：" + parts[1].trim(); // 答案部分

        // 生成唯一的会话 ID
        String sessionId = UUID.randomUUID().toString();

        // 将伪问题和答案保存到缓存中
        answerCache.put(sessionId, story);

        // 返回问题部分和会话 ID
        return sessionId + "|" + question;
    }

    /**
     * 处理用户输入并返回 AI 回复
     *
     * @param sessionId 会话 ID
     * @param userInput 用户输入
     * @return AI 回复
     */
    public String handleUserInput(String sessionId, String userInput) {
        // 获取story 完整内容
        String story = answerCache.get(sessionId);
        if (story == null) {
            throw new IllegalArgumentException("无效的会话 ID");
        }


        // 拼接用户输入和问题
        StringBuilder context = new StringBuilder(story)
                .append("用户输入：").append(userInput).append("\n");

        // 拼接上下文 用户输入
        answerCache.put(sessionId, context.toString());

        // 获取 AI 回复
        String  systemPrompt = """
            你是一个海龟汤游戏的主持人，故事已经生成过了会在用户的消息里拼接，但是用户并不知道，你现在引导用户玩游戏
            1.根据用户的输入以及答案内容，提示用户是或者不是
            2.用户表现出不想玩了，就给出答案
            3.根据用户输入提供提示或答案
            4.用户输出了核心的真相，以及相关的细节，就可以提示用户恭喜通过了答案，并且输出故事的真相
            4.不要生成多余内容
            """;
        String aiResponse = aimanager.doChat(systemPrompt, context.toString());
        // 将 AI 回复保存到缓存中
        answerCache.put(sessionId, answerCache.get(sessionId) + "AI 回复:" + aiResponse+"\n");

        // 返回 AI 回复
        return aiResponse;
    }


}
