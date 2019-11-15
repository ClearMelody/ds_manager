<template>
  <div>
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
            <el-input v-model="addLabelFormData.name" :precision="2" :step="0.01"></el-input>
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
            <el-input v-model="editLabelFormData.name" :precision="2" :step="0.01"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
              <el-button @click="editLabelDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="edit()">确 定</el-button>
            </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
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
        pageData: {
          currentPage: 1,
          pageSize: 5,
          pageSizes: [5],
          total: 0,
          rows: []
        },
        addLabelDialog: {
          dialogVisible: false,
          currentData: {}
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
      }
    },
    methods: {
      add() {

      },
      edit() {

      },
      delete() {

      }
    }
  }
</script>

<style scoped>

</style>
