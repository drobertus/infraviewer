package com.mechzombie.infraview

class Enterprise {

    String name
    Date activeDate
    Location location
    Enterprise parentEnterprise
    static hasMany = [assetClasses: AssetClass, users: User, childEnterprises: Enterprise ]    
    
    
    static mapping = {
    }

    static constraints = {
        name( blank: false, nullable: false, unique: true )
        parentEnterprise blank:true, nullable: true
    }
	
}
