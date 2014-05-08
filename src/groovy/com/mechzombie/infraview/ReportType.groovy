/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview

/**
 *
 * @author David
 */
enum ReportType {
    
    AssetReplacementProjection("Asset Replacement Projection"),
    AssetReplacementHistory("Asset Replacement History"),
    InspectionAndMaintenanceProjection("Inspection and Maintenance Projection"),
    InspectionAndMaintenanceHistory("Inspection and Maintenance History"),
    
    private fullTitle
    ReportType(fullTitle) {
        this.fullTitle = fullTitle
    }
    
    def getFullTitle() {
        return fullTitle
    }
}

