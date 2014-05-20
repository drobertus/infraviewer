package com.mechzombie.infraview.service

import com.mechzombie.infraview.*
import grails.transaction.Transactional

//@Transactional
class ReportService {

    static transactional = true
    def grailsApplication
    
    def runReport(Report reportToBuild) {
        println("Running!")
        def folderPath = grailsApplication.config.infraview.report.root_folder
        println("param path= ${folderPath}" )
        File reportFolder = new File( folderPath + 
            reportToBuild.enterprise.id + "/")// + reportToBuild.title + ".json")
        
        if (!reportFolder.exists()) {
            reportFolder.mkdirs()
        }
        File report = new File(reportFolder, reportToBuild.title + ".json")
        
        report << "This is a report"


    }
}
