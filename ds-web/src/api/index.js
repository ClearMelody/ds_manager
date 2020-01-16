/**
 \* Created with IntelliJ IDEA.
 \* User: wyk
 \* Date: 2020/01/02
 \* Description:
 \*/

import Env from './env';
import axios from 'axios';
import { Message } from 'element-ui';

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
    Message.error('系统错误，请刷新页面');
    return null
  }
}

//通用方法
export const POST = (url, params) => {
  return axios.post(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  }).catch(() => {
    Message.error('系统错误，请刷新页面');
  });
};

export const GET = (url, params) => {
  return axios.get(`${base}${url}`, {params: params}).then(res => {
    return dealResultData(res)
  }).catch(() => {
    Message.error('系统错误，请刷新页面');
  });
};

export const PUT = (url, params) => {
  return axios.put(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  }).catch(() => {
    Message.error('系统错误，请刷新页面');
  });
};

export const DELETE = (url, params) => {
  return axios.delete(`${base}${url}`, {params: params}).then(res => {
    return dealResultData(res)
  }).catch(() => {
    Message.error('系统错误，请刷新页面');
  });
};

export const PATCH = (url, params) => {
  return axios.patch(`${base}${url}`, params).then(res => {
    return dealResultData(res)
  }).catch(() => {
    Message.error('系统错误，请刷新页面');
  });
};

export const EXPORT = (url, fileName, params) => {
  return axios.get(`${base}${url}`,{params: params,responseType: 'blob'}).then(res => {
    console.log(res)
    if (!res) {
      return
    }
    //将response 新建成文件流
    var blob = new Blob([res.data], {type: 'application/vnd.ms-excel;charset=utf-8'})
    //创建下载地址以及a标签,并且模拟a标签的点击事件进行下载文件。
    var url = window.URL.createObjectURL(blob);
    var aLink = document.createElement("a");
    aLink.style.display = "none";
    aLink.download = fileName;
    aLink.href = url;
    document.body.appendChild(aLink)
    aLink.click()
    document.body.removeChild(aLink); //下载完成移除元素
    window.URL.revokeObjectURL(url); //释放掉blob对象
  }).catch(() => {
    //alert("系统错误，请刷新页面");
  });
};
