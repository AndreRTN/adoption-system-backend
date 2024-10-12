package com.challenge.adoption.common.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@NoArgsConstructor
@Data
public class ErrorValidationData {
    private String field;
    private String message;

    public ErrorValidationData(FieldError field) {
        this.field = field.getField();
        this.message = field.getDefaultMessage();
    }
}
