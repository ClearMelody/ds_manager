<template>
  <div>
    <el-card class="box-card">
      <el-form :inline="true" :model="queryParam" style="text-align: start">
        <el-form-item>
<!--          <el-button type="primary" @click="query()" :loading="loading">查询</el-button>-->
<!--          <el-button type="primary" @click="reset()">重置</el-button>-->
          <el-button type="primary" @click="addCatLogDialogShow()">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card class="box-card">
      <el-table v-loading="loading" :data="pageData.rows" stripe>
        <el-table-column align="center" min-width="110" label="洗澡时间" prop="washTime"></el-table-column>
        <el-table-column align="center" min-width="80" label="体重（Kg）" prop="weight"></el-table-column>
        <el-table-column align="center" min-width="110" label="建议体内驱虫时间" prop="vivo"></el-table-column>
        <el-table-column align="center" min-width="110" label="建议体外驱虫时间" prop="vitro"></el-table-column>
        <el-table-column align="center" min-width="240" label="操作">
          <template slot-scope="props">
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
      append-to-body
      :visible.sync="delDialog.dialogVisible"
      width="30%">
      <span>确定删除吗？</span>
      <span slot="footer" class="dialog-footer">
          <el-button @click="delDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteCatLog()">确 定</el-button>
        </span>
    </el-dialog>

    <add-cat-log v-if="addCatLogDialog.dialogVisible" :catId="catId" :closeDialogFunc="addCatLogDialogHide"></add-cat-log>
  </div>
</template>

<script>
  import API from "../../api/api_cat";
  import AddCatLog from "./AddCatLog"
  export default {
    name: "CatLog",
    props: {
      catId: {
        type: String
      }
    },
    components: {
      AddCatLog
    },
    data() {
      return {
        loading: false,
        queryParam: {
          catId: "",
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
        addCatLogDialog: {
          dialogVisible: false,
          currentData: ""
        }
      };
    },
    methods: {
      listCatLogsByPage() {
        let _that = this;
        _that.loading = true;
        API.listCatLogsByPage(_that.queryParam).then(result => {
          if (!result) {
            _that.loading = false;
            return;
          }
          result.pageSizes = [5];
          _that.pageData = result;
          _that.loading = false;
        }).catch(() => {
          _that.loading = false;
        });
      },
      resetPage() {
        let _that = this;
        _that.queryParam = {
          catId: _that.queryParam.catId,
          limit: 10,
          page: 1
        };
        _that.listCatLogsByPage();
      },
      handleCurrentChange(val) {
        let _that = this;
        _that.queryParam.page = val;
        _that.listCatLogsByPage();
      },
      handleSizeChange(val) {
        let _that = this;
        _that.queryParam.pageSize = val;
        _that.listCatLogsByPage();
      },
      query() {
        let _that = this;
        _that.listCatLogsByPage();
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
      deleteCatLog() {
        let _that = this;
        API.deleteCatLog(_that.delDialog.currentData).then(() => {
          _that.delDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {});
      },
      addCatLogDialogShow() {
        let _that = this;
        _that.addCatLogDialog.dialogVisible = true;
      },
      addCatLogDialogHide(needRefresh) {
        let _that = this;
        _that.addCatLogDialog.dialogVisible = false;
        if (needRefresh) {
          _that.query();
        }
      }
    },
    mounted() {
      let _that = this;
      _that.queryParam.catId = _that.catId;
      _that.resetPage();
    }
  }
</script>

<style scoped>

</style>
