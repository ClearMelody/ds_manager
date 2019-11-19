/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/11/18
 \* @filename: api_label
 \* Description:
 \*/
import * as API from './'

export default {
  addLabel: params => API.POST('/label/add', params),
  editLabel: params => API.POST('/label/edit', params),
  delLabel: params => API.POST('/label/delete', params),
  listLabelsByPage: params => API.GET('/label/list', params),
  listAllLabels: params => API.GET('/label/all', params),
  changeUserLabel: params => API.POST('/user/label/change', params)
}
