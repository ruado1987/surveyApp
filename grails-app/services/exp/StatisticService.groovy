package exp

class StatisticService {

    static transactional = true

    def countOpinionBasedOnGender() {
        def femaleAmt = countOpinionBasedOnGender('Female')
        def maleAmt = countOpinionBasedOnGender('Male')
        return [femaleAmt: femaleAmt, maleAmt: maleAmt]
    }

    private countOpinionBasedOnGender(gender){
        def cb = UserOpinion.createCriteria()
        def amt = cb.count {
                answers {
                    and {
                        question {
                            eq('text', 'What is your gender?')
                        }
                        eq('text', gender)
                    }
                }
        }
        amt
    }
}
