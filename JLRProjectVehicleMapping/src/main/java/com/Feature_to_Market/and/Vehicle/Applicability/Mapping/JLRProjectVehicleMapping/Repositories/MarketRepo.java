package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.MarketTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepo extends JpaRepository<MarketTable,Long> {
    boolean existsByNameAndRegionIgnoreCase (String name, String region);
}
