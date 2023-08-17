package com.pos.service.toolsRentalSystem.payloads.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ToolRequest {

    @NotBlank @NotNull private String code;

    @NotBlank @NotNull private String type;

    @NotNull @NotBlank private String brand;

    @NotNull
    @Min(0)
    private Integer availableAmount;
}
