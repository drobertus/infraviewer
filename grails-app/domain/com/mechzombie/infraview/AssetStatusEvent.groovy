package com.mechzombie.infraview

class AssetStatusEvent {

    Date statusDate
    double status
    String eventType
    Asset asset
    
    static mapping = {
        sort statusDate: "desc"
    }
    
    static constraints = {
        eventType inList: AssetStatusEventType.values()*.toString(), nullable: false, blank:true 
    }
}
