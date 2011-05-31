package exp

import grails.plugin.spock.UnitSpec

class StatisticServiceSpec extends UnitSpec {

    def service

    def setup() {
        service = new StatisticService()
    }

    def 'test countOpinionBasedOnGender'() {
        setup:
        def fakeCriteriaImpl = [count: {Closure s -> 1}]
        def mockControl = mockFor(UserOpinion)
        mockControl.demand.static.countBasedOnGender(1..2) {
            fakeCriteriaImpl
        }
        when:
        def result = service.countOpinionBasedOnGender()
        then:
        1 == result.female
        1 == result.male
    }

    def 'test allRecommendations'() {
        setup:
        def mockControl = mockFor(UserOpinion)
        mockControl.demand.static.executeQuery(1..2) {String q, Map m ->
            []
        }
        when:
        def result = service.allRecommendations()
        mockControl.verify() //if method calls are as expected
        then:
        containsGenderKey(result)
        noExceptionThrown()//then no exception thrown
    }

    def 'test count number of opinions in terms of a particular question'() {
        setup:
        def mockControl = mockFor(UserOpinion)
        mockControl.demand.static.executeQuery(1..2) {String q, Map m ->
            [1]
        }
        when:
        def result = service.countOpinionsByQuestion(question, answer)
        mockControl.verify()
        then:
        containsGenderKey(result)
        noExceptionThrown()
        where:
        question << ['some question']
        answer << ['some answer']
    }

    private def containsGenderKey(def params) {
        params.any {k, v ->
            k =~ /[Ff]emale|[Mm]ale/
        }
    }
}
