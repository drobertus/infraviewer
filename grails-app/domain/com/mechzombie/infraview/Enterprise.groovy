package com.mechzombie.infraview

class Enterprise {

    String name
    Date activeDate
    Location location
    static hasMany = [assetClasses: AssetClass, users: User ]    
    
    static mapping = {
    }

    static constraints = {
        name( blank: false, nullable: false, unique: true )
    }
}
