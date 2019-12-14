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
            <el-button style="margin-top: 0.5rem;" type="primary" size="mini">编辑
            </el-button>
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

  </div>
</template>

<script>
  import API from "../../api/api_cat";
  import {mapActions, mapGetters} from 'vuex';

  export default {
    name: "User",
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
