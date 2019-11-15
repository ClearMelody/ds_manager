import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/login/Login'
import User from '@/views/user/User'
import Admin from '@/views/admin/Admin'
import WxWelcome from '@/views/wxwelcome/WxWelcome'
import Coupon from '@/views/coupon/Coupon'
import Homepage from '@/views/homepage/Homepage'
import Label from '@/views/label/Label'
import Role from '@/views/role/Role'

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
      redirect: '/home/index',
      children: [
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
        {
          path: 'coupon',
          component: Coupon,
          name: 'coupon',
        },
        {
          path: 'label',
          component: Label,
          name: 'label',
        },
        {
          path: 'index',
          component: Homepage,
          name: 'index',
        },
        {
          path: 'role',
          component: Role,
          name: 'role',
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
