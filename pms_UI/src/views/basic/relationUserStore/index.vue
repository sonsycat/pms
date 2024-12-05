<template>
<!-- 对应关系设置 - 用户与仓库对应关系 -->
  <div class="app-container">
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            :disabled="singleUser"
            v-hasPermi="['basic:relationUserStore:add']"
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
            <el-form :model="queryParamsStoreUser" ref="queryFormStoreUser" :inline="true" v-show="showSearchStoreUser" label-width="68px" row-class-name="tableRowClassName">
              <el-col :span="24">
                <el-form-item  label="已选仓库" prop="">
                </el-form-item>
              </el-col>
              <el-form-item  label="" prop="queryCode">
                <el-input
                  v-model="queryParamsStoreUser.queryCode"
                  placeholder="输入信息（仓库、单位）"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQueryStoreUser"
                  @input="handleQueryStoreUser"
                />
              </el-form-item>
            </el-form>
        </div>
        <el-table  v-loading="storeListUserLoading" :data="storeListUser" :height="stableHeight" @selection-change="handleSelectionChangeStoreUser" @select="selectionStoreUser">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="仓库编码" align="left" prop="storeCode" />
          <el-table-column label="仓库名称" align="left" prop="storeName" show-overflow-tooltip/>
          <el-table-column label="库房分类" align="left" prop="storeType" :formatter="storeTypeFormat" />
          <el-table-column label="单位" align="left" prop="compName" show-overflow-tooltip/>
        </el-table>
        <pagination
          v-show="totalStoreUser>0"
          :total="totalStoreUser"
          :page.sync="queryParamsStoreUser.pageNum"
          :limit.sync="queryParamsStoreUser.pageSize"
          @pagination="getListStoreUser"
        />
          </el-card>
      </el-col>
      <el-col :span="24" >
        <el-card>
        <el-form :model="queryParamsStore" ref="queryFormStore" :inline="true" v-show="showSearchStore" label-width="68px" row-class-name="tableRowClassName">
          <el-col :span="24">
            <el-form-item  label="待选仓库" prop="">
            </el-form-item>
          </el-col>
          <el-form-item  label="" prop="queryCode">
            <el-input
              v-model="queryParamsStore.queryCode"
              placeholder="输入信息（仓库、单位）"
              clearable
              size="small"
              @keyup.enter.native="handleQueryStore"
              @input="handleQueryStore"
            />
          </el-form-item>
        </el-form>

        <el-table  v-loading="storeListLoading" :data="storeList" :height="stableHeight"  @selection-change="handleSelectionChangeStore" @select="selectionStore">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="仓库编码" align="left" prop="storeCode" />
          <el-table-column label="仓库名称" align="left" prop="storeName" show-overflow-tooltip/>
          <el-table-column label="库房分类" align="left" prop="storeType" :formatter="storeTypeFormat" />
          <!--<el-table-column label="单位编码" align="left" prop="compCode" show-overflow-tooltip/>-->
          <el-table-column label="单位" align="left" prop="compName" show-overflow-tooltip/>
        </el-table>
        <pagination
          v-show="totalStore>0"
          :total="totalStore"
          :page.sync="queryParamsStore.pageNum"
          :limit.sync="queryParamsStore.pageSize"
          @pagination="getListStore"
        />
        </el-card>
      </el-col>
    </el-col>

</el-row>






  </div>
</template>

<script>
import {
  listRelationUserStore,
  addRelationUserStore
} from "@/api/basic/relationUserStore";
import { listStoreUser } from "@/api/basic/store";
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
      storeListUserLoading:false,
      storeListLoading:false,
      // 导出遮罩层
      exportLoading: false,
      // 用户选中数组
      idsUser: [],
      // 未关联仓库选中数组
      idsStore: [],
      // 已关联仓库选中数组
      idsStoreUser: [],
      // 仓库编码
      storeCode: [],
      // 仓库名称
      storeName: [],
      // 仓库分类
      storeType: [],
      // 单位编码
      compCode: [],
      // 单位
      compName: [],
      // 选择用户编码
      userCodePost: [],
      // 选择关联仓库列表
      storeCodeList: [],
      // 非单个禁用
      singleUser: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 显示用户搜索条件
      showSearchUser: true,
      // 显示未关联仓库搜索条件
      showSearchStore: true,
      // 显示用户关联仓库搜索条件
      showSearchStoreUser: true,
      // 用户总条数
      totalUser: 0,
      // 未关联仓库总条数
      totalStore: 0,
      // 用户关联仓库总条数
      totalStoreUser: 0,
      // 用户仓库关联表格数据
      relationUserStoreList: [],
      // 用户数据
      userList: [],
      // 用户未关联仓库数据
      storeList: [],
      // 用户关联仓库数据
      storeListUser: [],
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
        userCode: null,
        storeCode: null
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
      //用户未关联仓库查询参数
      queryParamsStore: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        storeCode: null,
        storeName: null,
        isUser: null,
        userCode: null,
        queryCode: null
      },
      //用户关联仓库查询参数
      queryParamsStoreUser: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        storeCode: null,
        storeName: null,
        isUser: null,
        userCode: null,
        queryCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      storeTypeOptions: [],
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
    storeListUser(val) {
      this.totalStoreUser = val.length;
    },
    storeList(val) {
      this.totalStore = val.length;
    }
  },
  created() {
    this.getListUser();
    this.getDicts("basic_store_type").then(response => {
      this.storeTypeOptions = response.data;
    });
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
      let sh = b + c + this.$store.state.settings.tableHeight + 160;
      sh = sh + "px";
      this.stableHeight = `calc((100vh - ${sh})/2)`;
    },
    tableRowClassName({ row, rowIndex }) {
      row.row_index = rowIndex;
    },
    /** 查询用户仓库关联列表 */
    getList() {
      this.loading = true;
      listRelationUserStore(this.queryParams).then(response => {
        if (response.code == 200) {
          this.relationUserStoreList = response.rows;
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
    /** 查询非用户关联仓库列表 */
    getListStore() {
      this.queryParamsStore.isUser = "2";
      if (this.queryParamsStore.userCode != null) {
        this.storeListLoading = true
        listStoreUser(this.queryParamsStore).then(response => {
          if (response.code == 200) {
            this.storeList = response.rows;
            console.log(this.storeList, "store");
            this.totalStore = response.total;
            this.storeListLoading = false            
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    /** 查询用户关联仓库列表 */
    getListStoreUser() {
      this.queryParamsStoreUser.isUser = "1";
      if (this.queryParamsStoreUser.userCode != null) {
        this.storeListUserLoading= true
        listStoreUser(this.queryParamsStoreUser).then(response => {
          if (response.code == 200) {
            this.storeListUser = response.rows;
            console.log(this.storeListUser, "storeUser");
            this.totalStoreUser = response.total;
            this.storeListUserLoading= false            
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    // 库房分类字典翻译
    storeTypeFormat(row, column) {
      return this.selectDictLabel(this.storeTypeOptions, row.storeType);
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
        userCode: null,
        storeCode: null,
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
    /** 未关联仓库搜索按钮操作 */
    handleQueryStore() {
      this.queryParamsStore.pageNum = 1;
      this.getListStore();
    },
    /** 关联仓库搜索按钮操作 */
    handleQueryStoreUser() {
      this.queryParamsStoreUser.pageNum = 1;
      this.getListStoreUser();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryFormUser");
      this.resetForm("queryFormStore");
      this.resetForm("queryFormStoreUser");
      this.handleQuery();
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
        this.storeList = [];
      } else {
        this.queryParamsStore.userCode = this.idsUser[0];
        this.userCodePost = this.idsUser[0];
        this.getListStore();
      }

      if (selection.length !== 1) {
        this.storeListUser = [];
      } else {
        this.queryParamsStoreUser.userCode = this.idsUser[0];
        this.getListStoreUser();
      }
    },
    // 仓库多选框选中数据
    handleSelectionChangeStore(selection) {
      this.idsStore = selection.map(item => item.storeCode);
    },

    // 仓库多选框选中数据
    selectionStore(selection, row) {
      this.idsStore = selection.map(item => item.storeCode);

      if (selection.length == 1) {
        let {
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        } = selection[0];
        this.storeListUser.push({
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        });
        this.storeList = this.storeList.filter(el => el.id != selection[0].id);
      }
    },
    // 关联用户仓库多选框选中数据
    handleSelectionChangeStoreUser(selection) {
      this.idsStoreUser = selection.map(item => item.storeCode);
    },
    // 关联用户仓库多选框选中数据
    selectionStoreUser(selection, row) {
      this.idsStoreUser = selection.map(item => item.storeCode);

      if (selection.length == 1) {
        let {
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        } = selection[0];
        this.storeList.push({
          storeCode,
          storeName,
          storeType,
          compName,
          compCode
        });
        this.storeListUser = this.storeListUser.filter(
          el => el.id != selection[0].id
        );
      }
    },
    /** 保存按钮操作 */
    handleAdd(row) {
      const ids = this.idsUser[0];
      this.$confirm(
        '是否确认保存用户编号为"' + ids + '"的关联仓库数据?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      ).then(() => {
        let data = {
          userCode: this.userCodePost,
          assetsBasicStoreList: this.storeListUser
        };
        addRelationUserStore(data).then(response => {
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
