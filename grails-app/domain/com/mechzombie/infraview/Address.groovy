package com.mechzombie.infraview

class Address {

    String addressLine1
    String addressLine2
    String city
    State state
    String postalCode

    static constraints = {
        addressLine2 blank:false, nullable: true
    }
    
    def getFormattedAddress() {
        def add = addressLine1 + '\n'
        if (addressLine2) {
            add = add+ addressLine2 + '\n'
        }
        add = add + city + ", " + state.code + " " + postalCode
    }
}
