<template>
  <div>
    <el-header style="border-bottom: 1px solid #ccc;height:40px;">
      <el-row style="margin:10px 0px;">
        <el-col :span="3/48">
          <el-link @click="openEditDialog" :underline="false">
            <svg-icon icon-class="add_database"/>
            新增
          </el-link>
        </el-col>
        <el-col :span="3/48">
          <el-link :underline="false">
            <svg-icon icon-class="delete_database"/>
            删除
          </el-link>
        </el-col>
        <el-col :span="3/48">
          <el-link :underline="false">
            <svg-icon icon-class="data_configuration"/>
            管理
          </el-link>
        </el-col>
        <el-col :span="3/48">
          <el-link :underline="false">
            <svg-icon icon-class="data_backup"/>
            刷新
          </el-link>
        </el-col>
        <el-col :span="3/48">
          <el-link :underline="false">
            <svg-icon icon-class="data_import"/>
            导入
          </el-link>
        </el-col>
        <el-col :span="3/48">
          <el-link :underline="false">
            <svg-icon icon-class="data_export"/>
            导出
          </el-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main class="table-view" v-loading="loading_tables">
      <el-row>
        <div v-for="(item) in tables" :key="item.id">
          <div class="table_item">
            <el-dropdown trigger="click" @command="handleCommand">
              <el-link :underline="false">
                <svg-icon icon-class="data_table" style="font-size: 50px;margin-left: 15px;"/>
                <span class="link-item" :title="item.dtNameCn">{{item.dtNameCn}}</span>
              </el-link>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="{key:'open',item:item}" icon="el-icon-folder-opened">打开</el-dropdown-item>
                <el-dropdown-item :command="{key:'edit',item:item}" icon="el-icon-edit">编辑</el-dropdown-item>
                <el-dropdown-item :command="{key:'delete',item:item}" icon="el-icon-delete">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </el-row>
    </el-main>
    <el-dialog :visible.sync="centerDialogVisible" top="20px" :close-on-click-modal="false"
               :title="tbForm.id?'编辑':'新增'" width="95%">
      <el-card>
        <el-steps :active="active" finish-status="success">
          <el-step title="数据源选择"></el-step>
          <el-step title="表选择"></el-step>
          <el-step title="列编辑"></el-step>
          <el-step title="完成"></el-step>
        </el-steps>
      </el-card>
      <el-card style="height:500px;overflow:auto;" v-loading="loading_dialog">
        <div v-show="active == 0" style="margin-top: 20px">
          <el-radio-group v-model="tbForm.dbId">
            <el-radio class="el-radio-icon" border v-for="item in dbList" :key="item.id" :label="item.id"
                      :value="item.id">
              <svg-icon class="el-radio-svg" icon-class="database"/>
              <span class="el-radio-span" :title="item.dbName">{{item.dbName}}</span>
            </el-radio>
          </el-radio-group>
        </div>
        <!--<div v-show="active == 1" style="margin-top: 20px;width:100%;">
          <el-radio-group v-model="tbForm.createType">
            <el-radio border label="1">新建</el-radio>
            <el-radio border label="2">关联已有表</el-radio>
          </el-radio-group>
        </div>
        <div v-show="active == 2 && tbForm.createType == 1" style="margin-top: 20px;width:100%;">
          <el-input type="textarea" style="width:100%;height:300px;" placeholder="请输入SQL" v-model="sql"></el-input>
        </div>-->
        <div v-show="active == 1">
          <el-radio-group v-model="tbForm.dtName">
            <el-radio class="el-radio-icon" border v-for="item in relateTables" :key="item" :label="item">
              <svg-icon class="el-radio-svg" icon-class="table"/>
              <span class="el-radio-span" :title="item">{{item}}</span>
            </el-radio>
          </el-radio-group>
        </div>
        <div v-show="active == 2">
          <el-table ref="filterTable" :data="tbForm.columns" style="width: 100%">
            <el-table-column prop="columnName" label="字段名称"></el-table-column>
            <el-table-column prop="dataType" label="数据类型"></el-table-column>
            <el-table-column label="长度" align="center"></el-table-column>
            <el-table-column prop="columnTitle" label="列标题" align="center">
              <template slot-scope="scope">
                <el-input v-model="scope.row.columnTitle"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="dictId" label="字典关联" align="center">
              <template slot-scope="scope">
                <el-cascader :options="dictTypeList" :props="dictProps" v-model="scope.row.dictId" :clearable="true" :show-all-levels="false"></el-cascader>
              </template>
            </el-table-column>
            <el-table-column label="是否列表显示" align="center">
              <template slot-scope="scope">
                <el-checkbox v-model="scope.row.visiable" :true-label="1" :false-label="0"></el-checkbox>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div v-show="active == 3" style="width:800px;margin: 200px auto;">
          <el-form label-width="120px">
            <el-form-item label="数据源名称：">
              <el-input v-model="tbForm.dtNameCn" maxlength="20" show-word-limit></el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <span slot="footer" class="dialog-footer">
        <el-button v-show="active > 0" type="info" @click="prevStep">上一步</el-button>
        <el-button type="primary" @click="nextStep" v-text="active == 3?'保存':'下一步'" v-loading="loading_save"></el-button>
      </span>
    </el-dialog>
    <el-dialog :visible.sync="detailDialogVisible" top="20px" :close-on-click-modal="false"
               :title="tbForm.dtNameCn" width="95%">
      <el-header style="height:40px;">
        <el-row style="margin:10px 0px;">
          <el-col :span="3/48">
            <el-link :underline="false">
              <svg-icon icon-class="table_data_add"/>
              新增
            </el-link>
          </el-col>
          <el-col :span="3/48">
            <el-link :underline="false">
              <svg-icon icon-class="table_data_search"/>
              搜索
            </el-link>
          </el-col>
          <el-col :span="3/48">
            <el-link @click="refreshTableData" :underline="false">
              <svg-icon icon-class="table_data_refresh"/>
              刷新
            </el-link>
          </el-col>
          <el-col :span="3/48">
            <el-link :underline="false">
              <svg-icon icon-class="table_data_import"/>
              导入
            </el-link>
          </el-col>
          <el-col :span="3/48">
            <el-link @click="exportTableData" :underline="false">
              <svg-icon icon-class="table_data_export"/>
              导出
            </el-link>
          </el-col>
          <el-col :span="3/48">
            <el-link @click="openImportModel" :underline="false">
              <svg-icon icon-class="excel"/>
              导入模板
            </el-link>
          </el-col>
        </el-row>
      </el-header>
      <div v-show="progress" style="text-align: center;">
        <el-progress  type="circle" :percentage="progress_percent" style="margin-top: 200px;"></el-progress>
        <div style="text-align: center;padding-top:20px;padding-bottom:200px;">导出进度</div>
      </div>

      <el-main v-show="!progress" v-loading="loading_detail" style="padding:0px;">
        <el-table style="min-height:600px;max-height:650px;width:100%;overflow: auto;margin-bottom: 30px;" :data="pageData.rows" highlight-current-row border stripe>
          <el-table-column
            v-for="(item) in tableRows"
            :prop="item.prop"
            :label="item.label"
            :key="item.id"
            sortable
            min-width="120"
            show-overflow-tooltip
          >
          </el-table-column>
        </el-table>
        <div class="page-inner" style="position: absolute;bottom: 20px;padding-top:10px;">
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
      </el-main>
    </el-dialog>
    <el-dialog :visible.sync="modelDialogVisible" :close-on-click-modal="false" width="600px">
    </el-dialog>
  </div>
</template>

<script>
  import API_DATABASE from "../../../api/api_database"
  import API_BASE_DB from "../../../api/api_base_db"
  import API_DATA_TABLE from "../../../api/api_data_table"
  import API_DICT_TYPE from "../../../api/api_dictionary_type"

  export default {
    name: "DataTableMain",
    data() {
      return {
        tbForm: {
          id: null,
          resId: null,
          dbId: null,
          dtName: null,
          dtNameCn: null,
          columns: []
        },
        centerDialogVisible: false,
        detailDialogVisible: false,
        modelDialogVisible: false,
        active: 0,
        tables: [],
        dbList: [],
        relateTables: [],
        tableRows: [],
        dictTypeList: [],
        dictProps: {
          children: 'children',
          label: 'name',
          value: 'id',
          emitPath: false
        },
        tableDataParam: {
          name: null,
          tbId: null,
          limit: 12,
          page: 1
        },
        pageData: {
          currentPage: 1,
          pageSize: 12,
          pageSizes: [10,20,50,100],
          total: 0,
          rows: []
        },
        loading_dialog: false,
        loading_save: false,
        loading_tables: false,
        loading_detail: false,
        loading_model: false,
        export_btn: true,
        progress: false,
        progress_percent: 0,
        timer: null,
      }
    },
    methods: {
      listDataTables(resId) {
        let _this = this;
        _this.loading_tables = true;
        if (resId) {
          this.tbForm.resId = resId;
        }
        API_DATA_TABLE.listAll({resId: this.tbForm.resId}).then(res => {
          _this.tables = res;
          _this.loading_tables = false;
        }).catch(() => {
          this.loading_tables = false;
        })
      },
      handleCommand(command) {
        let _this = this;
        _this.tbForm.id = command.item.id;
        _this.tbForm.dtName = command.item.dtName;
        _this.tbForm.dtNameCn = command.item.dtNameCn;
        switch (command.key) {
        case "open":
          _this.tableOpen(command.item.id);
          break;
        case "edit":
          _this.tableEdit(command.item.id);
          break;
        case "delete":
          _this.tableDelete(command.item.id);
          break;
        }
      },
      tableOpen(id) {
        let _this = this;
        _this.detailDialogVisible = true;
        _this.tableDataParam.tbId = id;
        _this.tableDataQuery();
      },
      tableDataQuery() {
        let _this = this;
        _this.loading_detail = true;
        _this.tableRows = [];
        _this.pageData = {
          currentPage: 1,
          pageSize: 12,
          pageSizes: [10,20,50,100],
          total: 0,
          rows: []
        };
        API_BASE_DB.pageData(_this.tableDataParam).then(res => {
          if (res) {
            _this.tableRows = res.tableRows;
            _this.pageData = res.pageData;
          }
          _this.loading_detail = false;
        }).catch(() => {
        })
      },
      handleCurrentChange(val) {
        let _this = this;
        _this.tableDataParam.page = val;
        _this.tableDataQuery();
      },
      handleSizeChange(val) {
        let _this = this;
        _this.tableDataParam.limit = val;
        _this.tableDataQuery();
      },
      tableEdit(id) {
        let _this = this;
        _this.active = 0;
        _this.centerDialogVisible = true;
        API_DATA_TABLE.getOne(id).then(res => {
          _this.tbForm = res;
          _this.listAllDbs();
        }).catch(() => {
        })
      },
      tableDelete(id) {
        let _this = this;
        _this.$confirm('是否删除该数据资源?').then(() => {
          API_DATA_TABLE.delete(id).then(res => {
            _this.listDataTables()
          }).catch(() => {
            this.$message.error('删除失败！');
          })
        })
      },
      refreshTableData() {
        this.tableDataQuery();
      },
      exportTableData() {
        let _this = this;
        API_BASE_DB.exportTableData(_this.tbForm.dtNameCn + ".xlsx",_this.tableDataParam).then(res => {
        }).catch(() => {
        })
        this.showExportProgress();
      },
      showExportProgress() {
        let _this = this;
        _this.progress_percent = 0;
        _this.progress = true;
        _this.export_btn = false;
        _this.timer = setInterval(() => {
          API_BASE_DB.exportTableDataProgress(null).then(res => {
            _this.progress_percent = res;
          }).catch(() => {window.clearInterval(_this.timer);})
        }, 1000);
      },
      openImportModel() {

      },
      openEditDialog() {
        let _this = this;
        this.active = 0;
        let resId = _this.tbForm.resId;
        this.tbForm = {
          resId: resId,
          dbId: null,
          dtName: null,
          dtNameCn: null,
          columns: []
        }
        this.centerDialogVisible = true;
        this.listAllDbs();
      },
      prevStep() {
        if (--this.active <= 1) {
        }
      },
      nextStep() {
        if (this.active == 0) {
          if (this.tbForm.dbId) {
            this.listRelateTables();
          } else {
            this.$message.error('请先选择数据源！');
            return;
          }
        } else if (this.active == 1) {
          if (this.tbForm.dtName) {
            this.listTableColumns();
          } else {
            this.$message.error('请先选择数据源关联表！');
            return;
          }
        } else if (this.active == 3) {
          this.save();
          return;
        }
        this.active++;
      },
      listAllDbs() {
        let _this = this;
        _this.loading_dialog = true;
        API_DATABASE.listAll(null).then(res => {
          _this.dbList = res;
          this.loading_dialog = false;
        }).catch(() => {
          this.loading_dialog = false;
        })
      },
      listRelateTables() {
        let _this = this;
        _this.loading_dialog = true;
        API_BASE_DB.listTables(_this.tbForm.dbId).then(res => {
          _this.relateTables = res;
          _this.loading_dialog = false;
        }).catch(() => {
          this.loading_dialog = false;
        })
      },
      listTableColumns() {
        let _this = this;
        _this.loading_dialog = true;
        API_BASE_DB.listTableColumns({dbId: _this.tbForm.dbId, dtName: _this.tbForm.dtName}).then(res => {
          let columns = [];
          if (_this.tbForm.columns.length > 0) {
            for (let i = 0; i < res.length; i++) {
              let isSaved = false;
              let obj = res[i];
              for (let j = 0; j < _this.tbForm.columns.length; j++) {
                let item = _this.tbForm.columns[j];
                if (obj.columnName == item.columnName) {
                  isSaved = true;
                  columns.push({
                    id: item.id,
                    columnName: item.columnName,
                    columnTitle: item.columnTitle,
                    visiable: item.visiable,
                    dictId: item.dictId,
                    isNullable: obj.isNullable,
                    dataType: obj.dataType,
                    columnKey: obj.columnKey,
                    columnComment: obj.columnComment
                  })
                  break;
                }
              }
              if (!isSaved) {
                columns.push(obj);
              }
            }
            _this.tbForm.columns = columns;
          } else {
            _this.tbForm.columns = res;
          }
          _this.loading_dialog = false;
        }).catch(() => {
          this.loading_dialog = false;
        })
      },
      queryDictTree() {
        let _this = this;
        API_DICT_TYPE.listAll().then(res => {
          _this.dictTypeList = res;
        })
      },
      save() {
        let _this = this;
        _this.loading_dialog = true;
        _this.loading_save = true;
        API_DATA_TABLE.save(this.tbForm).then(res => {
          _this.loading_dialog = false;
          _this.centerDialogVisible = false;
          _this.loading_save = false;
        }).catch(() => {
          _this.loading_dialog = false;
          _this.loading_save = false;
        })
      },
      openDetailDialog() {
        let _this = this;
      }
    },
    mounted() {
      this.queryDictTree();
    },
    watch: {
      progress_percent(val) {
        if (val >= 100) {
          setTimeout(() => {
            this.export_btn = true;
            this.progress = false;
          },1000)
          window.clearInterval(this.timer);
        }
      }
    },
    beforeDestroy() {
      window.clearInterval(this.timer);
    },
    props: {}
  }
</script>

<style scoped>
  .svg-icon {
    font-size: 25px;
  }

  .el-link {
    font-size: 18px;
  }

  .el-col {
    margin-right: 25px;
  }

  .table-view {
    height: calc(100% - 60px);
    margin-left: -15px;
  }

  .table_item {
    float: left;
    width: 80px;
    height: 90px;
    margin-right: 10px;
  }

  .link-item {
    bottom: 0px;
    width: 80px;
    display: block;
    text-align: center;
    font-size: 14px;
    height: 20px;
    line-height: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .el-radio {
    display: block;
    width: 350px;
    margin-top: 20px;
    margin-left: 10px;
  }

  .el-radio-icon {
    display: inline-block;
    margin-top: 20px;
    margin-left: 10px;
    height: 56px;
    padding: 12px 20px 12px 10px;
    float: left;
  }

  .el-radio-icon >>> .el-radio__input {
    float: left;
    margin-top: 8px;
  }

  .el-radio-icon >>> .el-radio__label {
    float: left;
  }

  .el-radio-span {
    display: block;
    padding-left: 10px;
    height: 30px;
    line-height: 30px;
    float: left;
    width: 250px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .el-radio-svg {
    display: block;
    float: left;
    font-size: 30px;
  }

  .el-textarea >>> .el-textarea__inner {
    height: 300px;
  }
  /*.el-dialog >>> .el-dialog__header {*/
  /*  background: #5081ef !important;*/
  /*  padding:15px;*/
  /*}*/
  /*.el-dialog >>> .el-dialog__title {*/
  /*  font-size: 20px;*/
  /*  color: #fff;*/
  /*}*/
</style>
