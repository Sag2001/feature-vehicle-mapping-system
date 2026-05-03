package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.FeatureTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountForFeature extends JpaRepository<FeatureTable,Long> {
}
