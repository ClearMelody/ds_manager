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
  addCat: params => API.POST('/cat/add', params),
  updateCat: params => API.POST('/cat/update', params),
  deleteCat: params => API.POST('/cat/delete', params)
}
