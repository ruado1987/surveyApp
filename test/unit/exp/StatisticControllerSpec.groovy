package exp

import grails.test.mixin.TestFor 
import grails.test.mixin.TestMixin
import grails.test.mixin.Mock

@TestFor(StatisticController)
@Mock(Question)
class StatisticControllerSpec extends spock.lang.Specification {

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
        serviceMock.allRecommendations() >> [female: [], male: []]
	
	when:
        controller.getAllRecommendations()
        
	then:
        assert view == '/statistic/recommendations'	
	assertContainsKey(model,['female', 'male']) 
    }

    def 'test show yes_no questions selection view'() {
        setup:
        def questionList = createYesNoQuestionList() 
	def mockControl = mockFor(Question)

	questionList*.save(flush: true)
	mockControl.demand.static.findAll(1..1) {String query, Map options->
		Question.findAll()
	}
         
	when:
        controller.showQuestionsSelection()
       
	then:
        assert view == '/statistic/questionsSelection' 
        assert model.questions.size() ==  2
    }

    def 'test get statistic for yes_no questions' (){
        setup:
        def sampleText = 'A simple question?'
        def question = createYesNoQuestion(sampleText)
        def statisticService = Mock(StatisticService)
        
	controller.statisticService = statisticService
	views['/statistic/_statisticDisplay.gsp'] = '${chartTitle} ${statistic[0][1]} ${statistic[1][1]}'
	statisticService.countOpinionsByQuestion(sampleText, 'Yes') >> [female: 1, male: 2]	
        
	when:
        controller.params.questionId = question.id
        controller.yesNoStatistic()
        
	then:
	response.text =~ /^Statistics for question .* 1 2$/
    }

    def 'test get statistic for yes_no questions when no question found' () {
        setup:
        def NOT_KNOWN_ID = 2
	
	when:
        controller.params.questionId=NOT_KNOWN_ID
        controller.yesNoStatistic()
        
	then:
        response.contentAsString =~ 'No question found'
    }

    private void assertContainsKey(model, keys) {
    	assert model.keySet().grep(keys)
    }

    private List createYesNoQuestionList() {
        def questionList = [
		createYesNoQuestion('yes no question 1'),
		createYesNoQuestion('yes no question 2')
	]

	questionList
    }

    private Question createYesNoQuestion(String qText) {
	def options = ['Yes', 'No']
	new Question(text: qText, options: options).save(flush: true)
    }	
}
