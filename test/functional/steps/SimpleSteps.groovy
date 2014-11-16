/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps

this.metaClass.mixin(cucumber.api.groovy.EN)

import java.util.logging.*;


/**
 *
 * @author David
 */


def logger = Logger.getLogger(this.class.getName());
    
Given (~'^that \"(.*?)\" is (\\d+)$') { String valName, int value ->
    println 'printlning name val =' + valName + ";" + value
    logger.info 'log.info name val =' + valName + ";" + value
    addMapVal(valName, value)
    def intheMap = getMapVal(valName)
    assert value == intheMap
}   
    
When (~'^when we perform \"(.*?)\" on \"(.*?)\"$') { String operator, String valsList ->
     
    def valsx = valsList.split(',')
    def vals = []
    def params = valsList + "|"
    valsx.each() {
        def theName = it.trim()
        def theVal = getMapVal(theName)
       // assert theVal == 7
        params << (theName + ':' + theVal + ",")
        vals << it.trim()
    }
    println('opeation=' + operator)
    def operation
    setParams(params)
    setOperation(operator)
    
    switch (operator) {
    case 'add':
        //log.inf 'adding!'
        vals.each() {
            def theVar = getMapVal(it)
            if (operation) {
                operation += theVar
            }else {
                operation = theVar
            }
        }
        //log.info 'result of add=' + operation
        break
    case 'subtract':
                
        vals.each() {
            if (operation) {
                operation -= it
            }else {
                operation = it
            }
        }
        break;
    case 'multiply':
                
        vals.each() {
            if (operation) {
                operation =  operation * it
            }else {
                operation = it
            }
        }
        break
    case 'divide':

        vals.each() {
            if (operation) {
                operation = operation / it
            }else {
                operation = it
            }
        }  
        break;
    default:
        break;   
    }
   
    if (operation) {
        setTotal(operation)
    }else {
        setTotal(154)
    }
}

Then(~'^the result should be (\\d+)\$') { int expectedResult ->    
    assert 'add-' + expectedResult == getOperation() + '-' + getTotal()
}


