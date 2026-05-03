package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MarketDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Region is required")
    @Size(max = 100, message = "Region cannot exceed 100 characters")
    private String region;

    @Size(max = 100, message = "regulations cannot exceed 100 characters")
    private String regulations;

    private LocalDateTime createdAt;
}
