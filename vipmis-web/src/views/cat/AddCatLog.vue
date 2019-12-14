<template>
  <div>
    <el-dialog
      append-to-body
      :show-close="false"
      title="新增猫咪日志"
      :visible.sync="dialogShow"
      :close-on-click-modal="false"
      style="text-align: center;"
      width="40%"
    >
      <el-form :model="formData" :rules="rule" ref="addCatForm" label-width="100px">
        <el-form-item label="洗澡时间" prop="washTime">
          <el-date-picker
            v-model="formData.washTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="体重（Kg）" prop="weight">
          <el-input-number
            v-model="formData.weight"
            :step="10.0"
            :precision="2"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="建议体内驱虫时间" prop="vivo">
          <el-date-picker
            v-model="formData.vivo"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="建议体外驱虫时间" prop="vitro">
          <el-date-picker
            v-model="formData.vitro"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="addCatLog()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import API from "../../api/api_cat"

  export default {
    name: "AddCatLog",
    props: {
      closeDialogFunc: {
        type: Function
      },
      catId: {
        type: String
      }
    },
    data() {
      return {
        loading: false,
        dialogShow: true,
        formData: {
          washTime: "",
          weight: "",
          vivo: "",
          vitro: "",
          catVo: {
            id: ""
          }
        },
        rule: {}
      }
    },
    methods: {
      closeDialog(needRefresh) {
        let _that = this;
        if (_that.closeDialogFunc) {
          _that.closeDialogFunc(needRefresh);
        }
      },
      addCatLog() {
        let _that = this;
        _that.loading = true;
        if (!_that.catId) {
          alert("页面异常");
          _that.loading = false;
          return;
        }
        _that.$refs.addCatForm.validate((valid) => {
          if (valid) {
            _that.formData.catVo.id = _that.catId;
            API.addCatLog(_that.formData).then(res => {
              _that.loading = false;
              if (!res) {
                return;
              }
              _that.closeDialog(true);
            }).catch(() => {
              _that.loading = false;
            });
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
