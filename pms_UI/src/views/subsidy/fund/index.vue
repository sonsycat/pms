<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="时间" prop="fundTime">
        <el-date-picker clearable size="small"
                        v-model="queryParams.fundTime"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="科室名称" prop="deptCode">
        <el-select class="search_w220" v-model="queryParams.deptCode" filterable clearable placeholder="请选择科室名称" >
          <el-option
            v-for="dict in deptIndexList"
            :key="dict.deptRoomId"
            :label="dict.deptRoomName"
            :value="dict.deptRoomId"
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
          v-hasPermi="['subsidy:fund:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['subsidy:fund:edit']"
        >修改</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['subsidy:fund:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-coin"
          size="mini"
          @click="submitBalance"
          v-hasPermi="['subsidy:fund:submit']"
        >提交科基金余额</el-button>
      </el-col>
      <el-col :span="1.5">
          <el-input v-model="form.remark" type="input" :placeholder="placeholderValue()" disabled style="width:280px"/>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="benefitList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column show-overflow-tooltip sortable label="科室" align="center" prop="deptName"/>
      <el-table-column show-overflow-tooltip sortable label="时间" align="center" prop="fundTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fundTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="科基金金额" align="center" prop="fundMoney" >
        <template slot-scope="scope">
          <span v-text="moneyFormat(scope.row.fundMoney)"></span>
        </template>
      </el-table-column>
<!--      <el-table-column show-overflow-tooltip sortable label="科基金余额" align="center" prop="fundBalance" >-->
<!--        <template slot-scope="scope">-->
<!--          <span v-text="moneyFormat(scope.row.fundBalance)"></span>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

  </div>
</template>

<script>
import { listFund, delFund, addFund, updateFund,submitFundBalance } from "@/api/subsidy/fund";
import addEdit from "./addEdit";
import {treeRoom} from '@/api/until';

export default {
  name: "fund",
  components: {
addEdit
  },
  watch: {
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.deptIndexList = val.deptInfo;
      },
      deep: true,
      immediate: true
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  data() {
    return {
      tableHeight: "auto",
      // 遮罩层
      loading: true,
      pvalue: "0.00",
      placeholderValue(){
        return "当前科室科基金余额为："+this.pvalue;
      },
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
      // 科基金表格数据
      benefitList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //科室字典
      deptIndexList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        // compCode: this.$store.state.user.userInfo.deptId,
        deptCode: this.$store.state.user.userInfo.deptRoomId,
        fundTime: new Date(),
        fundBalance: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getDeptList(this.$store.state.user.userInfo.deptId);
    this.getList();

  },
  methods: {
    getDeptList(deptId){
      let data={deptId:deptId}
      treeRoom(data).then(response=>{
        if(response.code==200){
          this.deptIndexList=JSON.parse(JSON.stringify(response.rows))
        }
      })
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询科基金列表 */
    getList() {
      this.loading = true;
      if(null == this.queryParams.deptCode || null == this.queryParams.fundTime){
        this.msgError("请选择时间和科室！");
      }else{
        listFund(this.queryParams).then(response => {
          this.benefitList = response.rows;
          if(response.total > 0){
            this.pvalue = response.rows[0].fundBalance;
          }else{
            this.pvalue = "0.00";
          }
          this.total = response.total;
          this.loading = false;
        });
      }

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
        deptName: null,
        fundTime: null,
        fundMoney: null,
        fundBalance: null,
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
      this.resetForm("queryForm");
      this.queryParams.deptCode=this.$store.state.user.userInfo.deptRoomId;
      this.queryParams.fundTime= new Date(),
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
            updateFund(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFund(this.form).then(response => {
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
      this.$confirm('是否确认删除当前科基金数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFund(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    submitBalance(row) {
      let quarm = this.queryParams;
      quarm.submitDate = this.queryParams.fundTime;
      let balanceAmount = 0;
      this.benefitList.forEach((item,index) =>{
        balanceAmount = balanceAmount + item.fundMoney;
      });
      if(0 > balanceAmount && ((0 - balanceAmount) > this.pvalue)){
          this.msgError("当前科室余额不足分配！");

      }else {
        this.$confirm("当前科室科基金余额为："+this.pvalue+',提交的余额为：'+balanceAmount+',是否确认提交当前科基金数据?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          quarm.fundBalance = balanceAmount;
          quarm.fundTime = new Date();
          return submitFundBalance(quarm);
        }).then(() => {
          this.getList();
          this.msgSuccess("提交成功");
        }).catch(() => {});
      }

    },
  }
};
</script>
