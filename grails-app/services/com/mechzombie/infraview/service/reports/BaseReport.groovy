/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview.service.reports

import com.mechzombie.infraview.Report
import com.mechzombie.infraview.ReportType

/**
 *
 * @author David
 */
abstract class BaseReport {
	
    def theType
    
    BaseReport(ReportType theType) {
        this.theType = theType
    }
    
    abstract void writeReport(File destination, Report params) 
    
    
    ReportType getType() {
        return theType
    }
    
    
    
}

