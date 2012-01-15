package exp

import org.hibernate.criterion.DetachedCriteria
import org.hibernate.criterion.Projections
import org.hibernate.criterion.Restrictions
import org.hibernate.criterion.Subqueries

class UserOpinion {
    Date submitDate
    Survey survey

    static hasMany = [answers: Answer]

    static constraints = {
        submitDate()
        survey()
        answers(validator: {value, obj ->
            value?.size() == obj?.survey?.questions?.size()
        })
    }

    static mapping = {
        submitDate(type: 'date')
    }
    
    static namedQueries = {
        countBasedOnGender {gender ->
            answers {
                and {
                    question {
                        eq('text', 'What is your gender?')
                    }
                    eq('text', gender)
                }
            }
        }

        countBasedOnGenderAndAge {gender, age ->
            def genderQuery = DetachedCriteria.forClass(Answer, 'answer').with {
                setProjection(Projections.id())
                createAlias('question', 'q').with {
                    add(Restrictions.eq('q.text', 'What is your gender?'))
                    add(Restrictions.eq('text', gender))
                    add(Subqueries.propertyIn('id',
                            DetachedCriteria.forClass(UserOpinion, 'uo')
                            .setProjection(Projections.property('answer.id'))
                            .createAlias('answers', 'answer')
                            .add(Restrictions.eqProperty('this.id', 'uo.id')))
                    )
                }
            }
            def ageQuery = DetachedCriteria.forClass(Answer, 'answer').with {
                setProjection(Projections.id())
                createAlias('question', 'q').with {
                    add(Restrictions.eq('q.text', 'Which range includes your age?'))
                    add(Restrictions.eq('text', '18-22'))
                    add(Subqueries.propertyIn('id',
                            DetachedCriteria.forClass(UserOpinion, 'uo')
                            .setProjection(Projections.property('answer.id'))
                            .createAlias('answers', 'answer')
                            .add(Restrictions.eqProperty('this.id', 'uo.id')))
                    )
                }
            }
            delegate.with {
                add(Restrictions.and(Subqueries.exists(genderQuery), Subqueries.exists(ageQuery)))
            }
        }

        countBasedOnQuestionAndAnswer {Map params->
            def subQuery1 = DetachedCriteria.forClass(UserOpinion, 'uo').with {
                setProjection(Projections.id())
                createAlias('answers', 'answer').with {
                    createAlias('answer.question', 'q').with {
                        add(Restrictions.eq('answer.text', params.genderAnswer))
                        add(Restrictions.eq('q.text', params.genderQuestion))
                        add(Restrictions.eqProperty('this.id', 'uo.id'))
                    }
                }
            }

            def subQuery2 = DetachedCriteria.forClass(UserOpinion, 'uo').with {
                setProjection(Projections.id())
                createAlias('answers', 'answer').with {
                    createAlias('answer.question', 'q').with {
                        add(Restrictions.eq('answer.text', params.ageAnswer))
                        add(Restrictions.eq('q.text', params.ageQuestion))
                        add(Restrictions.eqProperty('this.id', 'uo.id'))
                    }
                }
            }

            delegate.with {
                add(Restrictions.and(Subqueries.exists(subQuery1), Subqueries.exists(subQuery2)))
            }
        }
    }
}
