package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Controllers;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.FeatureDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services.FeatureServices;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/feature")
public class FeatureAPI {

    private final FeatureServices featureServices;

    public FeatureAPI(FeatureServices featureServices) {
        this.featureServices = featureServices;
    }

    //Feature API CAll
    //This calls is for to getting features
    @GetMapping
    public ResponseEntity<List<FeatureDTO>> getFeatureDetails() {
        return ResponseEntity.ok(featureServices.getFeatureDetails());
    }

    //This call is for to get the particular feature by its id
    @GetMapping(path = "{id}")
    public ResponseEntity<FeatureDTO> getFeatureById(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok(featureServices.getFeatureById(id));
    }

    //This call is used for add feature in the list

    @PostMapping
    public ResponseEntity<FeatureDTO> createFeature(@RequestBody FeatureDTO featureDTO) {
        return ResponseEntity.ok(featureServices.createFeature(featureDTO));
    }

    //This API call is for delete the feature which is not in used

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteFeature(@PathVariable Long id) {

        try {
            boolean deleted = featureServices.deleteFeature(id);

            if (deleted) {
                return ResponseEntity.ok("Feature deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Feature not found");
            }

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete feature because it is used in mapping");
        }
    }
}
