package exp

import grails.test.mixin.TestFor

@TestFor(Question)
class QuestionSpec extends AbstractConstraintSpec {

    def "test constraints on question text"() {
        setup:
            mockForConstraintsTests Question, [new Question(text: 'duplicate')]
            def question = new Question(text: value)
        when:
            def result = question.validate()
        then:
	    validateConstraint(validator, question, 'text')	
        where:
	    value 	| validator
	    ''	  	| 'blank'
	    null  	| 'nullable'
	    'q1'  	| 'valid'
    }

    /*def "test constraints on collection values" () {
        setup:
            mockDomain Question
        when:
            def question = new Question(text: 'a question')
            options.each {
                question.addToOptions(it)
            }
            def result = question.validate()
        then:
            assert result == valid, "Validate result of ${options} is not as expected"
            validator == question.errors['options']
        where:
            options << [['1','2'], ['1','2','3','4'], ['1','2','3','4','5']]
            valid << [false, true, false]
            validator << ['size', null, 'size']
    }*/

    /*def "test constraints on empty collection values" () {
          setup:
            mockForConstraintsTests Question
          when:
            def question = new Question('text': 'a question', options: [])
            def result = question.validate()
          then:
            result
            'size' == question.errors['options']
    }*/
}
