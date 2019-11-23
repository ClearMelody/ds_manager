/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2018/12/13
 \* @time: 15:02
 \* Description: 用户管理后端交互接口
 \*/

import * as API from './'

export default {
  listUsersByPage: params => API.GET('/user/list', params),
  charge: params => API.POST(`/user/charge`, params),
  pay: params => API.POST(`/user/pay`, params),
  useGoal: params => API.POST(`/user/goal/use`, params),
  updateUser: params => API.POST(`/user/update`, params)
}
