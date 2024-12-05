<script src="../../../zjry-spd-prov-ui/src/permission.js"></script>
<template>
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <template v-if="sideTheme === 'theme-dark' || sideTheme === 'theme-blue'">
    	<sidebar class="sidebar-container" :style="{ backgroundColor: variables.menuBg}" />
    </template>
    <template v-else-if="sideTheme === 'theme-light' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'">
    	<sidebar class="sidebar-container" :style="{ backgroundColor: variables.menuLightBg}" />
    </template>
    <template v-else-if="(sideTheme === 'theme-blue3' || sideTheme === 'theme-blue5') && (theme=== '#1890FF')">
    	<sidebar class="sidebar-container" :style="{ backgroundColor: variables.menuBlue}" />
    </template>
    <template v-else-if="(sideTheme === 'theme-blue3' || sideTheme === 'theme-blue5') && (theme!= '#1890FF')">
    	<sidebar class="sidebar-container" :style="{ backgroundColor: theme}" />
    </template>

    <div :class="{hasTagsView:needTagsView}" class="main-container" style="background:#f2f2f2">
      <div :class="{'fixed-header':fixedHeader}">
      	<template v-if="sideTheme === 'theme-dark' || sideTheme === 'theme-blue3'">
        	<navbar :style="{ backgroundColor: variables.menuLightBg }"/>
		    </template>
		    <template v-else-if="sideTheme === 'theme-light'">
		    	<navbar :style="{ backgroundColor: variables.menuLightBg}" />
		    </template>
		    <template v-else-if="(sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'|| sideTheme === 'theme-blue5') && (theme=== '#1890FF')">
		    	<navbar :style="{ backgroundColor: variables.menuBlue}" />
		    </template>
		    <template v-else-if="(sideTheme === 'theme-blue' || sideTheme === 'theme-blue2' || sideTheme === 'theme-blue4'|| sideTheme === 'theme-blue5') && (theme!= '#1890FF')">
		    	<navbar :style="{ backgroundColor: theme}" />
		    </template>

        <tags-view v-if="needTagsView" />
      </div>
      <app-main />
      <right-panel v-if="showSettings">
        <settings />
      </right-panel>
    </div>
    <el-footer height="29px">
      <div class="box">
		<span class="boxs">
          当前单位：<span>{{compName}}({{compId}})</span>
        </span>

        <span class="boxs">
          科室：<span>{{deptName}}({{deptId}})</span>
        </span>
        <span class="boxs">
          用户：<span>{{nickName}}({{userName}})</span>
        </span>
        <span class="boxs">
          IP地址：<span>{{ip}}</span>
        </span>
        <span class="boxs">
          当前时间：<span>{{nowTime}}</span>
        </span>
      </div>
    </el-footer>

  </div>
</template>

<script>
import RightPanel from "@/components/RightPanel";
import { AppMain, Navbar, Settings, Sidebar, TagsView } from "./components";
import ResizeMixin from "./mixin/ResizeHandler";
import { mapState } from "vuex";
import variables from "@/assets/styles/variables.scss";
import { getInfo } from "@/api/basic/user";
// import baseSettings from "@/utils/settingUrl"

export default {
  name: "Layout",
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView
  },
  data() {
    return {
      compName: "",
      compId: "",
      deptName: "",
      deptId: "",
      userName: "",
      nickName: "",
      ip: "",
      nowTime: "",
      accountName: "",
      accountId: ""
    };
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      theme: state => state.settings.theme,
      sideTheme: state => state.settings.sideTheme,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      showSettings: state => state.settings.showSettings,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile"
      };
    },
    variables() {
      return variables;
    }
  },
  created() {
    // baseSettings().then(()=>{
      this.getSysParams()
    // })
    // this.$store.dispatch('GetAccountInfo');
    // this.$store.dispatch("getBaseDicts");
  },
  mounted() {
    this.getInfo();
    this.nowTimes();
  },
  methods: {
    // 获取系统参数
    getSysParams(){
       // 获取系统参数  是否启用供应商  ISSUPPLY    0启用，1不启用
        this.$store.dispatch('settings/getIssupply');
        // 系统参数  是否启用波次管理   ISWELL  0启用，1不启用
        this.$store.dispatch('settings/getIswell');
        // 字典
        this.$store.dispatch("getDicts");
    },
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
    getInfo() {
      getInfo().then(response => {
        if (response.basicdataUser.deptName == null) {
          this.compName = "无";
        } else {
          this.compName = response.basicdataUser.deptName;
          this.compId = response.basicdataUser.deptId;
          this.deptName = response.basicdataUser.deptRoomName;
          this.deptId = response.basicdataUser.deptRoomId;
          this.accountName = response.basicdataUser.accountSet.code;
          this.accountId = response.basicdataUser.accountSet.name;
        }
        this.userName = response.basicdataUser.userName;
        this.nickName = response.basicdataUser.nickName;
        this.ip = response.basicdataUser.loginIp;
      });
    },
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month =
        new Date(timeStamp).getMonth() + 1 < 10
          ? "0" + (new Date(timeStamp).getMonth() + 1)
          : new Date(timeStamp).getMonth() + 1;
      let date =
        new Date(timeStamp).getDate() < 10
          ? "0" + new Date(timeStamp).getDate()
          : new Date(timeStamp).getDate();
      let hh =
        new Date(timeStamp).getHours() < 10
          ? "0" + new Date(timeStamp).getHours()
          : new Date(timeStamp).getHours();
      let mm =
        new Date(timeStamp).getMinutes() < 10
          ? "0" + new Date(timeStamp).getMinutes()
          : new Date(timeStamp).getMinutes();
      let ss =
        new Date(timeStamp).getSeconds() < 10
          ? "0" + new Date(timeStamp).getSeconds()
          : new Date(timeStamp).getSeconds();
      this.nowTime =
        year + "-" + month + "-" + date + "" + " " + hh + ":" + mm + ":" + ss;
    },
    nowTimes() {
      this.timeFormate(new Date());
      setInterval(this.nowTimes, 1000);
      this.clear();
    },
    clear() {
      clearInterval(this.nowTimes);
      this.nowTimes = null;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/mixin.scss";
@import "~@/assets/styles/variables.scss";

.el-footer {
  border-top: 1px solid #eee;
  background: #fef9f9;
  .box {
    line-height: 24px;
    width: 100%;
    .boxs {
      font-size: 12px;
      margin-right: 30px;
      span {
        color: #666;
      }
    }
  }
}
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }
}

.drawer-bg {
  background: #000;
  opacity: 0.3;
  width: 100%;
  top: 0;
  height: 100%;
  position: absolute;
  z-index: 999;
}

.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}

.hideSidebar .fixed-header {
  width: calc(100% - 54px);
}

.mobile .fixed-header {
  width: 100%;
}
</style>
