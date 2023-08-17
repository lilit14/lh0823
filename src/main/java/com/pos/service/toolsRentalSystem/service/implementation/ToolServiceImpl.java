package com.pos.service.toolsRentalSystem.service.implementation;

import com.pos.service.toolsRentalSystem.data.model.Tool;
import com.pos.service.toolsRentalSystem.data.model.ToolType;
import com.pos.service.toolsRentalSystem.data.repository.ToolRepository;
import com.pos.service.toolsRentalSystem.data.repository.ToolTypeRepository;
import com.pos.service.toolsRentalSystem.exceptions.RentalAgreementException;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.ToolRequest;
import com.pos.service.toolsRentalSystem.service.ToolService;
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

    @Autowired private ToolRepository toolRepository;

    @Autowired private ToolTypeRepository toolTypeRepository;

    @Override
    public Tool create(ToolRequest request) {
        ToolType toolType = toolTypeRepository.findByNameEquals(request.getType());
        if (toolType == null) {
            throw new RentalAgreementException(
                    "Unknown Tool Type", "The tool type by the given name does not exist.");
        }
        Tool tool = new Tool();
        tool.setCode(request.getCode());
        tool.setType(toolType);
        tool.setBrand(request.getBrand());
        tool.setAvailableAmount(request.getAvailableAmount());
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
        return toolRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", id));
    }

    @Override
    public List<Tool> getAll() {
        return toolRepository.findAll();
    }
}
