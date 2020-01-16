/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/01/02
 \* @filename: api_data-resource.js
 \* Description:
 \*/
import * as API from './'

export default {
  save: params => API.PUT('/data-resource/save', params),
  listAll: params => API.GET('/data-resource/listAll', params),
  delete: id => API.DELETE('/data-resource/delete/' + id, null)
}
