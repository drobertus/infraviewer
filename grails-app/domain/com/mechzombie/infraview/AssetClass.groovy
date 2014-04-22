package com.mechzombie.infraview

class AssetClass {

    String name
    String description
    Integer statusValueNew
    Integer statusValueReplace
    Integer statusValueDestroyed
    Double expectedLifeSpanYears
    Double standardInspectionInterval
    Double standardMaintenanceInterval
    Enterprise enterprise
    static hasMany = [assets: Asset]
    
    
    // TODO: add configurations for "has/requires [Address, Geometry, Location, external id] 
    // plus possibly notes (per enterprise) about id source
    // for an asset class, types of acceptable Geometry (is it a point, line, area, network?
    //
    boolean assetHasAddress 
    boolean assetHasLocation
    Geometry.GeometryType assetGeometryType
    String assetExternalIdSource
    
    boolean organic
    
    
    
    static constraints = {
        name( blank: false, nullable: false)
        description (blank: true, nullable: true)
        enterprise (nullable: false, blank: false)
        statusValueNew(min: 1, blank: false)
        statusValueDestroyed(min: 0, blank: false)
        expectedLifeSpanYears(min: 0.01d, blank: false, nullable:false)
        standardMaintenanceInterval(blank: true, nullable: true)
        assetGeometryType blank:true, nullable:true
    }
}
