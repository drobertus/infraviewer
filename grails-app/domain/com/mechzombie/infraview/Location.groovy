package com.mechzombie.infraview

class Location {

    String locationNotes
    Double centroidLat
    Double centroidLon
    Address address
    Geometry geometry
    boolean hasAddress
    
    static constraints = {
        locationNotes blank:true, nullable: true
        geometry blank: true, nullable: true
        address blank: true, nullable: true
        
        centroidLat (blank:true, nullable: true, validator: { value ->
                if (value && (value < -90.0 || value > 90.0)) return ["Latitude invalid"] }
            )
        centroidLon (blank:true, nullable: true, validator: { value ->
                if (    value && (value < -180.0 || value > 180.0)) return ["Longitude invalid"] }
            )
      
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
