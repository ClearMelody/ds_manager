/**
 \* Created with IntelliJ IDEA.
 \* @author: walter_long
 \* @date: 2019/12/14
 \* @filename: pageJumpValue
 \* Description: 页面跳转传值
 \*/
const state = {
  // userVue跳转至catVue带的值
  userVue2CatVue: {
    cardCord: ""
  },
};

const getters = {
  userVue2CatVue: state => {
    return state.userVue2CatVue;
  }
};

const actions = {
  /**
   * 用户登录
   * @param state
   * @param commit
   * @param params
   * @returns {Promise<any>}
   */
  setUserVue2CatVueAct({state, commit}, params) {
    commit('setUserVue2CatVue', params);
  },
};

const mutations = {
  setUserVue2CatVue(state, entity) {
    state.userVue2CatVue = entity
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
  namespaced: true
}
