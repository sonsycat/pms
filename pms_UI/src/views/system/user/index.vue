<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="5" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container-body">
          <el-tree
            lazy
            :data="deptOptions"
            :props="defaultProps"
            :load="getDeptDatas"
            :filter-node-method="filterNode"
            :highlight-current="true"
            :indent=8
            ref="tree"
            @node-click="handleNodeClick"
          />
          <!--style="size: 500px"-->
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="19" :xs="24">
        <div ref="search">
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="用户名称" prop="userName">
              <el-input
                v-model="queryParams.userName"
                placeholder="请输入用户名称"
                clearable
                size="small"
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input
                v-model="queryParams.phonenumber"
                placeholder="请输入手机号码"
                clearable
                size="small"
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="queryParams.status"
                placeholder="用户状态"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="dateRange"
                size="small"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['system:user:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['system:user:edit']"
              >修改</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['system:user:remove']"
              >删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="info"
                icon="el-icon-upload2"
                size="mini"
                @click="handleImport"
                v-hasPermi="['system:user:import']"
              >导入</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                icon="el-icon-upload2"
                size="mini"
                :loading="exportLoading"
                @click="handleExport"
                v-hasPermi="['system:user:export']"
              >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
          </el-row>
        </div>
        <el-table
          ref="userTable"
          v-loading="loading"
          :height="tableHeight"
          :data="userList"
          :row-style="rowStyle"
          @row-click="rowClick"
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="用户名称" align="left" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="用户昵称" align="left" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="部门" align="left" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="科室" align="left" key="deptRoomName" prop="deptRoomName" v-if="columns[4].visible" :show-overflow-tooltip="true" />
          <el-table-column label="手机号码" align="right" key="phonenumber" prop="phonenumber" v-if="columns[5].visible" width="120" />
          <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                title="修改"
                v-hasPermi="['system:user:edit']"
              ></el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                title="删除"
                v-hasPermi="['system:user:remove']"
              ></el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-key"
                @click="handleResetPwd(scope.row)"
                title="重置密码"
                v-hasPermi="['system:user:resetPwd']"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <el-dialog :close-on-click-modal = "false"  :title="title" :visible.sync="doctorOpen" width="700px" append-to-body custom-class="el-button--primary dialogPeople">
      <el-table v-loading="loading" :data="doctorList" :row-class-name="tableRowClassName" @select="handleSelectionDocChange" @row-click="handleDocRowClick" ref="itsmDataTable" >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="工号" align="center" key="empCode" prop="empCode"  v-if="columns[0].visible" />
        <el-table-column label="姓名" align="center" key="empName" prop="empName"  v-if="columns[1].visible" :show-overflow-tooltip="true" />
        <el-table-column label="机构" align="center" key="compCode" prop="compCode" v-if="columns[2].visible" :show-overflow-tooltip="true" />
        <el-table-column label="科室" align="center" key="deptCode" prop="deptCode" v-if="columns[3].visible" :show-overflow-tooltip="true" />
        <el-table-column label="性别" align="center" key="gender" prop="gender" :formatter="sexFormat" v-if="columns[4].visible" :show-overflow-tooltip="true" />
      </el-table>
      <pagination
        v-show="docTotal>0"
        :total="docTotal"
        :page.sync="docParams.pageNum"
        :limit.sync="docParams.pageSize"
        @pagination="selectDoctor"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmDoc">确 定</el-button>
        <el-button @click="doctorOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <!--          <el-col :span="12">-->
          <!--            <el-form-item label="用户类型" prop="userType">-->
          <!--              <el-select v-model="form.userType" placeholder="请选择" @change="handleUserTypeChange">-->
          <!--                <el-option-->
          <!--                  v-for="dict in userTypeOptions"-->
          <!--                  :key="dict.dictValue"-->
          <!--                  :label="dict.dictLabel"-->
          <!--                  :value="dict.dictValue"-->
          <!--                ></el-option>-->
          <!--              </el-select>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <el-col :span="12">
            <!-- <supplierPopover :form="form" forItemProp="docCode" v-if="form.userType == '02'" @click="handleSupplierClick"></!-->
            <el-form-item label="用户昵称" prop="nickName">
              <!-- <el-input v-model="form.nickName" placeholder="请输入用户昵称" /> -->
              <el-input v-model="form.nickName" placeholder="请选择用户" maxlength="50" />
              <!-- <el-input v-model="form.empCode" type="hidden"  v-show="false"/> -->
            </el-form-item>
          </el-col>
          <el-col :span="12" >
            <orgSearch label="单位" :queryParams="form.dept" compCode="deptId" compName="deptName" @click="handleClickOrg"></orgSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="deptRoomId">
              <el-select v-model="form.deptRoomId" placeholder="请选择科室" class="w100" filterable @change="handleChangeDeptRoom">
                <el-option
                  v-for="dict in deptList"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item  label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.userId == undefined">
            <el-form-item  label="用户密码" prop="password">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择" class="w100">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body custom-class="el-button--primary">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUserNew,
  updateUser,
  exportUser,
  resetUserPwd,
  changeUserStatus,
  importTemplate
} from "@/api/system/user";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import {
  listDoctor,
  getDoctor,
  delDoctor,
  addDoctor,
  updateDoctor,
  exportDoctor
} from "@/api/system/doctor";
import { LOAD_CHILDREN_OPTIONS } from "@riophae/vue-treeselect";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import orgSearch from "@/views/components/orgSearch";
import {getDeptByComp} from "@/api/system/dict/data";

export default {
  // name: "User",
  components: { TreeSelect, orgSearch,
    // supplierPopover
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (this.form.dept.deptName == null || this.form.dept.deptName == "") {
        callback(new Error("单位不可为空"));
      } else {
        callback();
      }
    };
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 医生选择是否弹出
      doctorOpen: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      doctorList: [],
      // 性别状态字典
      sexOptions: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 用于显示查询的机构名称
      queryShowDeptName: "",
      // 表格上方机构查询dialog
      deptSearchDialog: false,
      // 部门名称
      deptNameDialog: undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
        // hasChildren:"hasChildren"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      tableHeight: "auto",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `用户名称`, visible: true },
        { key: 2, label: `用户昵称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
        userName: [
          {
            required: true,
            message: "用户名称不能为空",
            trigger: ["blur", "change"]
          }
        ],
        nickName: [
          { required: true, message: "用户昵称不能为空", trigger: "blur" }
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, validator: validatePass, trigger: "blur" }
        ],
        deptRoomId: [
          { required: true, message: "科室不能为空", trigger: "blur" }
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      },
      docTotal:0,
      docParams:{
        pageNum:1,
        pageSize:20
      },
      getDeptId:"",
      deptList:[],
      selectionRow:[]
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
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
    // 根据名称筛选部门树
    deptNameDialog(val) {
      this.$refs.treeDialog.filter(val);
    },
    "form.deptId":{
      handler(val){
        // 获取科室数据
        this.getDeptByComp(val)
      },
      immediate:true,
      deep:true
    }
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
    // DEPID
    this.getConfigKey("DEPID").then(response => {
      this.getDeptId = response.msg
    })
  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    handleClickOrg(data){
      this.deptList = [];
      this.form.deptRoomId = null
      this.getDeptByComp(data.deptId)
    },
    // 获取当前登录用户单位下的科室
    getDeptByComp(compCode){
      let data = {
        compCode,
      }
      getDeptByComp(data).then(res => {
        this.deptList = res.data
      })
    },
    // 科室发生改变
    handleChangeDeptRoom(val){
      this.form.deptRoomName = this.selectDictLabel(this.deptList,val);
      console.log(this.form.deptRoomName,"vallll11")
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let h = a + 18 + 45 + 84 + 40 + 10;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.gender);
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        treeselect({ parentId: parentNode.id }).then(res => {
          parentNode.children = res.data.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null;
            }
            return obj;
          });
          setTimeout(() => {
            callback();
          }, 200);
        });
      }
    },
    getDeptDatas(node, resolve) {
      var pId = "";
      if (node.level === 0) {
        pId = "";
      } else {
        pId = node.data.id;
      }
      setTimeout(() => {
        treeselect({ parentId: pId }).then(res => {
          if (resolve) {
            resolve(res.data);
          } else {
            this.deptOptions = res.data;
          }
        });
      }, 100);
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect(parentId) {
      treeselect({ parentId: parentId }).then(response => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // dialog中search节点单击事件
    handleDialogNodeClick(data) {
      this.form.deptId = data.id;
      this.queryShowDeptName = data.label;
      this.deptSearchDialog = false;
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.userName + '"用户吗?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.msgSuccess(text + "成功");
        })
        .catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        userType:"01",
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: [],
        docCode:undefined,
        deptRoomId:null,
        dept: {
          deptName: "",
          deptId: ""
        }
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    rowStyle({
      row,
      rowIndex
    }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
      })
    },
    rowClick(row, flag = true) {
      let refsElTable = this.$refs.userTable;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
        if (findRow && this.selectionRow.length == 1) {
          refsElTable.toggleRowSelection(row, false);
          return;
        }
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let requirementTable = this.$refs.userTable;
      if (selection.length > 1) {
        requirementTable.clearSelection();
        requirementTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        );
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }


      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
      this.selectionRow=selection;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.$refs.userTable.clearSelection()
      this.open = true;
      this.title = "用户 / 新增";
      // this.getTreeselect('0');
      getUser().then(response => {
        this.roleOptions = response.roles;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      // this.getTreeselect('0');
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        console.log(response);
        this.queryShowDeptName = response.data.dept.deptName;
        this.form = response.data;
        // if(this.form.dept){
        //   this.getDeptByComp(this.form.deptId)
        // }
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        this.open = true;
        this.title = "用户 / 修改";
        this.form.password = "";
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.msgSuccess("修改成功，新密码是：" + value);
          });
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.userType == '02'){
            this.form.deptId = this.getDeptId
          }
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUserNew(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$confirm(
        '是否确认删除用户编号为"' + userIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delUser(userIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有用户数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportUser(queryParams);
        })
        .then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        this.download(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 选择医生
    selectDoctor() {
      listDoctor(this.docParams).then(response => {
        console.log(response);
        this.doctorOpen = true;
        this.doctorList = response.rows;
        this.docTotal = response.total;
        this.currentRowIndex,
          this.docSelectData=null
      });
    },
    tableRowClassName({row, rowIndex}){
      row.row_index = rowIndex;
    },
    // 医生设置单选
    handleSelectionDocChange(selection, row){
      this.singleTable(row)
    },
    handleDocRowClick(row){
      this.singleTable(row)
    },
    singleTable(row){
      let tableRef =  this.$refs.itsmDataTable;
      tableRef.clearSelection();
      if(this.currentRowIndex==row.row_index){
        tableRef.toggleRowSelection(row, false);
        this.currentRowIndex = null;
        this.docSelectData = null
      }else{
        tableRef.toggleRowSelection(row)
        this.currentRowIndex = row.row_index;
        this.docSelectData = row;
      }
      console.log(this.docSelectData)
    },
    confirmDoc() {
      const _selectData = this.$refs.itsmDataTable.selection;
      if (_selectData.length == 1) {
        this.form.docCode = _selectData[0].empCode;
        this.form.userName = _selectData[0].empCode;
        this.form.nickName = _selectData[0].empName;
        this.form.sex = _selectData[0].gender;
        this.form.dept.deptName = _selectData[0].compName;
        this.form.deptId = _selectData[0].compCode;
        this.doctorOpen = false;
      }
      // console.log(_selectData)
    },
    handleUserTypeChange(val){
      this.form.deptId = null;
      this.form.dept ={
        deptName: "",
        deptId: ""
      }
      this.form.docCode = null;
      this.form.nickName = null
    },
    handleSupplierClick(data){
      this.form.nickName = data.supplierName
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-dialog.dialogPeople {
  height: 80%!important;
  overflow: hidden;
  .el-dialog__body {
    height: calc(100% - 100px);
    overflow: auto;
    .el-table {
      overflow: auto;
      height: calc(100% - 25px) !important;
    }
  }
}
.head-container-body{
  max-height:calc(100vh - 240px);
  overflow: auto;
  &::-webkit-scrollbar {
    width: 6px;
    height: 6px
  }
  &::-webkit-scrollbar-thumb  {
    background-color: #cccccc;
    border-radius: 8px;
  }
}
</style>
