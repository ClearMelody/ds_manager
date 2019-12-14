<template>
  <div>
    <el-card class="box-card">
      <el-form :inline="true" :model="queryParam" style="text-align: start">
        <el-form-item label="猫咪名字">
          <el-input v-model="queryParam.name" placeholder @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="主人卡号">
          <el-input
            v-model="queryParam.cardCord"
            placeholder
            @keyup.enter.native.prevent="query()"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query()" :loading="loading">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <el-table v-loading="loading" :data="pageData.rows" stripe>
        <el-table-column align="center" min-width="80" label="猫咪名字" prop="name"></el-table-column>
        <el-table-column align="center" min-width="120" label="生日" prop="birthday"></el-table-column>
        <el-table-column align="center" min-width="110" label="性别" prop="sex"></el-table-column>
        <el-table-column align="center" min-width="110" label="主人卡号" prop="userInfoVo.cardCord"></el-table-column>
        <el-table-column align="center" min-width="240" label="操作">
          <template slot-scope="props">
            <el-button style="margin-top: 0.5rem;" type="primary" size="mini" @click="editCatDialogShow(props.row)">资料修改</el-button>
            <el-button style="margin-top: 0.5rem;" type="primary" size="mini" @click="catLogDialogShow(props.row)">日志查看</el-button>
            <el-button style="margin-top: 0.5rem;" type="primary" size="mini" @click="delDialogShow(props.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-inner">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageData.currentPage"
          :page-size="pageData.pageSize"
          :page-sizes="pageData.pageSizes"
          layout="total, prev, pager, next, jumper"
          :total="pageData.total"
        ></el-pagination>
      </div>
    </el-card>

    <el-dialog
      title="提示"
      :visible.sync="delDialog.dialogVisible"
      width="30%">
      <span>确定删除吗？</span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="delDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteCat()">确 定</el-button>
        </span>
    </el-dialog>

    <el-dialog
      title="猫咪日志"
      :visible.sync="catLogDialog.dialogVisible"
      width="90%">
      <cat-log :catId="catLogDialog.currentData.id"></cat-log>
    </el-dialog>

    <edit-cat v-if="editCatDialog.dialogVisible" :catInfo="editCatDialog.currentData" :closeDialogFunc="editCatDialogHide"></edit-cat>
  </div>
</template>

<script>
  import API from "../../api/api_cat";
  import {mapActions, mapGetters} from 'vuex';
  import CatLog from "./CatLog"
  import EditCat from "./EditCat"

  export default {
    name: "Cat",
    components: {
      CatLog,
      EditCat
    },
    computed: {
      ...mapGetters('pageJumpValue', [
        'userVue2CatVue'
      ]),
    },
    data() {
      return {
        loading: false,
        queryParam: {
          name: "",
          cardCord: "",
          limit: 10,
          page: 1
        },
        pageData: {
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5],
          total: 0,
          rows: []
        },
        delDialog: {
          dialogVisible: false,
          currentData: ""
        },
        catLogDialog: {
          dialogVisible: false,
          currentData: ""
        },
        editCatDialog: {
          dialogVisible: false,
          currentData: ""
        }
      };
    },
    methods: {
      ...mapActions('pageJumpValue', [
        'setUserVue2CatVueAct'
      ]),
      listCatsByPage() {
        let _that = this;
        _that.loading = true;
        API.listCatsByPage(_that.queryParam).then(result => {
          if (!result) {
            _that.loading = false;
            return;
          }
          result.pageSizes = [5];
          _that.pageData = result;
          _that.loading = false;
          // console.log(result);
        }).catch(() => {
          _that.loading = false;
        });
      },
      resetPage() {
        let _that = this;
        _that.queryParam = {
          name: "",
          cardCord: "",
          limit: 10,
          page: 1
        };
        _that.listCatsByPage();
      },
      handleCurrentChange(val) {
        let _that = this;
        _that.queryParam.page = val;
        _that.listCatsByPage();
      },
      handleSizeChange(val) {
        let _that = this;
        _that.queryParam.pageSize = val;
        _that.listCatsByPage();
      },
      query() {
        let _that = this;
        _that.listCatsByPage();
      },
      reset() {
        let _that = this;
        _that.resetPage();
      },
      delDialogShow(val) {
        let _that = this;
        _that.delDialog.currentData = val;
        _that.delDialog.dialogVisible = true;
      },
      deleteCat() {
        let _that = this;
        API.deleteCat(_that.delDialog.currentData).then(() => {
          _that.delDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {});
      },
      catLogDialogShow(val) {
        let _that = this;
        _that.catLogDialog.currentData = val;
        _that.catLogDialog.dialogVisible = true;
      },
      editCatDialogShow(val) {
        let _that = this;
        _that.editCatDialog.currentData = val;
        _that.editCatDialog.dialogVisible = true;
      },
      editCatDialogHide(needQuery) {
        let _that = this;
        _that.editCatDialog.dialogVisible = false;
        if (needQuery) {
          _that.query();
        }
      }
    },
    mounted() {
      let _that = this;
      if (_that.userVue2CatVue && _that.userVue2CatVue.cardCord) {
        _that.queryParam.cardCord = _that.userVue2CatVue.cardCord;
      }
      _that.query();
      _that.setUserVue2CatVueAct({cardCord: ""});
    }
  };
</script>

<style scoped>
  .el-form-item__label {
    width: 80px !important;
  }
</style>
