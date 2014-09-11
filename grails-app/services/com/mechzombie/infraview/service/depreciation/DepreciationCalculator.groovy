/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mechzombie.infraview.service.depreciation

import com.mechzombie.infraview.Asset

/**
 *
 * @author David
 */
interface DepreciationCalculator {

    Double estimateCurrentStatus(Asset asset )
    
    double getFutureStatus(double currentStatus, int newStatus, int destroyedStatus, int replacementStatus, double replacementTimeYrs, double yearsAhead);
    
    double getYearsToNextReplacement(double currentStatus, double replacementStatus, double newStatus, double yearsToReplace);
    
    double cumulativeReplacementCostOverTime(int timesToReplace, double yearsBetweenReplacement, double yearsToFirstReplacement, double currentReplacementCost, double projectedInflationRate)
    
    
}

