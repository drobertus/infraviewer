package com.mechzombie.infraview

class Enterprise {

    String name
    Date activeDate
    Location location
    boolean hasAddress
    static hasMany = [assetClasses: AssetClass, users: User ]


    static mapping = {
    }

    static constraints = {
        name( blank: false, nullable: false, unique: true )
        location( blank:true, nullable: true)
        //activeDate(blank: true, defaultValue:

    }
}
