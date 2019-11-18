<template>
  <div>
    <div>
      <el-form :inline="true" :model="queryParam" style="text-align: start">
        <el-form-item label="分组名">
          <el-input v-model="queryParam.name" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button :loading="loading" type="primary" @click="query()">查询</el-button>
          <el-button type="primary" @click="resetPage()">重置</el-button>
          <el-button type="primary" @click="addDialogShow()">添加</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="pageData.rows" stripe>
        <el-table-column align="center" min-width="160" label="分组名" prop="name"></el-table-column>
        <el-table-column align="center" min-width="160" label="操作">
          <template slot-scope="props">
            <el-button type="primary" size="mini" @click="editDialogShow(props.row)">编辑</el-button>
            <el-button type="primary" size="mini" @click="deleteDialogShow(props.row)">删除</el-button>
            <!--          <el-button type="primary" size="mini" :disabled="false">详情</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <div class="page-inner">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="pageData.currentPage" :page-size="pageData.pageSize"
                       :page-sizes="pageData.pageSizes"
                       layout="total, prev, pager, next, jumper" :total="pageData.total"></el-pagination>
      </div>

      <el-dialog
        title="新增分组"
        :visible.sync="addLabelDialog.dialogVisible" :close-on-click-modal="false"
        width="30%">
        <el-form :model="addLabelFormData" :rules="rules" ref="addLabelForm" label-width="100px">
          <el-form-item label="分组名" prop="name">
            <el-input v-model="addLabelFormData.name" maxlength="50" show-word-limit></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
              <el-button @click="addLabelDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="add()">确 定</el-button>
            </span>
      </el-dialog>

      <el-dialog
        title="编辑分组"
        :visible.sync="editLabelDialog.dialogVisible" :close-on-click-modal="false"
        width="30%">
        <el-form :model="editLabelFormData" :rules="rules" ref="editLabelForm" label-width="100px">
          <el-form-item label="分组名" prop="name">
            <el-input v-model="editLabelFormData.name" maxlength="50" show-word-limit></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
              <el-button @click="editLabelDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="edit()">确 定</el-button>
            </span>
      </el-dialog>

      <el-dialog
        title="删除分组"
        :visible.sync="delLabelDialog.dialogVisible"
        width="30%">
        <el-row>
          确定删除该分组吗？
        </el-row>
        <span slot="footer" class="dialog-footer">
              <el-button @click="delLabelDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="del()">确 定</el-button>
            </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import API from "../../api/api_label";
  export default {
    name: "Label",
    data() {
      return {
        loading: false,
        queryParam: {
          name: "",
          limit: 10,
          page: 1
        },
        labelVo: {
          name: ""
        },
        pageData: {
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5],
          total: 0,
          rows: []
        },
        addLabelDialog: {
          dialogVisible: false
        },
        addLabelFormData: {
          name: ""
        },
        rules: {
          name: [
            { required: true, message: '必填', trigger: 'blur' }
          ],
        },
        editLabelDialog: {
          dialogVisible: false,
          currentData: {}
        },
        editLabelFormData: {
          name: ""
        },
        delLabelDialog: {
          dialogVisible: false,
          currentData: {}
        },
      }
    },
    mounted() {
      let _that = this;
      _that.resetPage();
    },
    methods: {
      addDialogShow() {
        let _that = this;
        _that.addLabelDialog.dialogVisible = true;
      },
      editDialogShow(val) {
        let _that = this;
        _that.editLabelDialog.dialogVisible = true;
        _that.editLabelDialog.currentData = val;
      },
      deleteDialogShow(val) {
        let _that = this;
        _that.delLabelDialog.dialogVisible = true;
        _that.delLabelDialog.currentData = val;
      },
      handleCurrentChange(val) {
        let _that = this;
        _that.queryParam.page = val;
        _that.listLabelsByPage();
      },
      handleSizeChange(val) {
        let _that = this;
        _that.queryParam.pageSize = val;
        _that.listLabelsByPage();
      },
      query() {
        let _that = this;
        _that.listLabelsByPage();
      },
      add() {
        let _that = this;
        _that.loading = true;
        let params = {
          name: _that.addLabelFormData.name
        };
        API.addLabel(params).then(res => {
          _that.listLabelsByPage().then(() => {
            _that.addLabelDialog.dialogVisible = false;
            _that.loading = false;
          }).catch(() => {
            _that.addLabelDialog.dialogVisible = false;
            _that.loading = false;
          });
        }).catch(() => {
          _that.loading = false;
        });
      },
      edit() {
        let _that = this;
        _that.loading = true;
        let params = _that.editLabelDialog.currentData;
        params.name = _that.editLabelFormData.name;
        API.editLabel(params).then(res => {
          _that.listLabelsByPage().then(() => {
            _that.addLabelDialog.dialogVisible = false;
            _that.loading = false;
          }).catch(() => {
            _that.addLabelDialog.dialogVisible = false;
            _that.loading = false;
          });
        }).catch(() => {
          _that.loading = false;
        });
      },
      del() {
        let _that = this;
        _that.loading = true;
        API.delLabel(_that.delLabelDialog.currentData).then(res => {
          _that.listLabelsByPage().then(() => {
            _that.delLabelDialog.dialogVisible = false;
            _that.loading = false;
          }).catch(() => {
            _that.delLabelDialog.dialogVisible = false;
            _that.loading = false;
          });
        }).catch(() => {
          _that.loading = false;
        });
      },
      listLabelsByPage() {
        let _that = this;
        _that.loading = true;
        return new Promise((resolve, reject) => {
          API.listLabelsByPage(_that.queryParam).then(result => {
            if (!result) {
              _that.loading = false;
              reject(result);
              return;
            }
            result.pageSizes = [5];
            _that.pageData = result;
            _that.loading = false;
            resolve(result);
          }).catch(e => {
            _that.loading = false;
            reject(e);
          });
        });
      },
      resetPage() {
        let _that = this;
        _that.queryParam = {
          name: "",
          limit: 10,
          page: 1
        };
        _that.listLabelsByPage();
      },
    }
  }
</script>

<style scoped>

</style>
