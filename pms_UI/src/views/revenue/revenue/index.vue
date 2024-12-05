<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="项目编码" prop="revenueCode">
        <el-input
          class="search_w220"
          v-model="queryParams.revenueCode"
          placeholder="请输入项目编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="revenueName">
        <el-input
          class="search_w220"
          v-model="queryParams.revenueName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="核算类型" prop="accountType">
        <el-select class="search_w220" v-model="queryParams.accountType" placeholder="请选择核算类型" clearable>
          <el-option
            v-for="dict in accountTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="成本对照" prop="projectCode">
         <el-select class="search_w220" v-model="queryParams.projectCode" :filterable="true" :remote="true" clearable placeholder="请选择">
                <el-option
                  v-for="dict in projectCodeOptions"
                  :key="dict.projectCode"
                  :label="dict.projectName"
                  :value="dict.projectCode"
                ></el-option>
              </el-select>
      </el-form-item>
      <el-form-item label="收入类别" prop="hisRevenumTypede">
        <el-select class="search_w220" v-model="queryParams.hisRevenumTypede" :filterable="true" :remote="true" clearable placeholder="请选择">
                <el-option
                  v-for="dict in hisRevenumTypedeOptions"
                  :key="dict.revenueCode"
                  :label="dict.revenueName"
                  :value="dict.revenueCode"
                ></el-option>
              </el-select>
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
          v-hasPermi="['revenue:revenue:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['revenue:revenue:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['revenue:revenue:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          :disabled="single"
          size="mini"
          @click="handleDept"
          v-hasPermi="['revenue:revenue:edit']"
        >第三方科室</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table :height="tableHeight" border stripe v-loading="loading" :data="revenueList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="项目编码" width="160px" align="center" prop="revenueCode" />
      <el-table-column sortable label="项目名称" align="left" width="160px" prop="revenueName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.revenueName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="拼音码" align="center" width="160px" prop="spell" />
      <el-table-column sortable label="住院开单(%)" width="140px" align="right" prop="inpBill" />
      <el-table-column sortable label="住院执行(%)" width="140px" align="right" prop="inpExec" />
      <el-table-column sortable label="住院护理(%)" width="140px" align="right" prop="inpCare" />
      <el-table-column sortable label="门诊开单(%)" width="140px" align="right" prop="outpBill" />
      <el-table-column sortable label="门诊执行(%)" width="140px" align="right" prop="outpExec" />
      <el-table-column sortable label="门诊护理(%)" width="140px" align="right" prop="outpCare" />
      <el-table-column sortable label="合作医疗(%)" width="140px" align="right" prop="coopMed" />
      <el-table-column sortable label="核算类型" align="center" width="160px" prop="accountType" :formatter="accountTypeFormat" />
      <el-table-column sortable label="折算比(%)" width="140px" align="right" prop="fixConv" />
      <el-table-column sortable label="成本对照" align="center" width="160px" prop="projectName" />
      <el-table-column sortable label="收入类别" align="center" width="160px" prop="hisRevenumTypedeName" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
	<revenueDept v-if="open1" :open="open1" @cancel="cancel1" :queryParams="form" @success="successSubmit1" ></revenueDept>
  </div>
</template>

<script>
import { listProject } from "@/api/project/project";
import { listIncome } from "@/api/basic/income";
import { listRevenue, getRevenue, delRevenue, addRevenue, updateRevenue, exportRevenue } from "@/api/revenue/revenue";
import addEdit from "./addEdit";
import revenueDept from './revenueDept'

export default {
  name: "Revenue",
  components: {
    addEdit,
    revenueDept
  },
  data() {
    return {
      tableHeight: "auto",
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
      // 收入核算方案表格数据
      revenueList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 核算类型字典
      accountTypeOptions: [],
      hisRevenumTypedeOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode:this.$store.state.user.userInfo.deptId,
        accountCode: null,
        revenueCode: null,
        revenueName: null,
        spell: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        hisRevenumTypede: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      projectCodeOptions:[],//成本项目
      open1:false,//科室设置弹框
      revenueCode:"",//科室设置查询参数

    };
  },
  created() {
    this.getHisType()
    this.getList();
    this.getProjectList()
    this.getDicts("pms_hs_type").then(response => {
      this.accountTypeOptions = response.data;
    });
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
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods: {
    handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h =  a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询收入核算方案列表 */
    getList() {
      this.loading = true;
      listRevenue(this.queryParams).then(response => {
        this.revenueList = response.rows;
        for (let i = 0; i < this.revenueList.length; i++) {
          for (var j in this.revenueList[i]) {
            if ((j=="inpBill" || j=="inpExec" || j=="inpCare" || j=="outpBill" || j=="outpExec" || j=="outpCare" || j=="coopMed" || j=="fixConv")&&j!="id") {
              this.revenueList[i][j] = this.moneyFormat(this.revenueList[i][j])
            }
          }
        }
        this.total = response.total;
        this.loading = false;
      });
    },
     //his收入类别
    getHisType(){
      listIncome(this.queryParams).then(res=>{
        if(res.code==200){
          this.hisRevenumTypedeOptions=res.rows
        }
      })
    },
    /** 查询分摊方案管理列表 */
      getProjectList() {
        let params=Object.assign({
          ...this.queryParams,
          isLast:0,
        })
        listProject(params).then(response => {
          this.projectCodeOptions = response.rows;
        });
      },
    // 核算类型字典翻译
    accountTypeFormat(row, column) {
      return this.selectDictLabel(this.accountTypeOptions, row.accountType);
    },
	  successSubmit(flag) {
      this.open = flag;
      this.getList();
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
        accountCode: null,
        revenueCode: null,
        revenueName: null,
        spell: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        projectName: null,
        hisRevenumTypede: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.accountType = selection.map(item => item.accountType)[0]
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.selectionRow=selection
    },
    rowClick(row, column, event) {
      let refsElTable = this.$refs.multipleTable;
      let findRow = this.selectionRow.find(c => c.rowIndex == row.rowIndex);
      if (findRow && this.selectionRow.length == 1) {
        refsElTable.toggleRowSelection(row, false);
        return;
      }
      refsElTable.clearSelection();
      refsElTable.toggleRowSelection(row);
    },
    rowStyle({
      row,
      rowIndex
    }) {
      Object.defineProperty(row, 'rowIndex', {
        value: rowIndex,
        writable: true,
        enumerable: false
      })
    },
    rowClassName({
      row,
      rowIndex
    }) {
      let rowName = "",
        findRow = this.selectionRow.find(c => c.rowIndex === row.rowIndex);
      if (findRow) {
        rowName = "current-row ";
      }
      return rowName;
    },
    /** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加收入核算方案";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true; // 弹框点开
      this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    cancel1() {
        this.open1 = false;
        this.reset();
    },
     successSubmit1(flag) {
      this.open1 = flag;
      this.getList();
    },
    //科室设置
    handleDept() {
        this.reset();
        this.open1 = true;
        this.title = "收入核算方案-科室设置";
        this.form.revenueCode=this.selectionRow[0].revenueCode
        this.form.revenueName=this.selectionRow[0].revenueName
        this.form.compCode=this.queryParams.compCode
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRevenue(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRevenue(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除收入核算方案编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRevenue(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>
<style lang="scss" scoped>
::v-deep{
  .el-table__body-wrapper.is-scrolling-left{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
    .el-table__body-wrapper.is-scrolling-middle{
      max-height:calc(100vh - 326px) !important;
      height:auto !important;
    }
    .el-table__body-wrapper.is-scrolling-right{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
}
</style>

