package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.MarketDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.MarketTable;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.DuplicateRecordFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception.ResourceNotFoundException;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.MarketRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketServices {

    private final MarketRepo marketRepo;
    private final ModelMapper mapper;

    public @Nullable List<MarketDTO> getAllMarketInfo() {
        List<MarketTable> marketTableList = marketRepo.findAll();
        return marketTableList
                .stream()
                .map(marketTable -> mapper.map(marketTable,MarketDTO.class))
                .toList();
    }

    public @Nullable MarketDTO getMarketInfoById(Long id) {
        if(!marketRepo.existsById(id)){
            throw new ResourceNotFoundException("Market details not exists with id: " + id);
        }
         return mapper.map(marketRepo.findById(id).orElse(null),MarketDTO.class);
    }

    public @Nullable MarketDTO createMarketInfo(MarketDTO marketDTO) {
        if(marketRepo.existsByNameAndRegionIgnoreCase(marketDTO.getName() ,marketDTO.getRegion())){
            throw new DuplicateRecordFoundException(
                    "Market already exists with name '" + marketDTO.getName() +
                            "' and region '" + marketDTO.getRegion() + "'"
            );
        }
        MarketTable marketTable = mapper.map(marketDTO,MarketTable.class);
        MarketTable saveMarket = marketRepo.save(marketTable);
        return mapper.map(saveMarket,MarketDTO.class);
    }

    public boolean deleteMarket(Long id) {
        if(!marketRepo.existsById(id)){
            throw new ResourceNotFoundException("Maret not found with id: "+ id);
        }
        marketRepo.deleteById(id);
        return true;
    }
}
