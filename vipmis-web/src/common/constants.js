/**
 \* Created with IntelliJ IDEA.
 \* User: walter_long
 \* Date: 2018/5/26
 \* Time: 11:55
 \* Description: 该文件用于记录常用的下拉框中等静态值
 \*/

import Env from '../api/env';
let base = Env.baseURL;
export default {
  QUESTIONNAIRE: {
    NOTISSUE: "未发布",
    ISSUE: "已发布"
  },
  WELCOME_UPLOAD_URL: `${base}/welcome/img/upload`,
  IMG_2_BASE64_URL: `${base}/img/2/base64`,
}
