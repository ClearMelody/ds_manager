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
      <el-table-column align="center" min-width="80" label="用户姓名" prop="realName"></el-table-column>
      <el-table-column align="center" min-width="160" label="微信昵称" prop="weChatName"></el-table-column>
      <el-table-column align="center" min-width="160" label="微信号" prop="weChatOpenId"></el-table-column>
      <el-table-column align="center" min-width="80" label="余额(元)" prop="deposit"></el-table-column>
      <el-table-column align="center" min-width="80" label="积分" prop="goal"></el-table-column>
      <el-table-column align="center" min-width="160" label="最近登录" prop="lastLoginTimeStr"></el-table-column>
      <el-table-column align="center" min-width="160" label="注册时间" prop="registerTimeStr"></el-table-column>
      <el-table-column align="center" min-width="160" label="操作">
        <template slot-scope="props">
          <el-button type="primary" size="mini" @click="payDialogShow(props.row)">消费</el-button>
          <el-button type="primary" size="mini" @click="chargeDialogShow(props.row)">充值</el-button>
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
      title="充值"
      :visible.sync="chargeDialog.dialogVisible" :close-on-click-modal="false"
      width="30%">
      <el-form :model="chargeFormData" :rules="rules" ref="chargeForm" label-width="100px">
        <el-form-item label="金额(元)" prop="money">
          <el-input-number v-model="chargeFormData.money" :precision="2" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="chargeDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="charge()">确 定</el-button>
            </span>
    </el-dialog>

    <el-dialog
      title="消费"
      :visible.sync="payDialog.dialogVisible" :close-on-click-modal="false"
      width="30%">
      <el-form :model="payFormData" :rules="payRules" ref="payForm" label-width="100px">
        <el-form-item label="猫咪售卖(元)" prop="money">
          <el-input-number v-model="payFormData.catSell" :precision="2" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="洗护服务(元)" prop="money">
          <el-input-number v-model="payFormData.washProtectService" :precision="2" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="周边产品(元)" prop="money">
          <el-input-number v-model="payFormData.peripheralProducts" :precision="2" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="payDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="pay()">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
  import API from "../../api/api_user";
  export default {
    name: "User",
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
        chargeDialog: {
          dialogVisible: false,
          currentData: {}
        },
        chargeFormData: {
          money: ""
        },
        payDialog: {
          dialogVisible: false,
          currentData: {}
        },
        payFormData: {
          catSell: "",
          washProtectService: "",
          peripheralProducts: ""
        },
        rules: {
          money: [
            { required: true, message: '必填', trigger: 'blur' }
          ],
        },
        payRules: {
          catSell: [
            {
              type: 'number',
              trigger: 'blur',
              required: false,
              message: '填数字',
            }
          ],
          washProtectService: [
            {
              type: 'number',
              trigger: 'blur',
              required: false,
              message: '填数字',
            }
          ],
          peripheralProducts: [
            {
              type: 'number',
              trigger: 'blur',
              required: false,
              message: '填数字',
            }
          ]
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
          console.log(result);
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
        _this.listUsersByPage();
      },
      reset() {
        let _this = this;
        _this.resetPage();
      },
      chargeDialogShow(val) {
        let _this = this;
        _this.chargeFormData.money = "";
        _this.chargeDialog.currentData = val;
        _this.chargeDialog.dialogVisible = true;
      },
      payDialogShow(val) {
        let _this = this;
        _this.payFormData = {
          catSell: "",
          washProtectService: "",
          peripheralProducts: ""
        };
        _this.payDialog.currentData = val;
        _this.payDialog.dialogVisible = true;
      },
      charge() {
        let _this = this;
        _this.loading = true;
        _this.chargeDialog.currentData.deposit = _this.chargeFormData.money;
        API.charge(_this.chargeDialog.currentData).then(() => {
          _this.loading = false;
          _this.chargeDialog.dialogVisible = false;
          _this.query();
        }).catch(() => {
          _this.loading = false;
          _this.chargeDialog.dialogVisible = false;
        });
      },
      pay() {
        let _this = this;
        _this.loading = true;
        _this.payDialog.currentData.catSell = _this.payFormData.catSell;
        _this.payDialog.currentData.washProtectService = _this.payFormData.washProtectService;
        _this.payDialog.currentData.peripheralProducts = _this.payFormData.peripheralProducts;
        API.pay(_this.payDialog.currentData).then(() => {
          _this.loading = false;
          _this.payDialog.dialogVisible = false;
          _this.query();
        }).catch(() => {
          _this.loading = false;
          _this.payDialog.dialogVisible = false;
        });
      }
    },
    mounted() {
      let _this = this;
      _this.resetPage();
    }
  }
</script>

<style scoped>

</style>
