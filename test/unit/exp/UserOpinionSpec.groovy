package exp

import grails.plugin.spock.UnitSpec

class UserOpinionSpec extends UnitSpec {

    def 'test constraints on scalar value' () {
        setup:
            mockForConstraintsTests UserOpinion
        when:
            def uo = new UserOpinion(submitDate: date, survey: newSurvey(), answers: answerList())
            def result = uo.validate()
        then:
            expectedResult == result
            validator == uo.errors['submitDate']
        where:
            date << [new Date(), null]
            expectedResult << [true, false]
            validator << [null, 'nullable']
    }

    def 'test constraints on association value' (){
         setup:
            mockForConstraintsTests UserOpinion
         when:
            def uo = new UserOpinion(submitDate: new Date(), survey: survey, answers: answerList())
            def result = uo.validate()
         then:
            expectedResult == result
            validator == uo.errors['survey']
         where:
            expectedResult << [true, false]
            survey << [new Survey(name: 'a survey', questions: questionList()), null]
            validator << [null, 'nullable']
    }

    def 'test constraint on collection value' (){
        //TODO: fix a fail test case if any answer of the answer list contains empty text
        setup:
            mockForConstraintsTests UserOpinion
        when:
            def uo = new UserOpinion(submitDate: new Date(), survey: newSurvey(), answers: answers)
            def result = uo.validate()
        then:
            assert expectedResult == result, "Validation result of _${answers.text}_ is not as expected"
            validator == uo.errors['answers']
        where:
            answers << [answerList(), [], emptyAnswerList(),
		            answerList() << new Answer(
                            text: 'a reply',
                            question: new Question(text: 'a question'))
            ]
            expectedResult << [true, false, true, false]
            validator << [null, 'validator', null, 'validator']
    }

    private newSurvey() {
        def questions = questionList()
        def survey = new Survey(name: 'a survey', questions: questions)
        survey
    }

    private answerList(def empty=false) {
        def questions = questionList()
        def answer = []
        questions.each {
            answer << new Answer(question: it, text: (empty?'':"Answer for question ${it}"))
        }
        answer
    }

    private emptyAnswerList() {
       answerList(true)
    }

    private questionList() {
        def questions = []
        (1..5).each {
            questions << new Question(text: "Question ${it}")
        }
        questions
    }
}
