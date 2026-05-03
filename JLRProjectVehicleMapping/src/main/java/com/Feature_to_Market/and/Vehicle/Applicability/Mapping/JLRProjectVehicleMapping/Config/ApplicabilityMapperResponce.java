package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Config;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityRequestDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityResponseDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.ApplicabilityMapping;
import org.springframework.stereotype.Component;

@Component
public class ApplicabilityMapperResponce {
    public ApplicabilityResponseDTO toDTO(ApplicabilityMapping entity) {

        ApplicabilityResponseDTO dto = new ApplicabilityResponseDTO();
        dto.setId(entity.getId());
        dto.setFeatureName(entity.getFeature().getName());
        dto.setVehicleName(entity.getVehicleModel().getName());
        dto.setMarketName(entity.getMarket().getName());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}
