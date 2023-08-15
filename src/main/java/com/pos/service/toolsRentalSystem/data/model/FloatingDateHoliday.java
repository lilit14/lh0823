package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Floating")
public class FloatingDateHoliday extends Holiday {

    @Min(1) // starting from SUNDAY
    @Max(7)
    @NotNull
    private Integer weekDay;
    @Min(1)
    @Max(4)
    @NotNull
    private Integer weekNumber;

    @Min(0)
    @Max(11)
    private Integer month;

    @Builder
    public FloatingDateHoliday(String name, Integer weekDay, Integer weekNumber, Integer month) {
        super(name);
        this.weekDay = weekDay;
        this.weekNumber = weekNumber;
        this.month = month;
    }

    @Override
    public LocalDate getDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, this.month);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, this.weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, this.weekDay);
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
    }


}
