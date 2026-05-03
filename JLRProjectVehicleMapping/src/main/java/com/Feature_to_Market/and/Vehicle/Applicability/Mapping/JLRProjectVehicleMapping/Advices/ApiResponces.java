package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponces<T> {
    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponces() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponces(T data) {
        this();
        this.data = data;
    }

    public ApiResponces(ApiError error) {
        this();
        this.error = error;
    }
}
