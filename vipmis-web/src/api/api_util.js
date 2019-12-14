/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/12/14
 \* @filename: api_util
 \* Description:
 \*/
import * as API from './'

export default {
  img2Base64: params => API.POST('/img/2/base64', params)
}
