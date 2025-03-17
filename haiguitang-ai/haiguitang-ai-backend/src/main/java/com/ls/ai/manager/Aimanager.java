package com.ls.ai.manager;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Ai 工具类
 */
@Component
public class Aimanager {

    @Resource
    private ArkService aiService;


    /**
     * AI 默认模型 Doubao-lite-128k  240828
     */
    private  String Default_Model= "doubao-lite-128k-240828";


    /**
     * 用户输入
     * @param userPrompt
     * @return
     */
    public String doChat(String userPrompt) {
        System.out.println("\n----- standard request -----");

        // 构造消息
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("").build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);



        // 构造请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                .model(Default_Model)
                .messages(messages)
                .build();
//        发起请求 并解析结果
        List<ChatCompletionChoice> choices = aiService.createChatCompletion(chatCompletionRequest).getChoices();

        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("AI生成失败");
        }

        // 解析结果
        StringBuilder stringBuilder = new StringBuilder();
        choices.forEach(choice -> stringBuilder.append(choice.getMessage().getContent()).append("\n"));
        return stringBuilder.toString();

    }


    /**
     * 系统输入 用户输入
     * @param systemPrompt
     * @param userPrompt
     * @return
     */
    public String doChat(String systemPrompt,String userPrompt) {
        System.out.println("\n----- standard request -----");

        // 构造消息
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);



        // 构造请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                .model(Default_Model)
                .messages(messages)
                .build();
//        发起请求 并解析结果
        List<ChatCompletionChoice> choices = aiService.createChatCompletion(chatCompletionRequest).getChoices();

        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("AI生成失败");
        }

        // 解析结果
        StringBuilder stringBuilder = new StringBuilder();
        choices.forEach(choice -> stringBuilder.append(choice.getMessage().getContent()).append("\n"));
        return stringBuilder.toString();

    }



    /**
     * 系统输入、用户输入，用户指定模型
     * @param systemPrompt
     * @param userPrompt
     * @param AiModel
     * @return
     */
    public String doChat(String systemPrompt, String userPrompt,String AiModel){


        System.out.println("\n----- standard request -----");
        // 构造消息
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);


        // 封装请求
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 指定您创建的方舟推理接入点 ID，此处已帮您修改为您的推理接入点 ID
                .model(AiModel)
                .messages(messages)
                .build();

        // 发起请求获取结果
        List<ChatCompletionChoice> choices = aiService.createChatCompletion(chatCompletionRequest).getChoices();

        if (choices == null || choices.isEmpty()) {
            throw  new RuntimeException("AI生成失败");
        }

        // 解析结果
        StringBuilder stringBuilder = new StringBuilder();
        choices.forEach(choice -> stringBuilder.append(choice.getMessage().getContent()).append("\n"));

        return stringBuilder.toString();
    }
}
