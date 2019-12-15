/**
 \* Created with IntelliJ IDEA.
 \* @author: wyk
 \* @date: 2019/12/09
 \* @filename: api_sysUser
 \* Description:
 \*/
import * as API from './'

export default {
  saveSysUser: params => API.PUT('/sysuser/saveSysUser', params),
  listSysRolesByPage: params => API.POST('/sysuser/list', params)
}
