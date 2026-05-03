package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.ApplicabilityMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicabilityMappingRepo extends JpaRepository<ApplicabilityMapping , Long> {
    boolean existsByFeature_IdAndVehicleModel_IdAndMarket_Id(
            Long featureId,
            Long vehicleId,
            Long marketId
    );
    Page<ApplicabilityMapping> findByFeature_Id(Long featureId, Pageable pageable);
    Page<ApplicabilityMapping> findByVehicleModel_Id(Long vehicleId, Pageable pageable);
    Page<ApplicabilityMapping> findByMarket_Id(Long marketId, Pageable pageable);
}

