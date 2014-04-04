package com.mechzombie.infraview

class Asset {

    def depreciationCalculatorService
    def maintenanceUtilityService
    
    String externalId
    String description
    String notes
    Location location
    AssetClass assetClass
    
    SortedSet statusHistory 
    static hasMany = [statusHistory: AssetStatusEvent]

        //TODO: make the assetStatusEvent history display in date sorted order
    static mapping = {
        
    } 
    
    static constraints = {
        description blank:true, nullable: true
        notes blank:true, nullable: true
        externalId unique: 'assetClass' 
        statusHistory nullable: true
    }
    
//    def getStatusHistory() {
//        statusHistory = AssetStatusEvent.findAllByAsset(this, [sort: "statusDate", order: "desc"])
//    }
    
    AssetStatusEvent findMostRecentStatusEvent() {
        if (statusHistory && statusHistory.size() > 0) {
           return statusHistory.sort{-it.statusDate.getTime()}[0] 
        }
        return null
//        
//        def criteria = AssetStatusEvent.createCriteria()
//        def mostRecentStatus =  criteria.get(){
//            asset{
//                idEq(ident())
//            }
//            order("statusDate", "desc")
//            maxResults 1
//        }
//        return mostRecentStatus
    }
    
    def getProjectedStatus() {
        return depreciationCalculatorService.estimateCurrentStatus(this)
    }
    
    def getNextProjectedMaintenance() {
        
        return maintenanceUtilityService.getNextProjectedMaintenance(this)
        
//        if (!statusHistory || statusHistory.size() == 0) {
//            return ""
//        }
//        // look back through the status event history
//        // for the most recent maintenance 
//        //  if found then add the maintenance interval from the AssetClass
//        //  if none found then add the maintenance interval from the installation
//        // if the calculated date is before today then return today? 
//        def nextMaintenance = new Date()
//        statusHistory.each() {
//            def eventType = AssetStatusEventType.valueOf(it.eventType)
//            if (eventType.equals(AssetStatusEventType.Maintenance ||
//                eventType.equals(AssetStatusEventType.Installation))){
//                def possible = it.statusDate + assetClass.standardMaintenanceInterval * (365.25)
//                if (possible > nextMaintenance) {
//                    return possible
//                }                
//            }
//        }
//        return nextMaintenance
    }
    
    def getNextProjectedInspection() {
        return maintenanceUtilityService.getNextProjectedInspection(this)
        // look back through the status event history
        // for the most recent inspection 
        //  if found then add the inspection interval from the AssetClass
        //  if none found then add the inspection interval from the installation
        // if the calculated date is before today then return today?
    }
}
