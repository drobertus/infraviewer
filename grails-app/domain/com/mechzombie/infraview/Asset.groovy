package com.mechzombie.infraview

class Asset {

    String externalId
    String description
    String notes
    Location location
    AssetClass assetClass
    
    static hasMany = [statusHistory: AssetStatusEvent]

        //TODO: make the assetStatusEvent history display in date sorted order
        
    static constraints = {
        description blank:true, nullable: true
        notes blank:true, nullable: true
        externalId unique: 'assetClass' 
    }
    
    def getSortedStatusHistory() {
        AssetStatusEvent.findAllByAsset(this, [sort: "statusDate", order: "desc"])
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
