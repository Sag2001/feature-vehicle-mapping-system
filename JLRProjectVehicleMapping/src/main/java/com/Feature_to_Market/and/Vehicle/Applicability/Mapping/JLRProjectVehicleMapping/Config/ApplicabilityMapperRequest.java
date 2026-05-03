package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Config;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityRequestDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.ApplicabilityMapping;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component

public class ApplicabilityMapperRequest {

    public ApplicabilityRequestDTO toDTO(ApplicabilityMapping entity) {

        ApplicabilityRequestDTO dto = new ApplicabilityRequestDTO();

        dto.setFeatureId(entity.getFeature().getId());
        dto.setVehicleModelId(entity.getVehicleModel().getId());
        dto.setMarketId(entity.getMarket().getId());
        dto.setStatus(entity.getStatus().name());

        return dto;
    }
}