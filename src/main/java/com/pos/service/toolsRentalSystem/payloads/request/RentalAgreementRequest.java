package com.pos.service.toolsRentalSystem.payloads.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalAgreementRequest {

    @NotBlank
    @NotNull
    private String toolCode;

    @NotBlank
    @NotNull
    private int rentalDayCount;

    @NotBlank
    @NotNull
    private int discountPercent;

    @NotBlank
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
    private LocalDate checkoutDate;

}
