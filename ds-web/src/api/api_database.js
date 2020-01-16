/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/01/02
 \* @filename: api_database.js
 \* Description:
 \*/
import * as API from './'

export default {
  save: params => API.PUT('/database/save', params),
  getOne: id => API.GET('/database/getOne/' + id, null),
  listPage: params => API.POST('/database/listPage', params),
  listAll: params => API.GET('/database/listAll', params),
  delete: id => API.DELETE('/database/delete/' + id, null)
}
