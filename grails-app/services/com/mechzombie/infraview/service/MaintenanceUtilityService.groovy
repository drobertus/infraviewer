/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview.service

import com.mechzombie.infraview.Asset
import com.mechzombie.infraview.AssetClass
import com.mechzombie.infraview.AssetStatusEvent
import com.mechzombie.infraview.AssetStatusEventType
/**
 *
 * @author David
 */
class MaintenanceUtilityService {

    def getNextProjectedMaintenance(asset) {
        def statusHistory = asset.statusHistory
        if (!statusHistory || statusHistory.size() == 0) {
            return null
        }
        
        statusHistory = statusHistory.sort{-it.statusDate.getTime()}
        
        // look back through the status event history
        // for the most recent maintenance 
        //  if found then add the maintenance interval from the AssetClass
        //  if none found then add the maintenance interval from the installation
        // if the calculated date is before today then return today? 
        def foundVal
        def nextMaintenance = new Date()
        for(AssetStatusEvent ase : statusHistory) {
        //statusHistory.each() {
            def eventType = AssetStatusEventType.valueOf(ase.eventType)
            //println "event type = " + eventType
            if (eventType.equals(AssetStatusEventType.Repair) ||
                eventType.equals(AssetStatusEventType.Installation)) {
                //println "status Date = ${ase.statusDate}"
                def possible = ase.statusDate.plus((int)(asset.assetClass.standardMaintenanceInterval * (365.25)))
                //println "possible = ${possible}"
                //println "today = ${nextMaintenance}"
                if (possible.after(nextMaintenance)) {
                    def asDate = new Date(possible.getTime())
                   // println "returning 1 " + possible.class
                   // println "asDate = "+ asDate
                    //foundVal = possible  
                    return asDate 
                }                
            }
        }

        //println("rturning  2")
        return nextMaintenance
    }
    
    def getNextProjectedInspection(asset) {
        def statusHistory = asset.statusHistory
        if (!statusHistory || statusHistory.size() == 0) {
            return null
        }
        
        statusHistory = statusHistory.sort{-it.statusDate.getTime()}
        // look back through the status event history
        // for the most recent maintenance 
        //  if found then add the maintenance interval from the AssetClass
        //  if none found then add the maintenance interval from the installation
        // if the calculated date is before today then return today? 
        def foundVal
        def nextMaintenance = new Date()
        
        for(AssetStatusEvent ase : statusHistory) {
        //statusHistory.each() {
            def eventType = AssetStatusEventType.valueOf(ase.eventType)
            //println "event type = " + eventType
            if (eventType.equals(AssetStatusEventType.Inspection) ||
                eventType.equals(AssetStatusEventType.Installation)) {
                //println "status Date = ${ase.statusDate}"
                def possible = ase.statusDate.plus((int)(asset.assetClass.standardInspectionInterval * (365.25)))
                //println "possible = ${possible}"
                //println "today = ${nextMaintenance}"
                if (possible.after(nextMaintenance)) {
                    def asDate = new Date(possible.getTime())
                   // println "returning 1 " + possible.class
                   // println "asDate = "+ asDate
                    //foundVal = possible  
                    return asDate 
                }                
            }
        }

        //println("rturning  2")
        return nextMaintenance
    }
}

