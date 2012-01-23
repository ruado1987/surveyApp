package exp

import spock.lang.Specification
import grails.test.mixin.*

@TestFor(SurveyController)
@Mock([Question, Survey, UserOpinion])
class SurveyControllerSpec extends Specification {

    def 'test newSurvey'() {
        when:
            controller.newSurvey()
        
	then:
            view == '/survey/newSurvey'
            model.questionList != null
    }

    def 'test successful save of survey should redirect to the show survey page'() {
        setup:
	    def questions = questionList()*.save(flush: true);
	    controller.params.name='A survey'
        //Grails will automatically populate the questions association of the Survey domain class using the list of identifiers
            controller.params.questions = questions.id 
	when:
            controller.saveSurvey()
        
	then:
	    response.redirectUrl == '/survey/show'
    }

    def 'test saveSurvey with invalid data' () {
        setup:
            controller.params.name = name
            controller.params.questions = questions
        
	when:
            controller.saveSurvey()
       
        then:
            view == '/survey/newSurvey'
            model.questionList != null
            model.surveyObj != null
        
	where:
            name << ['', 'a new survey']
            questions << [[1,2,3,4,5], [1,2,3]]
    }

    def 'test index action' () {
        when:
            controller.index()
        then:
	    response.redirectUrl == '/survey/renderSurvey/1'
    }

    def 'test render survey action' (){
        setup:
	    new Survey(id: 1, name: 'a survey', questions: questionList()).save(flush: true)
        
	when:
	    controller.params.id=1
            def model = controller.renderSurvey()
        
	then:
            model['survey']
    }

    def 'test render survey action when no survey is found'(){
        setup:
	    new Survey(id: 1, name: 'a survey', questions: questionList()).save(flush: true)
        
	when:	    
	    controller.params.id=2
            def model = controller.renderSurvey()

	then:
            controller.flash.message
	    response.redirectUrl == '/'
    }

    def 'test save user opinion action'(){
        setup:
	    questionList()*.save(flush: true)
            new Survey(id: 1, name: 'a survey', questions: Question.list()).save(flush: true)	    
	    
	    controller.params.'answer_1' = 'some text'
            controller.params.'answer_2' = 'another text'
            controller.params.'answer_3' = 'fuzzy text'
            controller.params.'answer_4' = 'sloppy text'
            controller.params.'answer_5' = 'stressful text'
            controller.params.surveyId = 1
        
	when:
            controller.saveUserOpinion()
        
	then:
	    assert response.redirectUrl =~ /\/survey\/thanks?.*/
    }

    def 'test save user opinion action with problems'() {
         setup:
	    questionList()*.save(flush: true)
            new Survey(id: 1, name: 'a survey', questions: Question.list()).save(flush: true)

	 when:
	    controller.params.surveyId = 1
            controller.saveUserOpinion()
         
	 then:
            view == '/survey/renderSurvey'
	    model.survey
            controller.flash.message
    }

    def 'test extract answers from params map' () {
        when:
            def result = controller.extractFromParams(['answer_1': 'some text',
                                      'answer_2': 'another text',
                                      'answer_3': 'yet another text',
                                      //a regression test value
                                      'answer_10': 'answer for a 2 digit question',
                                      'not answer': 'ohho'])
        then:
            4 == result.size()
            [1: 'some text', 2: 'another text', 3: 'yet another text', 10: 'answer for a 2 digit question'] == result
    }

    def 'test thanks action'() {
	setup:
	    views['/survey/_thanks.gsp'] = "thanks"

        when:
            controller.thanks()
    
	then:
	    response.text == "thanks"
    }

    private questionList() {
        def questions = []
        (1..5).each {
            questions << new Question(text: "Question ${it}")
        }
        questions
    }
}
