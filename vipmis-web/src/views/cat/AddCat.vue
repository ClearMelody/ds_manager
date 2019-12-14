<template>
  <div>
    <el-dialog
      title="新增猫咪"
      :visible.sync="dialogShow"
      :close-on-click-modal="false"
      style="text-align: center;"
      width="30%"
    >
      <el-form :model="formData" :rules="rule" ref="addCatForm" label-width="100px">
        <el-form-item label="猫咪名字" prop="name">
          <el-input
            maxlength="50"
            show-word-limit
            v-model="formData.name"
          ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="formData.sex" placeholder="请选择">
            <el-option
              v-for="item in ['公', '母']"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker
            v-model="formData.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="品种" prop="breed">
          <el-input
            maxlength="50"
            show-word-limit
            v-model="formData.breed"
          ></el-input>
        </el-form-item>
        <el-form-item label="是否绝育" prop="sterilization">
          <el-select v-model="formData.sterilization" placeholder="请选择">
            <el-option
              v-for="item in ['已绝育', '未绝育']"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="addCat()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import API from "../../api/api_cat"
  export default {
    name: "AddCat",
    props: {
      closeDialogFunc: {
        type: Function
      },
      userId: {
        type: String
      }
    },
    data() {
      return {
        loading: false,
        dialogShow: true,
        formData: {
          img: "",
          name: "",
          birthday: "",
          sex: "",
          breed: "",
          sterilization: "",
          userInfoVo: {
            id: ""
          }
        },
        rule: {
          name: [
            { required: true, message: '必填', trigger: 'blur' }
          ],
          sex: [
            { required: true, message: '必填', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      closeDialog() {
        let _that = this;
        if (_that.closeDialogFunc) {
          _that.closeDialogFunc();
        }
      },
      addCat() {
        let _that = this;
        if (!_that.userId) {
          alert("页面异常");
          return;
        }
        _that.$refs.addCatForm.validate((valid) => {
          if (valid) {
            _that.formData.userInfoVo.id = _that.userId;
            API.addCat(_that.formData).then(() => {
              _that.closeDialog();
            }).catch(() => {});
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
