package com.mechzombie.infraview

class Location {

    Address address
    Geometry geometry

    static constraints = {
        geometry blank: true, nullable: true
    }
    
    //return the states list
    def getStatesList() {
        return ['AL', 'CO', 'CA','TX','WY','WA']
    }
}
