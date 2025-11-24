package dev.joaobraz12.RepairHouseAi.Tool.service;

import dev.joaobraz12.RepairHouseAi.Tool.model.ToolItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class ChatGptService {

    private final WebClient webClient;
    private String          apiKey= System.getenv("CHATGPT_API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateSolution(List<ToolItem> toolItems, String issue){
        System.out.println("APIKEY: "+ apiKey);
        String tools = toolItems.stream()
                .map(item -> String.format("%s - quantity: %d, category: %s",
                        item.getName(), item.getQuantity(), item.getCategory()))
                .collect(Collectors.joining("\n"));

        String prompt = "how do i "+ issue + " with theses tools: "+tools;
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1",
                "messages", List.of(
                        Map.of("role", "system","content", "you are a specialist of house repairing"),
                        Map.of("role", "user", "content", prompt)
                )
        );
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    if(choices != null && !choices.isEmpty()){
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return (String) message.get("content").toString();
                    }
                    return "No solution generated";
                });
    }
}
