package exp

import grails.plugin.spock.ControllerSpec

class StatisticControllerSpec extends ControllerSpec {

    def 'test GenderStatistic action'() {
        setup:
            def mockControl = mockFor(StatisticService)
            mockControl.demand.countOpinionBasedOnGender(1..1)  {->
                return genderStatistic
            }
            controller.statisticService = mockControl.createMock()
        when:
            def model = controller.genderStatistic()
        then:
            model.statisticColNames
            ['Female', 1] == model.statisticData[0]
            ['Male', 2] == model.statisticData[1]
        where:
            genderStatistic << [[FemaleAmt: 1, MaleAmt: 2], [femaleAmt: 1, maleAmt: 2]]
    }
}
