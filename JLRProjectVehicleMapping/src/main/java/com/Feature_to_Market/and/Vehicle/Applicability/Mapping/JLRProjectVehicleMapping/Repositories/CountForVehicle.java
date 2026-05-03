package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.VehicleTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountForVehicle extends JpaRepository<VehicleTable,Long> {
}
