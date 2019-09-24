/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @time: 2018/11/15 12:01
 \* Description: 用户state，用于存放用户相关数据（用户、权限组、菜单）
 \*/
import API from "../../api/api_user";
const state = {
  // 当前用户
  currentUser: "fdfdsafdasffads",
  // 被选中的用户
  userDialog: {},
  // 用户列表分页
  users: "fasdfads",
};

const getters = {
  currentUser: state => {
    return state.currentUser;
  },
  users: state => {
    return state.users;
  }
};

/**
 * 登录后，将后台发送权限数据进行格式化处理
 * @param userData
 */
function formatCUserPermissions(userData) {
  let user = JSON.parse(userData);
  user.roleVo.permissionGroup.forEach(menu => {
    menu.permission = menu.permission.map(buttonPermission => {
      return buttonPermission.value;
    })
  });
  return user;
}

const actions = {
  /**
   * 用户登录
   * @param state
   * @param commit
   * @param params
   * @returns {Promise<any>}
   */
  loginAct({state, commit}, params) {
    return new Promise((resolve, reject) => {
      API.login(params).then(result => {
        if (result && result.errCode === CONSTANTS.SUCCESS) {
          let currentUser = formatCUserPermissions(result.data);
          commit('setCurrentUser', currentUser);
          resolve(result);
        } else {
          reject(result.msg)
        }
      }, error => {
        reject(error);
      }).catch(err => {
        reject(err);
      })
    })
  },
};

const mutations = {

  setCurrentUser(state, entity) {
    state.currentUser = entity
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
  namespaced: true
}
