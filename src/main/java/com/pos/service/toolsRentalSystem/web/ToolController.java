package com.pos.service.toolsRentalSystem.web;

import com.pos.service.toolsRentalSystem.data.model.Tool;
import com.pos.service.toolsRentalSystem.payloads.request.ToolRequest;
import com.pos.service.toolsRentalSystem.payloads.response.MessageResponse;
import com.pos.service.toolsRentalSystem.service.ToolService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tool")

public class ToolController {
    @Autowired
    ToolService toolService;

    @GetMapping("/all")
    public ResponseEntity<List<Tool>> getAll () {
        List<Tool> tools = toolService.getAll();
        return new ResponseEntity<>(tools, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Tool> get (@PathVariable("id") UUID id) {
        Tool toolType = toolService.get(id);
        return new ResponseEntity<>(toolType, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Tool> create(@RequestBody ToolRequest request) {
        Tool tool = toolService.create(request);
        return new ResponseEntity<>(tool, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        toolService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Successfully deleted by id: " +id ),HttpStatus.OK);
    }
}
