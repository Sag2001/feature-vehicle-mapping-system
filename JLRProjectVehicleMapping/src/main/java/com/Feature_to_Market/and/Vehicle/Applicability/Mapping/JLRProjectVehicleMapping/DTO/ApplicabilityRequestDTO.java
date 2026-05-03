package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicabilityRequestDTO {

    private Long featureId;
    private Long vehicleModelId;
    private Long marketId;
    private String status;
}
