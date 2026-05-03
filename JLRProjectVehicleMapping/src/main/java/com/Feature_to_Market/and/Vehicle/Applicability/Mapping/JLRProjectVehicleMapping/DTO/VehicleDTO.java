package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VehicleDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Platform is required")
    @Size(max = 100, message = "Platform cannot exceed 100 characters")
    private String platform;

    @NotBlank(message = "Variant is required")
    @Size(max = 100, message = "Variant cannot exceed 100 characters")
    private String variant;

    private LocalDateTime createdAt;
}