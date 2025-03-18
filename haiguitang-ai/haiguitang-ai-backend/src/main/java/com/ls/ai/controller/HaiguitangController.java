package com.ls.ai.controller;

import com.ls.ai.service.haiguitangSevice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/haiguitang")
public class HaiguitangController {

    @Resource
    private haiguitangSevice haiguitangService;

    /**
     * 生成海龟汤故事
     */
    @GetMapping("/generate")
    public String generateStory() {
        return haiguitangService.generateStory();
    }

    /**
     * 处理用户输入
     *
     * @param sessionId     会话id ---获取上下文生成的故事
     * @param userInput 用户输入
     * @return AI 回复
     */
    @PostMapping("/interact")
    public String handleUserInput(@RequestParam String sessionId, @RequestParam String userInput) {
        return haiguitangService.handleUserInput(sessionId, userInput);
    }
}