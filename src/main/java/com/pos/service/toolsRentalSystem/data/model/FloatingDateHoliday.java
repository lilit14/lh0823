package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Objects;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Floating")
public class FloatingDateHoliday extends Holiday {

    @NotNull private DayOfWeek dayOfWeek;

    @Min(1)
    @Max(4)
    @NotNull
    private Integer weekNumber;

    @NotNull private Month month;

    @Builder
    public FloatingDateHoliday(String name, DayOfWeek dayOfWeek, Integer weekNumber, Month month) {
        super(name);
        this.dayOfWeek = dayOfWeek;
        this.weekNumber = weekNumber;
        this.month = month;
    }

    @Override
    public LocalDate getDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(
                Calendar.MONTH,
                this.month.getValue() - 1); // Calendar hes months from 0-11, we keep in 1 - 12
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, this.weekNumber);
        calendar.set(
                Calendar.DAY_OF_WEEK,
                (this.dayOfWeek.getValue() == 7
                        ? 1
                        : this.dayOfWeek.getValue()
                                + 1)); // Calendar keeps days of week starting from 1 - 7  Sunday
        // we keep from 1 - 7 starting from Monday
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId())
                .toLocalDate();
    }

    @Override
    public String toString() {
        return "Holiday{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", dayOfWeek='"
                + dayOfWeek
                + '\''
                + ", weekNumber='"
                + weekNumber
                + '\''
                + ", month='"
                + month
                + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dayOfWeek, weekNumber, month);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FloatingDateHoliday holiday = (FloatingDateHoliday) object;
        return Objects.equals(id, holiday.id)
                && name.equals(holiday.name)
                && dayOfWeek.equals(holiday.dayOfWeek)
                && weekNumber.equals(holiday.weekNumber)
                && month.equals(holiday.month);
    }
}
