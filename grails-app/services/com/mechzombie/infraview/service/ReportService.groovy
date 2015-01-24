package com.mechzombie.infraview.service

import com.mechzombie.infraview.*
import grails.transaction.Transactional
import groovy.json.JsonBuilder
import groovy.xml.MarkupBuilder

@Transactional
class ReportService {

    static transactional = true
    def grailsApplication
    def assetReplacementProjectionService 
    
    static dateFormat="yyyyMMdd"
    
    def runReport(Report reportToBuild) {
        println("Running!")
        def folderPath = grailsApplication.config.infraview.report.root_folder

        File reportFolder = new File( folderPath + 
            reportToBuild.enterprise.id + File.separator)
        
        if (!reportFolder.exists()) {
            reportFolder.mkdirs()
        }
        
        def fullReportName = getFormattedFileName(reportToBuild) + ".xml"
        File report = new File(reportFolder, fullReportName)
        if (report.exists()){
            report.delete()
        }
        //TODO: switch to MarkupBuilder, write to XML (or direct to HTML?)
       // def writer = new StringWriter()
       // def xml = new MarkupBuilder(writer)
        
        def ent = reportToBuild.enterprise
        def assetClasses = reportToBuild.reportedAssetClasses        
        
        switch ( reportToBuild.reportType ) {
            case ReportType.AssetReplacementProjection:
                assetReplacementProjectionService.writeReport(report, reportToBuild)
                break;
            default :
                println('What are you taling about? ' + reportToBuild.reportType + ' is bullshit, dude.')
            
            
        }
//        xml.records() {
//            car(name:'HSV Maloo', make:'Holden', year:2006) {
//                country('Australia')
//                record(type:'speed', 'Production Pickup Truck with speed of 271kph')
//            }
//            car(name:'P50', make:'Peel', year:1962) {
//                country('Isle of Man')
//                record(type:'size', 'Smallest Street-Legal Car at 99cm wide and 59 kg in weight')
//            }
//            car(name:'Royale', make:'Bugatti', year:1931) {
//                country('France')
//                record(type:'price', 'Most Valuable Car at $15 million')
//            }
//        }
//
//        report << writer.toString()
//        
        def urlPath  = report.absolutePath.replace("\\", "/")
        
        reportToBuild.pathToReportFile = urlPath
        //TODO: why return anything here?
        return urlPath
    }
    
    def getFormattedFileName(theReport) {
        def rptPath = theReport.title + "_" +
        theReport.startDate.format(dateFormat ) + "_to_" + 
        theReport.endDate.format(dateFormat )
            
        rptPath = rptPath.replaceAll(" ", "_")
        return rptPath
    }
}
