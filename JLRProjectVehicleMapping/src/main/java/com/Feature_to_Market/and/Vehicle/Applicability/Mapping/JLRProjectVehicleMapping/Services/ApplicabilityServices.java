package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Config.ApplicabilityMapperRequest;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Config.ApplicabilityMapperResponce;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityRequestDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityResponseDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.*;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.DuplicateRecordFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.ApplicabilityMappingRepo;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.FeatureRepo;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.MarketRepo;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.VehicleTableRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Data
@RequiredArgsConstructor
public class ApplicabilityServices {

    private final ApplicabilityMappingRepo applicabilityMappingRepo;
    private final ModelMapper modelMapper;
    private final FeatureRepo featureRepo;
    private final VehicleTableRepo vehicleTableRepo;
    private final MarketRepo marketRepo;
    private final ApplicabilityMapperRequest applicabilityMapper;
    private final ApplicabilityMapperResponce applicabilityMapperResponce;

    public ApplicabilityRequestDTO createApplicability(ApplicabilityRequestDTO AppDTO) {
        FeatureTable feature = featureRepo.findById(AppDTO.getFeatureId())
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found"));

        VehicleTable vehicle = vehicleTableRepo.findById(AppDTO.getFeatureId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        MarketTable market = marketRepo.findById(AppDTO.getMarketId())
                .orElseThrow(() -> new ResourceNotFoundException("Market not found"));

        if (applicabilityMappingRepo.existsByFeature_IdAndVehicleModel_IdAndMarket_Id(
                feature.getId(), vehicle.getId(), market.getId())
        ) {
            throw new DuplicateRecordFoundException(
                    String.format(
                            "Applicability already exists with featureId '%s', vehicleId '%s' and marketId '%s'",
                            feature.getId(),
                            vehicle.getId(),
                            market.getId()
                    )
            );
        }

        ApplicabilityMapping entity = new ApplicabilityMapping();
        entity.setFeature(feature);
        entity.setVehicleModel(vehicle);
        entity.setMarket(market);
        entity.setStatus(Status.valueOf(AppDTO.getStatus()));

        ApplicabilityMapping saved = applicabilityMappingRepo.save(entity);
        return applicabilityMapper.toDTO(saved);


    }

    public Page<ApplicabilityResponseDTO> getApplicability(
            Long featureId,
            Long vehicleId,
            Long marketId,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ApplicabilityMapping> result;

        if (featureId != null) {
            result = applicabilityMappingRepo.findByFeature_Id(featureId, pageable);

        } else if (vehicleId != null) {
            result = applicabilityMappingRepo.findByVehicleModel_Id(vehicleId, pageable);

        } else if (marketId != null) {
            result = applicabilityMappingRepo.findByMarket_Id(marketId, pageable);

        } else {
            result = applicabilityMappingRepo.findAll(pageable);
        }

        return result.map(applicabilityMapperResponce::toDTO);
    }

    public  ApplicabilityResponseDTO getApplicabilityById(Long id) {
        if (!applicabilityMappingRepo.existsById(id)){
            throw new ResourceNotFoundException("No such a applicable market with id : "+ id);
        }
        ApplicabilityMapping applicabilityMapping = applicabilityMappingRepo.
                findById(id).
                orElseThrow((null));
        ApplicabilityResponseDTO applicabilityResponseDTO = applicabilityMapperResponce.toDTO(applicabilityMapping);
        return applicabilityResponseDTO;
    }


    public boolean deleteApplicabilityById(Long id) {
        if (!applicabilityMappingRepo.existsById(id)){
            throw new ResourceNotFoundException("No such a applicable market with id : "+ id);
        }
        applicabilityMappingRepo.deleteById(id);
        return true;
    }
}
