package com.mechzombie.infraview

import spock.lang.*
import static org.junit.Assert.assertEquals
/**
 *
 */
class AssetIntegrationTestSpec extends Specification {

    def theId
    def lastAssetStatus

    
    def setup() {
        def testAsset
        def nonSelectedAsset
    
        //println("runnig asset integ setup")
        //TODO: there is one asset class loaded by default in the setup data
        //this test is dependent on that.  Stabilize that dependency if not found
        testAsset = new Asset(assetClass: AssetClass.load(1), 
            externalId: 'externalId', 
            location: new Location(hasAddress: false).save())
        
        
        nonSelectedAsset = new Asset(assetClass: AssetClass.load(1),
            externalId: 'ext2',
            location: new Location(hasAddress: false).save())       
        testAsset.save(flush: true)
        //println("testAsset id in setup= ${testAsset.id}")
        nonSelectedAsset.save(flush:true)
        
        //println("asset settup the id = ${testAsset.id}")
        //println("alt asset settup the id = ${nonSelectedAsset.id}")
        //assert (testAsset.id > 0)
        def y = 5
        while ( y-- > 0 ) {
            def eventDate = new Date() - (y * 10)
            def statusEvent = new AssetStatusEvent(asset: nonSelectedAsset, statusDate: eventDate,
                status: y, eventType: AssetStatusEventType.Inspection).save()
            //println "status event (shouldnt appear)= ${statusEvent.id} ${statusEvent.statusDate} ${statusEvent.asset.id}  "
            nonSelectedAsset.addToStatusHistory(statusEvent) //getStatusHistory().add(statusEvent)//.save(flush:true)
        }
        nonSelectedAsset.save(flush:true)
        y = 5
        while ( y-- > 0 ) {
            def eventDate = new Date() - (y * 10)
            def statusEvent = new AssetStatusEvent(asset: testAsset, statusDate: eventDate,
                status: y, eventType: AssetStatusEventType.Inspection).save()
            //println "status event on testAsset= ${statusEvent.id} ${statusEvent.statusDate} ${statusEvent.asset.id}  "
            testAsset.addToStatusHistory(statusEvent)//.save(flush:true)
            lastAssetStatus = statusEvent.id
        }
        testAsset.save(flush:true)
        //println("testAsset.statusHistory.size() = ${testAsset.statusHistory.size()}")
        //println("nonSelectedAsset.statusHistory.size() = ${nonSelectedAsset.statusHistory.size()}")
        
        theId = testAsset.id
        //println( "AssetStatus count= ${AssetStatusEvent.count()}")
    }

    def cleanup() {
    }

    void "test get most recent status"() {
        when:
        
            def anAsset = Asset.get(theId)
            def mostRecent = anAsset.findMostRecentStatusEvent()
            
           // println("testAsset.statusHistory.size() in test= ${anAsset.statusHistory.size()}")
        then:
           // println "mostRecent = ${mostRecent.id}"
            assertEquals(lastAssetStatus, mostRecent.id)            
    }
    
}
