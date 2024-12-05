<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="收入项目" prop="revenueCode">
            <el-select v-model="queryParams.revenueCode" filterable clearable placeholder="请选择收入项目">
              <el-option
                v-for="dict in projectList"
                :key="dict.revenueCode"
                :label="dict.revenueName"
                :value="dict.revenueCode"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="收入类别" prop="revenueType">
            <el-select v-model="queryParams.revenueType" placeholder="请选择收入类别" clearable>
              <el-option
                v-for="dict in revenueTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="结算标识" prop="settleMark">
            <el-select v-model="queryParams.settleMark" placeholder="请选择结算标识" clearable>
              <el-option
                v-for="dict in settleMarkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="开单科室" prop="billDept">
            <el-select v-model="queryParams.billDept" filterable placeholder="请选择开单科室" clearable>
              <el-option
                v-for="dict in billDeptList"
                :key="dict.deptRoomId"
                :label="dict.deptRoomName"
                :value="dict.deptRoomId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="开单医生" prop="billDoc">
              <el-input
                v-model="queryParams.billDoc"
                placeholder="请输入开单医生"
                clearable
              />
          </el-form-item>
          <el-form-item label="发生时间">
            <el-date-picker
              v-model="daterangeOccureTime"
              :style="{width: '300px'}"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="执行科室" prop="execDept">
            <el-select v-model="queryParams.execDept" placeholder="请选择执行科室" filterable clearable>
                <el-option
                  v-for="dict in execDeptList"
                  :key="dict.deptRoomId"
                  :label="dict.deptRoomName"
                  :value="dict.deptRoomId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="护理单元" prop="careDept">
              <el-select v-model="queryParams.careDept" placeholder="请选择护理单元" filterable clearable>
                <el-option
                  v-for="dict in careDeptList"
                  :key="dict.deptRoomId"
                  :label="dict.deptRoomName"
                  :value="dict.deptRoomId"
                />
              </el-select>
            </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="queryParams.remark"
              placeholder="请输入备注"
              clearable
            
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
            <el-form-item label="应收金额" prop="receiveNum">
                <el-input
                  v-model="queryParams.receiveNumB"
                  placeholder="请输入"
                  :style="{width:'120px'}"
                />
                至
                <el-input
                  v-model="queryParams.receiveNumE"
                  placeholder="请输入"
                  clearable
                  :style="{width:'120px'}"
                />
            </el-form-item>
            <el-form-item label="实际金额" prop="actualNum">
              <el-input
                v-model="queryParams.actualNumB"
                placeholder="请输入"
                clearable
                :style="{width:'120px'}"
              />
              至
              <el-input
                v-model="queryParams.actualNumE"
                placeholder="请输入"
                clearable
                :style="{width:'120px'}"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-col>
      </el-row>    
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
          v-hasPermi="['revenue:revenuenum:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['revenue:revenuenum:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['revenue:revenuenum:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
          :loading="exportLoading"
          @click="handleExtract"
          v-hasPermi="['revenue:revenue:export']"
        >收入提取</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table :height="tableHeight" border stripe v-loading="loading" border stripe :data="revenuenumList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="收入项目" align="left" prop="revenueName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.revenueName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="应收金额" align="right" prop="receiveNum" />
      <el-table-column sortable label="实际金额" align="right" prop="actualNum" />
      <el-table-column sortable label="开单科室" align="center" prop="billDeptName" />
      <el-table-column sortable label="执行科室" align="center" prop="execDeptName" />
      <el-table-column sortable label="护理单元" align="center" prop="careDeptName" />
      <el-table-column sortable label="开单医生" align="left" prop="billDoc" />
      <el-table-column sortable label="收入类别" align="center" prop="revenueType" :formatter="revenueTypeFormat" />
      <el-table-column sortable label="结算标识" align="center" prop="settleMark" :formatter="settleMarkFormat" />
      <el-table-column sortable label="发生时间" align="center" prop="occureTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.occureTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="备注" align="left" prop="remark" />
    
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" :queryParams="queryParams" @success="successSubmit"></addEdit>
  <extractForm v-if="openExtract" :open="openExtract" :isInCome="false" :form="form"  @cancel="cancel1" @success="successSubmit1"></extractForm>

  </div>
</template>

<script>
import { listRevenuenum, getRevenuenum, delRevenuenum, addRevenuenum, updateRevenuenum, exportRevenuenum } from "@/api/revenue/revenuenum";
import {listRevenue} from "@/api/revenue/revenue";
import {treeRoom} from '@/api/until';
import addEdit from "./addEdit";
import extractForm from "@/views/components/extractForm";

export default {
  name: "Revenuenum",
  components: {
    extractForm,
    addEdit
  },
  data() {
    return {
      tableHeight: "auto",
      openExtract:false,
      // 遮罩层
      loading: false,
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
      // 收入录入表格数据
      revenuenumList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 发生时间时间范围
      daterangeOccureTime: [],
      // 收入类别(1门诊，2住院)字典
      revenueTypeOptions: [],
      // 结算标识(1收付实现，2责权发生)字典
      settleMarkOptions: [],
      projectList:[],//收入项目列表
      billDeptList:[],//开单科室
      execDeptList:[],//执行科室
      careDeptList:[],//护理单元
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        revenueCode: null,
        receiveNum: null,
        actualNum: null,
        billDept: null,
        execDept: null,
        careDept: null,
        billDoc: null,
        occureTime: null,
        revenueType: null,
        settleMark: null,
        revenueKind: null,
        remark:null,
        receiveNumB:null,
        receiveNumE:null,
        actualNumB:null,
        actualNumE:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("pms_srlb_type").then(response => {
      this.revenueTypeOptions = response.data;
    });
    this.getDicts("pms_jsbs_type").then(response => {
      this.settleMarkOptions = response.data;
    });
    this.getRevenueList();
    this.getDeptList(this.$store.state.user.userInfo.deptId);
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
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    successSubmit1(flag) {
      this.openExtract=flag;
      this.getList();
      this.resetQuery()
    },

    // 取消按钮
    cancel1() {
      this.openExtract=false;
      this.reset();
      this.resetQuery()
    },

    /** 查询收入录入列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOccureTime && '' != this.daterangeOccureTime) {
        this.queryParams.params["beginOccureTime"] = this.daterangeOccureTime[0];
        this.queryParams.params["endOccureTime"] = this.daterangeOccureTime[1];
      }
      listRevenuenum(this.queryParams).then(response => {
        this.revenuenumList = response.rows;
        this.total = response.total;
        this.loading = false;
        for (let i = 0; i < this.revenuenumList.length; i++) {
            for (var j in this.revenuenumList[i]) {
              if ((typeof this.revenuenumList[i][j]) == "number" && this.revenuenumList[i][j] != 0 && j!="id") {
                this.revenuenumList[i][j] = this.moneyFormat(this.revenuenumList[i][j])
              }
            }
          }
      });
    },
    //获取开单科室
    getDeptList(deptId){
      let data={deptId:deptId}
      treeRoom(data).then(response=>{
        if(response.code==200){
          this.billDeptList=JSON.parse(JSON.stringify(response.rows))
          this.execDeptList=JSON.parse(JSON.stringify(response.rows))
          this.careDeptList=JSON.parse(JSON.stringify(response.rows))
        }
      })
    },
    //获取收入核算收入项目列表
    getRevenueList(){
      listRevenue(this.queryParams).then(response=>{
        this.projectList=response.rows
      })
    },
    // 收入类别(1门诊，2住院)字典翻译
    revenueTypeFormat(row, column) {
      return this.selectDictLabel(this.revenueTypeOptions, row.revenueType);
    },
    // 结算标识(1收付实现，2责权发生)字典翻译
    settleMarkFormat(row, column) {
      return this.selectDictLabel(this.settleMarkOptions, row.settleMark);
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
    // 提取按钮操作
    handleExtract(){
      this.openExtract=true
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        revenueCode: null,
        receiveNum: null,
        actualNum: null,
        billDept: null,
        execDept: null,
        careDept: null,
        billDoc: null,
        occureTime: null,
        revenueType: null,
        settleMark: null,
        revenueKind: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
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
      this.daterangeOccureTime = [];
      this.queryParams.receiveNumB=undefined;
      this.queryParams.receiveNumE=undefined;
      this.queryParams.actualNumB=undefined;
      this.queryParams.actualNumE=undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
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
      this.title = "添加收入录入";
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRevenuenum(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRevenuenum(this.form).then(response => {
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
      this.$confirm('是否确认删除收入录入编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRevenuenum(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有收入录入数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportRevenuenum(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-input--mini{
  width:300px
}
::v-deep .el-pagination__editor.el-input{
  width: 50px !important;
}
</style>
