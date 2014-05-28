package com.mechzombie.infraview.service

import com.mechzombie.infraview.*
import grails.transaction.Transactional
import groovy.json.JsonBuilder

//@Transactional
class ReportService {

    static transactional = true
    def grailsApplication
    
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
        def builder = new JsonBuilder()
        
        def root = builder.people {
            person {
               firstName 'Guillame'
               lastName 'Laforge'
               // Named arguments are valid values for objects too
               address(
                       city: 'Paris',
                       country: 'France',
                       zip: 12345,
               )
               married true
               // a list of values
               conferences 'JavaOne', 'Gr8conf'
           }
        }

        report << builder.toString()
        
        def urlPath  = report.absolutePath.replace("\\", "/")
        
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
