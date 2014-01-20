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
    }
}
