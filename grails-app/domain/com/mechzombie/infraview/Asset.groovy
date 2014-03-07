package com.mechzombie.infraview

class Asset {

    String externalId
    String description
    String notes
    Location location
    AssetClass assetClass
    static hasMany = [statusHistory: AssetStatus]

    static constraints = {
        description blank:true, nullable: true
        notes blank:true, nullable: true
        
    }
}
