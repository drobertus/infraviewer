package com.mechzombie.infraview

class InfraUser {

    transient springSecurityService

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    
    //user added
    //String firstName
    //String lastName
    Enterprise enterprise

    static constraints = {
        username blank: false, unique: true, email: true
        password blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        if(id) {
            return InfraUserRole.findAllByUser(this).collect { it.role } as Set
        }
        else {
            return new HashSet<Role>()
        }
    }
    
    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
