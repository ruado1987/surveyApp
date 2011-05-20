package exp

class SurveyController {

    static scaffold = true


    def index = {
        redirect(action: 'renderSurvey', id: 1)
    }

    def newSurvey = {
        renderNewSurvey()
    }

    def saveSurvey = {
        def survey = new Survey(params)
        def result = survey.save()
        if(result) {
          flash.message = "A survey whose name is ${params.name} has been saved successfully"
          redirect(action: 'show', id: survey.id)
        }else{
          renderNewSurvey(survey)
        }
    }

    def renderSurvey = {
        def survey = Survey.get(params.id)
        if(!survey){
            //TODO: a candidate for functional testing
            flash.message = "A survey whose id is ${params.id} is not found, please make sure you choose an available survey"
            redirect(uri: '/')
        }else{
            [survey: survey]
        }
    }

    def saveUserOpinion = {
        def answerMap = extractFromParams(params)
        def survey = Survey.get(params.surveyId)
        def answers = answerMap.collect {k,v->
            def question = Question.get(k)
            new Answer(question: question, text: v)
        }
        def uo = new UserOpinion(submitDate: new Date(), answers: answers, survey: survey)
        def result = uo.save()
        if(result) {
             redirect(action: 'thanks', params: [opinionId: uo.id])
        }else{
             flash.message = 'Please choose a survey or choose answers for all questions'
             render(view: 'renderSurvey')
        }
    }

    def thanks = {
        render(template: 'thanks')
    }

    private void renderNewSurvey(survey = new Survey()) {
        def questions = Question.list()
        render(view: 'newSurvey', model: [surveyObj: survey, questionList: questions])
    }

    protected extractFromParams(params){
        def prefix = 'answer_'
        def pattern = ~/answer_\d/
        def notAnswers = params.collect {k, v->
            if(!(pattern.matcher(k).matches())) {
                k
            }
        }
        def allParamNames = params.collect {k, v->
            k
        }
        def answers = allParamNames - notAnswers
        def questionIds = answers.collect {k->
            (k - prefix)
        }
        def result = [:]
        questionIds.each {qid->
            result << [(qid as Integer): params."${prefix}${qid}"]
        }
        result
    }
}
