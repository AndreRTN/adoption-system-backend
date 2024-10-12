package com.challenge.adoption.common.param;

import com.challenge.adoption.type.AdoptionStatus;
import com.challenge.adoption.type.AnimalCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class AnimalRequestParam {

    @NotNull
    @Size(min=2, max=30)
    private String name;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String urlImage;

    @NotNull
    private AnimalCategory category;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotNull
    private AdoptionStatus status;

}
