package com.ls.ai;

import com.ls.ai.manager.Aimanager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Scanner;

@Component
public class haiguitangTest implements CommandLineRunner {

    @Resource
    private Aimanager aimanager;

    @Override
    public void run(String... args) throws Exception {
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
        // 处理生成的故事
        String[] split = story.split("答案");


        System.out.println("AI生成的故事：\n" + split[0]);

        Scanner scanner = new Scanner(System.in);

        StringBuilder gameContext = new StringBuilder();
        gameContext.append(story);
        for (int i = 0; i < 10; i++) {
             systemPrompt = """
            你是一个海龟汤游戏的主持人，故事已经生成过了会在用户的消息里拼接，但是用户并不知道，你现在引导用户玩游戏
            1.根据用户的输入以及答案内容，提示用户是或者不是
            2.用户表现出不想玩了，就给出答案
            3.根据用户输入提供提示或答案
            4.不要生成多余内容
            """;
            System.out.print("用户请询问：");
            String userInput = scanner.nextLine();
            System.out.println("用户输入：" + userInput);

            // 拼接 用户 消息
            StringBuilder userContext = gameContext.append("用户输入：").append(userInput).append("\n");

            // 根据用户输入获取 AI 的回复
            String aiResponse = aimanager.doChat(systemPrompt, userContext.toString());
            System.out.println("AI回复：" + aiResponse);
            // 拼接 AI 消息
            gameContext.append("AI回复：").append(aiResponse).append("\n");

            // 如果用户输入“退出”，结束循环 或者 AI 回复中有答案
            if ("退出".equals(userInput) || aiResponse.contains("答案")) {
                break;
            }
        }

        scanner.close();
    }
}