package com.Feature_to_Market.and.Vehicle.Applicability.Mapping.JLRProjectVehicleMapping.Exception;

public class DuplicateRecordFoundException extends ResourceNotFoundException{
    public DuplicateRecordFoundException (String message){
        super(message);
    }
}
