package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Controllers;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityRequestDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.ApplicabilityResponseDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services.ApplicabilityServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "Applicability")
@RequiredArgsConstructor
public class ApplicabilityAPI {
    private final ApplicabilityServices applicabilityServices;

    //This API call is used for create a new applicability info
    @PostMapping
    public ResponseEntity<ApplicabilityRequestDTO> createApplicability (@RequestBody ApplicabilityRequestDTO DTO){
        return ResponseEntity.ok(applicabilityServices.createApplicability(DTO));
    }
    //This API call is used for get all applicability info
    @GetMapping
    public Page<ApplicabilityResponseDTO> getApplicability(
            @RequestParam(required = false) Long featureId,
            @RequestParam(required = false) Long vehicleId,
            @RequestParam(required = false) Long marketId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return applicabilityServices.getApplicability(
                featureId, vehicleId, marketId,
                page, size, sortBy, sortDir);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ApplicabilityResponseDTO> getApplicabilityById(@PathVariable (name = "id",required = true)Long id){
        return ResponseEntity.ok(applicabilityServices.getApplicabilityById(id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> deleteApplicabilityById (@PathVariable (name = "id") Long id){
        boolean ifExits = applicabilityServices.deleteApplicabilityById(id);
        if(ifExits){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }
}
