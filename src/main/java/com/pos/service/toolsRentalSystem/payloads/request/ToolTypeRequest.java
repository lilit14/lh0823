package com.pos.service.toolsRentalSystem.payloads.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ToolTypeRequest {
    @NotNull public Boolean weekendCharge;
    @NotNull public Boolean holidayCharge;
    @NotBlank @NotNull private String name;
    @NotNull private BigDecimal dailyCharge;
    @NotNull private Boolean weekdayCharge;
}
