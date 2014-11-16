
package world

//import grails.plugin.remotecontrol.RemoteControl
import static cucumber.api.groovy.Hooks.World


class SimpleWorld {
    def binding

    SimpleWorld (def binding) {
        this.binding = binding
    } 
}

World () {
    def world = new SimpleWorld (binding)
    world.metaClass.mixin SimpleState    
    world
}

