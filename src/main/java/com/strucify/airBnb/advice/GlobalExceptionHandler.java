package com.strucify.airBnb.advice;


import com.strucify.airBnb.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.View;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError= ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        ApiError apiError= ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .message(ex.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, List<String>> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                f -> f.getDefaultMessage() != null ? f.getDefaultMessage() : "Invalid value",
                                Collectors.toList()
                        )
                ));
    ApiError apiError= ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message("Validation Fail")
            .subErrors(fieldErrors)
            .build();
    return buildErrorResponseEntity(apiError);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String rootMessage=ex.getRootCause().getMessage() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String userMessage="Database Operation failed";
        HttpStatus status=HttpStatus.BAD_REQUEST;
        if (rootMessage.contains("uk_") || rootMessage.contains("unique")) {
            userMessage = "A record with this information already exists.";
            status = HttpStatus.CONFLICT; // 409 is the standard for duplicates
        } else if (rootMessage.contains("fk_") || rootMessage.contains("foreign key")) {
            userMessage = "This operation cannot be completed because the record is linked to other data.";
        }
        ApiError apiError = ApiError.builder()
                .status(status)
                .message(userMessage)
                // Optional: Put the raw message in subErrors only for dev environment
                .build();
        return buildErrorResponseEntity(apiError);

    }


    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
