<template>
    <div>
      <el-upload
        class="avatar-uploader"
        :action="uploadUrl"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
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
        console.log(res);
        console.log(file);
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
