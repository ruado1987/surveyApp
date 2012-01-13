package exp

import grails.test.mixin.TestFor

@TestFor(Answer)
class AnswerSpec extends spock.lang.Specification {

    def setup() {
        mockForConstraintsTests Answer
    }

    def "test constraints on scalar value"() {
        setup:            
            def answer = new Answer(text: 'answer', question: new Question(text: 'question'))   
        when:
            answer.validate()
        then: 
            validateConstraint('valid', answer, 'text')                                       
    }

    def "test question is mandatory"() {
        setup:
            def answer = new Answer(text: "my answer")
        when:
            answer.validate()
        then: 
            validateConstraint("nullable", answer, "question");
    }

    private void validateConstraint(String validator, ouv, String fieldName) {
        if(validator == 'valid')
            assert ouv.errors["${fieldName}"] == null
        else    
            assert ouv.errors["${fieldName}"] == validator
    }
}
