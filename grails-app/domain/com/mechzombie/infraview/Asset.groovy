package com.mechzombie.infraview

class Asset {

    String externalId
    Location location
    AssetClass assetClass
    static hasMany = [statusHistory: AssetStatus]

    static constraints = {
    }
}
