<template>
  <el-container style="height:100%;">
    <el-container style="height:100%;">
      <el-menu default-active="menuDefaultActive" router :collapse="isCollapse" class="el-menu-vertical"
               background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <div v-for="(item) in menus" :key="item.id">
          <el-menu-item @click="changeTitle(item.name)" v-if="item.children.length == 0" :index="item.href">
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
          </el-menu-item>
        </div>
      </el-menu>
      <el-main width="100%">
        <el-container class="myview">
          <el-header style="border-bottom: 1px solid #ccc;height:50px;">
            <el-row>
              <el-col :span="1"><i @click="collapse()" :class="collapseClass"
                                   style="font-size: 30px;margin-top: 10px;cursor: pointer;"></i></el-col>
              <el-col :span="22" style="text-align:center;"><span
                style="font-size: 20px;line-height: 50px;cursor: pointer;">{{title}}</span></el-col>
            </el-row>
          </el-header>
          <el-main class="myview">
            <router-view class="myview"></router-view>
          </el-main>
        </el-container>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        menuDefaultActive: "",
        isCollapse: false,
        collapseClass: "el-icon-s-fold",
        menus: [],
        title: '资源统计'
      }
    },
    methods: {
      collapse() {
        if (this.isCollapse) {
          this.collapseClass = "el-icon-s-fold";
        } else {
          this.collapseClass = "el-icon-s-unfold";
        }
        this.isCollapse = !this.isCollapse;
      },
      changeTitle(title) {
        this.title = title;
      }
    },
    mounted() {
      this.menus.push({
        id: 1,
        name: "资源统计",
        icon: "el-icon-s-data",
        hasChild: false,
        href: "/home/monitorIndex",
        children: []
      });
      this.menus.push({
        id: 2,
        name: "数据资源",
        icon: "el-icon-notebook-2",
        hasChild: false,
        href: "/home/sourceSchema",
        children: []
      });
      this.menus.push({
        id: 3,
        name: "数据字典",
        icon: "el-icon-collection",
        hasChild: false,
        href: "/home/dictionary",
        children: []
      });
      this.menuDefaultActive = this.$route.path;
    }
  };
</script>
<style scoped>
  .el-menu {
    text-align: left;
    border: none;
  }

  .el-menu-vertical:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }

  .el-main {
    height: 100%;
    padding: 0px;
  }

  .el-header {
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    position: relative;
    z-index: 2;
  }

  .myview {
    height: calc(100% - 61px);
    overflow: auto;
  }

  .logo {
    width: 48px;
    height: 48px;
  }

  .logoBar {
    padding: 0 20px;
  }

  .logoBar span {
    display: inline-block;
    margin: 0 0 0 12px;
    color: #fff;
    font-weight: 600;
    font-size: 20px;
    font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    vertical-align: 14px;
  }

  .logoBar {
    padding: 4px 20px;
    margin-bottom: 20px;
  }

  .el-aside {
    box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
    position: relative;
    z-index: 2;
  }

  .el-submenu .el-menu-item {
    background: #00070e !important;
  }

  .el-menu-item.is-active {
    background: #e6a23c !important;
    color: #fff !important;
  }
</style>
