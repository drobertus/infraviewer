package com.mechzombie.infraview

class Location {

    String locationNotes
    Address address
    Geometry geometry

    static constraints = {
        locationNotes blank:true, nullable: true
        geometry blank: true, nullable: true
        address blank: true, nullable: true
    }
    
}
