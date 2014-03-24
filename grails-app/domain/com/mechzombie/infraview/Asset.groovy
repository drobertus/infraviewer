package com.mechzombie.infraview

class Asset {

    String externalId
    String description
    String notes
    Location location
    AssetClass assetClass
    
    static hasMany = [statusHistory: AssetStatusEvent]

    static constraints = {
        description blank:true, nullable: true
        notes blank:true, nullable: true
    
    }
    
    AssetStatusEvent findMostRecentStatusEvent() {
                   
        def criteria = AssetStatusEvent.createCriteria()
        def mostRecentStatus =  criteria.get(){
            asset{
                idEq(ident())
            }
            order("statusDate", "desc")
            maxResults 1
        }
        return mostRecentStatus
    }
    
    Double getCurrentProjectedStatus() {
        null
    }
}
