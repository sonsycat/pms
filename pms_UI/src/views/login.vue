<template>
  <div class="login">
<!--    <el-row>-->
<!--      <el-col :span="24" class="login_title">-->
<!--       绩效管理-->
<!--      </el-col>-->
<!--            </el-row>-->
      <el-row type="flex"  justify="center">
        <el-col :span="6" class="hrbgc text-center">
          <!-- <img src="" alt=""> -->
          <img src="@/assets/images/loginLeft.jpg" alt="light" class="leftImg">

        </el-col>
        <el-col :span="5">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <el-col class = "tatile">
<!--        <img src="@/assets/images/login-avater.jpg" alt="light" class="avaterImg">-->
<!--        <span class = "designation">内蒙古自治区脑科医院(第三医院)</span>-->
      </el-col>

      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaFlag">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberPwd" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="licenseLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
        </el-col>
    </el-row>
<!--    <el-row class="footer-text">-->
<!--      <el-col :span="24">Copyright © 2018-2022 北京数字心医科技股份有限公司</el-col>-->
<!--    </el-row>-->

    <!-- 填写授权码对话框 -->
    <loginCheck v-if="open" :open="open" :mcode="mcode" @cancel="cancel" @success="successActive" @error="errorActive"></loginCheck>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import { getConfigKey } from "@/api/system/config";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt';

import {authLicenseVerify, active} from '@/api/license';
import  loginCheck from "./components/loginCheck/index"
import loginMixin from "./mixins/login"

export default {
  name: "Login",
  mixins:[loginMixin],
  components:{
    loginCheck
  },
  data() {
    return {
      captchaKey:'login-captchaFlag',
      captchaFlag:true,
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "",
        password: "",
        rememberPwd: false,
        rememberMe:false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
      },
      loading: false,
      redirect: undefined,
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getConfigCaptchaFlag();
    this.getCode();
    this.getCookie();
  },
  methods: {
    getConfigCaptchaFlag(){
      getConfigKey('login.captchaFlag').then(res => {
        if(res.msg === '1'){
          this.captchaFlag = true;
        }else if (res.msg === '0'){
          this.captchaFlag = false;
        }else{
          this.captchaFlag = true;
        }
      })
    },
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberPwd = Cookies.get('rememberPwd')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberPwd: rememberPwd === undefined ? false : Boolean(rememberPwd)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberPwd) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberPwd', this.loginForm.rememberPwd, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberPwd');
          }


          this.$store
            .dispatch("Login", this.loginForm)
            .then(response => {
              if (response.code == 200) {
                sessionStorage.setItem("indexDialogVisible", "1");
                this.$router.push({ path: this.redirect || "/" }).catch(() => {
                });
              } else {
                this.msgError(response.msg);
                this.loginForm.code = "";
                this.loading = false;
                this.getCode();
              }
            })
            .catch(() => {
              this.loading = false;
              this.getCode();
            });



          // this.$store.dispatch("Login", this.loginForm).then(() => {
          //   this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          // }).catch(() => {
          //   this.loading = false;
          //   this.getCode();
          // });
        }
      });
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  // display: flex;
  // justify-content: center;
  // align-items: center;
  padding-top: 15%;
  padding-left: 11%;
  height: 100%;
  background-image: url("../assets/images/login_bg.jpg");
  background-size: cover;
}
.hrbgc {
  border-bottom-left-radius: 6px;
  border-top-left-radius: 6px;
  //background-image: url("../assets/images/pop.png");
  background-size: 10% 10%;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}
.leftImg{
  height: 428px;
  width: 304px;
}
.avaterImg{
  height: 10%;
  width: 10%;
  vertical-align: middle;
  margin-right: 10px;
}
.tatile{
  margin-bottom: 30px;
}
.designation{
  display: inline-block;
  vertical-align: middle;
  margin-right: 10px;
}
.login-form {
  border-radius: 0px;
  border-bottom-left-radius: 0;
  border-top-left-radius: 0;
  background: #ffffff;
  width: 350px;
  height: 428px;
  margin-left: -18px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height:50px;
    input {
      height: 50px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
.login_title {
  height: 80px;
  text-align: center;
  color: #fff;
  font-size: 35px;
  font-weight: 600;
  letter-spacing: 5px;
}
.footer-text {
  margin-top: 20px;
  text-align: center;
  color: #fff;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>
