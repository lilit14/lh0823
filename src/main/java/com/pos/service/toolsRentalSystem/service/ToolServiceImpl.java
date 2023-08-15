package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.Tool;
import com.pos.service.toolsRentalSystem.data.repository.ToolRepository;
import com.pos.service.toolsRentalSystem.data.repository.ToolTypeRepository;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.ToolRequest;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    ToolTypeRepository toolTypeRepository;

    @Override
    public Tool create(ToolRequest request) {
        Tool tool = new Tool();
        tool.setCode(request.getCode());
        tool.setType(toolTypeRepository.findByNameEquals(request.getType()));
        tool.setBrand(request.getBrand());
        return toolRepository.save(tool);
    }


    @Override
    public void delete(UUID id) {
        if (toolRepository.getReferenceById(id).getId().equals(id)) {
            toolRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Tool", "id", id);
    }

    @Override
    public Tool get(UUID id) throws ResourceNotFoundException {
        return toolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tool", "id", id));
    }

    @Override
    public List<Tool> getAll() {
        return toolRepository.findAll();
    }
}
