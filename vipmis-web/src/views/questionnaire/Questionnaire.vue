<template>
    <div>
      <el-form :inline="true" :model="queryParam" style="text-align: start">
        <el-form-item label="问卷名">
          <el-input v-model="queryParam.name" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="queryParam.tag" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query()">查询</el-button>
          <el-button type="primary" @click="reset()">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="pageData.rows" stripe>
        <el-table-column  align="center" min-width="160" label="问卷名" prop="name" ></el-table-column>
        <el-table-column  align="center" min-width="160" label="备注" prop="tag" ></el-table-column>
        <el-table-column  align="center" min-width="160" label="创建时间" prop="createTime" ></el-table-column>
        <el-table-column  align="center" min-width="160" label="操作" >
          <template slot-scope="props">
            <el-button v-if="props.row.state===QUESTIONNAIRE.NOTISSUE" type="primary" size="mini" @click="issue(props.row)">发布</el-button>
            <el-button v-if="props.row.state===QUESTIONNAIRE.NOTISSUE" type="primary" size="mini" @click="edit(props.row)">编辑</el-button>
            <el-button v-if="props.row.state===QUESTIONNAIRE.NOTISSUE" type="primary" size="mini" @click="del(props.row)">删除</el-button>
            <el-button v-if="props.row.state===QUESTIONNAIRE.ISSUE" type="primary" size="mini" @click="detail(props.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page-inner">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="pageData.currentPage" :page-size="pageData.pageSize" :page-sizes="pageData.pageSizes"
                       layout="total, prev, pager, next, jumper" :total="pageData.total"></el-pagination>
      </div>

      <el-dialog
        title="提示"
        :visible.sync="delDialog.dialogVisible"
        width="30%">
        <span>确定删除吗？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="delDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="deleteQuestionnaire()">确 定</el-button>
        </span>
      </el-dialog>

      <el-dialog
        title="提示"
        :visible.sync="issueDialog.dialogVisible"
        width="30%">
        <span>确定发布吗？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="issueDialog.dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="issueQuestionnaire()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
</template>

<script>
  import dateUtils from "@/common/date_util"
  import CONSTANTS from "@/common/constants"
  import {mapActions, mapGetters} from 'vuex';
  export default {
    name: "questionnaire",
    computed: {
      ...mapGetters('questionnaire', [
        'pageData',
        'pageParam'
      ]),
      QUESTIONNAIRE() {
        return {
          NOTISSUE: CONSTANTS.QUESTIONNAIRE.NOTISSUE,
          ISSUE: CONSTANTS.QUESTIONNAIRE.ISSUE,
        }
      }
    },
    data() {
      return {
        queryParam: {
          name: "",
          tag: ""
        },
        delDialog: {
          dialogVisible: false,
          currentData: {}
        },
        issueDialog: {
          dialogVisible: false,
          currentData: {}
        }
      }
    },
    methods: {
      ...mapActions('questionnaire', [
        'listQuestionnairesByPageAct',
        'resetPageAct',
        'deleteQuestionnaireAct',
        'issueQuestionnaireAct'
      ]),
      handleCurrentChange(val) {
        let _this = this;
        let param = _this.pageParam;
        param.page = val;
        _this.listQuestionnairesByPageAct(param);
      },
      handleSizeChange(val) {
        let _this = this;
        let param = _this.pageParam;
        param.pageSize = val;
        _this.listQuestionnairesByPageAct(param);
      },
      deleteQuestionnaire() {
        let _this = this;
        _this.deleteQuestionnaireAct(_this.delDialog.currentData).then(() => {
          _this.listQuestionnairesByPageAct().then(() => {
            _this.delDialog.dialogVisible = false;
          }).catch(() => {});
        }).catch(() => {});
      },
      issueQuestionnaire() {
        let _this = this;
        _this.issueQuestionnaireAct(_this.issueDialog.currentData).then(() => {
          _this.listQuestionnairesByPageAct().then(() => {
            _this.issueDialog.dialogVisible = false;
          }).catch(() => {});
        }).catch(() => {});
      },
      issue(val) {
        let _this = this;
        _this.issueDialog.currentData = val;
        _this.issueDialog.dialogVisible = true;
      },
      edit(val) {

      },
      detail(val) {

      },
      del(val) {
        let _this = this;
        _this.delDialog.currentData = val;
        _this.delDialog.dialogVisible = true;
      },
      query() {
        let _this = this;
        let param = _this.pageParam;
        param.tag = _this.queryParam.tag;
        param.name = _this.queryParam.name;
        this.listQuestionnairesByPageAct(param);
      },
      reset() {
        let _this = this;
        _this.queryParam.name = "";
        _this.queryParam.tag = "";
        _this.resetPageAct();
      }
    },
    mounted() {
      let _this = this;
      _this.reset();
    }
  }
</script>

<style scoped>

</style>
