package dev.joaobraz12.RepairHouseAi.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${chatgpt.api.url:https://api.openai.com/v1/chat/completions}")
    private String gtpApiUrl;

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl(gtpApiUrl).build();
    }
}
