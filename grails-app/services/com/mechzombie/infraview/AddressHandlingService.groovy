package com.mechzombie.infraview

import grails.transaction.Transactional

//@Transactional
class AddressHandlingService {

    static transactional = true
    
    def buildAddress(Location loc, def params) {
        println("building address")
        if (!params['hasAddress']) {
            println 'deleting address'
            loc.hasAddress = false
            loc.address = null;
            loc.save flush: true            
           
            return
        }
        println 'saving address'
        loc.hasAddress = true
        def theState = State.get(params.state)
        
        
        println("the state id " + theState.id)
        loc.address = Address.findOrSaveWhere(addressLine1: params.addressLine1,
            addressLine2: params.addressLine2, city: params.city, state: theState,
            postalCode: params.postalCode)
        println("find or saved address " + loc.address.id)
        loc.address.validate()
        println("errros in addy are:" + loc.address.getErrors())
    }
}
