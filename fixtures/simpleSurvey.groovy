import exp.Survey

include 'sampleQuestions'

fixture {
    survey(Survey) {
        name = 'Survey on the need for another social network to share creative ideas'
        questions = [question1, question2, question3,
                question4, question5, question6,
                question7, question8, question9,
                question10]
    }
}
