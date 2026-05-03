package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping;

import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Entities.FeatureTable;
import com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Repositories.FeatureRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class JlrProjectVehicleMappingApplicationTests {

    @Autowired
    FeatureRepo featureRepo;

	@Test
	void contextLoads() {
	}

//    @Test
//
//    void testDb(){
//        FeatureTable featureTable = new FeatureTable();
//        featureTable.setName("Auto Parking");
//        featureTable.setCategory("Safety");
//        FeatureTable answer = featureRepo.save(featureTable);
//        assertNotNull(answer.getId(),"ID should be generated");
//        List<FeatureTable> tableList = featureRepo.findAll();
//        assertEquals(1,tableList.size(),"One record should exist");
//    }
}
