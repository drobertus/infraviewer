package com.mechzombie.infraview

class Location {

    String locationNotes
    def centroidLat
    def centroidLon
    Address address
    Geometry geometry
    boolean hasAddress
    
    static constraints = {
        locationNotes blank:true, nullable: true
        geometry blank: true, nullable: true
        address blank: true, nullable: true
        centroidLat blank: true, nullable: true
        centroidLon blank: true, nullable: true
    }
    
    /**
    *This returns a String formatted for list display
    */
    def getLocationString() {
        if(hasAddress) {
            return address.getFormattedAddress()
        }
        if(centroidLat && centroidLon){
            return "lat/lng: " + centroidLat + "/" + centroidLon
        }
        if(geometry){
            return "Geo"
        }
        return "NA"
    }
    
}
