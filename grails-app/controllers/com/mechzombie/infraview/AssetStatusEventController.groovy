package com.mechzombie.infraview

class AssetStatusEventController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    def index() { }
    
    def delete() {
        println("delete params = ${params}")
        def deleted = AssetStatusEvent.get(params["id"])
        deleted.delete(flush: true)
        render "BLEAH!"
        
    }
    
    def saveToAssetPage() {
        println("save status params = ${params}")
        def theAsset = Asset.get(params["asset.id"])
        def statusEvent = new AssetStatusEvent(params)
        
        statusEvent.asset = theAsset
        statusEvent.validate()
        println("status event errors = " + statusEvent.errors)
        statusEvent.save()
        theAsset.statusHistory.add(statusEvent)
        theAsset.save(flush:true)
        //statusEvent.save(flush: true)
        println "newStatusEvent = ${statusEvent.ident()}"
        render "status event = ${statusEvent.ident()}"
        //[assetStatusEvent: statusEvent] //${statusEvent.ident()}"
        
    }
}
