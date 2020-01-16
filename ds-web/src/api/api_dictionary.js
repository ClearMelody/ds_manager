/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/1/13
 \* @filename: api_dictionary.js
 \* Description:
 \*/
import * as API from './'

export default {
  save: params => API.PUT('/dictionary/save', params),
  getOne: id => API.GET('/dictionary/getOne/' + id, null),
  delete: id => API.DELETE('/dictionary/delete/' + id, null),
  listPage: params => API.GET('/dictionary/listPage', params)
}
