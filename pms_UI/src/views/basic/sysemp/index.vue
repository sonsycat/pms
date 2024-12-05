<template>
    <!-- 基础信息 - 人员信息 -->
  <div class="app-container">
    <div ref="search" >
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <orgSearch size="small" label="单位" :queryParams="queryParams"  ></orgSearch>
      <el-form-item label="科室名称" prop="deptName">
        <el-select v-model="queryParams.deptCode" clearable @click.native="getDivision()" size="small">
          <el-option
            v-for="item in deptList"
            :key="item.deptRoomId"
            :label="item.deptRoomName"
            :value="item.deptRoomId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="人员信息" prop="empName">
        <el-input
          v-model="queryParams.empName"
          placeholder="请输入工号或姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    </div>
    <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['basic:sysEmp:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['basic:sysEmp:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['basic:sysEmp:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport"
                     v-hasPermi="['basic:sysEmp:import']">导入
          </el-button>
        </el-col>
<!--        <el-col :span="1.5">-->
<!--          <el-button-->
<!--            type="primary"-->
<!--            icon="el-icon-edit"-->
<!--            size="mini"-->
<!--            :disabled="single"-->
<!--            @click="processExamine"-->
<!--            v-hasPermi="['emp:sysEmp:edit']"-->
<!--          >流转历史</el-button>-->
<!--        </el-col>-->
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table :height="tableHeight" :row-class-name="tableRowClassName"  v-loading="loading" ref="sysEmpListTable" :data="sysEmpList" @selection-change="handleSelectionChange"   highlight-current-row @row-click="handleRowClick"  >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工号" align="center" prop="empCode" sortable/>
      <el-table-column label="姓名" align="left" prop="empName" show-overflow-tooltip sortable :sort-method="(a,b) => a.empName.localeCompare(b.empName)">
        <template slot-scope="scope">
          <el-link type="primary" class="displayBlock text-ellipsis "  v-text="scope.row.empName" @click.stop="handleClickTableName(scope.row)"></el-link>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="left" prop="compName"  show-overflow-tooltip sortable :sort-method="(a,b) => a.compName==null?0:a.compName.localeCompare(b.compName==null?0:b.compName)"/>
      <el-table-column label="科室" align="left" prop="deptName"  show-overflow-tooltip sortable :sort-method="(a,b) => a.deptName==null?0:a.deptName.localeCompare(b.deptName==null?0:b.deptName)"/>
      <el-table-column label="人员分类" align="center" width="140" prop="kindCode" :formatter="kindCodeFormat" sortable :sort-method="(a,b) => a.kindCode==null?0:a.kindCode.localeCompare(b.kindCode==null?0:b.kindCode)"/>
      <el-table-column label="人员类别" align="center" prop="staffsort" width="140" :formatter="staffsortFormat" sortable :sort-method="(a,b) => a.staffsort==null?0:a.staffsort.localeCompare(b.staffsort==null?0:b.staffsort)"/>
      <el-table-column label="性别" align="center" prop="gender" width="120" :formatter="genderFormat" sortable />
      <el-table-column label="人员状态" align="center" prop="isPost" width="120" :formatter="isPostFormat" sortable/>
      <el-table-column label="来院时间" align="center" prop="inhospitalDate" width="110" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inhospitalDate, '{y}-{m}-{d}') }}</span>
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

    <!-- 新增编辑 -->
    <addEdit v-if="open" :open="open" :id="addEditId" :proclnsIds="proclnsIds[0]"  @success="successSubmit" @cancel="cancel"></addEdit>
    <!-- 流转历史对话框 -->
    <el-dialog title="流转历史" :visible.sync="processOpen" width="1200px" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <div v-loading="loading" :style="'height:'+ height">
        <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
      </div>
    </el-dialog>
    <!--单位编码树结构-->
    <el-dialog :visible.sync="deptSearchFormDialog" :close-on-click-modal="false" title="选择单位编码" append-to-body
               width="500px" custom-class="el-button--primary">
      <div style="height: auto">
        <div class="head-container">
          <el-input
            v-model="deptNameForm"
            placeholder="请输入编码名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="treeForm"
            default-expand-all
            @node-click="handleFormNodeClick"
          />
        </div>
      </div>
    </el-dialog>
    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
                 :action="upload.url + '?updateSupport=' + upload.updateSupport"
                 :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess"
                 :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text"><em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          <a href="/templateFile/人员信息模板.xlsx" download>下载模板</a>
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
  listSysEmp,
  getSysEmp,
  delSysEmp,
  addSysEmp,
  updateSysEmp,
  exportSysEmp
} from "@/api/basic/sysEmp";
import { checkInfo2, treeselect, treeRoom } from "@/api/until";
import addEdit from "./addEdit";
import orgSearch from "@/views/components/orgSearch";
import { getToken } from "@/utils/auth";

export default {
  // name: "Empinfo",
  components: {
    addEdit,
    orgSearch
  },
  data() {
    return {
      tableHeight: "auto",
      open: false,
      rules: {},
      addEditId: "",
      // form10: {},
      baseInfo: {},
      //审核
      form22: {},
      shh: false,
      shtitle2: "",
      deptList: [],
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      empDetailFlag: true,
      deptNameForm: undefined,
      deptSearchFormDialog: false,

      empCode: [],
      // // 选中数组
      // ids3: [],
      // ids4: [],
      // ids5: [],
      // ids6: [],
      // ids7: [],
      // 非单个禁用
      single3: true,
      single4: true,
      single5: true,
      single6: true,
      single7: true,
      sh: true,

      // // 查询参数
      // queryParams3: {
      //   pageNum: 1,
      //   pageSize: 10,
      //   empCode: null
      // },
      // ids2: [],

      // 查询参数
      // queryParams2: {
      //   pageNum: 1,
      //   pageSize: 10,
      //   empCode: null
      // },
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      proclnsIds: [],
      src: "",
      height: "500px;",
      // 流转历史对话框
      processOpen: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员基本信息表格数据
      sysEmpList: [],
      // 弹出层标题
      // title: "",
      // 是否显示弹出层
      // open: false,
      // 性别字典
      genderOptions: [],

      // 是否采购字典
      isStockOptions: [],

      // 人员状态字典
      isPostOptions: [],
      // 人员分类字典
      kindCodeOptions: [],

      // 是否停用字典
      isStopOptions: [],
      // 是否同步字典
      isSynOptions: [],

      // 人员类别字典
      staffsortOptions: [],

      // 保险支付字典
      ispaymentOptions: [],

      // 其他分类字典
      personsOtherOptions: [],
      // 导入参数
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
        url: process.env.VUE_APP_BASE_API + "/basic/sysEmp/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: null,
        empName: null,
        deptCode: null,
        deptName: null
      },
      selectionRow: [],
      lzState: "2",
      defaultSort: {}
    };
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    },
    "queryParams.compCode": {
      handler(val) {
        this.getTreeDept(val);
      },
      deep: true,
      immediate: true
    },
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
    // 根据名称筛选部门树
    deptNameForm(val) {
      this.$refs.treeForm.filter(val);
    }
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.genderOptions = response.data;
    });

    this.getDicts("hr_is_change").then(response => {
      this.isStockOptions = response.data;
    });

    this.getDicts("hr_personnel_state").then(response => {
      this.isPostOptions = response.data;
    });
    this.getDicts("hr_personnel_classification").then(response => {
      this.kindCodeOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isStopOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isSynOptions = response.data;
    });

    this.getDicts("hr_personnel_category").then(response => {
      this.staffsortOptions = response.data;
    });

    this.getDicts("hr_insurance_pay").then(response => {
      this.ispaymentOptions = response.data;
    });

    this.getDicts("hr_other_classification").then(response => {
      this.personsOtherOptions = response.data;
    });
  },
  mounted() {
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  methods: {
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    // 离职背景色
    tableRowClassName({ row, rowIndex }) {
      if (row.isPost == this.lzState) {
        return "warning-row";
      } else {
        if (rowIndex % 2 === 1) {
          return "striped";
        } else {
          return "";
        }
      }
    },
    // 点击姓名 弹出编辑
    handleClickTableName(data) {
      this.handleRowClick(data, false);
      this.open = true;
      this.addEditId = data.id;
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + this.$store.state.settings.tableHeight;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    async validID(rule, value, callback) {
      // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
      let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
      if (reg.test(value)) {
        await this.go(value.length);
        callback();
      } else {
        callback(new Error("身份证号码不正确"));
        this.form.age = null;
        this.form.birthday = null;
        this.form.gender = null;
      }
    },
    // 实现自动生成生日，性别，年龄
    go(val) {
      let iden = this.form.idNumber;
      let sex = null;
      let birth = null;
      let myDate = new Date();
      let month = myDate.getMonth() + 1;
      let day = myDate.getDate();
      let age = 0;

      if (val === 18) {
        age = myDate.getFullYear() - iden.substring(6, 10) - 1;
        sex = iden.substring(16, 17);
        birth =
          iden.substring(6, 10) +
          "-" +
          iden.substring(10, 12) +
          "-" +
          iden.substring(12, 14);
        if (
          iden.substring(10, 12) < month ||
          (iden.substring(10, 12) == month && iden.substring(12, 14) <= day)
        )
          age++;
      }
      if (val === 15) {
        age = myDate.getFullYear() - iden.substring(6, 8) - 1901;
        sex = iden.substring(13, 14);
        birth =
          "19" +
          iden.substring(6, 8) +
          "-" +
          iden.substring(8, 10) +
          "-" +
          iden.substring(10, 12);
        if (
          iden.substring(8, 10) < month ||
          (iden.substring(8, 10) == month && iden.substring(10, 12) <= day)
        )
          age++;
      }

      //0：男， 1：女 2：未知
      if (sex % 2 === 0) sex = 1;
      else sex = 0;
      this.form.gender = sex;
      this.form.age = age;
      this.form.birthday = birth;
    },
    //  审核
    submitForm22() {
      let data = {
        id: this.idd[0],
        state: this.form22.state,
        checkDate: this.form22.checkDate
      };

      checkInfo2(data).then(response => {
        if (response.code == 200) {
          this.msgSuccess("审核成功");
          this.shh = false;
          this.resetSh();
          this.getList();
        } else {
          this.msgError(response.msg);
        }
      });
    },
    resetSh() {
      this.form22 = {
        state: null,
        checkDate: null
      };
      this.resetForm("form");
    },
    cancelSh() {
      this.shh = false;
    },
    //取消审核
    confirms(row) {
      this.idd = row.id || this.ids;
      this.shh = true;
      this.shtitle2 = "审核";
    },
    getName(res) {
      this.form.deptName = res.deptRoomName;
    },
    //获取科室列表
    getTreeDept(deptId) {
      let data = { deptId: deptId };
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptList = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // form表单中search节点单击事件
    handleFormNodeClick(data) {
      if (this.open == true) {
        this.form.compCode = data.id;
        this.form.compName = data.label;
      } else {
        this.queryParams.compCode = data.id;
        this.queryParams.compName = data.label;
      }
      this.deptSearchFormDialog = false;
    },
    // search筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },

    /** 查询人员基本信息列表 */
    getList() {
      this.loading = true;
      listSysEmp(this.queryParams).then(response => {
        if (response.code == 200) {
          this.sysEmpList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    // 性别字典翻译
    genderFormat(row, column) {
      return this.selectDictLabel(this.genderOptions, row.gender);
    },

    // 是否采购字典翻译
    isStockFormat(row, column) {
      return this.selectDictLabel(this.isStockOptions, row.isStock);
    },

    // 人员状态字典翻译
    isPostFormat(row, column) {
      return this.selectDictLabel(this.isPostOptions, row.isPost);
    },
    // 人员分类字典翻译
    kindCodeFormat(row, column) {
      return this.selectDictLabel(this.kindCodeOptions, row.kindCode);
    },
    // 是否停用字典翻译
    isStopFormat(row, column) {
      return this.selectDictLabel(this.isStopOptions, row.isStop);
    },
    // 是否同步字典翻译
    isSynFormat(row, column) {
      return this.selectDictLabel(this.isSynOptions, row.isSyn);
    },

    // 人员类别字典翻译
    staffsortFormat(row, column) {
      return this.selectDictLabel(this.staffsortOptions, row.staffsort);
    },

    // 保险支付字典翻译
    ispaymentFormat(row, column) {
      return this.selectDictLabel(this.ispaymentOptions, row.ispayment);
    },

    // 其他分类字典翻译
    personsOtherFormat(row, column) {
      return this.selectDictLabel(this.personsOtherOptions, row.personsOther);
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    successSubmit() {
      this.open = false;
      this.getList();
    },
    // // 表单重置
    // reset() {
    //   this.form = {
    //     id: null,
    //     compCode: null,
    //     empCode: null,
    //     empName: null,
    //     formerName: null,
    //     gender: null,
    //     birthday: null,
    //     idNumber: null,
    //     homeAdd: null,
    //     homePost: null,
    //     photoUrl: null,
    //     homeTown: null,
    //     studySpecsort: null,
    //     bankAccount: null,
    //     postCode: null,
    //     titleCode: null,
    //     addMark: null,
    //     spell: null,
    //     isStock: null,
    //     sysUserId: null,
    //     empDesc: null,
    //     nationName: null,
    //     politicalName: null,
    //     eduName: null,
    //     deptCode: null,
    //     isPost: null,
    //     kindCode: null,
    //     isStop: null,
    //     isSyn: null,
    //     isIntern: null,
    //     isArmy: null,
    //     bonusFactor: null,
    //     deptType: null,
    //     inhospitalDate: null,
    //     job: null,
    //     jobDate: null,
    //     staffsort: null,
    //     beenrolledinDate: null,
    //     workdate: null,
    //     duty: null,
    //     dutydate: null,
    //     techincClass: null,
    //     technicClassDate: null,
    //     civilserviceClass: null,
    //     civilserviceClassDate: null,
    //     santspecSort: null,
    //     rootspecSort: null,
    //     inputUser: null,
    //     inputDate: null,
    //     userDate: null,
    //     deptGroup: null,
    //     homeplace: null,
    //     maritalStatus: null,
    //     edu1: null,
    //     graduateAcademy: null,
    //     rank: null,
    //     markUser: null,
    //     userId: null,
    //     jobTitle: null,
    //     titleDate: null,
    //     expert: null,
    //     fileNo: null,
    //     gl: null,
    //     politicalTime: null,
    //     titleLevel: null,
    //     language: null,
    //     languageLevel: null,
    //     wyzhshshj: null,
    //     ifReservecadres: null,
    //     practice: null,
    //     practiceData: null,
    //     household: null,
    //     householdType: null,
    //     telephone: null,
    //     email: null,
    //     health: null,
    //     overseaschinese: null,
    //     titleGet: null,
    //     titleTime: null,
    //     ifAppoint: null,
    //     appointDate: null,
    //     ifAbroad: null,
    //     dutyLevel: null,
    //     rzhfsh: null,
    //     jobtype: null,
    //     joblevel: null,
    //     joblevelname: null,
    //     bank: null,
    //     bangkCard: null,
    //     jhcyDate: null,
    //     jhAge: null,
    //     jdAge: null,
    //     inhospitalAge: null,
    //     jdhospitalAge: null,
    //     personsType: null,
    //     ispayment: null,
    //     staffHeight: null,
    //     staffWeight: null,
    //     bloodType: null,
    //     jobdate: null,
    //     isbraid: null,
    //     titleKindCode: null,
    //     deptName: null,
    //     postName: null,
    //     age: null,
    //     branchCourts: null,
    //     personsOther: null,
    //     personsOther1: null,
    //     staffid: null,
    //     partyBranch: null,
    //     source: null,
    //     socialNumber: null,
    //     salaryScale: null,
    //     studyOverDate: null,
    //     dateOfGradetitle: null,
    //     dimissionType: null,
    //     workid: null,
    //     delFlag: null,
    //     createBy: null,
    //     createTime: null,
    //     updateBy: null,
    //     updateTime: null,
    //     remark: null
    //   };
    //   this.resetForm("form");
    // },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.compCode = null;
      this.queryParams.compName = null;
      this.queryParams.deptCode = null;
      this.handleQuery();
    },
    handleRowClick(row, flag = true) {
      let sysEmpListTable = this.$refs.sysEmpListTable;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          sysEmpListTable.toggleRowSelection(row, false);
          return;
        }
      }
      sysEmpListTable.clearSelection(); //清空列表的选中
      sysEmpListTable.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let sysEmpListTable = this.$refs.sysEmpListTable;
      if (selection.length > 1) {
        sysEmpListTable.clearSelection(); //清空列表的选中
        sysEmpListTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        ); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      sysEmpListTable.setCurrentRow(selection[0]);

      if (selection.length == 1) {
        selection[0].isChange == 1
          ? (this.single = false)
          : (this.single = false);
      } else {
        this.single = true;
      }

      this.empCode = selection.map(item => item.empCode);
      this.ids = selection.map(item => item.id);
      this.proclnsIds = selection.map(item => item.proclnsId);
      this.multiple = selection.length !== 1;
      this.sh = selection.length !== 1;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.addEditId = "";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true;
      this.addEditId = this.ids[0];
      // getsysEmp(id).then(response => {
      //   console.log(response);
      //   this.form = response.data;
      //   this.title = "修改人员基本信息";
      // });
    },
    /** 流转历史操作 */
    handleExamine() {
      this.src =
        `${this.processUrl}/api/wf/processHistoric?procInsId=${this.proclnsIds[0]}`;
    },
    /** 流转历史按钮操作 */
    processExamine() {
      if (this.proclnsIds[0] == null || !this.proclnsIds[0]) {
        this.msgError("没有流转历史数据！");
      } else {
        this.handleExamine();
        this.processOpen = true;
      }
    },
    /** 提交按钮 */
    // submitForm() {
    //   this.$refs["form"].validate(valid => {
    //     console.log(this.form);
    //     if (valid) {
    //       if (this.form.id != null) {
    //         updateEmpinfo(this.form).then(response => {
    //           this.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         addEmpinfo(this.form).then(response => {
    //           this.msgSuccess("新增成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       }
    //     }
    //   });
    // },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除人员基本信息编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delSysEmp(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // /** 导出按钮操作*/
    // handleExport() {
    //   const queryParams = this.queryParams;
    //   this.$confirm('是否确认导出所有人员基本信息数据项?', "警告", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   }).then(() => {
    //     this.exportLoading = true;
    //     return exportEmpinfo(queryParams);
    //   }).then(response => {
    //     this.download(response.msg);
    //     this.exportLoading = false;
    //   }).catch(() => {
    //   });
    // },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then(response => {
        if (response.code == 200) {
          this.download(response.msg);
        } else {
          this.msgError(response.msg);
        }
      });
    },
    /** 文件上传中处理 */
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    /** 文件上传成功处理 */
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    /** 提交上传文件 */
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
<style scoped>
.common {
  width: 210px;
}
</style>
