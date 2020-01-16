/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/1/13
 \* @filename: api_dictionary_type.js
 \* Description:
 \*/
import * as API from './'

export default {
  save: params => API.PUT('/dictionary-type/save', params),
  getOne: id => API.GET('/dictionary-type/getOne/' + id, null),
  delete: id => API.DELETE('/dictionary-type/delete/' + id, null),
  listAll: params => API.GET('/dictionary-type/listAll/', params)
}
