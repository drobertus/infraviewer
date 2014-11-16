/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world

import groovy.util.logging.*
/**
 *
 * @author David
 */
@Log
class SimpleState {
	
    static def total = 0
    static def operation
    static def params
    static def valsMap = [:]
    
    void addMapVal(name, val) {
        valsMap.put(name, val)
    }
    
    def getMapVal(name) {
        return valsMap.get(name)
    }
    
    void setParams(val) {
        params = val
    }
    
    def getParams() {
        return params
    }
    
    void setTotal(val) {
        log.info 'total=' + val
        total = val
    }
    
    def getTotal() {
        return total
    }
    
    void setOperation(op) {
        log.info 'operation=' + op
        operation = op
    }
    
    def getOperation() {
        operation
    }
}

