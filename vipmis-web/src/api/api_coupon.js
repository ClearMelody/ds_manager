/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/10/28
 \* @filename: api_coupon
 \* Description:
 \*/

import * as API from './'

export default {
  listCouponByPage: params => API.GET('/coupon/list', params),
  addCoupon: params => API.POST(`/coupon/add`, params),
  updateCoupon: params => API.POST(`/coupon/update`, params),
  listUserCouponByPage: params => API.GET(`/user/coupon/list`, params),
  addUserCoupon: params => API.POST(`/user/coupon/add`, params),
  updateUserCoupon: params => API.POST(`/user/coupon/use`, params)
}
