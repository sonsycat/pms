<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="年月" prop="rewardMonth">
        <el-date-picker clearable size="small"
                        v-model="queryParams.rewardMonth"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="选择年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="奖惩科室" prop="deptCode">
        <el-select class="search_w220" v-model="queryParams.deptCode" filterable clearable placeholder="请选择奖惩科室" >
          <el-option
            v-for="dict in deptList"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="Number(dict.dictValue)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="奖惩项目" prop="rewardCode">
        <el-select class="search_w220" v-model="queryParams.rewardCode" filterable clearable placeholder="请选择奖惩项目" >
          <el-option
            v-for="dict in rewardOptions"
            :key="dict.rewardCode"
            :label="dict.rewardName"
            :value="dict.rewardCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目金额" prop="amount">
        <el-input
          v-model="queryParams.amountMin"
          placeholder="请输入"
          clearable
          :style="{width:'120px'}"
          @keyup.enter.native="handleQuery"
        />
        至
        <el-input
          v-model="queryParams.amountMax"
          placeholder="请输入"
          clearable
          :style="{width:'120px'}"
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
          v-hasPermi="['subsidy:input:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['subsidy:input:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['subsidy:input:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="inputList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="奖惩时间" align="center" prop="rewardTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rewardTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="奖惩科室" align="left" prop="deptCode" :formatter="deptCodeFormat"  />
      <el-table-column show-overflow-tooltip sortable label="奖惩项目" align="left" prop="rewardCode" :formatter="rewardFormat"/>
      <el-table-column sortable label="项目金额" align="right" prop="amount" >
        <template slot-scope="scope">
          <span v-text="moneyFormat(scope.row.amount)"></span>
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

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

  </div>
</template>

<script>
import { listInput, getInput, delInput, addInput, updateInput, exportInput } from "@/api/subsidy/input";
import { listReward} from "@/api/subsidy/reward";
import addEdit from "./addEdit";

export default {
  name: "Input",
  components: {
addEdit
  },
  watch: {
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.deptList = val.deptInfo;
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
      // 单项奖惩录入表格数据
      inputList: [],
      selectionRow: [],
      //科室字典
      deptList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 项目类型(1单项奖惩2其他奖励)字典
      rewardTypeOptions: [],
      //项目字典
      rewardOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        rewardCode: null,
        deptCode: null,
        amount: null,
        amountMin: null,
        amountMax: null,
        rewardTime: null,
        rewardMonth: null,
        rewardType: "1",
        rewardReason: null,
        isEnable: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rewardCode: [
          { required: true, message: "项目编码不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "项目金额不能为空", trigger: "blur" }
        ],
        rewardTime: [
          { required: true, message: "奖励时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("REWARD_TYPE").then(response => {
      this.rewardTypeOptions = response.data;
    });
    let data = {
      compCode: this.$store.state.user.userInfo.deptId,
      rewardType: "1",
    };
    listReward(data).then(response => {
      this.rewardOptions = response.rows;
    });
  },
  methods: {
    // 科室翻译
    deptCodeFormat(row, column) {
      return this.selectDictLabel(this.deptList, row.deptCode);
    },
    // 科室翻译
    rewardFormat(row, column) {
      var actions = [];
      if(this.rewardOptions && this.rewardOptions.length>0){
        Object.keys(this.rewardOptions).some((key) => {
          if (this.rewardOptions[key].rewardCode == ('' + row.rewardCode)) {
            actions.push(this.rewardOptions[key].rewardName);
            return true;
          }
        })
      }
      return actions.join('');
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询单项奖惩录入列表 */
    getList() {
      this.loading = true;
      listInput(this.queryParams).then(response => {
        this.inputList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 项目类型(1单项奖惩2其他奖励)字典翻译
    rewardTypeFormat(row, column) {
      return this.selectDictLabel(this.rewardTypeOptions, row.rewardType);
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        rewardCode: null,
        deptCode: null,
        amount: null,
        rewardTime: null,
        rewardMonth: null,
        rewardType: "1",
        rewardReason: null,
        isEnable: null,
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
      this.queryParams.amountMax=undefined;
      this.queryParams.amountMin=undefined;
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
            updateInput(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInput(this.form).then(response => {
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
      this.$confirm('是否确认删除单项奖惩录入编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delInput(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
  }
};
</script>
