<template>
  <div>
    <el-card class="box-card">
      <el-form :inline="true" :model="queryParam" class="demo-form-inline">
        <el-form-item label="权限名称">
          <el-input v-model="queryParam.name" placeholder="权限名称" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="权限路径">
          <el-input v-model="queryParam.href" placeholder="权限路径" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="权限标识">
          <el-input v-model="queryParam.permission" placeholder="权限标识" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="queryParam.type" placeholder="请选择" clearable @change="query()">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="query()">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
          <el-button type="success" @click="handleAdd()">新增</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <el-table :data="permissionData" ref="multipleTable" default-expand-all tooltip-effect="dark" v-loading="loading" style="width: 100%" row-key="id" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <!--<el-table-column type="selection" prop="id"></el-table-column>-->
        <el-table-column prop="name" label="权限名称"></el-table-column>
        <el-table-column prop="href" label="路径"></el-table-column>
        <el-table-column prop="permission" label="权限标识"></el-table-column>
        <el-table-column prop="type" label="类型" :formatter="formatType"></el-table-column>
        <el-table-column prop="sort" label="排序"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" @click="handleAdd(scope.row)">新增</el-button>
            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="权限编辑" :visible.sync="dialogFormVisible">
      <el-form :model="form" v-loading="formLoading">
        <el-form-item v-if="form.pname != null" label="父级权限名称" :label-width="formLabelWidth">
            <span v-text="form.pname" style="padding-left:10px;color:blue;"></span>
        </el-form-item>
        <el-form-item label="权限名称" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限标识" :label-width="formLabelWidth">
          <el-input v-model="form.permission" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径" :label-width="formLabelWidth">
          <el-input v-model="form.href" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="样式（图标）" :label-width="formLabelWidth">
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" :label-width="formLabelWidth">
          <el-input v-model="form.sort" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSysPermission()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import api from "../../api/api_permission"
  export default {
    name: "Permission",
    data() {
      return {
        loading: false,
        dialogFormVisible: false,
        multipleSelection: [],
        formLoading: false,
        typeOptions: [{
          label: '菜单',
          value: 1
        },{
          label: '按钮',
          value: 2
        },{
          label: '面板',
          value: 3
        }],
        queryParam: {
          name: null,
          href: null,
          permission: null,
          type: null
        },
        form: {
          pid: null,
          pname: '',
          permission: null,
          path: null,
          name: null,
          href: null,
          icon: null,
          type: null,
          sort: null
        },
        permissionData: [],
        formLabelWidth: '120px'
      }
    },
    methods: {
      formatType(row, column, cellValue, index) {
        return row.type == 1 ? '菜单' : row.type == 2 ? '按钮' : (row.type == 3 ? '面板' : '未知');
      },
      reset() {
        this.queryParam = {
          name: null,
          href: null,
          permission: null,
          type: null
        }
      },
      query() {
        let _this = this;
        _this.loading = true;
        api.allPermissions(_this.queryParam).then(res => {
          _this.permissionData = res;
          _this.loading = false;
        })
      },
      handleAdd(row) {
        let _this = this;
        _this.dialogFormVisible = true;
        _this.formLoading = true;
        if (row) {
          _this.form = {
            pid: row.id,
            pname: row.name,
            permission: null,
            path: row.path,
            name: null,
            href: null,
            icon: null,
            type: null,
            sort: null
          };
        } else {//新增一级权限菜单
          _this.form = {
            pid: null,
            pname: null,
            permission: null,
            path: null,
            name: null,
            href: null,
            icon: null,
            type: null,
            sort: null
          };
        }
        _this.formLoading = false;
      },
      handleEdit(row) {
        let _this = this;
        _this.dialogFormVisible = true;
        _this.formLoading = true;
        api.getSysPermissionById(row.id).then(res => {
          _this.form = res;
          _this.formLoading = false;
        })
      },
      handleDelete(row) {
        let _this = this;
        _this.$confirm('确认删除该权限及其所有子权限？')
          .then(_ => {
            api.deleteSysPermission(row.id).then(res => {
              _this.query();
            })
          })
          .catch(_ => {});
      },
      saveSysPermission() {
        let _this = this;
        _this.formLoading = true;
        api.saveSysPermission(_this.form).then(res => {
          _this.formLoading = false;
          _this.dialogFormVisible = false;
          _this.query();
        })
      }
    },
    mounted() {
      var _this = this;
      _this.query();
    }
  }
</script>
