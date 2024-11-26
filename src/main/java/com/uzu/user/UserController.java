package com.uzu.user;

import com.uzu.user.dto.UserDTO;
import com.uzu.user.dto.UserRequest;
import com.uzu.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "User Operation Management")
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Save a user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update a user by id")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserDTO updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/all")
    @Operation(summary = "Save all users")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> saveAllUsers(@RequestBody @Valid List<UserRequest> userRequest) {
        return userService.saveAllUsers(userRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Delete a user by id")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(String.format("User deleted Successfully with id.", id));
    }
}
