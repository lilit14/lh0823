package com.pos.service.toolsRentalSystem.data.repository;

import com.pos.service.toolsRentalSystem.data.model.ToolType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolTypeRepository extends JpaRepository<ToolType, UUID> {
    ToolType findByNameEquals(String name);
}
