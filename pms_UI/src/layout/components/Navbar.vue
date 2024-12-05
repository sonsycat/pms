<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item" />
        <el-badge :max="99" :value="messageCount" class="item right-menu-item">
          <!-- <el-button size="mini" @click="openMsg">消息</el-button> -->
        </el-badge>
        <screenfull id="screenfull" class="right-menu-item hover-effect" />
        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <!--<img :src="avatar" class="user-avatar" :onerror="defaultImg" alt="">-->
          <el-image :src="avatar" class="user-avatar">
			      <div slot="error" class="user-avatar">
  						<img :src="avatar2" class="user-avatar" alt="">
			      </div>
			    </el-image>
          <template v-if="sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'|| sideTheme === 'theme-blue5'">
            <i class="el-icon-caret-bottom" :style = "{ color: variables.menuLightBg}"/>
          </template>
          <template v-else>
            <i class="el-icon-caret-bottom" />
          </template>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <!-- <el-dropdown-item @click.native="cutAccountSet">
            <span>切换账套</span>
          </el-dropdown-item> -->
<!--          <el-dropdown-item v-if="this.roleConfig === 'singleUse'" @click.native="cutRole">-->
            <!-- <el-dropdown-item @click.native="cutRole">
            <span>切换角色</span>
          </el-dropdown-item> -->
          <el-dropdown-item divided @click.native="logout">
            <span>{{logoutName}}</span>
          </el-dropdown-item>
          <!--<el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- <el-dialog :title="username" :visible.sync="dialogVisible" width="26%"  append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="60px">
        <el-form-item v-if="this.showType === 'account'" label="账套" prop="accountSetId" >
          <el-select v-model="form.accountSetId" placeholder="请选择账套" class="w100">
            <el-option v-for="item in accountSetOptions" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="this.showType === 'role'" label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色" class="w100">
            <el-option v-for="item in roleOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>



<script>
import { mapGetters, mapState } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import TopNav from "@/components/TopNav";
import Hamburger from "@/components/Hamburger";
import Screenfull from "@/components/Screenfull";
import SizeSelect from "@/components/SizeSelect";
import Search from "@/components/HeaderSearch";
import ZjRyGit from "@/components/ZjRy/Git";
import ZjRyDoc from "@/components/ZjRy/Doc";
import variables from "@/assets/styles/variables.scss";
import { getCasConfig } from '@/api/basic/yml'
import {queryNotReadMsgNum} from '@/api/system/message'
import {
  initAccountMsg,
  initRoleMsg,
  initLoginMsgById
} from "@/api/system/user";
export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    ZjRyGit,
    ZjRyDoc
  },
  data() {
    return {
    	defaultImg:'this.src="'+require('@/assets/images/profile.png')+'"',
    	avatar2:require('@/assets/images/profile.png'),
      dialogVisible: false,
      // 账套列表
      accountSetOptions: [],
      // 角色列表
      roleOptions: [],
      // 显示类型
      showType: "",
      // 角色参数
      roleConfig: "",
      logoutName: "",
      username: this.$store.state.user.name,
      form: {
        // accountSetId: "",
        // roleId: ""
      },
      // 表单校验
      rules: {
        // accountSetId: [
        //   { required: true, message: "账套不能为空", trigger: "change" }
        // ],
        // roleId: [{ required: true, message: "角色不能为空", trigger: "change" }]
      },
      websocket: null,
      messageCount: ''
    };
  },
  created() {
    this.initWS()
    // this.queryMessageCount()
    // 获取角色系统参数信息
    this.getConfigKey("roleFlag").then(response => {
      console.log(response);
      this.roleConfig = response.msg;
    });
    //根据系统参数判断系统返回单点登录主页面或者退出登录
    this.getConfigKey('cas_enabled').then(response => {
      if(response.msg === '1'){
        this.logoutName = '返回主页面'
      }else{
        this.logoutName = '退出登录'
      }
    })
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device"]),
    variables() {
      return variables;
    },
    ...mapState({
      sideTheme: state => state.settings.sideTheme
    }),
    setting: {
      get() {
        return this.$store.state.settings.showSettings;
      },
      set(val) {
        this.$store.dispatch("settings/changeSetting", {
          key: "showSettings",
          value: val
        });
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav;
      }
    }
  },
  methods: {
    // 切换账套
    // cutAccountSet() {
    //   // 查询账套列表
    //   initAccountMsg().then(response => {
    //     console.log(response.user);
    //     if (response.user.userType === "01") {
    //       this.accountSetOptions = response.user.accountSetList;
    //       this.accountSetOptions.forEach(item => {
    //         if (item.useFlag === "1") {
    //           this.form.accountSetId = item.id;
    //         }
    //       });
    //       // this.username = Cookies.get('username')
    //       this.dialogVisible = true;
    //       this.showType = "account";
    //     } else {
    //       this.msgInfo("系统用户无需切换账套");
    //     }
    //   });
    // },
    // // 切换角色
    // cutRole() {
    //   // 查询角色列表
    //   initRoleMsg().then(response => {
    //     if (response.user.userType === "01") {
    //       console.log(response);
    //       this.roleOptions = response.user.roles;
    //       this.roleOptions.forEach(item => {
    //         if (item.useFlag === "1") {
    //           this.form.roleId = item.roleId;
    //         }
    //       });
    //       //this.username = Cookies.get('username')
    //       this.dialogVisible = true;
    //       this.showType = "role";
    //     } else {
    //       this.msgInfo("系统用户无需切换角色");
    //     }
    //   });
    // },
    // // 确定按钮,
    handleSubmit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 向后台发送选择的账套,获取科室列表并保存在session中
          initLoginMsgById(this.form.accountSetId, this.form.roleId).then(
            response => {
              sessionStorage.setItem(
                "deptRoomList",
                JSON.stringify(response.user.deptRoomList)
              );
              console.log(JSON.parse(sessionStorage.getItem("deptRoomList")));
              this.msgSuccess("切换成功");
              this.dialogVisible = false;
              this.$router.go(0);
            }
          );
        }
      });
    },
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      //根据系统参数判断系统返回单点登录主页面或者退出登录
      this.getConfigKey('cas_enabled').then(response => {
        if(response.msg === '1'){
          this.$confirm('确定返回主页面吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            //查询CAS配置参数
            // getCasConfig().then(response => {
              //跳转到单点登录首页
              // window.location.href = response.data.url
            // })
            window.location.href = this.caslogoutUrl
          }).catch(() => {
          })
        }else{
          this.$confirm('确定注销并退出系统吗？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$store.dispatch('LogOut').then(() => {
              location.href = '/index'
            })
          }).catch(() => {
          })
        }
      })
    },
    // websocket message begin
    initWS() {
      if ('WebSocket' in window) {
        const sysMessageWSUrl = `ws://127.0.0.1:5880/websocket/sysMessage/${this.$store.state.user.userInfo.userId}`
        // const sysMessageWSUrl = 'ws://192.168.5.222:5870/websocket/sysMessage/'+this.$store.state.user.userId;
        this.websocket = new WebSocket(sysMessageWSUrl)
        console.log('websocket1-----' + this.websocket)
        this.initWebSocket()
      } else {
        alert('当前浏览器 Not support websocket')
      }
    },
    initWebSocket() {
      // 连接错误
      this.websocket.onerror = this.onError
      // 连接成功
      this.websocket.onopen = this.onOpen
      // 收到消息的回调
      this.websocket.onmessage = this.onMessage
      // 连接关闭的回调
      this.websocket.onclose = this.onClose
      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = this.onbeforeunload
    },
    onError() {
      console.log('WebSocket连接发生错误, 状态码：' + this.websocket.readyState)
    },
    onOpen() {
      console.log('WebSocket连接成功, 状态码：' + this.websocket.readyState)
    },
    onMessage(event) {
      console.log('服务端返回：' + event.data)
      const msg = JSON.parse(event.data)
      const msgType = msg.msgType;
      console.log('msgType---' + msgType)
      // 接收消息数量
      if("getCount" === msgType) {
        this.messageCount = msg.notReadMsgNum;
      }
      // 接收消息内容
      if("getContent" === msgType) {
        this.messageCount = msg.notReadMsgNum;
        let _this = this;
        this.$notify({
          title: '【消息提醒】'+msg.title,
          dangerouslyUseHTMLString: true,
          message: '<span class="shortContent">'+msg.content+'</span>',
          position: 'bottom-right',
          onClick() {
            _this.toMsgUrl(msg.msgUrl, msg.id);
          }
        })
      }
    },
    onClose() {
      this.initWebSocket()
    },
    onbeforeunload() {
      this.closeWebSocket()
    },
    closeWebSocket() {
      this.websocket.close()
    },
    toMsgUrl(msgUrl, id) {
      msgUrl = msgUrl == '' ? '/message/showDetails' : msgUrl;
      this.$router.push({
        path: msgUrl,
        query: { id: id }
      });
    },
    openMsg() {
      this.$router.push({
        path: '/system/message'
      });
    },
    // queryMessageCount() {
    //   queryNotReadMsgNum().then(response => {
    //     this.messageCount = response.data;
    //   });
    // }
    // websocket message end
  }
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
