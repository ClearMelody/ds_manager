// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueLogger from 'vuejs-logger';
import { HappyScroll } from 'vue-happy-scroll';
import 'vue-happy-scroll/docs/happy-scroll.css'
import './icons';

const isProduction = process.env.NODE_ENV === 'production';

const options = {
  isEnabled: true,
  logLevel : isProduction ? 'error' : 'debug',
  stringifyArguments : false,
  showLogLevel : false,
  showMethodName : false,
  separator: '|',
  showConsoleColors: false
};

Vue.use(VueLogger, options);
Vue.use(ElementUI);
Vue.component('happy-scroll', HappyScroll);

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
