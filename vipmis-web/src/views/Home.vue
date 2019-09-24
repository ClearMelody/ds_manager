<template>
    <el-container style="height:100%">
      <el-header style="background-color: #fff; border-bottom: solid 1px #e6e6e6"><h1>问卷调查后台系统</h1></el-header>
      <el-container style="height:100%" height="100%">
        <el-aside width="13rem">
          <el-menu :default-active="menus.length !== 0 ? menus[0].link : ''" router>
            <el-menu-item v-for="(item) in menus" :key="item.id" :index="item.link">
              <i class="el-icon-menu"></i>
              <span slot="title">{{item.name}}</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main width="100%"><router-view></router-view></el-main>
      </el-container>
    </el-container>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex';
  export default {
    name: "Home",
    data() {
      return {
        menus: []
      }
    },
    computed: {
      ...mapGetters('user', {
        currentUser: 'currentUser',
        area: 'area',
        user: 'users'
      }),
      ...mapGetters('user', [
        'currentUser',
        'area',
        'users'
      ]),
    },
    methods: {
    },
    mounted() {
      this.menus.push({id: 1, name: "调查管理", link: "/home/questionnaire"}, {id: 2, name: "问卷管理", link: ""})
    }
  }
</script>

<style scoped>

</style>
