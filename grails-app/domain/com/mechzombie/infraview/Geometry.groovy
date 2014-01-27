package com.mechzombie.infraview

class Geometry {

    enum GeometryType { POINT, LINE, AREA }

    GeometryType type
    //List<double[]> points
    String points 
    
    static mapping = {
        points column: 'wkt_geom', type: 'text'
    }

    static constraints = {
        type()
    }
}
