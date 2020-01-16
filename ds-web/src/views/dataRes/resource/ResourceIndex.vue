<template>
  <el-container>
    <el-aside width="250px" style="padding:20px;border-right: 1px solid #ccc;">
      <span>数据资源<i class="el-icon-plus el-icon--right" @click.stop="addSource()"
                   style="float:right;margin-top:3px;margin-right:10px;cursor:pointer;"></i> </span>
      <div style="padding-top: 10px;">
        <ul class="aside-ul" v-for="(item, index) in list" :key="item.id">
          <li :class="{aside_li_on:item.checked}" @click="isActive(item)">
            <a @mouseover="hover(index)" @mouseout="showUpdate = -1">
              <span v-if='item.flag'>{{item.dsName}}</span>
              <el-input v-model="item.dsName" v-else @click.stop='input(item)' class="hover-input"
                        size="mini"></el-input>
              <span class="li-icon" v-show="showUpdate == index">
                <i class="el-icon-edit pr10" @click.stop="editRes(item)"></i>
                <i class="el-icon-delete" @click.stop="deleteRes(item)"></i>
              </span>
            </a>
          </li>
        </ul>
      </div>
    </el-aside>
    <el-main style="padding-top: 0px;">
      <DataTableMain ref="dataTableEvent"></DataTableMain>
    </el-main>
  </el-container>
</template>

<script>
  import API_RESOURCE from "../../../api/api_data_resource"
  import API_DATABASE from "../../../api/api_database"
  import API_BASE_DB from "../../../api/api_base_db"
  import API_DATA_TABLE from "../../../api/api_data_table"
  import {clearObject} from "../../../utils/util"
  import DataTableMain from "./DataTableMain"

  export default {
    name: "ResourceIndex",
    components: {
      DataTableMain
    },
    data() {
      return {
        centerDialogVisible: false,
        showUpdate: -1,
        sql: null,
        list: [],
        resId: null,
        tbId: null,
      }
    },
    methods: {
      hover(index) {
        this.showUpdate = index;
      },
      isActive(item) {
        this.list.forEach(item => {
          item.checked = false;
        })
        this.$set(item, 'checked', !item.checked);
        this.$refs.dataTableEvent.listDataTables(item.id);
      },
      editRes(item) {
        this.clearList();
        item.flag = false;
      },
      deleteRes(item) {
        let _this = this;
        if (item.id) {
          _this.$confirm('是否删除该资源目录?').then(() => {
            API_RESOURCE.delete(item.id).then(res => {
              _this.queryAllResources();
            }).catch(() => {
            })
          })
        } else {
          _this.queryAllResources();
        }
      },
      input(item) {
        item.flag = false;
      },
      addSource() {
        this.list.push({
          dsName: '新建资源目录',
          flag: false
        })
      },
      clearList() {
        let _this = this;
        _this.list.forEach(item => {
          item.flag = true;
        })
      },
      saveResource() {
        let _this = this;
        let item = null;
        for (let i = 0; _this.list && i < _this.list.length; i++) {
          if (!_this.list[i].flag) {
            item = _this.list[i];
            break;
          }
        }
        if (!item) {
          return;
        }
        if (!item.dsName) {
          this.$message({
            message: '资源目录名称不能为空！',
            type: 'success'
          });
          return;
        }
        API_RESOURCE.save(item).then(res => {
          if (res == -1) {
            this.$message.error('资源目录名称重复！');
          }
          _this.queryAllResources();
        }).catch(() => {
          _this.$message({
            message: '保存失败',
            type: 'success'
          });
        })
      },
      queryAllResources() {
        let _this = this;
        API_RESOURCE.listAll(null).then(res => {
          _this.list = res;
          _this.clearList();
          if (_this.list.length > 0) {
            _this.isActive(_this.list[0]);
          }
        })
      },
      initEvents() {
        let _this = this;
        document.addEventListener('click', e => {
          let input = document.querySelector('.hover-input');
          if (!input || !input.contains(e.target)) {
            _this.saveResource();
          }
        })
      }
    },
    mounted() {
      let _this = this;
      _this.queryAllResources();
      _this.initEvents();
    }
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

  .aside-ul {
    padding: 0;
    margin: 0;
    overflow: hidden;
  }

  .aside-ul li {
    list-style: none;
    display: list-item;
    text-align: -webkit-match-parent;
    padding-left: 20px;
  }

  /*.aside-ul li:hover {*/
  /*  background-color: #d5dbe7;*/
  /*}*/

  .aside_li_on {
    font-weight: 600;
    background-color: #d5dbe7;
  }

  .aside-ul li a {
    display: block;
    height: 40px;
    color: #444;
    line-height: 40px;
    font-size: 14px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin: 0;
    padding: 0;
    text-decoration: none;
    position: relative;
    transition: .15s ease-out;
    cursor: pointer;
  }

  .li-icon {
    position: absolute;
    right: 10px;
  }

  .pr10 {
    padding-right: 10px;
  }

  .hover-input {
    width: 120px;
    height: 30px;
  }

  .link-item {
    bottom: 0px;
    width: 100%;
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
</style>
