package com.mechzombie.infraview

class AssetClass {

    String name
    String description
    Double statusValueNew
    Double statusValueReplace
    Double statusValueDestroyed
    Double expectedLifeSpanYears
    Double standardInspectionSpanYears
    Enterprise enterprise
    static hasMany = [assets: Asset]

    static constraints = {
        name( blank: false, nullable: false)
        description (blank: true, nullable: true)
        enterprise (nullable: false, blank: false)
        statusValueNew(min: 1.0d, blank: false)
        statusValueDestroyed(min: 0.0d, blank: false)
        expectedLifeSpanYears(min:0.01d, blank: false, nullable:false)
    }
}
