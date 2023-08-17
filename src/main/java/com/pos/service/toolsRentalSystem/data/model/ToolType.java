package com.pos.service.toolsRentalSystem.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ToolType {

    @NotBlank
    @NotNull
    @Column(unique = true)
    public String name;

    @NotNull public BigDecimal dailyCharge;
    @NotNull public Boolean weekdayCharge;
    @NotNull public Boolean weekendCharge;
    @NotNull public Boolean holidayCharge;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Override
    public String toString() {
        return "ToolType{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", dailyCharge='"
                + dailyCharge
                + '\''
                + ", weekdayCharge='"
                + weekdayCharge
                + '\''
                + ", weekendCharge='"
                + weekendCharge
                + '\''
                + ", holidayCharge="
                + holidayCharge
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ToolType toolType = (ToolType) object;
        return Objects.equals(id, toolType.id)
                && name.equals(toolType.name)
                && Objects.equals(dailyCharge, toolType.dailyCharge)
                && Objects.equals(weekdayCharge, toolType.weekdayCharge)
                && Objects.equals(weekendCharge, toolType.weekendCharge)
                && Objects.equals(holidayCharge, toolType.holidayCharge);
    }
}
