<template>
  <div class="app-container home" :style="{'height':pageHeight}">
    <!-- <depSelect></depSelect>
  <el-divider/>
  <depTree></depTree> -->
    <!-- <el-dialog :title="username" :visible.sync="dialogVisible" width="26%" :show-close="false"
               :close-on-click-modal="false" :close-on-press-escape="false"  custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="60px" style="border-bottom:0px!important">
        <el-form-item label="账套" prop="accountSetId">
          <el-select v-model="form.accountSetId" placeholder="请选择账套" class="w100">
            <el-option v-for="item in accountSetOptions" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="this.roleConfig === 'singleUse'" label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色" class="w100">
            <el-option v-for="item in roleOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog> -->
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="mb20">
        <el-card class="update-log">
          <div slot="header">
            <b>比例设置</b>
            <el-button style="float: right; padding: 3px 0" type="text"  @click="menuClick('/basic/apportionment/dictionary')"  >更多...</el-button>
          </div>

          <div class="body" style="font-size: 16px;height: 280px;overflow:auto;">
            <div>
              <el-table ref="recordListTable" v-loading="loading" stripe :data="recordList" highlight-current-row @row-click="handleRowClick">
                <el-table-column show-overflow-tooltip label="比例编码" align="left" prop="ratioCode"  width="180"/>
                <el-table-column show-overflow-tooltip label="比例名称" align="left" prop="ratioName"/>
                <el-table-column label="比例类型" align="center" :show-overflow-tooltip="true" prop="ratioType" sortable="custom" :sort-orders="['descending', 'ascending']" :formatter="ratioTypeFormat" >
                </el-table-column>
              </el-table>
            </div>
          </div>
          <el-dialog :title="noticeTitle" :visible.sync="noticeDialog" width="780px" append-to-body>
            <span v-html="noticeContent"></span>
          </el-dialog>
        </el-card>

      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="mb20">
        <el-card class="update-log">
          <div slot="header">
            <b>方案设置</b>
            <el-button style="float: right; padding: 3px 0" type="text"  @click="menuClick('/basic/apportionment/projectFT')"  >更多...</el-button>
          </div>
          <div class="body" style="font-size: 16px;height: 280px;overflow:auto;">
            <div>
              <el-table ref="recordListTable1" v-loading="loading1" stripe :data="recordList1" highlight-current-row @row-click="handleRowClick1">
                <el-table-column show-overflow-tooltip label="方案名称" align="left" prop="projectName"  width="150"/>
                <el-table-column show-overflow-tooltip label="拼音码" align="center" prop="spell"/>
                <el-table-column show-overflow-tooltip label="年月" align="center" prop="nowDate"  width="90"/>
                <el-table-column show-overflow-tooltip label="方案类型" align="center" prop="projectType"  width="160" :formatter="projectTypeFormat" />
              </el-table>
            </div>
          </div>
          <el-dialog :title="noticeTitle" :visible.sync="noticeDialog" width="780px" append-to-body>
            <span v-html="noticeContent"></span>
          </el-dialog>
        </el-card>

      </el-col>
<!--      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="mb20">-->
<!--        <el-card class="update-log">-->
<!--          <div slot="header">-->
<!--            <b>分摊计算</b>-->
<!--            <el-button style="float: right; padding: 3px 0" type="text"  @click="menuClick('/basic/apport/apport')"  >更多...</el-button>-->
<!--          </div>-->

<!--          <div class="body" style="font-size: 16px;height: 280px;overflow:auto;">-->
<!--            <div>-->
<!--              <el-table ref="recordListTable2" v-loading="loading2" stripe :data="recordList2" highlight-current-row @row-click="handleRowClick2">-->
<!--                <el-table-column show-overflow-tooltip label="分摊编码" align="left" prop="apportCode"  width="180"/>-->
<!--                <el-table-column show-overflow-tooltip label="年月" align="left" prop="nowDate"/>-->
<!--                <el-table-column show-overflow-tooltip label="分摊级别" align="center" prop="apportType" :formatter="apportTypeFormat" />-->
<!--              </el-table>-->
<!--            </div>-->
<!--          </div>-->
<!--          <el-dialog :title="noticeTitle" :visible.sync="noticeDialog" width="780px" append-to-body>-->
<!--            <span v-html="noticeContent"></span>-->
<!--          </el-dialog>-->
<!--        </el-card>-->

<!--      </el-col>-->
      <el-col :xs="24" :sm="24" :md="12" :lg="12" class="mb20">
        <el-card class="update-log">
          <div slot="header">
            <b>更新日志</b>
          </div>
          <el-collapse accordion style="height: 280px;overflow:auto;">
            <el-collapse-item title="v3.5.01 - 2021-07-2">
              <ol>
                <li>自定义查询组件</li>
                <li>集成ureport2报表开发</li>
                <li>单点登录</li>
                <li>登录密码加密、数据库密码加密</li>
                <li>集成v-charts图表开发组件</li>
                <li>新增组件样例</li>
                <li>机构、科室、医生、区划功能开发与数据导入</li>
                <li>集成mybatis-plus</li>
                <li>集成swagger接口文档与联调测试</li>
                <li>首页开发</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" class="mb20">
        <el-card class="update-log">
          <div slot="header">
            <b>快速开始 / 便捷导航</b>
          </div>
          <el-collapse accordion style="height: 280px;overflow:auto">
            <el-row>
              <el-col :span="6"><a @click="menuClick('/system/user')">用户管理</a></el-col>
              <el-col :span="6"><a @click="menuClick('/system/menu')">菜单管理</a></el-col>
              <el-col :span="6"><a @click="menuClick('/componentExample/userDefinedQueryExample')">自定义查询</a></el-col>
              <el-col :span="6"><a @click="menuClick('/tool/ureport')">报表设计</a></el-col>
            </el-row>
            <el-row style="margin-top: 20px">
              <el-col :span="6"><a @click="menuClick('/tool/gen')">代码生成</a></el-col>
              <el-col :span="6"><a @click="menuClick('/tool/swagger')">系统接口</a></el-col>
            </el-row>
          </el-collapse>
        </el-card>
        <component v-if="open" :is="componentUrl" :id="componentId" :proclnsIds="proclnsIds" :open="open" @cancel="cancel" @success="submitSuccess"></component>

        <!-- 流转历史对话框 -->
        <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
          <div v-loading="loading" :style="'height:'+ height">
            <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>


<script>
  import { mapGetters } from "vuex";
  import { listNotice } from "@/api/system/notice";
  import { getUserProfile, getZjryConfig } from "@/api/system/user";
  import depTree from "@/components/Dept/dept_tree";
  import depSelect from "@/components/Dept/dept_select";
  import store from "@/store";
  import Cookies from "js-cookie";
  import {
    initAccountMsg,
    initLoginMsgById,
    initRoleMsg
  } from "@/api/system/user";
  import { listBacklog, listIntransit, listAlready } from "@/api/process/model";

  export default {
    name: "index1",
    components: { depTree, depSelect },
    data() {
      return {
        // 导航栏切换index
        activeIndex: "1",
        // 版本号
        version: "3.5.0",
        dialogVisible: false,
        // 账套列表
        accountSetOptions: [],
        // 角色列表
        roleOptions: [],
        // 角色参数
        roleConfig: "",
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
        // 通知列表数据
        informList: [],
        // 公告列表数据
        noticeList: [],
        // 通知公告标题
        noticeTitle: "",
        // 通知公告窗口按钮
        noticeDialog: false,
        // 通知公告内容
        noticeContent: "",
        // 用户信息
        user: {
          userName: "",
          dept: {
            deptName: ""
          }
        },
        //比例设置列表
        recordList: [],
        //比例类型
        ratioTypeOptions:[],
        loading:false,
        loading1:false,
        loading2:false,
        //方案设置列表
        recordList1: [],
        //属性类型
        projectTypeOptions: [],
        //分摊计算列表
        recordList2: [],
        // 分摊级别字典
      apportTypeOptions: [],
        componentUrl: null,
        // 选中数组
        proclnsIds: "",
        // 流转历史对话框
        processOpen: false,
        componentId: "",
        src: "",
        height: "500px;",
        // 是否显示弹出层
        open: false,
        selectionRow: [],
        // 查询参数
        queryParams: {
          pageNo: 1,
          pageSize: 30,
          compCode: null,
          deptCode: null,
          systemId: "PMS",
          userId: this.$store.state.user.userInfo.userId
        },
        total:0,
        total1:0,
        total2:0,
        postGroup: {},
        loading: false,
        pageHeight: "200px"
      };
    },

    computed: {
      ...mapGetters(["avatar"])
    },
    created() {
      console.log('加载index.vue' );
      this.getList();
      this.getList1();
      // this.getList2();
      this.getDicts("cost_scale_type").then(response => {
        this.ratioTypeOptions = response.data;
      });
      this.getDicts("cost_fa_type").then(response => {
       this.projectTypeOptions = response.data;
      });
      this.getDicts("cost_apport_type").then(response => {
        this.apportTypeOptions = response.data;
      });
      let indexDialogVisible = sessionStorage.getItem("indexDialogVisible");
      if (indexDialogVisible === "1") {
        // 查询账套列表
        initAccountMsg().then(response => {
          if (response.user.userType === "01") {
            this.accountSetOptions = response.user.accountSetList;
            //this.username = Cookies.get('username')
            this.dialogVisible = true;
          }
        });
        // 获取角色系统参数信息
        this.getConfigKey("roleFlag").then(response => {
          this.roleConfig = response.msg;
          //判断是橘色是单个使用还是合并使用
          if (response.msg === "singleUse") {
            // 查询角色列表
            initRoleMsg().then(response => {
              console.log(response, 12565225);
              if (response.user.userType === "01") {
                this.roleOptions = response.user.roles;
                //this.username = Cookies.get('username')
                this.dialogVisible = true;
              }
            });
          }
        });
      }
      /** 查询通知列表 */
      /*listNotice({ noticeType: "1" }).then(response => {
        this.informList = response.rows;
      });
      /!** 查询公告列表 *!/
      listNotice({ noticeType: "2" }).then(response => {
        this.noticeList = response.rows;
      });*/
      // 获取用户信息
      getUserProfile().then(response => {
        this.user = response.data;
        this.postGroup = response.postGroup;
      });

      getZjryConfig().then(response => {
        this.version = response.data.version;
      });
    },
    methods: {
      // 比例类型字典翻译
    ratioTypeFormat(row, column) {
      return this.selectDictLabel(this.ratioTypeOptions, row.ratioType);
    },
      // 方案类型字典翻译
    projectTypeFormat(row, column) {
      return this.selectDictLabel(this.projectTypeOptions, row.projectType);
    },
    // 分摊级别字典翻译
    apportTypeFormat(row, column) {
      return this.selectDictLabel(this.apportTypeOptions, row.apportType);
    },
      // // 取消按扭
      // close() {
      //   // 重定向到登录页面
      //   store.dispatch("LogOut").then(() => {
      //     location.href = "/index";
      //   });
      //   sessionStorage.setItem("indexDialogVisible", "0");
      //   this.dialogVisible = false;
      // },
      // // 确定按钮,
      // handleSubmit() {
      //   this.$refs["form"].validate(valid => {
      //     if (valid) {
      //       // 向后台发送选择的账套,获取科室列表并保存在session中
      //       initLoginMsgById(this.form.accountSetId, this.form.roleId).then(
      //         response => {
      //           sessionStorage.setItem("indexDialogVisible", "0");
      //           sessionStorage.setItem(
      //             "deptRoomList",
      //             JSON.stringify(response.user.deptRoomList)
      //           );
      //           console.log(JSON.parse(sessionStorage.getItem("deptRoomList")));
      //           this.dialogVisible = false;
      //           this.$router.go(0);
      //         }
      //       );
      //     }
      //   });
      // },
      /** 导航切换 */
      handleSelect(key, keyPath) {
        this.activeIndex = key;
      },
      getList() {
        this.loading=true
        listBacklog(this.queryParams).then(response => {
          if (response.code===200) {
             this.recordList =response.rows
             this.total=response.total
             this.loading=false
          }
        });
      },
      getList1() {
        this.loading1=true
        listIntransit(this.queryParams).then(response => {
           if (response.code===200) {
             this.recordList1 =response.rows
             this.total1=response.total
             this.loading1=false
          }
        });
      },
      // getList2() {
      //   this.loading2=true
      //   listAlready(this.queryParams).then(response => {
      //      if (response.code===200) {
      //        this.recordList2 =response.rows
      //        this.total2=response.total
      //        this.loading2=false
      //     }
      //   });
      // },
      // 点击行比例编辑跳转
      handleRowClick(row) {
        this.menuClick('/basic/apportionment/dictionary',row)
      },
      // 点击行
      handleRowClick1(row) {
       this.menuClick('/basic/apportionment/projectFT',row)
      },
      // 点击行
      handleRowClick2(row) {
        this.menuClick('/basic/apport/apport',row)
      },
      submitSuccess(flag) {
        this.open = flag;
        this.getList();
      },
      /** 流转历史初始化 */
      processExamineForm() {
        this.src =
          `${this.processUrl}/api/wf/processHistoric?procInsId=${this.proclnsIds}`;
      },
      /** 流转历史按钮操作 */
      processExamine() {
        this.loading = true;
        console.log(this.proclnsIds);
        if (this.proclnsIds == null || !this.proclnsIds) {
          this.msgError("没有流转历史数据！");
        } else {
          this.processExamineForm();
          this.processOpen = true;
          this.loading = false;
        }
      },
      handleClick(row) {
        let url = row.formUrl;
        this.componentUrl = require(`@/views/${url}`).default;
        this.open = true;
        this.componentId = row.busiId;
        this.proclnsIds = row.procInsId;
      },
      // 通知公告点击事件
      clickNotice(notice) {
        this.noticeTitle = notice.noticeTitle;
        this.noticeContent = notice.noticeContent;
        this.noticeDialog = true;
      },
      // 菜单点击事件
      menuClick(path,query) {
        this.$router.push({ path: path,query:query });
      },
      // 链接跳转
      goTarget(href) {
        window.open(href, "_blank");
      },
      // 取消按钮
      cancel(flag) {
        this.open = flag;
      },
    }
  };
</script>

<style scoped lang="scss">
  .home {
    padding: 20px;
    overflow-y: auto;
    blockquote {
      padding: 10px 20px;
      margin: 0 0 20px;
      font-size: 17.5px;
      border-left: 5px solid #eee;

      .user-avatar {
        cursor: pointer;
        width: 80px;
        height: 80px;
        border-radius: 10px;
      }
    }

    hr {
      margin-top: 20px;
      margin-bottom: 20px;
      border: 0;
      border-top: 1px solid #eee;
    }

    .col-item {
      margin-bottom: 20px;
    }

    ul {
      padding: 0;
      margin: 0;
      list-style-type: disc;

      li {
        margin-left: 10px;
      }
    }

    font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
    font-size: 13px;
    color: #676a6c;
    overflow-x: hidden;

    h4 {
      margin-top: 0px;
    }

    h2 {
      margin-top: 10px;
      font-size: 26px;
      font-weight: 100;
    }

    p {
      margin-top: 10px;

      b {
        font-weight: 700;
      }
    }

    .update-log {
      ol {
        display: block;
        list-style-type: decimal;
        margin-block-start: 1em;
        margin-block-end: 1em;
        margin-inline-start: 0;
        margin-inline-end: 0;
        padding-inline-start: 40px;
      }
    }
  }
</style>

