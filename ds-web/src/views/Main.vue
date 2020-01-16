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
    name: "Main",
    data() {
      return {
        menuDefaultActive: "",
        isCollapse: true,
        collapseClass: "el-icon-s-unfold",
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
        href: "/main/monitorIndex",
        children: []
      });
      this.menus.push({
        id: 2,
        name: "数据资源",
        icon: "el-icon-notebook-2",
        hasChild: false,
        href: "/main/resourceIndex",
        children: []
      });
      this.menus.push({
        id: 3,
        name: "数据源",
        icon: "el-icon-coin",
        hasChild: false,
        href: "/main/databaseIndex",
        children: []
      });
      this.menus.push({
        id: 4,
        name: "数据字典",
        icon: "el-icon-collection",
        hasChild: false,
        href: "/main/dictionary",
        children: []
      });
      this.menuDefaultActive = this.$route.path;
    }
  };
</script>
<style scoped>
  .el-menu-vertical:not(.el-menu--collapse) {
    width: 150px;
    min-height: 400px;
  }
  .myview {
    height: 100%;
    overflow: auto;
  }
  .el-header {
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    position: relative;
    z-index: 2;
  }
  .el-menu {
    text-align: left;
    border: none;
  }

  .el-menu-vertical:not(.el-menu--collapse) {
    width: 150px;
    min-height: 400px;
  }

  .el-main {
    height: 100%;
    padding: 0px;
  }
</style>
