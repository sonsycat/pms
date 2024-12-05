<template>
<!-- 对应关系设置 - 用户与账套对应关系 -->
  <div class="app-container">
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            size="mini"
            @click="handleAdd"
            :disabled="singleUser"
          >保存</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            icon="el-icon-refresh"
            size="mini"
            @click="resetQuery">重置</el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getListUser"></right-toolbar>
      </el-row>
  </div>


<el-row :gutter="15">
    <el-col :span="12" >
    <el-card>
        <div ref="search">
          <el-form :model="queryParamsUser" ref="queryFormUser" :inline="true" v-show="showSearchUser" label-width="68px">
            <el-form-item label="" prop="userName" >
              <el-input
                v-model="queryParamsUser.userName"
                placeholder="输入信息（人员、单位）"
                clearable
                size="small"
                @input="handleQueryUser"
                @keyup.enter.native="handleQueryUser"
              />
            </el-form-item>
          </el-form>
      </div>
        <el-table :height="tableHeight" v-loading="loading" ref="userListRef" :data="userList" @selection-change="handleSelectionChangeUser" :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="编号" align="left" key="userId" prop="userId" />
          <el-table-column label="登录名" align="left" key="userName" prop="userName" />
          <el-table-column label="姓名" align="left" key="nickName" prop="nickName" />
          <el-table-column label="单位" align="left" key="deptName" prop="dept.deptName"/>
          <el-table-column label="科室" align="left"  prop="" />
        </el-table>
        <pagination
          v-show="totalUser>0"
          :total="totalUser"
          :page.sync="queryParamsUser.pageNum"
          :limit.sync="queryParamsUser.pageSize"
          @pagination="getListUser"
        />
      </el-card>
    </el-col>

    <el-col :span="12" >
      <el-col :span="24" class="mb10">
        <el-card>
          <div ref="ssearch">
            <el-form :model="queryParamsAccountUser" ref="queryFormAccountUser" :inline="true" v-show="showSearchAccountUser" label-width="68px" row-class-name="tableRowClassName">
              <el-col :span="24">
                <el-form-item  label="已选账套" prop="">
                </el-form-item>
              </el-col>
              <el-form-item  label="" prop="queryCode">
                <el-input
                  v-model="queryParamsAccountUser.queryCode"
                  placeholder="输入信息（账套id、编码、名称）"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQueryAccountUser"
                  @input="handleQueryAccountUser"
                />
              </el-form-item>
            </el-form>
        </div>
        <el-table  v-loading="accountListUserLoading" :data="accountListUser" :height="stableHeight" @selection-change="handleSelectionChangeAccountUser" @select="selectionAccountUser">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="账套id" align="left" prop="id" />
          <el-table-column label="账套编码" align="left" prop="accountCode" />
          <el-table-column label="账套名称" align="left" prop="accountName" show-overflow-tooltip/>
        </el-table>
        <pagination
          v-show="totalAccountUser>0"
          :total="totalAccountUser"
          :page.sync="queryParamsAccountUser.pageNum"
          :limit.sync="queryParamsAccountUser.pageSize"
          @pagination="getListAccountUser"
        />
          </el-card>
      </el-col>
      <el-col :span="24" >
        <el-card>
        <el-form :model="queryParamsAccount" ref="queryFormAccount" :inline="true" v-show="showSearchAccount" label-width="68px" row-class-name="tableRowClassName">
          <el-col :span="24">
            <el-form-item  label="待选账套" prop="">
            </el-form-item>
          </el-col>
          <el-form-item  label="" prop="queryCode">
            <el-input
              v-model="queryParamsAccount.queryCode"
              placeholder="输入信息（账套id、编码、名称）"
              clearable
              size="small"
              @keyup.enter.native="handleQueryAccount"
              @input="handleQueryAccount"
            />
          </el-form-item>
        </el-form>

        <el-table  v-loading="accountListLoading" :data="accountList" :height="stableHeight"  @selection-change="handleSelectionChangeAccount" @select="selectionAccount">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="账套id" align="left" prop="id" />
          <el-table-column label="账套编码" align="left" prop="accountCode" />
          <el-table-column label="账套名称" align="left" prop="accountName" show-overflow-tooltip/>
        </el-table>
        <pagination
          v-show="totalAccount>0"
          :total="totalAccount"
          :page.sync="queryParamsAccount.pageNum"
          :limit.sync="queryParamsAccount.pageSize"
          @pagination="getListAccount"
        />
        </el-card>
      </el-col>
    </el-col>

</el-row>






  </div>
</template>

<script>
import {
  listRelationUserAccount,
  addRelationUserAccount
} from "@/api/basic/relationUserAccount";
import { listAccountUser } from "@/api/basic/account";
import { listUser } from "@/api/system/user";

export default {
  //name: "RelationUserStore",
  components: {},
  data() {
    return {
      tableHeight: "auto",
      stableHeight: "auto",
      // 遮罩层
      loading: true,
      accountListUserLoading:false,
      accountListLoading:false,
      // 导出遮罩层
      exportLoading: false,
      // 用户选中数组
      idsUser: [],
      // 未关联账套选中数组
      idsAccount: [],
      // 已关联账套选中数组
      idsAccountUser: [],
      // 账套编码
      accountCode: [],
      // 账套名称
      accountName: [],
      // 账套分类
      storeType: [],
      // 单位编码
      compCode: [],
      // 单位
      compName: [],
      // 选择用户编码
      userIdPost: [],
      // 选择关联账套列表
      accountCodeList: [],
      // 非单个禁用
      singleUser: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 显示用户搜索条件
      showSearchUser: true,
      // 显示未关联账套搜索条件
      showSearchAccount: true,
      // 显示用户关联账套搜索条件
      showSearchAccountUser: true,
      // 用户总条数
      totalUser: 0,
      // 未关联账套总条数
      totalAccount: 0,
      // 用户关联账套总条数
      totalAccountUser: 0,
      // 用户账套关联表格数据
      relationUserAccountList: [],
      // 用户数据
      userList: [],
      // 用户未关联账套数据
      accountList: [],
      // 用户关联账套数据
      accountListUser: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        deptCode: null,
        userId: null,
        accountCode: null
      },
      //用户查询参数
      queryParamsUser: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: this.$store.state.user.userInfo.deptId
      },
      //用户未关联账套查询参数
      queryParamsAccount: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        accountCode: null,
        accountName: null,
        isUser: null,
        userId: null,
        queryCode: null
      },
      //用户关联账套查询参数
      queryParamsAccountUser: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        accountCode: null,
        accountName: null,
        isUser: null,
        userId: null,
        queryCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: []
    };
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    },
    accountListUser(val) {
      this.totalAccountUser = val.length;
    },
    accountList(val) {
      this.totalAccount = val.length;
    }
  },
  created() {
    this.getListUser();
  },
  mounted() {
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + this.$store.state.settings.tableHeight + 24;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
      let c = this.$refs.ssearch.offsetHeight;
      let sh = b + c + this.$store.state.settings.tableHeight + 134;
      sh = sh + "px";
      this.stableHeight = `calc((100vh - ${sh})/2)`;
    },
    tableRowClassName({ row, rowIndex }) {
      row.row_index = rowIndex;
    },
    /** 查询用户账套关联列表 */
    getList() {
      this.loading = true;
      listRelationUserAccount(this.queryParams).then(response => {
        if (response.code == 200) {
          this.relationUserAccountList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    /** 查询用户列表 */
    getListUser() {
      this.loading = true;

      listUser(this.addDateRange(this.queryParamsUser, this.dateRange)).then(
        response => {
          this.userList = response.rows;
          this.totalUser = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询非用户关联账套列表 */
    getListAccount() {
      this.queryParamsAccount.isUser = "2";
      if (this.queryParamsAccount.userId != null) {
        this.accountListLoading = true
        listAccountUser(this.queryParamsAccount).then(response => {
          if (response.code == 200) {
            this.accountList = response.rows;
            console.log(this.accountList, "store");
            this.totalAccount = response.total;
            this.accountListLoading = false
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    /** 查询用户关联账套列表 */
    getListAccountUser() {
      this.queryParamsAccountUser.isUser = "1";
      if (this.queryParamsAccountUser.userId != null) {
        this.accountListUserLoading= true
        listAccountUser(this.queryParamsAccountUser).then(response => {
          if (response.code == 200) {
            this.accountListUser = response.rows;
            console.log(this.accountListUser, "storeUser");
            this.totalAccountUser = response.total;
            this.accountListUserLoading= false
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        deptCode: null,
        userId: null,
        accountCode: null,
        queryCode: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 用户搜索按钮操作 */
    handleQueryUser() {
      this.queryParamsUser.pageNum = 1;
      this.getListUser();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 未关联账套搜索按钮操作 */
    handleQueryAccount() {
      this.queryParamsAccount.pageNum = 1;
      this.getListAccount();
    },
    /** 关联账套搜索按钮操作 */
    handleQueryAccountUser() {
      this.queryParamsAccountUser.pageNum = 1;
      this.getListAccountUser();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryFormUser");
      this.resetForm("queryFormAccount");
      this.resetForm("queryFormAccountUser");
      this.accountListUser = [];
      this.accountList = []
      this.handleQueryUser();
    },
    handleRowClick(row, flag = true) {
      let userListRef = this.$refs.userListRef;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.userId == row.userId);
        if (findRow) {
          userListRef.toggleRowSelection(row, false);
          return;
        }
      }
      userListRef.clearSelection(); //清空列表的选中
      userListRef.toggleRowSelection(row, true);
    },
    handleSortChange() {},
    // 用户多选框选中数据
    handleSelectionChangeUser(selection) {
      let userListRef = this.$refs.userListRef;
      if (selection.length > 1) {
        userListRef.clearSelection(); //清空列表的选中
        userListRef.toggleRowSelection(selection[selection.length - 1], true); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      this.idsRelation = selection.map(item => item.relaCode);
      this.singleRelation = selection.length !== 1;
      this.multiple = !selection.length;

      this.idsUser = selection.map(item => item.userId);
      this.singleUser = selection.length !== 1;
      this.multiple = !selection.length;

      if (selection.length !== 1) {
        this.accountList = [];
      } else {
        this.queryParamsAccount.userId = this.idsUser[0];
        this.userIdPost = this.idsUser[0];
        this.getListAccount();
      }

      if (selection.length !== 1) {
        this.accountListUser = [];
      } else {
        this.queryParamsAccountUser.userId = this.idsUser[0];
        this.getListAccountUser();
      }
    },
    // 账套多选框选中数据
    handleSelectionChangeAccount(selection) {
      this.idsAccount = selection.map(item => item.id);
    },

    // 账套多选框选中数据
    selectionAccount(selection, row) {
      this.idsAccount = selection.map(item => item.id);

      if (selection.length == 1) {
        let {
          id,
          accountCode,
          accountName,
        } = selection[0];
        this.accountListUser.push({
          id,
          accountCode,
          accountName,
        });
        this.accountList = this.accountList.filter(el => el.id != selection[0].id);
      }
    },
    // 关联用户账套多选框选中数据
    handleSelectionChangeAccountUser(selection) {
      this.idsAccountUser = selection.map(item => item.id);
    },
    // 关联用户账套多选框选中数据
    selectionAccountUser(selection, row) {
      this.idsAccountUser = selection.map(item => item.id);

      if (selection.length == 1) {
        let {
          id,
          accountCode,
          accountName,
        } = selection[0];
        this.accountList.push({
          id,
          accountCode,
          accountName,
        });
        this.accountListUser = this.accountListUser.filter(
          el => el.id != selection[0].id
        );
      }
    },
    /** 保存按钮操作 */
    handleAdd(row) {
      const ids = this.idsUser[0];
      this.$confirm(
        '是否确认保存用户编号为"' + ids + '"的关联账套数据?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        let data = {
          userId: this.userIdPost,
          assetsBasicAccounts: this.accountListUser
        };
        addRelationUserAccount(data).then(response => {
          if (response.code == 200) {
            this.getListUser();
            this.msgSuccess("保存成功");
          } else {
            this.msgError(response.msg);
          }
        });
      });
    }
  }
};
</script>
