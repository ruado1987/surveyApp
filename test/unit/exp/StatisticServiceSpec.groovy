package exp

import grails.plugin.spock.UnitSpec

class StatisticServiceSpec extends UnitSpec {
    def 'test countOpinionBasedOnGender'(){
        setup:
            def service = new StatisticService()
            def fakeCriteriaImpl = [count: {Closure s-> 1}]
            def mockControl = mockFor(UserOpinion)
            mockControl.demand.static.createCriteria(1..2) {
                fakeCriteriaImpl
            }
        when:
            def result = service.countOpinionBasedOnGender()
        then:
            result.femaleAmt
            result.maleAmt
    }
}
