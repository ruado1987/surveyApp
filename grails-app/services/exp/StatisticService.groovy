package exp

import org.springframework.transaction.annotation.Transactional

class StatisticService {

    static transactional = true

    static final String GENDER_QUESTION = 'What is your gender?'
    static final String SUGGESTION_QUESTION = 'Among basic features like sharing photos, posting comments, what are the other features you would like to recommend?'

    @Transactional(readOnly = true)
    def countOpinionBasedOnGender() {
        def femaleAmt = amountOf('Female') 
        def maleAmt   = amountOf('Male') 
        return [female: femaleAmt, male: maleAmt]
    }

    @Transactional(readOnly=true)
    def allRecommendations() {
        def femaleSuggestion = recommendationsOf('Female')
        def maleSuggestion = recommendationsOf('Male')
        [male: maleSuggestion, female: femaleSuggestion]
    }

    /**
     *
     * @param question the text representation of the question
     * @param answer the desire answer for the question
     * @return  number of females/males who gave the specific answer for the passed question
     */
    def countOpinionsByQuestion(def question, def answer){
        def sql = '\
            select count(uo) from UserOpinion uo join uo.answers ans \
            where ans.question.text = :genderQuestion and ans.text = :gender \
            and exists ( \
                    select ans1 from uo.answers ans1 \
                    where ans1.question.text = :targetQuestion \
                    and ans1.text = :targetAnswer \
            )'
        def femaleCount = UserOpinion.executeQuery(sql,
                [genderQuestion: GENDER_QUESTION, gender: 'Female',
                 targetQuestion: question, targetAnswer: answer])
        def maleCount = UserOpinion.executeQuery(sql,
                [genderQuestion: GENDER_QUESTION, gender: 'Male',
                 targetQuestion: question, targetAnswer: answer])
       
	[female: femaleCount[0], male: maleCount[0]]
    }

    private def recommendationsOf(def gender) {
        def sql = '\
            select distinct ans.text from UserOpinion uo join uo.answers ans \
            where ans.question.text = :suggestionQuestion \
            and exists ( \
                    select ans1 from uo.answers ans1 where \
                    ans1.question.text = :genderQuestion \
                    and ans1.text = :genderAnswer) \
        '
        UserOpinion.executeQuery(sql,
                //named parameters
                [genderQuestion: GENDER_QUESTION, genderAnswer: gender,
                        suggestionQuestion: SUGGESTION_QUESTION])
    }

    private int amountOf(String gender) {
	def genderNumber = UserOpinion.where {
		answers {
			text == gender && 
			question {
				text == 'What is your gender?'
			}
		}
    	}
	genderNumber.count()
    }	
}
