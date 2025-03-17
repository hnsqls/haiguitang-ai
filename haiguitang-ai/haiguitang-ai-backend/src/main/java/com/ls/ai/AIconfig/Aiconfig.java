package com.ls.ai.AIconfig;

import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * AI 配置类
 */
@Configuration
@ConfigurationProperties(prefix = "ai")
@Data// 因为springboot 参数的注入是直接setter 方法的，所以要加@Data
public class Aiconfig {

    private  String apikey;

    static String baseUrl = "https://ark.cn-beijing.volces.com/api/v3";

    static ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
    static Dispatcher dispatcher = new Dispatcher();

    @Bean
    public ArkService getAIService() {
        return ArkService.builder().dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .baseUrl(baseUrl)
                .apiKey(apikey).build();
    }
}
