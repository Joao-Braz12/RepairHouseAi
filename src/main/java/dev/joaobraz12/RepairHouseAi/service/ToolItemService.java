package dev.joaobraz12.RepairHouseAi.service;

import dev.joaobraz12.RepairHouseAi.model.ToolItem;
import dev.joaobraz12.RepairHouseAi.repository.ToolItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToolItemService {

    ToolItemRepository repository;

    public ToolItemService(ToolItemRepository repository) {
        this.repository = repository;
    }

    public ToolItem saveTool(ToolItem toolItem){
        return repository.save(toolItem);
    }

    public List<ToolItem> listTools(){
        return repository.findAll();
    }

    public Optional<ToolItem> listById(Long id){
        return repository.findById(id);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public ToolItem update(ToolItem toolItem){
        return repository.save(toolItem);
    }

    public ToolItem updateById(Long id, ToolItem newTool){
        Optional<ToolItem> toolItem = repository.findById(id);
        if(toolItem.isPresent())
        {
            ToolItem updated = repository.save(newTool);
            updated.setId(id);
            return updated;
        }
        return null;
    }
}
