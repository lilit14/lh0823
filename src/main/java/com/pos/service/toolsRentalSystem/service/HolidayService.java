package com.pos.service.toolsRentalSystem.service;

import com.pos.service.toolsRentalSystem.data.model.Holiday;
import com.pos.service.toolsRentalSystem.payloads.request.HolidayRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public interface HolidayService {
    Holiday createFloatingDateHoliday(HolidayRequest request);

    Holiday createFixedDateHoliday(HolidayRequest request);

    void delete(UUID id);

    Holiday get(UUID id);

    List<Holiday> getAll();
}
