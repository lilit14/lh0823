package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.Tool;
import com.pos.service.toolsRentalSystem.payloads.request.ToolRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface ToolService {
    Tool create(ToolRequest request);

    void delete(UUID id);

    Tool get(UUID id);

    List<Tool> getAll();
}
