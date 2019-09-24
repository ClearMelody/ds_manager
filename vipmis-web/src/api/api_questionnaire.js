/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/9/17
 \* @filename: api_questionnaire
 \* Description:
 \*/

import * as API from './'

export default {
  listQuestionnairesByPage: params => API.GET('/questionnaire/list', params),
  deleteQuestionnaire: params => API.POST(`/questionnaire/delete`, params),
  issueQuestionnaire: params => API.POST(`/questionnaire/issue`, params)
}
