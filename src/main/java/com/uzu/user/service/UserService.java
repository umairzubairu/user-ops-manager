package com.uzu.user.service;

import com.uzu.user.dto.UserDTO;
import com.uzu.user.dto.UserRequest;
import java.util.List;

public interface UserService {

    UserDTO save(UserRequest userRequest);

    List<UserDTO> saveAllUsers(List<UserRequest> userRequest);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    void deleteUserById(Long id);

    UserDTO updateUser(Long id, UserRequest userRequest);
}
