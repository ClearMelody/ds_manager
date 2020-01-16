<template>
  <el-container>
    <el-aside width="250px" style="padding:20px;border-right: 1px solid #ccc;">
      <span>数据字典<i class="el-icon-plus el-icon--right" @click.stop="addDictType()"
                   style="float:right;margin-top:3px;margin-right:10px;cursor:pointer;"></i> </span>
      <div style="padding-top: 10px;">
        <el-tree
          class="filter-tree"
          :data="dictTypeList"
          :props="defaultProps"
          default-expand-all
          @node-click="nodeClick"
          :highlight-current="true"
          ref="tree">
          <span @mouseover="hover(node.id)" @mouseout="showUpdate = -1" class="custom-tree-node" style="width:100%;" slot-scope="{ node, data }">
            <span style="font-size: 14px;">{{ node.label }}</span>
            <span v-show="showUpdate == node.id" style="padding-left:20px;color:blue;">
              <i class="el-icon-plus pr10" @click.stop="addDictType(node.data)"></i>
              <i class="el-icon-edit pr10" @click.stop="editDictType(node.data.id)"></i>
              <i class="el-icon-delete" @click.stop="deleteDictType(node.data.id)"></i>
            </span>
          </span>
        </el-tree>
      </div>
    </el-aside>
    <el-main style="padding: 0px;">
      <el-card class="box-card" style="height:100px;">
        <el-form :inline="true" :model="queryParam" label-width="80px" class="demo-form-inline">
          <el-form-item label="名称">
            <el-input v-model="queryParam.name" placeholder="名称" @keyup.enter.native.prevent="listDicts()"></el-input>
          </el-form-item>
          <el-form-item label="编号">
            <el-input v-model="queryParam.code" placeholder="编号" @keyup.enter.native.prevent="listDicts()"></el-input>
          </el-form-item>
          <el-form-item style="padding-left:50px;">
            <el-button type="primary" @click="listDicts()">查询</el-button>
            <el-button type="primary" @click="reset()">重置</el-button>
            <el-button type="success" @click="addDict()">添加</el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card class="box-card content-view">
        <el-table v-loading="loadingDict" :data="pageData.rows" stripe>
          <el-table-column align="center" min-width="80" label="字典名称" prop="name" sortable></el-table-column>
          <el-table-column align="center" min-width="120" label="字典编号" prop="code" sortable></el-table-column>
          <el-table-column align="center" min-width="110" label="父编号" prop="pcode" sortable></el-table-column>
          <el-table-column align="center" min-width="110" label="排序" prop="sort" sortable></el-table-column>
          <el-table-column align="center" min-width="100" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="addDict(scope.row)">添加</el-button>
              <el-button type="primary" size="mini" @click="editDict(scope.row.id)">编辑</el-button>
              <el-button type="danger" size="mini" @click="deleteDict(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-inner" style="position: absolute;bottom: 10px;">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageData.currentPage"
            :page-size="pageData.pageSize"
            :page-sizes="pageData.pageSizes"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pageData.total"
          ></el-pagination>
        </div>
      </el-card>
    </el-main>
    <el-dialog :visible.sync="dialogDictTypeVisible" :close-on-click-modal="false" title="字典类型编辑" width="600px">
      <el-form label-width="120px" v-loading="formLoading">
        <el-form-item label="字典类型名称：">
          <el-input v-model="typeForm.name" maxlength="10" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="排序：">
          <el-input v-model="typeForm.sort"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDictTypeVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveDictType">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogDictVisible" :close-on-click-modal="false" title="字典编辑" width="600px">
      <el-form label-width="120px" v-loading="formLoadingDict">
        <el-form-item label="字典编码：">
          <el-input v-model="dictForm.code"></el-input>
        </el-form-item>
        <el-form-item label="字典名称：">
          <el-input v-model="dictForm.name" maxlength="10" show-word-limit></el-input>
        </el-form-item>
        <el-form-item v-show="dictForm.pcode != null" label="父字典编码：">
          <el-input v-model="dictForm.pcode" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item v-show="dictForm.pname != null" label="父字典名称：">
          <el-input v-model="dictForm.pname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="排序：">
          <el-input v-model="dictForm.sort"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDictVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveDict">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
  import API_DICT from '../../api/api_dictionary'
  import API_DICT_TYPE from '../../api/api_dictionary_type'
  export default {
    name: "Dictionary",
    data() {
      return {
        filterText: '',
        showUpdate: -1,
        dialogDictTypeVisible: false,
        dialogDictVisible: false,
        formLoading: false,
        formLoadingDict: false,
        loadingDict: false,
        typeId: null,
        typeForm: {
          id: null,
          pid: null,
          name: null,
          pname: null,
          path: null,
          sort: null
        },
        dictTypeList: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        dictForm:{
          id: null,
          code: null,
          name: null,
          pcode: null,
          pname: null,
          sort: null,
          typeId: null
        },
        queryParam: {
          name: '',
          code: '',
          typeId: '',
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
      hover(id) {
        this.showUpdate = id;
      },
      addDictType(node) {
        let _this = this;
        _this.dialogDictTypeVisible = true;
        if (node) {
          _this.typeForm = {
            id: null,
            pid: node.id,
            name: null,
            pname: node.name,
            path: node.path,
            sort: null
          };
        } else {
          _this.typeForm = {
            id: null,
            pid: null,
            name: null,
            pname: null,
            path: null,
            sort: null
          };
        }
      },
      editDictType(id) {
        let _this = this;
        _this.formLoading = true;
        _this.dialogDictTypeVisible = true;
        API_DICT_TYPE.getOne(id).then(res => {
          _this.typeForm = res;
          _this.formLoading = false;
        })
      },
      saveDictType() {
        let _this = this;
        _this.formLoading = true;
        API_DICT_TYPE.save(_this.typeForm).then(res => {
          _this.formLoading = false;
          _this.dialogDictTypeVisible = false;
          _this.listDictTypeMenus();
        })
      },
      deleteDictType(id) {
        let _this = this;
        _this.$confirm('是否删除该字典及其子类型字典?').then(() => {
          API_DICT_TYPE.delete(id).then(res => {_this.listDictTypeMenus();}).catch(() => {})
        })
      },
      listDictTypeMenus() {
        let _this = this;
        API_DICT_TYPE.listAll().then(res => {
          _this.dictTypeList = res;
        })
      },
      nodeClick(node) {
        if (!node.chidren || node.children.length == 0) {//叶子节点
          this.typeId = node.id;
          this.listDicts();
        }
      },
      addDict(row) {
        let _this = this;
        _this.dialogDictVisible = true;
        if (row) {
          _this.dictForm = {
            id: null,
            code: null,
            name: null,
            pcode: row.code,
            pname: row.name,
            sort: null,
            typeId: null
          };
        } else {
          _this.dictForm = {
            id: null,
            code: null,
            name: null,
            pcode: null,
            pname: null,
            sort: null,
            typeId: null
          };
        }
      },
      editDict(id) {
        let _this = this;
        _this.formLoadingDict = true;
        _this.dialogDictVisible = true;
        if (id) {
          API_DICT.getOne(id).then(res => {
            _this.dictForm = res;
            _this.formLoadingDict = false;
          })
        }
      },
      saveDict() {
        let _this = this;
        _this.formLoadingDict = true;
        _this.dictForm.typeId = _this.typeId;
        API_DICT.save(_this.dictForm).then(res => {
          _this.formLoadingDict = false;
          _this.dialogDictVisible = false;
          _this.listDicts();
        }).catch(() => {
          this.$message.error('保存失败！');
        })
      },
      deleteDict(id) {
        let _this = this;
        _this.$confirm('是否删除该条字典数据?').then(() => {
          API_DICT.delete(id).then(res => {
            _this.listDicts();
          }).catch(() => {})
        })
      },
      handleCurrentChange(val) {
        let _this = this;
        _this.queryParam.page = val;
        _this.listDicts();
      },
      handleSizeChange(val) {
        let _this = this;
        _this.queryParam.limit = val;
        _this.listDicts();
      },
      listDicts() {
        let _this = this;
        _this.loadingDict = true;
        _this.queryParam.typeId = _this.typeId;
        API_DICT.listPage(_this.queryParam).then(res => {
          _this.pageData = res;
          _this.loadingDict = false;
        }).catch(() => {
          _this.loadingDict = false;
        })
      }
    },
    mounted() {
      this.listDictTypeMenus();
      this.listDicts();
    }
  }
</script>

<style scoped>
  .content-view {
    position: relative;
    height: calc(100% - 164px);
  }
</style>
