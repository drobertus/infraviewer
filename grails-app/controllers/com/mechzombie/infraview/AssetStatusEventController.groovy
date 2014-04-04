package com.mechzombie.infraview

class AssetStatusEventController {

    def depreciationCalculatorService
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    def index() { }
    
    def delete() {
        //println("delete params = ${params}")
        def deleted = AssetStatusEvent.get(params["id"])
        def parentAsset = deleted.asset;
        deleted.delete(flush: true)
        
        render(template:"assetStatusEventHistory", 
            model:[ assetInstance: parentAsset])
    }
    
    def saveToAssetPage() {
        //println("save status params = ${params}")
        def parentAsset = Asset.get(params["asset.id"])
        def statusEvent = new AssetStatusEvent(params)
        
        statusEvent.asset = parentAsset
        statusEvent.validate()
        //println("status event errors = " + statusEvent.errors)
        statusEvent.save()
        parentAsset.statusHistory.add(statusEvent)
        parentAsset.save(flush:true)
        
        //println "newStatusEvent = ${statusEvent.ident()}"
                
        render(template:"assetStatusEventHistory", 
            model:[assetInstance: parentAsset])
        
    }
    
}
