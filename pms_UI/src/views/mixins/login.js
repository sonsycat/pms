import {authLicenseVerify} from '@/api/license';
export default {
  data(){
    return {
      mcode: "",
      open: false,
    }
  },
  methods:{
    licenseLogin() {
      this.handleLogin();
      // 验证授权是否到期
      // authLicenseVerify().then(r => {
      //   if(r.code == 0) {
      //     const o = r.data;
      //     // 验证成功
      //     if(!o.result) {
      //       this.handleLogin();
      //     } else {
      //       // 打开授权码填写界面
      //       this.open = true;
      //       this.mcode = r.data.mac
      //       console.log('mcode:' + r.data.mac);
      //     }
      //   } else {
      //     this.$msgbox(r.msg);
      //   }
      // })
    },
    cancel() {
      this.open = false;
    },
    successActive(){
      this.msgSuccess('激活成功！');
      this.cancel();
      this.handleLogin();
    },
    errorActive(errorMsg) {
      this.$message.error(errorMsg);
    }
  }
}
