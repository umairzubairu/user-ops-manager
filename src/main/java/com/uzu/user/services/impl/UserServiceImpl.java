package com.uzu.user.services.impl;

import com.uzu.user.dtos.UserDTO;
import com.uzu.user.dtos.UserRequest;
import com.uzu.user.entities.User;
import com.uzu.user.exceptions.DuplicateException;
import com.uzu.user.exceptions.NotFoundException;
import com.uzu.user.repositories.UserRepository;
import com.uzu.user.services.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final int BATCH_SIZE = 1000;

    @Override
    public UserDTO save(UserRequest userRequest) {
        userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(userRequest.getFirstName(), userRequest.getLastName()).ifPresent(user -> {
            throw new DuplicateException(String.format("User with First Name %s and Last Name %s already exist.", user.getFirstName(), user.getLastName()));
        });

        User user = UserDTO.userRequestToEntity(userRequest);
        log.info("Going to save the User {}", userRequest);
        user = userRepository.save(user);
        log.info("Successfully Saved the User {}", user);
        return UserDTO.toDto(user);
    }


    @Override
    public List<UserDTO> saveAllUsers(List<UserRequest> userRequests) {
        // Combining firstName and lastName into fullName to check for duplicates condition
        // the reason why each object was not pass in the save method to save the too many DB calls.
        List<String> fullNames = userRequests.stream().map(user -> (user.getFirstName() + " " + user.getLastName())).collect(Collectors.toList());

        // Checking for existing users with full names in a single query
        List<User> existingUsers = userRepository.findByFullNameInIgnoreCase(fullNames);
        if (!existingUsers.isEmpty()) {
            throw new DuplicateException("Some users already exist");
        }
        // Convert UserRequest to User entities
        List<User> usersToSave = userRequests.stream().map(UserDTO::userRequestToEntity).collect(Collectors.toList());

        // Save in batches
        List<User> savedUsers = new ArrayList<>();
        for (int i = 0; i < usersToSave.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, usersToSave.size());
            List<User> batch = usersToSave.subList(i, endIndex);
            savedUsers.addAll(userRepository.saveAll(batch));
        }

        // Converting saved Users back to DTOs
        return savedUsers.stream().map(UserDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        return UserDTO.toDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Order.asc("lastName").ignoreCase())).stream().map(UserDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));
        user.setDeleted(true);
        userRepository.save(user);
        log.info("Soft deleted the user with id {}", id);
        // above logic is for soft delete and for hard delete we can use below option.
        // userRepository.delete(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserRequest userRequest) {
        // Fetch the existing user by ID or throw an exception if not found
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s does not exist.", id)));

        // Check for duplicate user
        userRepository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(userRequest.getFirstName(), userRequest.getLastName()).ifPresent(user -> {
            if (!user.getId().equals(id)) {
                throw new DuplicateException(String.format("User with First Name %s and Last Name %s already exists.", user.getFirstName(), user.getLastName()));
            }
        });

        // Map the request fields to the existing user object
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());

        // Save updated user in the repository
        User updatedUser = userRepository.save(existingUser);

        // Return the updated user as a DTO
        return UserDTO.toDto(updatedUser);
    }

}
