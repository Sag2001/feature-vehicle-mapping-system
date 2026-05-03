package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.FeatureDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.FeatureTable;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.DuplicateRecordFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.FeatureRepo;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class FeatureServices {

    private final FeatureRepo featureRepo;

    private final ModelMapper modelMapper;

    public FeatureServices(FeatureRepo featureRepo, ModelMapper modelMapper) {
        this.featureRepo = featureRepo;
        this.modelMapper = modelMapper;
    }

    public List<FeatureDTO> getFeatureDetails() {
        List<FeatureTable> featureTables = featureRepo.findAll();
        return featureTables.
                stream().
                map(featureTable -> modelMapper.map(featureTable,FeatureDTO.class))
                .toList();
    }

    public FeatureDTO getFeatureById(Long id) {
        if(!featureRepo.existsById(id)){
            throw new ResourceNotFoundException("Feature not exists with id: " + id);
        }
        FeatureTable featureTable = featureRepo.findById(id).orElse(null);
        return modelMapper.map(featureTable,FeatureDTO.class);

    }

    public FeatureDTO createFeature(FeatureDTO featureDTO) {
        if(featureRepo.existsByNameIgnoreCase(featureDTO.getName())){
            throw new DuplicateRecordFoundException("Feature already exists with name: " + featureDTO.getName());
        }
        FeatureTable feature = modelMapper.map(featureDTO,FeatureTable.class);
        FeatureTable saveFeature = featureRepo.save(feature);
        return modelMapper.map(saveFeature,FeatureDTO.class);
    }

    public @Nullable Boolean deleteFeature(Long id) {
        if(!featureRepo.existsById(id)) {
            throw new ResourceNotFoundException("Feature not found with id: "+ id);
        };
            featureRepo.deleteById(id);
        return true;
    }
}
