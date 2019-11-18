<template>
  <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-position="left" label-width="0px"
           class="login-container">
    <h3 class="title">后台管理登录</h3>
    <el-form-item prop="userName">
      <el-input type="text" v-model="ruleForm.userName" @keyup.enter.native.prevent="submitForm('ruleForm')"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="ruleForm.password" @keyup.enter.native.prevent="submitForm('ruleForm')"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width:100%;">
      <el-button type="primary" style="width:100%;" @click.native.prevent="submitForm('ruleForm')" :loading="loading">登录
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import API from "../../api/api_user";

  export default {
    name: "adminLogin",
    data() {
      return {
        ruleForm: {
          userName: '',
          password: ''
        },
        rules: {
          userName: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        loading: false
      }
    },
    methods: {
      submitForm(formName) {
        let _self = this;
        _self.loading = true;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //TODO
            // Api.user.adminLogin(_self.ruleForm).then(data => {
            //   if (data.success) {
            sessionStorage.setItem("loginFlag", "isLogin");
            // this.$message(data.message);
            this.$router.push("/home");
            // } else {
            //   _self.$message(data.message);
            // }
            _self.loading = false;
            // }).catch(e => {
            //   _self.$message('网络异常');
            //   _self.loading = false;
            // });
          } else {
            _self.loading = false;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .login-box {
    margin: auto;
  }

  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 12% auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: rgba(232, 232, 244, 0.3);
    /*opacity: 0.6;*/
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

    background: -ms-linear-gradient(top, #ace, #00C1DE); /* IE 10 */
    background: -moz-linear-gradient(top, #ace, #00C1DE); /*火狐*/
    background: -webkit-gradient(linear, 0% 0%, 0% 100%, from(#ace), to(#00C1DE)); /*谷歌*/
    background: -webkit-linear-gradient(top, #ace, #00C1DE); /*Safari5.1 Chrome 10+*/
    background: -o-linear-gradient(top, #ace, #00C1DE); /*Opera 11.10+*/
  }
</style>
