package com.pos.service.toolsRentalSystem.web;

import com.pos.service.toolsRentalSystem.data.model.Holiday;
import com.pos.service.toolsRentalSystem.payloads.request.HolidayRequest;
import com.pos.service.toolsRentalSystem.payloads.response.MessageResponse;
import com.pos.service.toolsRentalSystem.service.HolidayService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holiday")

public class HolidayController {
    @Autowired
    HolidayService holidayService;


    @GetMapping("/all")
    public ResponseEntity<List<Holiday>> getAll() {
        List<Holiday> holidays = holidayService.getAll();
        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Holiday> get(@PathVariable("id") UUID id) {
        Holiday holiday = holidayService.get(id);
        return new ResponseEntity<>(holiday, HttpStatus.OK);
    }

    @PostMapping("/create/fixedDateHoliday")
    public ResponseEntity<Holiday> createFixedHoliday(@RequestBody HolidayRequest request) {
        Holiday holiday = holidayService.createFixedDateHoliday(request);
        return new ResponseEntity<>(holiday, HttpStatus.CREATED);
    }

    @PostMapping("/create/floatingDateHoliday")
    public ResponseEntity<Holiday> createFloatingHoliday(@RequestBody HolidayRequest request) {
        Holiday holiday = holidayService.createFloatingDateHoliday(request);
        return new ResponseEntity<>(holiday, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        holidayService.delete(id);
        return new ResponseEntity<>(new MessageResponse("Successfully deleted by id: " + id), HttpStatus.OK);
    }
}
