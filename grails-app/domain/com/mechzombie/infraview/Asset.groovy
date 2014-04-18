package com.mechzombie.infraview

class Asset {

    def depreciationCalculatorService
    def maintenanceUtilityService
    
    String externalId
    String description
    String notes
    Location location
    AssetClass assetClass
    
    static hasMany = [statusHistory: AssetStatusEvent]

        //TODO: make the assetStatusEvent history display in date sorted order
    static mapping = {
        
    } 
    
    static constraints = {
        description blank:true, nullable: true
        notes blank:true, nullable: true
        externalId unique: 'assetClass' 
        //statusHistory nullable: true
    }
    
    def getSortedStatusHistory() {
        statusHistory = AssetStatusEvent.findAllByAsset(this, [sort: "statusDate", order: "desc"])
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
    
    def getProjectedStatus() {
        return depreciationCalculatorService.estimateCurrentStatus(this)
    }
    
    def getNextProjectedMaintenance() {        
        return maintenanceUtilityService.getNextProjectedMaintenance(this)
    }
    
    def getNextProjectedInspection() {
        return maintenanceUtilityService.getNextProjectedInspection(this)
    }
}
