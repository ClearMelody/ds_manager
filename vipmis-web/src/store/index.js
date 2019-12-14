import Vue from "vue";
import Vuex from "vuex";
import user from  './modules/user'
import pageJumpValue from  './modules/pageJumpValue'

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== "production";

export default new Vuex.Store({
  modules: {
    user,
    pageJumpValue
  },
  // 不使用严格模式，vuex绑定checkbox中的坑
  // strict: debug,
  strict: false,
});
