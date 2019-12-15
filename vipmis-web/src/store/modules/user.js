import {getToken, removeToken, setToken} from '@/utils/auth'
import store from '../../store'
import router from '../../router'
import API_LOGIN from "../../api/api_login"

const user = {
  state: {
    id: "",
    role: '',
    menus: [],
    permissions: [],
  },
  mutations: {
    SET_USER: (state, userInfo) => {
      state.id = userInfo.id;
      state.role = userInfo.sysRoleVo;
      state.menus = userInfo.menus;
      state.permissions = userInfo.permissions;
    },
    RESET_USER: (state) => {
      state.id = "";
      state.role = '';
      state.menus = [];
      state.permissions = [];
    }
  },
  actions: {
    // 登录
    Login({commit, state}, loginForm) {
      return new Promise((resolve, reject) => {
        API_LOGIN.login(loginForm).then(data => {
          if (data.result === "success") {
            setToken();
          }
          resolve(data);
        })
      })
    },
    // 获取用户信息
    GetInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        API_LOGIN.getUserInfo(null).then(data => {
          if (data) {
            //储存用户信息
            commit('SET_USER', data);
            setToken();
            resolve(data)
          } else {
            removeToken();
          }
          console.log(data);
        }).catch(() => {
          console.log("error");
          removeToken();
        })
      })
    },
    // 登出
    LogOut({commit}) {
      return new Promise((resolve) => {
        API_LOGIN.logout(null).then(data => {
          commit('RESET_USER')
          removeToken()
          resolve(data);
        }).catch(() => {
          commit('RESET_USER')
          removeToken()
        })
      })
    },
  }
}
export default user
