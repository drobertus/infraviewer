package com.mechzombie.infraview

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Ignore
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Ignore
@TestFor(Asset)
@Mock([Asset, AssetClass, AssetStatusEvent, Location])
class AssetSpec extends Specification {

    def testAsset
    def nonSelectedAsset
    //def testAssetClass = Mock(AssetClass)
    
    def setup() {
    
        testAsset = new Asset(assetClass: AssetClass.load(1), 
            externalId: 'externalId', 
            location: new Location(hasAddress: false).save())
        
        nonSelectedAsset = new Asset(assetClass: AssetClass.load(1),
            externalId: 'ext2',
            location: new Location(hasAddress: false).save())
        
        
        testAsset.save(flush: true)
        log.info("testAsset id in setup= ${testAsset.id}")
        nonSelectedAsset.save(flush:true)
        
        println("asset settup the id = ${testAsset.id}")
        println("alt asset settup the id = ${nonSelectedAsset.id}")
        assertNotNull(testAsset)
        def y = 5
        while ( y-- > 0 ) {
            def eventDate = new Date() - (y * 10)
            def statusEvent = new AssetStatusEvent(asset: nonSelectedAsset, statusDate: eventDate,
                status: y).save(flush: true)
            println "status event = ${statusEvent.id} ${statusEvent.statusDate} ${statusEvent.asset.id}  "
            nonSelectedAsset.addToStatusHistory(statusEvent)
        }
        y = 5
        while ( y-- > 0 ) {
            def eventDate = new Date() - (y * 10)
            def statusEvent = new AssetStatusEvent(asset: testAsset, statusDate: eventDate,
                status: y).save(flush: true)
            println "status event = ${statusEvent.id} ${statusEvent.statusDate} ${statusEvent.asset.id}  "
            testAsset.addToStatusHistory(statusEvent)
        }
        
        
    }

    def cleanup() {
    }

    void "test get most recent status"() {
        when:
            log.info("testAsset id in test = ${testAsset.id}")
            log.info("nonSelectedAsset id in test = ${nonSelectedAsset.id}")
            def mostRecent = testAsset.findMostRecentStatusEvent()
        then:
            assertNotNull(mostRecent)
    }
}
