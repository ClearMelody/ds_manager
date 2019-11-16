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
      <el-table-column  align="center" min-width="160" label="优惠券" prop="name" ></el-table-column>
      <el-table-column  align="center" min-width="160" label="创建时间" prop="createTime" ></el-table-column>
      <el-table-column  align="center" min-width="160" label="操作" >
        <template slot-scope="props">
          <el-button type="primary" size="mini" :disabled="false">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="page-inner">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="pageData.currentPage" :page-size="pageData.pageSize" :page-sizes="pageData.pageSizes"
                     layout="total, prev, pager, next, jumper" :total="pageData.total"></el-pagination>
    </div>

<!--    <el-dialog-->
<!--      title="提示"-->
<!--      :visible.sync="delDialog.dialogVisible"-->
<!--      width="30%">-->
<!--      <span>确定删除吗？</span>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--          <el-button @click="delDialog.dialogVisible = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="deleteQuestionnaire()">确 定</el-button>-->
<!--        </span>-->
<!--    </el-dialog>-->

<!--    <el-dialog-->
<!--      title="提示"-->
<!--      :visible.sync="issueDialog.dialogVisible"-->
<!--      width="30%">-->
<!--      <span>确定发布吗？</span>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--          <el-button @click="issueDialog.dialogVisible = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="issueQuestionnaire()">确 定</el-button>-->
<!--        </span>-->
<!--    </el-dialog>-->
  </div>
</template>

<script>
  import API from "../../api/api_coupon";
  export default {
    name: "Coupon",
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
      listCouponsByPage() {
        let _that = this;
        API.listCouponByPage(_that.queryParam).then(result => {
          if (!result) {
            return;
          }
          result.pageSizes = [5];
          _that.pageData = result;
        }).catch(() => {

        })
      },
      resetPage() {
        let _that = this;
        _that.queryParam = {
          name: "",
          limit: 10,
          page: 1
        };
        _that.listCouponsByPage();
      },
      handleCurrentChange(val) {
        let _that = this;
        _that.queryParam.page = val;
        _that.listCouponByPage();
      },
      handleSizeChange(val) {
        let _that = this;
        _that.queryParam.pageSize = val;
        _that.listCouponByPage();
      },
      query() {
        let _that = this;
        _that.listQuestionnairesByPage();
      },
      reset() {
        let _that = this;
        _that.resetPage();
      }
    }
  }
</script>

<style scoped>

</style>
