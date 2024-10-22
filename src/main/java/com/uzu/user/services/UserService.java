package com.uzu.user.services;

import com.uzu.user.dtos.UserDTO;
import com.uzu.user.dtos.UserRequest;
import java.util.List;

public interface UserService {

    UserDTO save(UserRequest userRequest);

    List<UserDTO> saveAllUsers(List<UserRequest> userRequest);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    void deleteUserById(Long id);

    UserDTO updateUser(Long id, UserRequest userRequest);
}
