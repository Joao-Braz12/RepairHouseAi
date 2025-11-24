package dev.joaobraz12.RepairHouseAi.Tool.controller;


import dev.joaobraz12.RepairHouseAi.Tool.model.ToolItem;
import dev.joaobraz12.RepairHouseAi.Tool.service.ToolItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tool")
public class ToolItemController {

    private final ToolItemService service;

    public ToolItemController(ToolItemService service) {
        this.service = service;
    }

    //POST
    @PostMapping("/create")
    public ResponseEntity<?> Create(@RequestBody ToolItem toolItem){
        ToolItem saved = service.saveTool(toolItem);
        return ResponseEntity.status(HttpStatus.CREATED).body("The Tool was created! \n" + toolItem);
    }

    //GET
    @GetMapping("/list")
    public ResponseEntity<List<ToolItem>> List(){
        List<ToolItem> toolList = service.listTools();
        return ResponseEntity.ok(toolList);
    }
    @GetMapping("/list/{id}")
    public ResponseEntity<?> ListById(@PathVariable Long id){
        Optional<ToolItem> tool = service.listById(id);
        if(tool.isPresent())
            return ResponseEntity.ok(tool);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The id: " + id+ " was not found!" );
    }
    //UPDATE
    @PutMapping("update/{id}")
    public ResponseEntity<?> Update(@PathVariable Long id,@RequestBody ToolItem newTool){
        return service.listById(id)
                .map(existingItem -> {
                    newTool.setId(existingItem.getId());
                    ToolItem updated = service.update(newTool);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable Long id){
        if(service.listById(id).isPresent()){
            service.deleteById(id);
            return ResponseEntity.ok("id: "+id+" Tool deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The tool with id: "+ id + " was not found");
    }

}
