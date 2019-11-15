<template>
    <el-container style="height:100%">
      <el-header style="background-color: #fff; border-bottom: solid 1px #e6e6e6"><h1>喵酱后台管理系统</h1></el-header>
      <el-container style="height:100%" height="100%">
        <el-aside width="13rem">
          <el-menu :default-active="menuDefaultActive" router>
            <div v-for="(item) in menus" :key="item.id">
              <el-menu-item v-if="!item.hasChild" :index="item.link">
                <i class="el-icon-menu"></i>
                <span slot="title">{{item.name}}</span>
              </el-menu-item>
              <el-submenu v-else index="">
                <template slot="title">
                  <i class="el-icon-location"></i>
                  <span>{{item.name}}</span>
                </template>
                <el-menu-item-group>
                  <el-menu-item v-for="(it) in item.childs" :key="it.id" :index="it.link">
                    <i class="el-icon-menu"></i>
                    <span slot="title">{{it.name}}</span>
                  </el-menu-item>
                </el-menu-item-group>
              </el-submenu>
            </div>
<!--            <el-menu-item v-for="(item) in menus" :key="item.id" :index="item.link">-->
<!--              <i class="el-icon-menu"></i>-->
<!--              <span slot="title">{{item.name}}</span>-->
<!--            </el-menu-item>-->
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
        menus: [],
        menuDefaultActive: ""
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
      let _this = this;
      this.menus.push({id: 1, name: "首页", hasChild: false, link: "/home/index"});
      this.menus.push({id: 6, name: "用户管理", hasChild: true, childs: [{id: 8, name: "会员管理", link: "/home/user"}, {id: 7, name: "分组管理", link: "/home/label"}, {id: 9, name: "优惠券管理", link: "/home/coupon"}]});
      this.menus.push({id: 2, name: "系统管理", hasChild: true, childs: [{id: 5, name: "欢迎页管理", link: "/home/wxWelcome"}, {id: 3, name: "管理员管理", link: "/home/admin"}, {id: 4, name: "权限管理", link: "/home/role"}]});
      _this.menuDefaultActive = _this.$route.path;
    }
  }
</script>

<style scoped>

</style>
