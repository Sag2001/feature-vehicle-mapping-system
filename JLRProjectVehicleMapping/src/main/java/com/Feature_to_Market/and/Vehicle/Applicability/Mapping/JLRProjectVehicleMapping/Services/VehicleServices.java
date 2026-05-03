package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.VehicleDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.VehicleTable;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.DuplicateRecordFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.VehicleTableRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServices {
    private final VehicleTableRepo  vehicleTableRepo;
    private final ModelMapper modelMapper;

    public List<VehicleDTO> getAllVehicleDeatils() {
        List<VehicleTable> vehicleTableRepos = vehicleTableRepo.findAll();
        return vehicleTableRepos
                .stream()
                .map(vehicleTableRepo1 -> modelMapper.map(vehicleTableRepo1,VehicleDTO.class))
                .toList();
    }

    public @Nullable VehicleDTO getVehicleById(Long id) {
        if(!vehicleTableRepo.existsById(id)){
            throw new ResourceNotFoundException("Vehicle not exists with id: " + id);
        }
        VehicleTable vehicleTable = vehicleTableRepo.findById(id).orElse(null);
        return modelMapper.map(vehicleTable,VehicleDTO.class);
    }

    public @Nullable VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        if (vehicleTableRepo.existsByNameAndPlatformAndVariantIgnoreCase(
                vehicleDTO.getName(),
                vehicleDTO.getPlatform(),
                vehicleDTO.getVariant()
        )) {
            throw new DuplicateRecordFoundException(
                    String.format(
                            "Vehicle already exists with name '%s', platform '%s' and variant '%s'",
                            vehicleDTO.getName(),
                            vehicleDTO.getPlatform(),
                            vehicleDTO.getVariant()
                    )
            );        }
        VehicleTable vehicleTable = modelMapper.map(vehicleDTO,VehicleTable.class);
        VehicleTable saveVehicle = vehicleTableRepo.save(vehicleTable);
        return modelMapper.map(saveVehicle,VehicleDTO.class);
    }

    public boolean deleteVehicle(Long id) {
        if(!vehicleTableRepo.existsById(id)){
            throw new ResourceNotFoundException("Vehicle not found with id: "+ id);
        }
        vehicleTableRepo.deleteById(id);
        return true;
    }
}
