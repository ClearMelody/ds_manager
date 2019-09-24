/**
 \* Created with IntelliJ IDEA.
 \* User: walter_long
 \* Date: 2018/4/14
 \* Time: 22:17
 \* Description:
 \*/

import Env from './env';
import axios from 'axios'

axios.defaults.withCredentials = true;

//基地址
let base = Env.baseURL;

//测试使用
export const ISDEV = Env.isDev;

// download url
export const download = url => {
  location.href = `${base}${url}`;
};

function dealResultData(res) {
  if (res && res.data && res.data.errcode === 0) {
    return res.data.data
  } else if (res && res.data && res.data.errcode !== 0) {
    alert(res.data.errmsg);
    return null
  } else {
    alert("系统错误，请刷新页面");
    return null
  }
}

//通用方法
export const POST = (url, params) => {
  return axios.post(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  })
};

export const GET = (url, params) => {
  return axios.get(`${base}${url}`, {params: params}).then(res => {
    return dealResultData(res)
  })
};

export const PUT = (url, params) => {
  return axios.put(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  })
};

export const DELETE = (url, params) => {
  return axios.delete(`${base}${url}`, {params: params}).then(res => {
    return dealResultData(res)
  })
};

export const PATCH = (url, params) => {
  return axios.patch(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  })
};
