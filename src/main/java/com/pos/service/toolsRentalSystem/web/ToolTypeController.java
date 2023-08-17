package com.pos.service.toolsRentalSystem.web;

import com.pos.service.toolsRentalSystem.data.model.ToolType;
import com.pos.service.toolsRentalSystem.payloads.request.ToolTypeRequest;
import com.pos.service.toolsRentalSystem.payloads.response.MessageResponse;
import com.pos.service.toolsRentalSystem.service.ToolTypeService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/toolType")
public class ToolTypeController {
    @Autowired private ToolTypeService toolTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<ToolType>> getAll() {
        List<ToolType> toolTypes = toolTypeService.getAll();
        return new ResponseEntity<>(toolTypes, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ToolType> get(@PathVariable("id") UUID id) {
        ToolType toolType = toolTypeService.get(id);
        return new ResponseEntity<>(toolType, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ToolType> create(@RequestBody ToolTypeRequest request) {
        ToolType toolType = toolTypeService.create(request);
        return new ResponseEntity<>(toolType, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        toolTypeService.delete(id);
        return new ResponseEntity<>(
                new MessageResponse("Successfully deleted by id: " + id), HttpStatus.OK);
    }
}
