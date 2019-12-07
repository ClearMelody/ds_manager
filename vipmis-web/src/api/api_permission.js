/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2019/12/02
 \* @filename: api_permission
 \* Description:
 \*/
import * as API from './'

export default {
  allPermissions: params => API.POST('/permission/allPermissions', params),
  getSysPermissionById: id => API.GET('/permission/getSysPermissionById/' + id, null),
  saveSysPermission: params => API.PUT('/permission/saveSysPermission', params),
  deleteSysPermission: id => API.DELETE('/permission/deleteSysPermission/' + id, null)
}
