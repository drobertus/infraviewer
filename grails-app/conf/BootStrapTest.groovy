import com.mechzombie.infraview.Enterprise

/**
 * Created by David on 1/19/14.
 */
class BootStrapTest {

    def init() {
        def e1 = new Enterprise(name: 'ent1').save(flush: true)
        def e2 = new Enterprise(name: 'ent2').save(flush: true)
        def ents = Enterprise.count()

        assert (2 == ents)
    }
}
