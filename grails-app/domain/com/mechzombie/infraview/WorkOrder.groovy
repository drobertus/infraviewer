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
    String workType
    AssetClass assetClass
    Asset asset
    String status
    
    static hasMany = [assignedStaff: InfraUser]
    
    static constraints = {
        workType inList: AssetStatusEventType.values()*.toString(), nullable: false, blank:true 
        status inList: WorkOrderStatusType.values()*.toString(), nullable: false, blank:true 
        asset nullable:true, blank: true
        assetClass nullable:false, blank: false
        notes nullable:true, blank:true
        projectManager nullable: true, blank: true
        certifiedCompleteBy nullable: true, blank: true
        completedDate nullable: true, blank: true
        
        
    }
}
