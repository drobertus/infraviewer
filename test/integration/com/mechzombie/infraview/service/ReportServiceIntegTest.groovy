/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview.service

import com.mechzombie.infraview.*
import com.mechzombie.infraview.service.reports.*
import spock.lang.*
import java.util.Random
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import groovy.util.XmlSlurper

/**
 *
 * @author David
 */
class ReportServiceIntegTest extends Specification {
	
    def grailsApplication
    def rptsToRun = [:]
    
    def testEnt
    def assetClasses = []
    
    def ReportService reportService = new ReportService()
    
    
    void "test Asset Replacement Projection report"() {
       
        when:
            def replacementRpt = rptsToRun.get(ReportType.AssetReplacementProjection)
            def reportName = reportService.runReport(replacementRpt)
            
            println "reportName= ${reportName}"        
        then:
            
            def theReport = new File(reportName)
            def rptXml = new XmlSlurper().parseText(theReport.getText())
            
            println "theReport= ${theReport.getText()}"
            
            //assertNotNull testEnt
            
            assert rptXml.name() == 'assetReport'
        assert rptXml.reportMetaData.@enterprise == testEnt.name
        assert rptXml.assetTypesList.@count == '1'
            assert rptXml.assetTypesList.asset.@name == 'Sign post'
            println "Should be 'Sign post' ${rptXml.assetTypesList.asset.@name}"
            println "testEnt= ${testEnt.name}"
            println "assetclass count= ${assetClasses.size}"
            assertNotNull reportName
            
            
    }
    
    void "test Asset Replacement History report"() {
        
        
        
    }
    
    void "test Inspection and Maintenance Projection report"() {
        
    }
    
    void "test Inspection and Maintenance History report"() {
        
        when:
            def historyRpt = rptsToRun.get(ReportType.InspectionAndMaintenanceHistory)
            def reportName = reportService.runReport(historyRpt)
            
        
        then:
        
            assertNotNull historyRpt
        
    }
    //TODO test each type of report
    
    def setup() {        
        
        //reportService.assetReplacementProjectionService = new AssetReplacementProjectionService()
                                                             // AssertReplacementProjectionService
        File testRptFolder = new File(grailsApplication.config.infraview.report.root_folder )
        log.info("report folder name = ${testRptFolder.absolutePath}")
        println("report folder name = ${testRptFolder.absolutePath}")
        testRptFolder.mkdirs()
        
        //TODO: add a report of each type  
        //first we need an enterprise
        def activeDt = new Date()
        testEnt = new Enterprise(name: 'ReportTestEnt', activeDate: activeDt, location: new Location().save()).save(flush: true)
      
        def posts = new AssetClass(name:'Sign post', enterprise: testEnt, 
            description: 'metal post',
            statusValueNew: 10.0,
            statusValueReplace: 5.0,
            statusValueDestroyed: 0.0,
            expectedLifeSpanYears: 10.0,
            standardInspectionInterval: 2.0,
            standardMaintenanceInterval: 5.0,
            assetHasAddress: false,
            assetHasLocation: true,
            assetGeometryType: Geometry.GeometryType.POINT,
            organic: false,
            assetClassCategory: "posts").save(flush: true)  
       
        assertNotNull testEnt
        //then we need multiple asset classes
        assetClasses << testEnt.assetClasses
      
        assert assetClasses.size > 0
      
        //now add a few more      
        //and individual assets   
        
//each with a history
        def now = new Date()
        def histStartDate = now - (365 * 20)
        
        def inspectInterval = 365 * 2
        def mainInterval = 365 * 5
        
        posts.assets = []
        
        assert AssetStatusEvent.count() == 0
        def origAssetCount =  Asset.count()
        
        
        (1..10).each{ id ->
            def aPost = new Asset(externalId: "ext-post-${id}", description: "The description for ext-post-${id}",
                inInventory: false,
                location:new Location().save(),
                assetClass: posts
            ).save(flush:true)
            
            def eventDate = histStartDate
            
            //add the installation
            new AssetStatusEvent(
                statusDate: eventDate + getRandomizedOffset() ,
                status: 10.0,
                eventType: AssetStatusEventType.Installation,
                asset: aPost
                ).save(flush: true)


            // add the Inspections
            (1..10).each { inps ->
                eventDate = eventDate + (2 * inspectInterval)
                
                new AssetStatusEvent(
                    statusDate: eventDate + getRandomizedOffset() ,
                    status: 8.0,
                    eventType: AssetStatusEventType.Inspection,
                    asset: aPost
                    ).save(flush: true)
                
            }    

            //reset the event Date
            eventDate = histStartDate
            // add the Maintenance
            (1..5).each { main ->
                
                eventDate = eventDate + (5 * inspectInterval)
                
                new AssetStatusEvent(
                    statusDate: eventDate + getRandomizedOffset() ,
                    status: 9.0,
                    eventType: AssetStatusEventType.Maintenance,
                    asset: aPost
                    ).save(flush: true)
            } 
            
            
            posts.assets << aPost
        
            
        }
        
        posts.save(flush: true)
        
        assert Asset.count() == origAssetCount + 10      
        assert AssetStatusEvent.count() == 160
                
        //then reports of each type
      
        def startDate = new Date()
        def endDate = startDate.plus(365 * 25)
        
        def reportedAssetClasses = []
        reportedAssetClasses << posts
        def hydReplaceRpt = new Report(enterprise: testEnt, title: "Hydrant replacement Projection",
            reportType: ReportType.AssetReplacementProjection ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
    
        def inspMaintRpt = new Report(enterprise: testEnt, title: "Hydrant Maintenance Projection",
            reportType: ReportType.InspectionAndMaintenanceProjection ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
    
        
        rptsToRun.put (ReportType.AssetReplacementProjection, hydReplaceRpt)
        rptsToRun.put (ReportType.InspectionAndMaintenanceProjection, inspMaintRpt)
            // AssetReplacementProjection("Asset Replacement Projection"),
            // 
        endDate = startDate.minus(365 * 25)
        def hydReplaceHist = new Report(enterprise: testEnt, title: "Hydrant replacement History",
            reportType: ReportType.AssetReplacementHistory ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
      
        def inspMainHist = new Report(enterprise: testEnt, title: "Hydrant maintenance History",
            reportType: ReportType.InspectionAndMaintenanceHistory ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
        
        rptsToRun.put (ReportType.AssetReplacementHistory, hydReplaceHist)   
        rptsToRun.put (ReportType.InspectionAndMaintenanceHistory, inspMainHist)   

        
        def hydProjRptOverdue = new Report(enterprise: testEnt, title: "Hydrant replacement overdue",
            reportType: ReportType.AssetReplacementOverdue ,
            startDate: startDate,
            endDate: endDate , 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
      
        def inspMaintOverdue = new Report(enterprise: testEnt, title: "Hydrant maintenance overdue",
            reportType: ReportType.InspectionAndMaintenanceOverdue ,
            startDate: startDate,
            endDate: endDate, 
            scheduledRunDate: startDate,
            reportedAssetClasses: reportedAssetClasses).save(flush: true)
        
        rptsToRun.put (ReportType.AssetReplacementOverdue, hydProjRptOverdue)   
        rptsToRun.put (ReportType.InspectionAndMaintenanceOverdue, inspMaintOverdue)   

        
        
        //InspectionAndMaintenanceOverdue("Current Inspection and Maintenance Overdue"),
        //AssetReplacementOverdue("Current Asset Replacement Overdue"),
        assert rptsToRun.size() == 6
    }
    
    
    def getRandomizedOffset() {
       // 0
        
        int diff = new Random().nextInt(50)
        int direction = new Random().nextInt(100)
        if(direction >= 50) {
            diff = diff * -1
        }
        return diff
    }
    
    def cleanup() {
        
        //delete the report files that were generated
    }
    
}

