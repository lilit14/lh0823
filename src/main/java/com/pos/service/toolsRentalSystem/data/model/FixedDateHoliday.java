package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Fixed")
public class FixedDateHoliday extends Holiday {
    @Min(1)
    @Max(31)
    @NotNull
    private Integer day;
    @Min(0)
    @Max(11)
    @NotNull
    private Integer month; // from 0 to 11
    @NotNull
    private Boolean isObservedHoliday; //if Sat,then Friday before, if Sunday, then Monday after

    @Builder
    public FixedDateHoliday(String name, Integer day, Integer month, Boolean isObservedHoliday) {
        super(name);
        this.day = day;
        this.month = month;
        this.isObservedHoliday = isObservedHoliday;
    }
    @Override
    public LocalDate getDate(int year) {
        LocalDate date = LocalDate.of(year, month, day);
        if (isObservedHoliday) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return LocalDate.of(year,month,day-1);
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return LocalDate.of(year,month,day+1);
            }
        }
        return date;
    }

}
