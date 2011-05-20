package exp

import grails.plugin.spock.ControllerSpec

class SurveyControllerSpec extends ControllerSpec {

    def 'test newSurvey'() {
        setup:
            mockDomain Question
        when:
            controller.newSurvey()
        then:
            'newSurvey' == renderArgs.view
             renderArgs.model.questionList != null
    }

    def 'test saveSurvey'() {
        setup:
            mockDomain Survey
            controller.params.name='A survey'
        //Grails will automatically populate the questions association of the Survey domain class using the list of identifiers
            controller.params.questions = [1,2,3,4,5]
        when:
            controller.saveSurvey()
        then:
            'show' == redirectArgs.action
            redirectArgs.id
    }

    def 'test saveSurvey with invalid data' () {
        setup:
            mockDomain Survey
            mockDomain Question
            controller.params.name = name
            controller.params.questions = questions
        when:
            controller.saveSurvey()
        then:
            'newSurvey' == renderArgs.view
            renderArgs.model.questionList != null
            renderArgs.model.surveyObj != null
        where:
            name << ['', 'a new survey']
            questions << [[1,2,3,4,5], [1,2,3]]
    }

    def 'test index action' () {
        when:
            controller.index()
        then:
            'renderSurvey' == redirectArgs.action
            1 == redirectArgs.id
    }

    def 'test render survey action' (){
        setup:
            mockDomain Survey, [new Survey(id: 1, name: 'a survey', questions: questionList())]
            controller.params.id=1
        when:
            def model = controller.renderSurvey()
        then:
            model['survey']
    }

    def 'test render survey action when no survey is found'(){
        setup:
            mockDomain Survey, [new Survey(id: 1, name: 'a survey', questions: questionList())]
            controller.params.id=2
        when:
            def model = controller.renderSurvey()
        then:
            controller.flash.message
            '/' == redirectArgs.uri
    }

    def 'test save user opinion action'(){
        setup:
            mockDomain UserOpinion
            mockDomain Survey, [new Survey(id: 1, name: 'a survey', questions: questionList())]
            mockDomain Question, questionList()
            controller.params.'answer_1' = 'some text'
            controller.params.'answer_2' = 'another text'
            controller.params.'answer_3' = 'fuzzy text'
            controller.params.'answer_4' = 'sloppy text'
            controller.params.'answer_5' = 'stressful text'
            controller.params.surveyId = 1
        when:
            controller.saveUserOpinion()
        then:
            'thanks' == redirectArgs.action
            redirectArgs.params.opinionId
    }

    def 'test save user opinion action with problems'() {
         setup:
            mockDomain UserOpinion
            mockDomain Survey, [new Survey(id: 1, name: 'a survey', questions: questionList())]
            mockDomain Question, questionList()
         when:
            controller.saveUserOpinion()
         then:
            'renderSurvey' == renderArgs.view
            controller.flash.message
    }

    def 'test extract answers from params map' () {
        when:
            def result = controller.extractFromParams(['answer_1': 'some text',
                                      'answer_2': 'another text',
                                      'answer_3': 'yet another text',
                                      'not answer': 'ohho'])
        then:
            3 == result.size()
            [1: 'some text', 2: 'another text', 3: 'yet another text'] == result
    }

    def 'test thanks action'() {
        when:
            controller.thanks()
        then:
            'thanks' == renderArgs.template
    }

    private questionList() {
        def questions = []
        (1..5).each {
            questions << new Question(text: "Question ${it}")
        }
        questions
    }
}
