package com.ls.ai.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AimanagerTest {


    @Resource
    private Aimanager aimanager;
    @Test
    void doChat() {
        String string = aimanager.doChat("你是谁");
        System.out.println("AI 回复 = " + string);
    }

    /**
     * 生成海龟汤
     */
    @Test
    void testDoChat() {

        String systemPrompt ="""
        你是一个海龟汤游戏的主持人，请你生成故事
        要求
        1.故事要有逻辑性，要有悬念
        2.故事要符合海龟汤游戏的规则，要有答案
        3.在开头,输出请你还原事情真相
        """;


        String string = aimanager.doChat(systemPrompt,"");
        System.out.println("string = " + string);

        Scanner scanner = new Scanner(System.in);

        for (int i= 0 ; i < 10 ; i++){
            String string1 = scanner.nextLine();
            System.out.println("用户 = " + string1);

            aimanager.doChat(systemPrompt,string1);

        }
    }

    @Test
    void testDoChat1() {
        String systemPrompt = """
        你是一个海龟汤游戏的主持人，请你生成故事
        要求
        1.故事要有逻辑性，要有悬念
        2.故事要符合海龟汤游戏的规则，要有答案
        3.在开头,输出“请你还原事情真相”
        4.生成故事后，等待用户输入，根据用户输入提供提示或答案
        """;

        // 生成初始故事
        String story = aimanager.doChat(systemPrompt, "");
        System.out.println("AI生成的故事：\n" + story);

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            System.out.print("请输入你的推理或输入“提示”获取提示，输入“答案”查看答案：");
            String userInput = scanner.nextLine();
            System.out.println("用户输入：" + userInput);

            // 根据用户输入获取 AI 的回复
            String aiResponse = aimanager.doChat(systemPrompt, userInput);
            System.out.println("AI回复：\n" + aiResponse);

            // 如果用户输入“退出”，结束循环
            if ("退出".equals(userInput)) {
                break;
            }
        }

        scanner.close();
    }
}