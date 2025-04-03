package com.example.dreamshops.controller;

import com.example.dreamshops.dto.UserDto;
import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.request.CreateUserRequest;
import com.example.dreamshops.request.UserUpdateRequest;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        // Logic to get user by ID
        try {
            User userAuth = userService.getAuthenticatedUser();
            User user = userService.getUserById(userId);
            if (userAuth == null || (!user.getId().equals(userAuth.getId()))) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(ApiResponse.builder()
                                .message("You are not authorized to view this user")
                                .data(null)
                                .build());
            }
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.builder().
                    message("Success").
                    data(userDto).
                    build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        // Logic to create user
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("User created successfully")
                    .data(userDto)
                    .build());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        // Logic to update user
        try {
            User userAuth = userService.getAuthenticatedUser();
            User user = userService.updateUser(request, userId);
            if (!user.getId().equals(userAuth.getId())) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(ApiResponse.builder()
                                .message("You are not authorized to update this user")
                                .data(null)
                                .build());
            }
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("User updated successfully")
                    .data(userDto)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // admin đang k xoas ddc, chuwa fix ddc
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        // Logic to delete user
        try {
            User userAuth = userService.getAuthenticatedUser();
            User user = userService.getUserById(userId);
            if (userAuth == null || (!user.getId().equals(userAuth.getId()))) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(ApiResponse.builder()
                                .message("You are not authorized to delete this user")
                                .data(null)
                                .build());
            }
            userService.deleteUser(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("User deleted successfully")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }


}
