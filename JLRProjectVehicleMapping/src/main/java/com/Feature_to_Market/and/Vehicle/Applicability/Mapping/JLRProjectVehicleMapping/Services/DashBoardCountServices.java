package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.DashboardCountDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.CountForApplicability;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.CountForFeature;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.CountForMarket;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.CountForVehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardCountServices {
    private final CountForFeature countForFeature;
    private final CountForVehicle countForVehicle;
    private final CountForMarket countForMarket;
    private final CountForApplicability countForApplicability;

    public DashboardCountDTO getDashBoardCount(){
         long featureCount = countForFeature.count();
         long vehicleCount = countForVehicle.count();
         long marketCount = countForMarket.count();
         long applicabilityCount = countForApplicability.count();

         return new DashboardCountDTO(
                 featureCount,
                 vehicleCount,
                 marketCount,
                 applicabilityCount
         );

    }
}
