package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.ApplicabilityMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountForApplicability extends JpaRepository<ApplicabilityMapping,Long> {
}
