package com.example.dreamshops.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LoginRequest {
    @NotBlank // to ensure that the field is not empty
    private String email;
    @NotBlank
    private String password;
}
