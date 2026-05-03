package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.Status;
import lombok.Data;

@Data
public class ApplicabilityResponseDTO {
    private Long id;

    private String featureName;
    private String vehicleName;
    private String marketName;

    private Status status;
}
