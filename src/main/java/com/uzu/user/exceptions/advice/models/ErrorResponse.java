package com.uzu.user.exceptions.advice.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse implements Serializable {
    private String code;
    private String message;

    public static ErrorResponse error(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
