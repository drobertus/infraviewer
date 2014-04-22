package com.mechzombie.infraview

class Report {

    String title
    Enterprise enterprise
    Geometry bounds
    ReportType reportType
    Date startDate
    Date endDate 
    
    static hasMany = [reportedAssetClasses: AssetClass]
    
    static constraints = {
        bounds( blank: true, true: false)
    }
}
