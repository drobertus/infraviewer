package com.mechzombie.infraview

class AssetClass {

    String name
    String description
    Double statusValueNew
    Double statusValueReplace
    Double statusValueDestroyed
    Double expectedLifeSpanYears
    Double standardInspectionInterval
    Double standardMaintenanceInterval
    Enterprise enterprise
    static hasMany = [assets: Asset]
    
    // TODO: add configurations for "has/requires [Address, Geometry, Location, external id] 
    // plus possibly notes (per enterprise) about id source
    // for an asset class, types of acceptable Geometry (is it a point, line, area, network?
    //

    static constraints = {
        name( blank: false, nullable: false)
        description (blank: true, nullable: true)
        enterprise (nullable: false, blank: false)
        statusValueNew(min: 1.0d, blank: false)
        statusValueDestroyed(min: 0.0d, blank: false)
        expectedLifeSpanYears(min:0.01d, blank: false, nullable:false)
    }
}
