package com.pos.service.toolsRentalSystem.data.repository;

import com.pos.service.toolsRentalSystem.data.model.Tool;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {
    Tool findByCodeEquals(String code);
}
