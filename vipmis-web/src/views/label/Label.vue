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
      <el-table v-loading="loading" :data="pageData.rows" stripe>
        <el-table-column align="center" min-width="160" label="分组">
          <template slot-scope="props">
            <el-tag effect="dark" :color="props.row.color">{{props.row.name}}</el-tag>
          </template>
        </el-table-column>
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
          <el-form-item label="颜色" prop="color">
            <el-color-picker v-model="addLabelFormData.color"></el-color-picker>
          </el-form-item>
          <el-form-item label="效果">
            <el-tag effect="dark" :color="addLabelFormData.color">{{addLabelFormData.name}}</el-tag>
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
          <el-form-item label="颜色" prop="color">
            <el-color-picker v-model="editLabelFormData.color"></el-color-picker>
          </el-form-item>
          <el-form-item label="效果">
            <el-tag effect="dark" :color="editLabelFormData.color">{{editLabelFormData.name}}</el-tag>
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
          name: "",
          color: ""
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
          name: "",
          color: ""
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
          name: "",
          color: ""
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
        _that.addLabelFormData.name = "";
        _that.addLabelFormData.color = "#409EFF";
        _that.addLabelDialog.dialogVisible = true;
      },
      editDialogShow(val) {
        let _that = this;
        _that.editLabelFormData.name = val.name;
        _that.editLabelFormData.color = val.color;
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
          name: _that.addLabelFormData.name,
          color: _that.addLabelFormData.color
        };
        API.addLabel(params).then(() => {
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
        params.color = _that.editLabelFormData.color;
        API.editLabel(params).then(() => {
          _that.listLabelsByPage().then(() => {
            _that.editLabelDialog.dialogVisible = false;
            _that.loading = false;
          }).catch(() => {
            _that.editLabelDialog.dialogVisible = false;
            _that.loading = false;
          });
        }).catch(() => {
          _that.loading = false;
        });
      },
      del() {
        let _that = this;
        _that.loading = true;
        API.delLabel(_that.delLabelDialog.currentData).then(() => {
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
