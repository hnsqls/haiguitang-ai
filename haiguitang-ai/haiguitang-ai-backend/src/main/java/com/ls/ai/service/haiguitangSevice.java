package com.ls.ai.service;

public interface haiguitangSevice {
    /**
     * 生成海龟汤故事
     * @return
     */
     String generateStory();


    /**
     * 处理用户输入并返回 AI 回复
     * @param sessionId
     * @param userInput
     * @return
     */
    String handleUserInput(String sessionId, String userInput);
}
