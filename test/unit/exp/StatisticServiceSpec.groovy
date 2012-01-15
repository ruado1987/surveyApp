package exp

import grails.test.mixin.TestFor
import grails.test.mixin.Mock

@TestFor(StatisticService)
@Mock([UserOpinion, Survey, Question, Answer])
class StatisticServiceSpec extends spock.lang.Specification {

    def 'test countOpinionBasedOnGender'() {
        setup:
	def survey = createSurveyContainsGenderRelatedQuestion('my survey')
	def userOpinion = createUserOpinionOfSurvey(survey)	

	when:
        def result = service.countOpinionBasedOnGender()
	
	then:
        result.female == 1
        result.male == 1
    }

    def 'test allRecommendations'() {
        setup:
        def mockControl = mockFor(UserOpinion)
        mockControl.demand.static.executeQuery(1..2) {String q, Map m ->
            []
        }
        
	when:
        def result = service.allRecommendations()
        
	then:
        containsGenderKey(result)
        noExceptionThrown()
    }

    def 'test count number of females and males in terms of a particular question'() {
        setup:
        def mockControl = mockFor(UserOpinion)
        mockControl.demand.static.executeQuery(1..2) {String q, Map m ->
            [1]
        }
        
	when:
        def result = service.countOpinionsByQuestion(question, answer)
        
	then:
        assert result.female == 1
	assert result.male   == 1	
	noExceptionThrown()
        
	where:
	question        | answer
	'some question' | 'some answer'
    }

    private void containsGenderKey(def params) {
        params.any {k, v ->
            assert k =~ /[Ff]emale|[Mm]ale/
        }
    }

    private Question createQuestion(String text) {
	new Question(text: text).save(flush: true, failOnError: true)
    }
   
    private Answer createAnswer(String text, Question question) {
	new Answer(text: text, question: question).save(flush: true, failOnError: true)
    }
    
    private Survey createSurveyContainsGenderRelatedQuestion(String surveyName) {
	def genderQuestion =  createQuestion('What is your gender?')
	def otherQuestion  =  createQuestion('How old are you?')
	new Survey(name: surveyName, questions: ([genderQuestion] * 2) + ([otherQuestion] * 3))
			.save(flush: true)
    }

    private Question findGenderQuestion() {
	findQuestion('What is your gender?')
    }

    private Question findQuestion(String qtext) {
	def c = Question.where {
		text == qtext
	}
	c.get()
    }	

    private UserOpinion createUserOpinionOfSurvey(Survey survey) {
	assert Question.count() == 2
	def otherQuestion  = findQuestion('How old are you?')
	def genderQuestion = findGenderQuestion()
	def femaleAnswer   = createAnswer('Female', genderQuestion)
	def maleAnswer     = createAnswer('Male', genderQuestion)
	def otherAnswer    = createAnswer('20', otherQuestion)
	def otherAnswer1   = createAnswer('21', otherQuestion)
	def otherAnswer2   = createAnswer('22', otherQuestion)
	def answers = [femaleAnswer, maleAnswer, otherAnswer, otherAnswer1, otherAnswer2]
	def uo = new UserOpinion(survey: survey, submitDate: new Date())
	answers.each {
		uo.addToAnswers(it)
	}		
	uo.save(flush: true, failOnError: true)	
    }	
}
