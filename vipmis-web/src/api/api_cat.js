/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/12/14
 \* @filename: api_cat
 \* Description:
 \*/
import * as API from './'

export default {
  listCatsByPage: params => API.GET('/cat/list', params),
  getCatDetail: params => API.GET('/cat/detail', params),
  addCat: params => API.POST('/cat/add', params),
  updateCat: params => API.POST('/cat/update', params),
  deleteCat: params => API.POST('/cat/delete', params),
  listCatLogsByPage: params => API.GET('/cat/log/list', params),
  addCatLog: params => API.POST('/cat/log/add', params),
  deleteCatLog: params => API.POST('/cat/log/delete', params)
}
