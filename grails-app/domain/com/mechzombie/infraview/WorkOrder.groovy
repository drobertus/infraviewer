package com.mechzombie.infraview

class WorkOrder {

    Enterprise enterprise    
    Location location
    Date reportedDate
    Date scheduledWorkDate
    String reportedBy
    String notes
    InfraUser projectManager
    String workOrderId
    Date completedDate    
    InfraUser certifiedCompleteBy
    AssetStatusEventType workType
    AssetClass assetClass
    Asset asset
    WorkOrderStatusType status
    StoredFileSet imageSet
    
    static hasMany = [assignedStaff: InfraUser]
    
    static constraints = {
        workType nullable: false, blank:true 
        status nullable: false, blank:true 
        asset nullable:true, blank: true
        assetClass nullable:false, blank: false
        notes nullable:true, blank:true
        projectManager nullable: true, blank: true
        certifiedCompleteBy nullable: true, blank: true
        completedDate nullable: true, blank: true
        scheduledWorkDate nullable: true, blank: true
        workOrderId unique: 'assetClass', nullable: false, blank: false
        imageSet nullable: true, blank:true
        location nullable: true, blank: true
        
    }
}
