package com.mechzombie.infraview

class Geometry {

    enum GeometryType { POINT, LINE, AREA }

    GeometryType type
    List<double[]> points

    static mapping = {
    }

    static constraints = {
        type()
    }
}
