package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.FixedDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.FloatingDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.Holiday;
import com.pos.service.toolsRentalSystem.data.repository.HolidayRepository;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.HolidayRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayRepository holidayRepository;


    @Override
    public Holiday createFloatingDateHoliday(HolidayRequest request) {
        FloatingDateHoliday floatingDateHoliday = new FloatingDateHoliday();
        floatingDateHoliday.setName(request.getName());
        floatingDateHoliday.setWeekDay(request.getWeekDay());
        floatingDateHoliday.setWeekNumber(request.getWeekNumber());
        floatingDateHoliday.setMonth(request.getMonth());
        return holidayRepository.save(floatingDateHoliday);
    }

    @Override
    public Holiday createFixedDateHoliday(HolidayRequest request) {
        FixedDateHoliday fixedDateHoliday = new FixedDateHoliday();
        fixedDateHoliday.setName(request.getName());
        fixedDateHoliday.setDay(request.getDay());
        fixedDateHoliday.setMonth(request.getMonth());
        fixedDateHoliday.setIsObservedHoliday(request.getIsObservedHoliday());
        return holidayRepository.save(fixedDateHoliday);
    }


    @Override
    public void delete(UUID id) {
        if (holidayRepository.getReferenceById(id).getId().equals(id)) {
            holidayRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Holiday", "id", id);
    }

    @Override
    public Holiday get(UUID id) {
        return holidayRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Holiday", "id", id));
    }

    @Override
    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }
}

