import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import Login from '@/views/login/Login'
import User from '@/views/user/User'
import Cat from '@/views/cat/Cat'
import CardDescription from '@/views/carddescription/CardDescription'
import Admin from '@/views/admin/Admin'
import WxWelcome from '@/views/wxwelcome/WxWelcome'
import Coupon from '@/views/coupon/Coupon'
import Homepage from '@/views/homepage/Homepage'
import Label from '@/views/label/Label'
import Role from '@/views/role/Role'
import Permission from '@/views/permission/Permission'
import SysUser from '@/views/sysUser/SysUser'

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: 'root',
    redirect: 'login'
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
        path: 'cat',
        component: Cat,
        name: 'cat',
      },
      {
        path: 'cardDescription',
        component: CardDescription,
        name: 'cardDescription',
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
        path: 'sysUser',
        component: SysUser,
        name: 'sysUser',
      },
      {
        path: 'role',
        component: Role,
        name: 'role',
      },
      {
        path: 'permission',
        component: Permission,
        name: 'permission',
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  }
];

const router = new Router({
  routes
});

// router.beforeEach((to, from, next) => {
//   let loginFlag = sessionStorage.getItem("loginFlag");
//   if (to.fullPath === "/login" || loginFlag === "isLogin") {
//     next();
//   } else {
//     next({
//       path: '/login'
//     })
//   }
// });

export default router
