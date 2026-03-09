package com.strucify.airBnb.advice;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ApiError {
    private String message;
    private HttpStatus status;
    private Map<String, List<String>> subErrors;
}
