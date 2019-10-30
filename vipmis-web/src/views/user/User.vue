<template>
  <div>
    <el-form :inline="true" :model="queryParam" style="text-align: start">
      <!--      <el-form-item label="问卷名">-->
      <!--        <el-input v-model="queryParam.name" placeholder="" @keyup.enter.native.prevent="query()"></el-input>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <!--        <el-button type="primary" @click="query()">查询</el-button>-->
        <!--        <el-button type="primary" @click="reset()">重置</el-button>-->
      </el-form-item>
    </el-form>
    <el-table :data="pageData.rows" stripe>
      <el-table-column align="center" min-width="160" label="用户名" prop="name"></el-table-column>
      <el-table-column align="center" min-width="160" label="创建时间" prop="createTime"></el-table-column>
      <el-table-column align="center" min-width="160" label="操作">
        <template slot-scope="props">
          <el-button type="primary" size="mini" :disabled="false">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page-inner">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="pageData.currentPage" :page-size="pageData.pageSize"
                     :page-sizes="pageData.pageSizes"
                     layout="total, prev, pager, next, jumper" :total="pageData.total"></el-pagination>
    </div>
  </div>
</template>

<script>
  import API from "../../api/api_user";
  export default {
    name: "User",
    data() {
      return {
        queryParam: {
          name: "",
          limit: 10,
          page: 1
        },
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
      listUsersByPage() {
        let _this = this;
        API.listUsersByPage(_this.queryParam).then(result => {
          if (!result) {
            return;
          }
          result.pageSizes = [5];
          _this.pageData = result;
        }).catch(() => {

        })
      },
      resetPage() {
        let _this = this;
        _this.queryParam = {
          name: "",
          limit: 10,
          page: 1
        };
        _this.listUsersByPage();
      },
      handleCurrentChange(val) {
        let _this = this;
        _this.queryParam.page = val;
        _this.listUsersByPage();
      },
      handleSizeChange(val) {
        let _this = this;
        _this.queryParam.pageSize = val;
        _this.listUsersByPage();
      },
      query() {
        let _this = this;
        _this.listQuestionnairesByPage();
      },
      reset() {
        let _this = this;
        _this.resetPage();
      }
    }
  }
</script>

<style scoped>

</style>
