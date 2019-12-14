/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/12/15
 \* @filename: api_card_description
 \* Description:
 \*/
import * as API from './'

export default {
  addCardDescription: params => API.POST('/cardDesc/add', params),
  getCardDescription: params => API.GET('/cardDesc/last', params)
}
