package com.pos.service.toolsRentalSystem.service.implementation;

import com.pos.service.toolsRentalSystem.data.model.FixedDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.FloatingDateHoliday;
import com.pos.service.toolsRentalSystem.data.model.Holiday;
import com.pos.service.toolsRentalSystem.data.repository.HolidayRepository;
import com.pos.service.toolsRentalSystem.exceptions.ResourceNotFoundException;
import com.pos.service.toolsRentalSystem.payloads.request.HolidayRequest;
import com.pos.service.toolsRentalSystem.service.HolidayService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {

    @Autowired private HolidayRepository holidayRepository;

    @Override
    public Holiday createFloatingDateHoliday(HolidayRequest request) {
        return holidayRepository.save(
                new FloatingDateHoliday(
                        request.getName(),
                        request.getDayOfWeek(),
                        request.getWeekNumber(),
                        request.getMonth()));
    }

    @Override
    public Holiday createFixedDateHoliday(HolidayRequest request) {
        return holidayRepository.save(
                new FixedDateHoliday(
                        request.getName(),
                        request.getDay(),
                        request.getMonth(),
                        request.getIsObservedHoliday()));
    }

    @Override
    public void delete(UUID id) {
        if (holidayRepository.getReferenceById(id).getId().equals(id)) {
            holidayRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Holiday", "id", id);
    }

    @Override
    public Holiday get(UUID id) {
        return holidayRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday", "id", id));
    }

    @Override
    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }
}
