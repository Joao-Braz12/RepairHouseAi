package dev.joaobraz12.RepairHouseAi.Issue.controller;

import dev.joaobraz12.RepairHouseAi.Tool.model.ToolItem;
import dev.joaobraz12.RepairHouseAi.Tool.service.ChatGptService;
import dev.joaobraz12.RepairHouseAi.Tool.service.ToolItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueItemController {

    private ToolItemService toolItemService;
    private ChatGptService chatGptService;

    public IssueItemController(ChatGptService chatGptService, ToolItemService toolItemService) {
        this.chatGptService = chatGptService;
        this.toolItemService = toolItemService;
    }


    @GetMapping("/generate/{issue}")
    public Mono<ResponseEntity<String>> generateSolution(@PathVariable String issue){
        List<ToolItem> toolItemList = toolItemService.listTools();
        return chatGptService.generateSolution(toolItemList, issue)
                .map(solution -> ResponseEntity.ok(solution))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
