package com.mechzombie.infraview

class Report {

    String title
    Enterprise enterprise
    Geometry bounds
    ReportType reportType
    Date startDate
    Date endDate 
    Date scheduledRunDate
    String pathToReportFile
    
    static hasMany = [reportedAssetClasses: AssetClass]
    
    static constraints = {
        bounds( blank: true, nullable: true)
        pathToReportFile (blank: true, nullable:true)
    }
}
