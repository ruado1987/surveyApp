package exp

import grails.plugin.spock.ControllerSpec

class StatisticControllerSpec extends ControllerSpec {

    def 'test GenderStatistic action'() {
        setup:
        def mockControl = mockFor(StatisticService)
        mockControl.demand.countOpinionBasedOnGender(1..1) {->
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

    def 'test getAllRecommendations action'() {
        setup:
        def serviceMock = Mock(StatisticService)
        controller.statisticService = serviceMock
        when:
        controller.getAllRecommendations()
        then:
        1 * serviceMock.allRecommendations() >> [female: [], male: []]
        'recommendations' == renderArgs.view
        keyMatched('[Ff]emale', renderArgs.model)
        keyMatched('[mM]ale', renderArgs.model)
    }

    def 'test show yes_no questions selection view'() {
        setup:
        def questionList = [new Question(text: 'yes no question', options: ['Yes', 'No']),
                new Question(text: 'another yes no question', options: ['Yes', 'No']),
                new Question(text: 'not a yes no question')
        ]
        mockDomain(Question, questionList)
        def mockControl = mockFor(Question)
        mockControl.demand.static.findAll(1..1) {String query, Map params ->
            questionList[0..1]
        }
        when:
        controller.showQuestionsSelection()
        then:
        'questionsSelection' == renderArgs.view
        2 == renderArgs.model.questions.size()
    }

    def 'test get statistic for yes_no questions' (){
        setup:
        def questionId = 1
        def sampleText = 'A simple question?'
        mockDomain(Question, [new Question(id: 1, text: sampleText, options: ['Yes', 'No'])])

        def statisticService = Mock(StatisticService)
        controller.statisticService = statisticService
        when:
        controller.params.questionId = questionId
        controller.yesNoStatistic()
        then:
        1 * statisticService.countOpinionsByQuestion(sampleText, 'Yes') >> [female: 1, male: 2]
        renderArgs.model.statistic[0][0] =~ /[Ff]emale|[Mm]ale/
        renderArgs.model.statistic[1][0] =~ /[Ff]emale|[Mm]ale/
        renderArgs.model.chartTitle =~ /^Statistics for question.*/
        'statisticDisplay' == renderArgs.template
    }

    def 'test get statistic for yes_no questions when no question found' () {
        setup:
        def NOT_KNOWN_ID = 2
        def sampleText = 'A simple question?'
        mockDomain(Question, [new Question(id: 1, text: sampleText, options: ['Yes', 'No'])])
        when:
        controller.params.questionId=NOT_KNOWN_ID
        controller.yesNoStatistic()
        then:
        //TODO: this test does not take i18n into consideration
        mockResponse.contentAsString =~ 'No question found'
    }

    private boolean keyMatched(def keyPattern, model) {
        model.any {k, v ->
            k =~ keyPattern
        }
    }
}
