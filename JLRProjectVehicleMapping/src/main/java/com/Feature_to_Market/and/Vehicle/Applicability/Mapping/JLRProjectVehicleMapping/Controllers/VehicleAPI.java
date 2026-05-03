package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Controllers;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.VehicleDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services.VehicleServices;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/vehicle")
@RequiredArgsConstructor
public class VehicleAPI {
    private final VehicleServices vehicleMappingServices;

    // This API is used for getting all vehicle from DB
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicleDeatils (){
        return ResponseEntity.ok(vehicleMappingServices.getAllVehicleDeatils());
    }

    //This API call is used for getting the vehicle by using id
    @GetMapping(path = "{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable (name = "id", required = true) Long id){
        return ResponseEntity.ok(vehicleMappingServices.getVehicleById(id));
    }

    //This API call is used for post new vehicle
    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle (@RequestBody VehicleDTO vehicleDTO){
        return ResponseEntity.ok(vehicleMappingServices.createVehicle(vehicleDTO));
    }

    //This API call is used for delete vehicle details
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {

        try {
            boolean deleted = vehicleMappingServices.deleteVehicle(id);

            if (deleted) {
                return ResponseEntity.ok("Vehicle deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vehicle not found");
            }

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete vehicle because it is used in mapping table");
        }
    }

}
