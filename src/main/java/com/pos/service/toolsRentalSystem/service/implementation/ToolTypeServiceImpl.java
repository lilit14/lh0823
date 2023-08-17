package com.pos.service.toolsRentalSystem.service.implementation;

import com.pos.service.toolsRentalSystem.data.model.ToolType;
import com.pos.service.toolsRentalSystem.data.repository.ToolTypeRepository;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.ToolTypeRequest;
import com.pos.service.toolsRentalSystem.service.ToolTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolTypeServiceImpl implements ToolTypeService {

    @Autowired private ToolTypeRepository toolTypeRepository;

    @Override
    public ToolType create(ToolTypeRequest request) {
        ToolType toolType = new ToolType();
        toolType.setName(request.getName());
        toolType.setDailyCharge(request.getDailyCharge());
        toolType.setWeekdayCharge(request.getWeekdayCharge());
        toolType.setWeekendCharge(request.getWeekendCharge());
        toolType.setHolidayCharge(request.getHolidayCharge());
        return toolTypeRepository.save(toolType);
    }

    @Override
    public void delete(UUID in) {
        if (toolTypeRepository.getReferenceById(in).getId().equals(in)) {
            toolTypeRepository.deleteById(in);
        } else throw new ResourceNotFoundException("TooType", "id", in);
    }

    @Override
    public ToolType get(UUID id) throws ResourceNotFoundException {
        return toolTypeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToolType", "id", id));
    }

    @Override
    public List<ToolType> getAll() {
        return toolTypeRepository.findAll();
    }
}
