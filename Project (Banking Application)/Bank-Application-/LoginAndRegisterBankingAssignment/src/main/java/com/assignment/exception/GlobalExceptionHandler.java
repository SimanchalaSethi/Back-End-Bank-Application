package com.assignment.exception;

import com.assignment.response.dto.ApiResponse;
import com.assignment.response.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    UserResponse response = null;
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<UserResponse> jwtHandler(JwtException jwtException) {
        response = new UserResponse();
        response.setData("Unauthorized");
        response.setResponseCode(HttpStatus.OK.value());
        response.setResponseDescription(jwtException.getMessage());
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
