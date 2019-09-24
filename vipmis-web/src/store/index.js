import Vue from "vue";
import Vuex from "vuex";
import user from  './modules/user'
import questionnaire from  './modules/questionnaire'

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== "production";

export default new Vuex.Store({
  modules: {
    user,
    questionnaire
  },
  // 不使用严格模式，vuex绑定checkbox中的坑
  // strict: debug,
  strict: false,
});
