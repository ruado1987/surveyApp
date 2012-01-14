package exp

import grails.test.mixin.TestFor

@TestFor(Survey)
class SurveySpec extends AbstractConstraintSpec {

    def "test constraints on scalar value"() {
        setup:
            def questionList = createQuestionList()
            mockForConstraintsTests Survey, [createSurvey('a unique survey')]
            def survey = new Survey(name: value)
        when:            
            survey.validate()
        then:
            validateConstraint(validator, survey, 'name')                        
        where:
            value               | validator
            ''                  | 'blank'
            'A survey'          | null
            'a unique survey'   | 'unique'
    }

    def "test constraints on collection values" () {
        setup:
            mockForConstraintsTests Survey
            def survey = createSurvey('a survey', questions)
        when:                        
            survey.validate()
        then:
            validateConstraint('minSize', survey, 'questions')                        
        where:            
            questions << [[], [new Question(text: 'A Question')]]
    }

    private Survey createSurvey(String name) {
        new Survey(name: name, questions: createQuestionList())
    }

    private Survey createSurvey(String name, List questions) {
        new Survey(name: name, questions: questions)
    }

    private List createQuestionList() {
        def questionList = []
        (1..5).each {
            questionList << new Question(text: "This is a question ${it}")
        }

        questionList
    }

}
