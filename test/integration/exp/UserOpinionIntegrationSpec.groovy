package exp

import grails.plugin.spock.IntegrationSpec

class UserOpinionIntegrationSpec extends IntegrationSpec {

    def fixtureLoader

    def 'test save new user opinion'() {
        setup:
        def fixture = fixtureLoader.load('simpleSurvey').load {
            (1..9).each {num ->
                "answer${num}"(Answer) {
                    question = ref("question${num}")
                    text = 'Some answer'
                }
            }
            orphanAnswer(Answer) {
                question = noOpQuestion
                text = 'Another answer'
            }
            uo(UserOpinion) {
                submitDate = new Date() + 9
                survey = survey
                answers = [answer1, answer2, answer3, answer4,
                        answer5, answer6, answer7,
                        answer8, answer9, orphanAnswer]
            }
        }
        when:
        def count = UserOpinion.count()
        def uo = UserOpinion.findBySubmitDate(new Date() + 9)
        then:
        0 < count
        uo
        uo.answers == fixture.uo.answers
    }

    def 'test countBasedOnGender query'() {
        setup:
        fixtureLoader.load('sampleOpinions')
        when:
        def femaleAmt = UserOpinion.countBasedOnGender('Female').count()
        def maleAmt = UserOpinion.countBasedOnGender('Male').count()
        then:
        2 == femaleAmt
        1 == maleAmt
    }

    def 'test countBasedOnGenderAndAge query'() {
        setup:
        fixtureLoader.load('sampleOpinions').load {
            uo4(UserOpinion){
                submitDate= new Date()
                survey= simpleSurvey
                answers = [
                    maleAnswer, ageAnswer, answer3, answer4, answer5
                ]
            }
        }
        when:
        def femaleAmt = UserOpinion.countBasedOnGenderAndAge('Female', '18-22').count()
        def maleAmt = UserOpinion.countBasedOnGenderAndAge('Male', '18-22').count()
        then:
        2 == femaleAmt
        2 == maleAmt
    }

    def 'test countBasedOnQuestionAndAnswer query'() {
        setup:
        fixtureLoader.load('sampleOpinions').load {
            uo4(UserOpinion){
                submitDate= new Date()
                survey= simpleSurvey
                answers = [
                    maleAnswer, ageAnswer, answer3, answer4, answer5
                ]
            }
        }
        when:
        def femaleAmt = UserOpinion.countBasedOnQuestionAndAnswer(
                genderQuestion: 'What is your gender?',
                genderAnswer: 'Female',
                ageQuestion: 'Which range includes your age?',
                ageAnswer: '18-22').count()
        def maleAmt = UserOpinion.countBasedOnQuestionAndAnswer(
                genderQuestion: 'What is your gender?',
                genderAnswer: 'Male',
                ageQuestion: 'Which range includes your age?',
                ageAnswer: '18-22').count()
        then:
        2 == femaleAmt
        2 == maleAmt
    }
}
