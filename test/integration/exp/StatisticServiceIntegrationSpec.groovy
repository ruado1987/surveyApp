package exp

import grails.plugin.spock.IntegrationSpec

class StatisticServiceIntegrationSpec extends IntegrationSpec {

    def fixtureLoader
    def statisticService

    void testGetAllRecommendations() {
        setup:
        fixtureLoader.load('sampleAnswers').load {
            shortSurvey(Survey) {
                name = 'A short survey'
                questions = [
                        question1,
                        question2,
                        question3,
                        question4,
                        question10
                ]
            }

            maleSuggestionAnswer(Answer, question: question10, text: maleSuggestion)
            maleSuggestionAnswer2(Answer, question: question10, text: maleSuggestion.reverse())
            femaleSuggestionAnswer(Answer, question: question10, text: femaleSuggestion)

            maleuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [maleAnswer, answer2, answer3, answer4, maleSuggestionAnswer]
            }
            femaleuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [femaleAnswer, answer2, answer3, answer4, femaleSuggestionAnswer]
            }
            anothermaleuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [maleAnswer, answer2, answer3, answer4, maleSuggestionAnswer2]
            }
            //an opinion with duplicate suggestions
            duplicateuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [maleAnswer, answer2, answer3, answer4, maleSuggestionAnswer]
            }
        }
        when:
        def result = statisticService.allRecommendations()
        then:
        result.male.grep([maleSuggestion, maleSuggestion.reverse()])
        result.female.grep(femaleSuggestion)
        result.male.size() == 2
        result.female.size() == 1
        where:
        maleSuggestion << ['A design tool', 'An exciting game with free of charge']
        femaleSuggestion << ['A makeup program', 'Attractive user interface']
    }

    void testCountOpinionsByWillingnessQuestion() {
        setup:
        def fixture = fixtureLoader.load('sampleAnswers').load {
            shortSurvey(Survey) {
                name = 'A short survey'
                questions = [
                        question1,
                        question2,
                        question3,
                        question4,
                        question8
                ]
            }

            yesAnswer(Answer) {
                question = question8
                text = 'Yes'
            }
            noAnswer(Answer) {
                question = question8
                text = 'No'
            }

            maleuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [
                        maleAnswer, answer2, answer3, answer4, yesAnswer
                ]
            }
            femaleuo(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [
                        femaleAnswer, answer2, answer3, answer4, noAnswer
                ]
            }
            femaleuo2(UserOpinion) {
                submitDate = new Date()
                survey = shortSurvey
                answers = [
                        femaleAnswer, answer2, answer3, answer4, yesAnswer
                ]
            }
        }
        def willingnessQuestion = fixture.question8
        when:
        def result = statisticService.countOpinionsByQuestion(willingnessQuestion.text, 'Yes')
        then:
        1 == result.male
        1 == result.female
    }
}
