/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2019/12/06
 \* @filename: api_role
 \* Description:
 \*/
import * as API from './'

export default {
  saveRole: params => API.PUT('/sysrole/saveRole', params),
  listSysRolesByPage: params => API.POST('/sysrole/listSysRolesByPage', params),
  getSysRole: id => API.GET('/sysrole/getSysRole/' + id, null),
  deleteSysRole: id => API.DELETE('/sysrole/deleteSysRole/' + id, null)
}
