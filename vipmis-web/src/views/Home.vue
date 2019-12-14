<template>
  <el-container style="height:100%;">
    <el-container style="height:100%;">
      <el-aside style=" background-color:#001529">
        <div class="logoBar">
          <img src="../assets/logo_1.png" alt srcset class="logo" />
          <span>喵匠后台管理系统</span>
        </div>
        
        <el-menu
          :default-active="menuDefaultActive"
          router
          :unique-opened="true"
          background-color="#001529"
          text-color="rgba(255,255,255,0.65)"
          active-text-color="#ffd04b"
        >
          <div v-for="(item) in menus" :key="item.id">
            <el-menu-item v-if="!item.hasChild" :index="item.link">
              <i class="el-icon-s-home"></i>
              <span slot="title">{{item.name}}</span>
            </el-menu-item>
            <el-submenu v-else :index="item.id.toString()">
              <template slot="title">
                <i :class="item.icon"></i>
                <span>{{item.name}}</span>
              </template>
              <el-menu-item-group>
                <el-menu-item v-for="(it) in item.childs" :key="it.id" :index="it.link">
                  <i :class="it.icon"></i>
                  <span slot="title">{{it.name}}</span>
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </div>
        </el-menu>
      </el-aside>
      <el-main width="100%">
        <el-header>
        </el-header>
        <router-view class="myview"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import { mapGetters } from "vuex";
  export default {
    name: "Home",
    data() {
      return {
        menus: [],
        menuDefaultActive: ""
      };
    },
    computed: {
      ...mapGetters("user", {
        currentUser: "currentUser",
        area: "area",
        user: "users"
      }),
      ...mapGetters("user", ["currentUser", "area", "users"])
    },
    methods: {},
    mounted() {
      let _that = this;
      this.menus.push({id: 1, name: "首页", hasChild: false, link: "/home/index"});
      this.menus.push({id: 6, name: "用户管理", icon:"el-icon-s-custom", hasChild: true, childs: [{id: 8, name: "会员管理", link: "/home/user"}, {id: 18, name: "猫咪管理", link: "/home/cat"},{id: 7, name: "分组管理", link: "/home/label"}]});
      this.menus.push({id: 2, name: "系统管理", icon:"el-icon-s-tools", hasChild: true, childs: [{id: 5, name: "分享管理", link: "/home/wxWelcome"}, {id: 3, name: "管理员管理", link: "/home/admin"}, {id: 4, name: "角色管理", link: "/home/role"}, {id: 10, name: "权限管理", link: "/home/permission"}]});
      _that.menuDefaultActive = _that.$route.path;
    }
  };
</script>
<style scoped >
.el-menu {
  text-align: left;
  border: none;
}
.el-main {
  height: 100%;
  overflow: auto;
  padding: 0px;
}
.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  position: relative;
  z-index: 2;
}
.myview {
  height: calc(100% - 60px);
  overflow: auto;
}
.logo {
  width: 48px;
  height: 48px;
}
.logoBar{
padding: 0 20px;
}
.logoBar span{
display: inline-block;
    margin: 0 0 0 12px;
    color: #fff;
    font-weight: 600;
    font-size: 20px;
    font-family: Avenir,Helvetica Neue,Arial,Helvetica,sans-serif;
    vertical-align: 14px;
}
.logoBar{
padding: 4px 20px;
margin-bottom: 20px;
}

.el-aside{
box-shadow: 2px 0 6px rgba(0,21,41,.35);
position: relative;
z-index: 2;
}
.el-submenu .el-menu-item{
  background: #00070e!important;
}
.el-menu-item.is-active{
  background: #e6a23c!important;
  color: #fff!important;
}
</style>
