<template>
  <div>
    <el-form :inline="true" :model="queryParam" style="text-align: start">
      <el-form-item label="用户姓名">
        <el-input v-model="queryParam.realName" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
      </el-form-item>
      <el-form-item label="微信昵称">
        <el-input v-model="queryParam.weChatName" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="queryParam.phone" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="queryParam.idCard" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
      </el-form-item>
      <el-form-item label="VIP卡号">
        <el-input v-model="queryParam.cardCord" placeholder="" @keyup.enter.native.prevent="query()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query()" :loading="loading">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="pageData.rows" stripe>
      <el-table-column align="center" min-width="80" label="用户姓名" prop="realName"></el-table-column>
      <el-table-column align="center" min-width="120" label="微信昵称" prop="weChatName"></el-table-column>
      <el-table-column align="center" min-width="110" label="手机号" prop="phone"></el-table-column>
      <el-table-column align="center" min-width="100" label="VIP卡号" prop="cardCord"></el-table-column>
      <el-table-column align="center" min-width="80" label="身份证" prop="idCard"></el-table-column>
      <el-table-column align="center" min-width="80" label="余额(元)" prop="deposit"></el-table-column>
      <el-table-column align="center" min-width="80" label="积分" prop="goal"></el-table-column>
      <el-table-column align="center" min-width="150" label="最近登录" prop="lastLoginTimeStr"></el-table-column>
      <el-table-column align="center" min-width="150" label="注册时间" prop="registerTimeStr"></el-table-column>
      <el-table-column align="center" min-width="100" label="分组">
        <template slot-scope="props">
          <el-tag effect="dark" style="cursor: pointer;" @click="selectLabelDialogShow(props.row)" :color="props.row.labelVo ? props.row.labelVo.color : ''">{{props.row.labelVo ? props.row.labelVo.name : '无分组'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" min-width="240" label="操作">
        <template slot-scope="props">
          <el-button type="primary" size="mini" @click="payDialogShow(props.row)">消费</el-button>
          <el-button type="primary" size="mini" @click="userGoalDialogShow(props.row)">使用积分</el-button>
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
          <el-input-number v-model="chargeFormData.money" :precision="0" :step="10" @keyup.enter.native.prevent="charge()"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="chargeDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="charge()">确 定</el-button>
            </span>
    </el-dialog>

    <el-dialog
      title="使用积分"
      :visible.sync="useGoalDialog.dialogVisible" :close-on-click-modal="false"
      width="30%">
      <el-form :model="useGoalFormData" ref="useGoalForm" label-width="100px">
        <el-form-item label="积分" prop="goal">
          <el-input-number v-model="useGoalFormData.goal" :precision="0" :step="10" @keyup.enter.native.prevent="useGoal()"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="useGoalDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="useGoal()">确 定</el-button>
            </span>
    </el-dialog>

    <el-dialog
      title="消费"
      :visible.sync="payDialog.dialogVisible" :close-on-click-modal="false"
      width="30%">
      <el-form :model="payFormData" :rules="payRules" ref="payForm" label-width="100px">
        <el-form-item label="猫咪售卖(元)" prop="money">
          <el-input-number v-model="payFormData.catSell" :precision="0" :step="10" @keyup.enter.native.prevent="pay()"></el-input-number>
        </el-form-item>
        <el-form-item label="洗护服务(元)" prop="money">
          <el-input-number v-model="payFormData.washProtectService" :precision="0" :step="10" @keyup.enter.native.prevent="pay()"></el-input-number>
        </el-form-item>
        <el-form-item label="周边产品(元)" prop="money">
          <el-input-number v-model="payFormData.peripheralProducts" :precision="0" :step="10" @keyup.enter.native.prevent="pay()"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="payDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="pay()">确 定</el-button>
            </span>
    </el-dialog>

    <el-dialog
      title="修改分组"
      :visible.sync="selectLabelDialog.dialogVisible" :close-on-click-modal="false"
      width="30%">
      <el-form :model="selectLabelFormData" ref="selectLabelForm" label-width="100px">
        <el-form-item label="选择分组">
          <el-select v-model="selectLabelFormData.id" filterable placeholder="请选择">
            <el-option
              v-for="item in labels"
              :key="item.id"
              :color="item.color"
              :label="item.name"
              :value="item.id">
              <el-tag effect="dark" :color="item.color">{{item.name}}</el-tag>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
              <el-button @click="selectLabelDialog.dialogVisible = false">取 消</el-button>
              <el-button type="primary" :loading="loading" @click="changeLabel()">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
  import API from "../../api/api_user";
  import LABEL_API from "../../api/api_label";
  export default {
    name: "User",
    data() {
      return {
        loading: false,
        queryParam: {
          realName: "",
          weChatName: "",
          phone: "",
          idCard: "",
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
        },
        chargeDialog: {
          dialogVisible: false,
          currentData: {}
        },
        chargeFormData: {
          money: ""
        },
        useGoalDialog: {
          dialogVisible: false,
          currentData: {}
        },
        useGoalFormData: {
          goal: ""
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
        },
        selectLabelDialog: {
          dialogVisible: false,
          currentData: {}
        },
        selectLabelFormData: {
          id: ""
        },
        labels: []
      }
    },
    methods: {
      listUsersByPage() {
        let _that = this;
        _that.loading = true;
        API.listUsersByPage(_that.queryParam).then(result => {
          if (!result) {
            _that.loading = false;
            return;
          }
          result.pageSizes = [5];
          _that.pageData = result;
          _that.loading = false;
          console.log(result);
        }).catch(() => {
          _that.loading = false;
        })
      },
      resetPage() {
        let _that = this;
        _that.queryParam = {
          realName: "",
          weChatName: "",
          phone: "",
          idCard: "",
          cardCord: "",
          limit: 10,
          page: 1
        };
        _that.listUsersByPage();
      },
      handleCurrentChange(val) {
        let _that = this;
        _that.queryParam.page = val;
        _that.listUsersByPage();
      },
      handleSizeChange(val) {
        let _that = this;
        _that.queryParam.pageSize = val;
        _that.listUsersByPage();
      },
      query() {
        let _that = this;
        _that.listUsersByPage();
      },
      reset() {
        let _that = this;
        _that.resetPage();
      },
      chargeDialogShow(val) {
        let _that = this;
        _that.chargeFormData.money = "";
        _that.chargeDialog.currentData = val;
        _that.chargeDialog.dialogVisible = true;
      },
      payDialogShow(val) {
        let _that = this;
        _that.payFormData = {
          catSell: "",
          washProtectService: "",
          peripheralProducts: ""
        };
        _that.payDialog.currentData = val;
        _that.payDialog.dialogVisible = true;
      },
      userGoalDialogShow(val) {
        let _that = this;
        _that.useGoalFormData.goal = "";
        _that.useGoalDialog.currentData = val;
        _that.useGoalDialog.dialogVisible = true;
      },
      charge() {
        let _that = this;
        _that.loading = true;
        let params = _that.chargeDialog.currentData;
        params.deposit = _that.chargeFormData.money;
        API.charge(params).then(() => {
          _that.loading = false;
          _that.chargeDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {
          _that.loading = false;
        });
      },
      useGoal() {
        let _that = this;
        _that.loading = true;
        let params = _that.useGoalDialog.currentData;
        params.goal = _that.useGoalFormData.goal;
        API.useGoal(params).then(() => {
          _that.loading = false;
          _that.useGoalDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {
          _that.loading = false;
        });
      },
      pay() {
        let _that = this;
        _that.loading = true;
        _that.payDialog.currentData.catSell = _that.payFormData.catSell;
        _that.payDialog.currentData.washProtectService = _that.payFormData.washProtectService;
        _that.payDialog.currentData.peripheralProducts = _that.payFormData.peripheralProducts;
        API.pay(_that.payDialog.currentData).then(() => {
          _that.loading = false;
          _that.payDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {
          _that.loading = false;
        });
      },
      selectLabelDialogShow(val) {
        let _that = this;
        _that.selectLabelDialog.dialogVisible = true;
        _that.selectLabelDialog.currentData = val;
        _that.selectLabelFormData.id = val.labelVo ? val.labelVo.name : "";
        LABEL_API.listAllLabels().then(res => {
          if (!res || res.length === 0) {
            _that.selectLabelDialog.dialogVisible = false;
            return;
          }
          _that.labels = res;
        }).catch(() => {
          _that.selectLabelDialog.dialogVisible = false;
        })
      },
      changeLabel() {
        let _that = this;
        _that.loading = true;
        let userVo = {
          id: _that.selectLabelDialog.currentData.id,
          labelVo: {
            id: _that.selectLabelFormData.id
          }
        };
        LABEL_API.changeUserLabel(userVo).then(() => {
          _that.loading = false;
          _that.selectLabelDialog.dialogVisible = false;
          _that.query();
        }).catch(() => {
          _that.loading = false;
        })
      }
    },
    mounted() {
      let _that = this;
      _that.resetPage();
    }
  }
</script>

<style scoped>

</style>
