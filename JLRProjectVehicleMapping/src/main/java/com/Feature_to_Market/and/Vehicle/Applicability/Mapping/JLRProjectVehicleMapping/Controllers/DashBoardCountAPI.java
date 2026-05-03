package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Controllers;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.DashboardCountDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services.DashBoardCountServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("dashboard/count")
@RequiredArgsConstructor
public class DashBoardCountAPI {
    private final DashBoardCountServices dashBoardCountServices;
    @GetMapping
    public DashboardCountDTO getDashBoardCount(){
        return (dashBoardCountServices.getDashBoardCount());
    }
}
