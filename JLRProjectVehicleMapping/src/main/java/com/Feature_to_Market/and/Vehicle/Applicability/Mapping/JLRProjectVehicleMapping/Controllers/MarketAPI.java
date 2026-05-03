package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Controllers;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.DTO.MarketDTO;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Services.MarketServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/market")
@RequiredArgsConstructor
public class MarketAPI {

    private final MarketServices marketServices;

    //This API call is used for fetching all market info

    @GetMapping
    public ResponseEntity<List<MarketDTO>> getAllMarketInfo (){
        return ResponseEntity.ok(marketServices.getAllMarketInfo());
    }

    // This API call is used for fetching the market details via ID
    @GetMapping (path = "{id}")
    public ResponseEntity<MarketDTO> getMarketInfoById (@PathVariable(name = "id",required = true) Long id){
        return ResponseEntity.ok(marketServices.getMarketInfoById(id));
    }

    //This API call is used for create new market details
    @PostMapping
    public ResponseEntity<MarketDTO> createMarketInfo (@RequestBody MarketDTO marketDTO){
        return ResponseEntity.ok(marketServices.createMarketInfo(marketDTO));
    }

    //This API call is used for delete the market info
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> deleteMarket (@PathVariable (name = "id",required = true) Long id){
        boolean ifExists = marketServices.deleteMarket(id);
        if(ifExists){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }


}
