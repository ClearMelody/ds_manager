<template>
  <div>
    <el-dialog
      title="猫咪资料修改"
      :show-close="false"
      :visible.sync="dialogShow"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      style="text-align: center;"
      width="40%"
    >
      <el-form :model="formData" :rules="rule" ref="editCatForm" label-width="100px">
        <el-form-item label="猫咪照片" prop="img">
          <img v-if="formData.img" :src="formData.img" class="avatar">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            list-type="picture-card"
            :before-upload="beforeAvatarUpload">
            <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
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
        <el-form-item label="疫苗初次时间" prop="vaccineFirst">
          <el-date-picker
            v-model="formData.vaccineFirst"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="疫苗最近时间" prop="vaccineLast">
          <el-date-picker
            v-model="formData.vaccineLast"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="疫苗下次时间" prop="vaccineNext">
          <el-date-picker
            v-model="formData.vaccineNext"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button type="primary" :loading="loading" @click="editCat()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import CONSTANTS from "../../common/constants"
  import API from "../../api/api_cat"
  export default {
    name: "EditCat",
    props: {
      closeDialogFunc: {
        type: Function
      },
      catId: {
        type: String
      }
    },
    computed: {
      uploadUrl() {
        return CONSTANTS.IMG_2_BASE64_URL;
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
          vaccineFirst: "",
          vaccineLast: "",
          vaccineNext: ""
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
      closeDialog(needQuery) {
        let _that = this;
        if (_that.closeDialogFunc) {
          _that.closeDialogFunc(needQuery);
        }
      },
      editCat() {
        let _that = this;
        _that.loading = true;
        _that.$refs.editCatForm.validate((valid) => {
          if (valid) {
            API.updateCat(_that.formData).then(res => {
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
      },
      handleAvatarSuccess(res, file) {
        // console.log(res);
        // console.log(file);
        if (res) {
          this.formData.img = res.data;
        }
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt500M = file.size / 1024 / 1024 < 500;

        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!');
        }
        if (!isLt500M) {
          this.$message.error('上传图片大小不能超过 500MB!');
        }
        return isJPG && isLt500M;
      }
    },
    mounted() {
      let _that = this;
      if (_that.catId) {
        API.getCatDetail({catId: _that.catId}).then(res => {
          if (!res) {
            return;
          }
          _that.formData = res;
        }).catch(() => {});
      }
    }
  }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    line-height: 148px;
    text-align: center;
  }
  .el-upload{
    float: left;

  }
  .avatar {
    width: 148px;
    height:148px;
    float: left;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    margin-right: 20px;
  }
  .box-card:after{
    content: "";
    clear: both;
  }
</style>
