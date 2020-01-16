/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2020/1/8
 \* @filename: api_data_table.js
 \* Description:
 \*/
import * as API from './'

export default {
  save: params => API.PUT('/data-table/save', params),
  getOne: id => API.GET('/data-table/getOne/' + id, null),
  delete: id => API.DELETE('/data-table/delete/' + id,null),
  listAll: params => API.GET('/data-table/listAll', params)
}
