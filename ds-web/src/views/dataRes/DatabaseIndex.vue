<template>
  <div>
    <el-card class="box-card" style="height:100px;">
      <el-form :inline="true" :model="queryParam" label-width="80px" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="queryParam.dbName" placeholder="名称" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="URL">
          <el-input v-model="queryParam.dbUrl" placeholder="URL" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-select :clearable="true" v-model="queryParam.dbType" placeholder="类型">
            <el-option v-for="item in typeList" :key="item.name" :label="item.name" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否可用">
          <template>
            <el-radio v-model="queryParam.dbUsable" label="1">是</el-radio>
            <el-radio v-model="queryParam.dbUsable" label="0">否</el-radio>
          </template>
        </el-form-item>
        <el-form-item style="padding-left:50px;">
          <el-button type="primary" @click="query()">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
          <el-button type="success" @click="add()">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card content-view">
      <el-table v-loading="queryLoading" :data="pageData.rows" stripe>
        <el-table-column align="center" min-width="80" label="数据源类型" prop="dbType">
          <template slot-scope="scope">
            <svg-icon :icon-class="getDbIcon(scope.row.dbType)"/>
<!--            <el-tag :key="scope.row.dbType" style="color:#fff;border:none;"-->
<!--                    :color="getTagColor(scope.row.dbType)"-->
<!--                    effect="dark">-->
<!--              {{ scope.row.dbType }}-->
<!--            </el-tag>-->
          </template>
        </el-table-column>
        <el-table-column align="center" min-width="80" label="数据源名称" prop="dbName"></el-table-column>
        <el-table-column align="center" min-width="120" label="数据源URL" prop="dbUrl"></el-table-column>
        <el-table-column align="center" min-width="110" label="数据源用户名" prop="dbUsername"></el-table-column>
        <el-table-column align="center" min-width="100" label="创建时间" prop="createTime"></el-table-column>
        <el-table-column align="center" min-width="100" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="edit(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="deleteItem(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-inner">
        <el-pagination
          :current-page="pageData.currentPage"
          :page-size="pageData.pageSize"
          :page-sizes="pageData.pageSizes"
          layout="total, prev, pager, next, jumper"
          :total="pageData.total"
        ></el-pagination>
      </div>
    </el-card>
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" title="数据源编辑" width="30%">
      <el-container style="margin:20px 0px;">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="数据源类型">
            <el-radio-group v-model="form.dbType" @change="changeUrlModel">
              <el-radio-button v-for="item in typeList" :key="item.name" :label="item.name" :value="item.name"></el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="数据源名称">
            <el-input v-model="form.dbName"></el-input>
          </el-form-item>
          <el-form-item label="连接URL">
            <el-input v-model="form.dbUrl"></el-input>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="form.dbUsername"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.dbPassword"></el-input>
          </el-form-item>
        </el-form>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" icon="el-icon-link">测试连接</el-button>
        <el-button type="primary" :loading="saveLoading" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import API_DATABASE from "../../api/api_database";
  import CONSTANTS from "../../common/constants";
  import {clearObject} from "../../utils/util"

  export default {
    name: "Database",
    data() {
      return {
        active: 0,
        dialogVisible: false,
        saveLoading: false,
        queryLoading: false,
        queryParam: {
          dbName: '',
          dbUrl: '',
          dbType: ''
        },
        form: {
          name: null,
          dbType: null,
          dbUrl: null,
          dbUsername: null,
          dbPassword: null
        },
        typeList: CONSTANTS.DB_TYPES,
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
        _this.queryLoading = true;
        API_DATABASE.listPage(_this.queryParam).then(res => {
          if (!res) {
            return;
          }
          res.pageSizes = [5];
          _this.pageData = res;
          _this.queryLoading = false;
        }).catch(() => {
          _this.queryLoading = false;
        })
      },
      reset() {

      },
      add() {
        let _this = this;
        clearObject(_this.form);
        _this.dialogVisible = true;
      },
      edit(row) {
        let _this = this;
        _this.dialogVisible = true;
        API_DATABASE.getOne(row.id).then(res => {
          _this.form = res;
        })
      },
      deleteItem(row) {
        let _this = this;
        _this.$confirm('是否删除该数据源?').then(() => {
          API_DATABASE.delete(row.id).then(res => {
            _this.$message({
              message: '删除成功',
              type: 'success'
            });
            _this.query();
          })
        })
      },
      changeUrlModel() {
        let _this = this;
        _this.form.dbUrl = _this.fetchDbType(_this.form.dbType).urlModel;
      },
      save() {
        let _this = this;
        _this.saveLoading = true;
        API_DATABASE.save(_this.form).then(res => {
          _this.saveLoading = false;
          _this.dialogVisible = false;
          _this.query();
          _this.$message({
            message: '保存成功',
            type: 'success'
          });
        })
      },
      getTagColor(dbType) {
        let _this = this;
        let item = _this.fetchDbType(dbType);
        return item.tagColor;
      },
      getDbIcon(dbType) {
        let _this = this;
        let item = _this.fetchDbType(dbType);
        return item.icon;
      },
      fetchDbType(dbType) {
        let _this = this;
        for (let i = 0; i < _this.typeList.length; i++) {
          if (_this.typeList[i].name == dbType) {
            return _this.typeList[i];
          }
        }
      }
    },
    mounted() {
      let _this = this;
      _this.query();
    }
  }
</script>

<style scoped>
  .content-view {
    height: calc(100% - 164px);
  }

  .svg-icon {
    font-size: 25px;
  }

</style>
