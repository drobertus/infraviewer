package com.mechzombie.infraview

class AssetStatusEvent {

    Date statusDate
    double status
    Asset asset

    static mapping = {
        sort statusDate: "desc"
    }

    static constraints = {
        //statusDate()
        //status()

    }
    
    static findMostRecentStatusEvent(Asset assetId) {
        def criteria = AssetStatusEvent.createCriteria()
        def results =  criteria.get(){
            asset{
                idEq(assetId.ident())
            }
            order("statusDate", "desc")
            maxResults 1
        }
        
        log "alt results = ${results.id} ${results.statusDate} | ${results.asset.id}"
        
        return results
    }
}
