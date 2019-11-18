/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/11/16
 \* @filename: api_statistic
 \* Description:
 \*/
import * as API from './'

export default {
  getProductProportion: params => API.GET('/statistic/product/proportion', params),
  getUserGrowth: params => API.GET('/statistic/user/growth', params),

}
