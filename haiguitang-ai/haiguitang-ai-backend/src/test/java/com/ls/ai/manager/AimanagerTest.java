package com.ls.ai.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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
}