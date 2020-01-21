import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/Main'
import MonitorIndex from '@/views/dataRes/monitor/MonitorIndex'
import ResourceIndex from '@/views/dataRes/resource/ResourceIndex'
import DatabaseIndex from '@/views/dataRes/DatabaseIndex'
import DictionaryIndex from '@/views/dataRes/DictionaryIndex'

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
        path: 'DictionaryIndex',
        component: DictionaryIndex,
        name: 'dictionaryIndex'
      },
      {
        path: 'MonitorIndex',
        component: MonitorIndex,
        name: 'monitorIndex'
      }
    ]
  }
];

const router = new Router({
  routes
});

export default router
