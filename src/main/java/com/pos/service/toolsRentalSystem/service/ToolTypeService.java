package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.ToolType;
import com.pos.service.toolsRentalSystem.payloads.request.ToolTypeRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface ToolTypeService {
    ToolType create(ToolTypeRequest request);

    void delete(UUID id);

    ToolType get(UUID id);

    List<ToolType> getAll();
}
