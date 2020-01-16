import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/Main'
import MonitorIndex from '@/views/dataRes/MonitorIndex'
import ResourceIndex from '@/views/dataRes/resource/ResourceIndex'
import DatabaseIndex from '@/views/dataRes/DatabaseIndex'
import Dictionary from '@/views/dataRes/Dictionary'

Vue.use(Router)

const routes = [
  {
    path: '/',
    name: 'root',
    redirect: 'main'
  },
  {
    path: '/main',
    name: 'main',
    component: Main,
    redirect: '/main/monitorIndex',
    children: [
      {
        path: 'monitorIndex',
        component: MonitorIndex,
        name: 'monitorIndex'
      },
      {
        path: 'resourceIndex',
        component: ResourceIndex,
        name: 'resourceIndex'
      },
      {
        path: 'databaseIndex',
        component: DatabaseIndex,
        name: 'databaseIndex'
      },
      {
        path: 'Dictionary',
        component: Dictionary,
        name: 'dictionary'
      }
    ]
  }
];

const router = new Router({
  routes
});

export default router
