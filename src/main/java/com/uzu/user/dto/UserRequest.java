package com.uzu.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "First name is a mandatory field.")
    private String firstName;
    @NotBlank(message = "Last name is a mandatory field.")
    private String lastName;
}
