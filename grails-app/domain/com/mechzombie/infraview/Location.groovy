package com.mechzombie.infraview

class Location {

    String locationNotes
    def centroidLat
    def centroidLon
    Address address
    Geometry geometry
    
    static constraints = {
        locationNotes blank:true, nullable: true
        geometry blank: true, nullable: true
        address blank: true, nullable: true
        centroidLat blank: true, nullable: true
        centroidLon blank: true, nullable: true
    }
    
    def hasAddress() {
        return (!address == null)
    }
    
    /**
    *This returns a String formatted for list display
    */
    def getLocationString() {
        if(hasAddress()) {
            return address.getFormattedAddress()
        }
        if(centroidLat && centroidLon){
            return "" + centroidLat + "/" + centroidLon
        }
        if(geometry){
            return "Geo"
        }
        return "NA"
    }
    
}
