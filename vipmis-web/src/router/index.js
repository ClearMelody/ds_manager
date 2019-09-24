import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
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
      ]
    },

  ]
})
