package com.pos.service.toolsRentalSystem.payloads.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HolidayRequest {

    @NotNull
    @NotBlank
    private String name;

    @Min(1)
    @Max(7)
    private int weekDay; //from 1 to 7 starting from MONDAY
    @Min(1)
    @Max(4)
    private int weekNumber; // from 1 to 4
    @Min(0)
    @Max(11)
    private int month; // from 1 to 12
    @Min(0)
    @Max(31)
    private int day;
    private Boolean isObservedHoliday; //if Sat,then Friday before, if Sunday, then Monday after

}
