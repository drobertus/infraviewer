package com.mechzombie.infraview.service

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Created by David on 1/22/14.
 */
class DepreciationCalculatorService {
    private static final int moneyPrecision = 2;

    /**
     * Projects the status of an asset in the future based on current status.  Assumes no replacement
     * so this will not return a value less than a "destroyed" value.
     *
     * @param currentStatus
     * @param newStatus
     * @param destroyedStatus
     * @param replacementStatus
     * @param replacementTimeYrs
     * @param yearsAhead
     * @return
     */
    double getFutureStatus(double currentStatus, int newStatus, int destroyedStatus, int replacementStatus, double replacementTimeYrs, double yearsAhead) {

        double statusReductionPerYear = (newStatus - replacementStatus) / replacementTimeYrs;
        double reductionOverTime = statusReductionPerYear * yearsAhead;
        double futureVal = currentStatus - reductionOverTime;

        if (futureVal < destroyedStatus) {
            futureVal = destroyedStatus;
        }
        return futureVal;
    }


    double getYearsToNextReplacement(double currentStatus, double replacementStatus, double newStatus, double yearsToReplace) {

        if (currentStatus <= replacementStatus) {
            return 0.0;
        }

        double depreciationPerYear = (newStatus - replacementStatus) / yearsToReplace;
        double statusToReplace = currentStatus - replacementStatus;

        return statusToReplace / depreciationPerYear;
    }
    /**
     * This function computed the number of times that an asset will need to be replaced
     * over a span of time given its current status.  It assumes a standard replacement time
     *
     * @param currentStatus
     * @param newStatus
     * @param replacementStatus
     * @param replacementTimeYrs
     * @param yearsAhead
     * @return the number of times that an asset can be expected to be replaced over a period of time
     * given a starting status and fixed replaced period
     */
    int getProjectedTimesToReplace(double currentStatus, int newStatus, int replacementStatus, double replacementTimeYrs, double yearsAhead) {

        int replacementCount = 0;
        //first see the time from current to replacement
        double yearsRemaining = yearsAhead;
        //if the asset needs to be replaced this year then
        if (currentStatus <= replacementStatus) {
            replacementCount ++;
        } else {
            double yearsToFirstReplace = getYearsToNextReplacement(currentStatus, replacementStatus, newStatus, replacementTimeYrs);
            if (yearsToFirstReplace <= yearsAhead) {
                yearsRemaining -= yearsToFirstReplace;
                replacementCount ++;
            }
        }
        replacementCount += new Double(yearsRemaining/replacementTimeYrs).intValue();

        return replacementCount;
    }

    /**
     *
     * @param timesToReplace
     * @param yearsBetweenReplacement
     * @param yearsToFirstReplacement
     * @param currentReplacementCost
     * @param projectedInflationRate
     * @return
     */
     double cumulativeReplacementCostOverTime(int timesToReplace, double yearsBetweenReplacement, double yearsToFirstReplacement, double currentReplacementCost, double projectedInflationRate) {

        double cumulativeCost = 0.0;

        for (int i = 0; i < timesToReplace; i++) {
            cumulativeCost += compoundedRate(currentReplacementCost, projectedInflationRate, yearsToFirstReplacement + (i * yearsBetweenReplacement));
        }
        return new BigDecimal(cumulativeCost).setScale(moneyPrecision, RoundingMode.HALF_EVEN).doubleValue();
    }

    private static double compoundedRate(double principal, double rate, double years) {
        double amount = principal * Math.pow(1.0 + rate, years);
        //System.out.println("compounded val=" + amount);
        return new BigDecimal(amount).setScale(moneyPrecision, RoundingMode.HALF_EVEN).doubleValue();
    }
}
