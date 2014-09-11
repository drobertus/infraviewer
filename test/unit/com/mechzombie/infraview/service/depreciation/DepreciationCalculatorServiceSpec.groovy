package com.mechzombie.infraview.service.depreciation

import grails.test.mixin.*
import spock.lang.Specification
import com.mechzombie.infraview.*
import spock.lang.*
/**
 * Test the DepreciationCalculator- this is the core
 * of replacement scheduling and budgeting
 */
@Mock([AssetClass, Enterprise, Asset, Location])
class DepreciationCalculatorServiceSpec extends Specification {

    def calculator = new DepreciationCalculatorService()

    void setup() {

    }
    
    def "test liveObjects" () {
        
        setup:
            def testEnt = Enterprise.load(1)
            def params = [:]
            params["name"] = 'hydrant'
            params["statusValueNew"] = "10"
            params["statusValueReplace"] = "5"
            params["statusValueDestroyed"] = "0"
            params["expectedLifeSpanYears"] = "20"
            params["standardInspectionInterval"] = "1"
            params["standardMaintenanceInterval"] = "3"
            params["enterprise"] = testEnt

            def assetClass = new AssetClass(params)        

            def location = Location.load(1)
            def asset = new Asset(assetClass: assetClass, externalId: "HYD 12", location: location).save()
            def statusDate = new Date() - (5 * 365) 
            def assetStatus = new AssetStatusEvent(statusDate: statusDate, status: 8.0,
                eventType: AssetStatusEventType.Inspection)

            Asset.metaClass.findMostRecentStatusEvent = { -> assetStatus}
        
        when:
        
            def projectedStatus = calculator.estimateCurrentStatus(asset)
            println("projected status= ${projectedStatus}")
        
        then:
            assertEquals 6.75, projectedStatus
            
    }
    
    def "test basic projected status of asset"() {
        expect:
        futureValue == calculator.getFutureStatus(currentStatus, newStatus, destoyedStatus, replacementStatus, replacementTimeYrs, yearsAhead)

        where:
        currentStatus | futureValue | newStatus | destoyedStatus | replacementStatus | replacementTimeYrs | yearsAhead
        10            | 10.0        | 10        | 0              | 5                 | 10                 | 0
        10            | 9.5         | 10        | 0              | 5                 | 10                 | 1
        10            | 7.5         | 10        | 0              | 5                 | 10                 | 5
        10            | 5           | 10        | 0              | 5                 | 10                 | 10
        10            | 0           | 10        | 0              | 5                 | 10                 | 20
        10            | 0           | 10        | 0              | 5                 | 10                 | 25

    }

    def "count the number of replacements needed over a span of time"() {

        expect:
        replacementCount == calculator.getProjectedTimesToReplace(currentStatus, newStatus, replacementStatus, replacementTimeYrs, yearsAhead)

        where:
        currentStatus | newStatus | replacementStatus | replacementTimeYrs | yearsAhead | replacementCount
        10            | 10        | 5                 | 5                  | 1          | 0
        10            | 10        | 5                 | 5                  | 4.9        | 0
        10            | 10        | 5                 | 5                  | 5          | 1
        10            | 10        | 5                 | 5                  | 10         | 2
        4             | 10        | 5                 | 5                  | 10         | 3
        5             | 10        | 5                 | 5                  | 10         | 3
        6             | 10        | 5                 | 5                  | 10         | 2
        10            | 10        | 5                 | 1                  | 1          | 1
        10            | 10        | 5                 | 2                  | 5          | 2
        10            | 10        | 5                 | 3                  | 5          | 1
        10            | 10        | 5                 | 4                  | 10         | 2
        4             | 10        | 5                 | 0.5                | 10         | 21
        5             | 10        | 5                 | 3.5                | 10         | 3
        6             | 10        | 5                 | 0.1                | 10         | 100
        10            | 10        | 0                 | 0.5                | 10         | 20
        10            | 10        | 0                 | 10                 | 10         | 1
        7.5           | 10        | 5                 | 4.5                | 10         | 2
        5.5           | 10        | 5                 | 4.4                | 10         | 3
        7.5           | 10        | 5                 | 4.6                | 10         | 2
        10            | 10        | 5                 | 0.25               | 0.4        | 1
        10            | 10        | 5                 | 0.25               | 0.5        | 2
        10            | 10        | 5                 | 0.25               | 0.2        | 0
    }

    def "evaluate replacement cost over time"() {
        expect:
        replacementCost == calculator.cumulativeReplacementCostOverTime(timesToReplace,
            yearsBetweenReplacement, yearsToFirstReplacement, currentReplacementCost, projectedInflationRate)

        where:
        replacementCost | timesToReplace | yearsBetweenReplacement | yearsToFirstReplacement | currentReplacementCost | projectedInflationRate
        100             | 1              | 1                       | 0                       | 100                    | 0
        100             | 1              | 10                      | 0                       | 100                    | 0
        100             | 1              | 1                       | 0                       | 100                    | 0.50
        150             | 1              | 5                       | 1                       | 100                    | 0.50
        200             | 2              | 1                       | 0                       | 100                    | 0
        204.71          | 1              | 80                      | 72                      | 100                    | 0.01
        304.71          | 2              | 72                      | 0                       | 100                    | 0.01
        2500            | 25             | 0.5                     | 0.08                    | 100                    | 0
        200.5           | 2              | 0.5                     | 0                       | 100                    | 0.01
        201.5           | 2              | 0.5                     | 0.5                     | 100                    | 0.01
        1022.74         | 10             | 0.5                     | 0                       | 100                    | 0.01
    }


}
