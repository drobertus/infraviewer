package com.mechzombie.infraview

import grails.test.mixin.TestFor
import spock.lang.Specification

//import com.vividsolutions.jts.geom.Point
//import com.vividsolutions.jts.geom.GeometryFactory
//import com.vividsolutions.jts.geom.Coordinate
//import org.hibernatespatial.criterion.SpatialRestrictions
//import com.vividsolutions.jts.io.WKTReader
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Location)
class LocationSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
//    Location.withNewTransaction {
//            Location p = new Location(hasAddress: false)
//            p.description = 'Desc'
//            p.centroid = geometryFactory.createPoint(new Coordinate(5d, 5d))
//            p.save(failOnError: true, flush: true)
//   }
//
//   Location.withNewTransaction {
//             println(Location.createCriteria()
//                     .add(SpatialRestrictions.within("centroid", reader.read('POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))')))
//                     .list())
//
//             def filter = reader.read('POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))')
//             Query q = Place.createQuery("from Place where within(centroid, ?) = true")
//             q.setParameter(0, filter, GeometryUserType.TYPE)
//             println (q.list())
//   }
    }
}
