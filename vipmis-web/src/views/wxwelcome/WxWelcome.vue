<template>
    <div>
      <el-card class="box-card">
         <div slot="header" class="clearfix">
          <span>分享小程序图片上传</span>
        </div>
         <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <el-upload
            class="avatar-uploader"
            :action="uploadUrl"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            list-type="picture-card"
            :before-upload="beforeAvatarUpload">
            <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-card>
    </div>
</template>

<script>
  import CONSTANTS from "@/common/constants"
  import WELCOME_API from "@/api/api_welcome"
  export default {
    name: "WxWelcome",
    data() {
      return {
        imageUrl: ''
      }
    },
    computed: {
      uploadUrl() {
        return CONSTANTS.WELCOME_UPLOAD_URL;
      }
    },
    methods: {
      handleAvatarSuccess(res, file) {
        // console.log(res);
        // console.log(file);
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt500M = file.size / 1024 / 1024 < 500;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt500M) {
          this.$message.error('上传头像图片大小不能超过 500MB!');
        }
        return isJPG && isLt500M;
      },
      getLastWelcome() {
        WELCOME_API.getLastWelcome().then(res => {
          this.imageUrl = res.content;
        }).catch(() => {});
      }
    },
    mounted() {
      this.getLastWelcome();
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
