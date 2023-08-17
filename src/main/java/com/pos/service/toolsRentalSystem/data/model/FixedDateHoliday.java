package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;
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

    @NotNull private Month month;

    @NotNull
    private Boolean isObservedHoliday; // if Sat,then Friday before, if Sunday, then Monday after

    @Builder
    public FixedDateHoliday(String name, Integer day, Month month, Boolean isObservedHoliday) {
        super(name);
        this.day = day;
        this.month = month;
        this.isObservedHoliday = isObservedHoliday;
    }

    @Override
    public LocalDate getDate(int year) {
        int month = this.month.getValue();
        LocalDate date = LocalDate.of(year, month, day);
        if (isObservedHoliday) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return LocalDate.of(year, month, day - 1);
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return LocalDate.of(year, month, day + 1);
            }
        }
        return date;
    }

    @Override
    public String toString() {
        return "Holiday{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", day='"
                + day
                + '\''
                + ", month='"
                + month
                + '\''
                + ", isObservedHoliday='"
                + isObservedHoliday
                + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, day, month, isObservedHoliday);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FixedDateHoliday holiday = (FixedDateHoliday) object;
        return Objects.equals(id, holiday.id)
                && name.equals(holiday.name)
                && day.equals(holiday.day)
                && month.equals(holiday.month)
                && isObservedHoliday.equals(holiday.isObservedHoliday);
    }
}
