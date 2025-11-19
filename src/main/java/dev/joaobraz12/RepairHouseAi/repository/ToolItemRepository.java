package dev.joaobraz12.RepairHouseAi.repository;

import dev.joaobraz12.RepairHouseAi.model.ToolItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolItemRepository extends JpaRepository<ToolItem, Long> {
}
