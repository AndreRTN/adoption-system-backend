package com.challenge.adoption.common.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimalUpdateStatusParam {
    @NotNull
    @NotBlank
    private String status;
}
