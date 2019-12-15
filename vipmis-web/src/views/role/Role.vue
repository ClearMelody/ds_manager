<template>
    <div>
        <el-card class="box-card">
          <el-form :inline="true" :model="queryParam" class="demo-form-inline">
            <el-form-item label="角色名">
              <el-input v-model="queryParam.name" placeholder="角色名" @keyup.enter.native.prevent="query()"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-if="hasPerm('role:query')" @click="query()">查询</el-button>
              <el-button type="primary" @click="reset()">重置</el-button>
              <el-button type="success" @click="roleAdd()">添加</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card class="box-card">
          <el-table v-loading="loading" :data="pageData.rows" stripe style="width: 100%">
            <el-table-column prop="name" label="角色名" min-width="200"></el-table-column>
            <el-table-column prop="createTime" label="添加时间" min-width="200"></el-table-column>
            <el-table-column prop="updateTime" label="修改时间" min-width="200"></el-table-column>
            <el-table-column align="center" min-width="240" label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="roleEdit(scope.row)">编辑</el-button>
                <el-button type="danger" size="mini" @click="roleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
        <el-dialog title="添加角色" :visible.sync="roleAddVisible" width="30%">
          <el-form :model="role" v-loading="formLoading">
            <el-form-item label="角色名" :label-width="'120'">
              <el-input v-model="role.name" placeholder="角色名" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="角色权限" :label-width="'120'"></el-form-item>
            <el-tree :data="permissionData" :default-checked-keys="role.permissionIds == null?[]:role.permissionIds.split(',')" check-strictly default-expand-all ref="tree" highlight-current show-checkbox node-key="id" :props="defaultProps"></el-tree>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="roleAddVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveRole()">确 定</el-button>
          </div>
        </el-dialog>
    </div>
</template>

<script>
  import API_PERMISSION from "../../api/api_permission";
  import API_ROLE from "../../api/api_role";
  export default {
    name: "Role",
    data() {
      return {
        loading: false,
        formLoading: false,
        queryParam: {
          name: '',
          description: ''
        },
        treeParam: {
          name: ''
        },
        role: {
          name: null,
          permissionIds: null
        },
        roleAddVisible : false,
        pageData: {
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5],
          total: 0,
          rows: []
        },
        permissionData: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    methods: {
      query() {
        if (!this.hasPerm('role:query')) {
          this.noPermTip();
        }
        let _this = this;
        _this.loading = true;
        API_ROLE.listSysRolesByPage(_this.queryParam).then(res => {
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
      roleAdd() {
        var _this = this;
        _this.role = {
          name: null,
          permissionIds: null
        }
        _this.roleAddVisible = true;
        _this.initPermissionTree();
      },
      roleEdit(row) {
        let _this = this;
        _this.role = {
          name: null,
          permissionIds: null
        }
        _this.formLoading = true;
        API_ROLE.getSysRole(row.id).then(res => {
          _this.roleAddVisible = true;
          _this.role = res;
          _this.initPermissionTree();
        })
      },
      roleDelete(row) {
        let _this = this;
        _this.$confirm('确认删除该角色？')
          .then(_ => {
            API_ROLE.deleteSysRole(row.id).then(res => {
              _this.query();
            })
          })
          .catch(_ => {});
      },
      saveRole() {
        let _this = this;
        let checkedKeys = _this.$refs.tree.getCheckedKeys();
        _this.role.permissionIds = checkedKeys.toString();
        _this.formLoading = true;
        API_ROLE.saveRole(_this.role).then(res => {
          _this.roleAddVisible = false;
          _this.formLoading = false;
          _this.query();
        })
      },
      initPermissionTree() {
        let _this = this;
        API_PERMISSION.allPermissions(_this.treeParam).then(res => {
          _this.permissionData = res;
          _this.formLoading = false;
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
