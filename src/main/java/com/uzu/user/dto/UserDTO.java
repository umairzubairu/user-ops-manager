package com.uzu.user.dto;

import com.uzu.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "First name is a mandatory field.")
    private String firstName;
    @NotBlank(message = "Last name is a mandatory field.")
    private String lastName;


    public static User toEntity(UserDTO userDTO) {
        return User.builder().firstName(userDTO.getFirstName()).lastName(userDTO.getLastName()).build();
    }

    public static User userRequestToEntity(UserRequest userRequest) {
        return User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).build();
    }

    public static UserDTO toDto(User user) {
        return UserDTO.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName()).build();
    }
}
