package com.mechzombie.infraview.service.reports

import grails.transaction.Transactional
import groovy.xml.MarkupBuilder
import com.mechzombie.infraview.*

//@Transactional
class AssetReplacementProjectionService extends BaseReport {

    static transactional = true
    
    AssetReplacementProjectionService() {
        super(ReportType.AssetReplacementProjection)
    }
    
    void writeReport(File rptFile, Report params) {
        
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        println(" we are writing to file " )
       xml.assetReport() {           
           reportMetaData( runDate: new Date(),  enterprise: params.enterprise.name, title: params.title) {}           
            dateRange(startDate:params.startDate , endDate: params.endDate, year: 2006) {}
            assetTypesList(count: params.reportedAssetClasses.size()) {
               params.reportedAssetClasses.each() { assetClass ->
                    asset(name: assetClass.name){}
                }
            }
       }
        
        rptFile << writer.toString()
    }
}
