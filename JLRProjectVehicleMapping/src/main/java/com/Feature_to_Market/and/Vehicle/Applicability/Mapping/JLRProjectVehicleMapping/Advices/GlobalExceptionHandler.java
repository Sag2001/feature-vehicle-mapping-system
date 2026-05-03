package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Advices;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.DuplicateRecordFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponces<?>> handleExceptionGlobally(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder().
                status(HttpStatus.NOT_FOUND).
                message(exception.getMessage()).
                build();
        return  buildApiErrorResponceEntity(apiError);
    }

    @ExceptionHandler(DuplicateRecordFoundException.class)
    public ResponseEntity<ApiResponces<?>> handleDuplicateRecord(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder().
                status(HttpStatus.CONFLICT).
                message(exception.getMessage()).
                build();
        return  buildApiErrorResponceEntity(apiError);
    }

    private ResponseEntity<ApiResponces<?>> buildApiErrorResponceEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponces<>(apiError),apiError.getStatus());
    }
}


