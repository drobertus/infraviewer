package com.mechzombie.infraview

class State {

    String name
    String code
    
    static constraints = {
    }
    
    static def getStateList() {
        State.list(sort: "code", order: "asc")
    }
    
    static State create(String name, String code, boolean flush = false) {
        new State(name: name, code: code).save(flush: flush, insert: true)
    }
}
