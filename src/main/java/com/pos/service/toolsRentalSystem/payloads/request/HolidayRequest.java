package com.pos.service.toolsRentalSystem.payloads.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Month;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HolidayRequest {

    @NotNull @NotBlank private String name;

    private Month month;

    private DayOfWeek dayOfWeek; // from 1 to 7 starting from MONDAY, has its oun validation

    @Min(1)
    @Max(4)
    private int weekNumber;

    @Min(1)
    @Max(31)
    private int day;

    private Boolean isObservedHoliday; // if Sat,then Friday before, if Sunday, then Monday after
}
