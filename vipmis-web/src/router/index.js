import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/login/Login'
import User from '@/views/user/User'
import Admin from '@/views/admin/Admin'
import WxWelcome from '@/views/wxwelcome/WxWelcome'
import Questionnaire from '@/views/questionnaire/Questionnaire'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'root',
      redirect: 'home'
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
      redirect: '/home/questionnaire',
      children: [
        {
          path: 'questionnaire',
          component: Questionnaire,
          name: 'questionnaire',
        },
        {
          path: 'user',
          component: User,
          name: 'user',
        },
        {
          path: 'admin',
          component: Admin,
          name: 'admin',
        },
        {
          path: 'wxWelcome',
          component: WxWelcome,
          name: 'wxWelcome',
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    }
  ]
})
