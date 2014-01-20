package com.mechzombie.infraview

class AssetStatus {

    Date statusDate
    double status
    Asset asset

    static mapping = {

    }

    static constraints = {
        statusDate()
        status()
    }
}
