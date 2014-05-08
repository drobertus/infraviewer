package com.mechzombie.infraview

class ReportTemplate {

    enum ReportPeriod{DAILY, WEEKLY, MONTHY, BI_MONTHY, QUARTERLY, BI_ANNUAL, ANNUAL}
    
    String title
    Enterprise enterprise    
    Geometry bounds
    ReportType reportType        
    ReportPeriod reportingPeriodicy
    
    static hasMany = [reportedAssetClasses: AssetClass]
    
    
    
    static constraints = {
    }
}
