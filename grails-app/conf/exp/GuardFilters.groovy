package exp

class GuardFilters {

    def filters = {
        surveyRelated(controller: 'survey', action: 'renderSurvey|saveUserOpinion|thanks') {
            before = {
                if((!params.id && !params.surveyId) && !params.opinionId){
                    log.warn "User requested for action: ${actionName} in an inappropriate way"
                    redirect(uri: '/')
                    return false
                }
            }
        }
        crudrelated(controller: '*', action: 'save|create|update|delete|show|list|new', find:true){
            before = {
                if(controllerName == 'survey' && actionName == 'saveUserOpinion'){
                    return true
                }else{
                    if(params.accessToken != '210787'){
                        log.warn "User requested for action: ${actionName} of controller: ${controllerName} in an inappropriate way"
                        flash.message = 'You do not have permission to access to administrator area'
                        redirect(uri:'/')
                        return false
                    }
                }
            }
        }
    }

}