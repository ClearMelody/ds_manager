/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/10/22
 \* @filename: api_welcome
 \* Description:
 \*/

import * as API from './'

export default {
  getLastWelcome: params => API.GET('/welcome/last', params),
}
