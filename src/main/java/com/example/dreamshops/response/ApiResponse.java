package com.example.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(message, data);
    }
}
