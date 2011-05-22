package exp

class UserOpinion {
    Date submitDate
    Survey survey

    static hasMany = [answers: Answer]

    static constraints = {
        submitDate()
        survey()
        answers(validator: {value, obj->
            value?.size() == obj?.survey?.questions?.size()
        })
    }

    static mapping = {
        submitDate(type: 'date')
    }
}
