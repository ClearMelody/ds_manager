<template>
  <div>
    <el-card class="box-card">
      <el-form :inline="true" :model="queryParam" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input v-model="queryParam.name" placeholder="用户名" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query()">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
          <el-button type="success" @click="add()">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <el-table v-loading="loading" :data="pageData.rows" stripe style="width: 100%">
        <el-table-column prop="name" label="用户名" min-width="200"></el-table-column>
        <el-table-column prop="createTime" label="添加时间" min-width="200"></el-table-column>
        <el-table-column prop="roleName" label="角色名" min-width="200"></el-table-column>
        <el-table-column align="center" min-width="240" label="操作">
          <template slot-scope="scope">
            <!--<el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>-->
            <el-button type="danger" size="mini" @click="delete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="添加用户" :visible.sync="addVisible" width="40%">
      <el-form :model="form" v-loading="formLoading">
        <el-form-item label="用户名" :label-width="'120px'">
          <el-input v-model="form.name" placeholder="用户名" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="'120px'">
          <el-input v-model="form.password" placeholder="密码" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="用户角色" :label-width="'120px'">
          <el-select v-model="form.roleId" v-loading="roleSelectLoading" placeholder="请选择">
            <el-option v-for="item in roles" :key="item.value" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import API_SYSUSER from "../../api/api_sysUser";
  import API_ROLE from "../../api/api_role";
  export default {
    name: "SysUser",
    data() {
      return {
        loading: false,
        formLoading: false,
        roleSelectLoading: false,
        roles: [],
        queryParam: {
          name: '',
          description: ''
        },
        form: {
          name: null,
          password: null,
          roleId: null
        },
        addVisible : false,
        pageData: {
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5],
          total: 0,
          rows: []
        }
      }
    },
    methods: {
      query() {
        let _this = this;
        _this.loading = true;
        API_SYSUSER.listSysRolesByPage(_this.queryParam).then(res => {
          if (!res) {
            return;
          }
          res.pageSizes = [5];
          _this.pageData = res;
          _this.loading = false;
        }).catch(() => {
          _this.loading = false;
        })
      },
      reset() {

      },
      add() {
        var _this = this;
        _this.form = {
          name: null,
          password: null,
          roleId: null
        }
        _this.addVisible = true;
        _this.initRoleSelect();
      },
      edit(row) {
        let _this = this;
        _this.form = {
          name: null,
          password: null,
          roleId: null
        }
        _this.formLoading = true;
        API_SYSUSER.getSysUser(row.id).then(res => {
          _this.user = res;
          _this.addVisible = true;
          _this.initRoleSelect();
        })
      },
      delete(row) {
        let _this = this;
        _this.$confirm('确认删除该用户？')
          .then(_ => {
            API_SYSUSER.delete(row.id).then(res => {
              _this.query();
            })
          })
          .catch(_ => {});
      },
      save() {
        let _this = this;
        _this.formLoading = true;
        // API_SYSUSER.saveSysUser(_this.form).then(res => {
        //   _this.addVisible = false;
        //   _this.formLoading = false;
        //   _this.query();
        // })
      },
      initRoleSelect() {
        let _this = this;
        _this.roleSelectLoading = true;
        API_ROLE.listAllBaseRoleInfos().then(res => {
          _this.roles = res;
          _this.roleSelectLoading = false;
        })
      }
    },
    mounted() {
      let _this = this;
      _this.query();
    }
  }
</script>

<style scoped>

</style>
