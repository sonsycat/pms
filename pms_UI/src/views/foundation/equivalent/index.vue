<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="75px">
      <el-form-item label="科室名称" prop="equivalentDeptCode">
        <el-select class="search_w220" v-model="queryParams.equivalentDeptCode" filterable clearable placeholder="请选择科室名称" >
          <el-option
            v-for="dict in equivalentDeptList"
            :key="dict.equivalentDeptCode"
            :label="dict.equivalentDeptName"
            :value="dict.equivalentDeptCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目名称" prop="equivalentItemCode">
        <el-select class="search_w220" v-model="queryParams.equivalentItemCode" filterable clearable placeholder="请选择项目名称"
                   @clear="handleClear"
                   remote
                   :remote-method="getEquivalentItem"
                   :loading="itemLoading"  >
          <el-option
            v-for="item in equivalentItemList"
            :key="item.equivalentItemCode"
            :label="item.equivalentItemName"
            :value="item.equivalentItemCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="对应科室" prop="matchDeptName">
        <el-select class="search_w220" v-model="queryParams.matchDeptName" filterable clearable placeholder="请选择对应科室名称" >
          <el-option
            v-for="dict in matchDeptList"
            :key="dict.matchDeptName"
            :label="dict.matchDeptName"
            :value="dict.matchDeptName"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['foundation:equivalent:edit']"
        >修改</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="equivalentList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="科室编码" align="center" prop="equivalentDeptCode"  />
      <el-table-column sortable label="科室名称" align="center" prop="equivalentDeptName" />
      <el-table-column sortable label="项目编码" align="center" prop="equivalentItemCode" />
      <el-table-column sortable label="项目名称" align="center" prop="equivalentItemName" />
      <el-table-column sortable label="类别" align="center" prop="equivalentTypeName" />
      <el-table-column sortable label="规格" align="center" prop="equivalentUnit" />
      <el-table-column sortable label="价格" align="center" prop="equivalentPrice" />
      <el-table-column sortable label="医嘱标准当量" align="center" prop="doctorAdviceNum" width="140"/>
      <el-table-column sortable label="医技标准当量" align="center" prop="doctorTechNum" width="140"/>
      <el-table-column sortable label="护理标准当量" align="center" prop="doctorNurseNum" width="140"/>
      <el-table-column sortable label="分组名称" align="center" prop="groupName" />

    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" :parentCode="parentCode" @success="successSubmit"></addEdit>

  </div>
</template>

<script>
import {listEquivalent, getEquivalentDept, getEquivalentItem, getMatchDept} from "@/api/foundation/equivalent";
import addEdit from "./addEdit";

export default {
  name: "equivalent",
  components: {
addEdit
  },
  data() {
    return {
      tableHeight: "auto",
      addState:true,
      stopBtn: false,
      startBtn: false,

      // 遮罩层
      loading: true,
      itemLoading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      equivalentDeptList: [],
      equivalentItemList: [],
      equivalentList: [],
      matchDeptList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        equivalentDeptCode: null,
        equivalentItemCode: null,
        matchDeptName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  watch:{
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    },
  },
  created() {
    this.getEquivalentDept();
    this.getMatchDept();
    // this.getEquivalentItem();
    this.getList();

  },
  mounted(){
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {

    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 182 ;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    handleClear() {
      this.equivalentItemList = null;
    },
    getMatchDept(){
      getMatchDept().then(response => {
        this.matchDeptList = response.data;
      });
    },
    getEquivalentDept(){
      getEquivalentDept().then(response => {
        this.equivalentDeptList = response.data;
      });
    },
    getEquivalentItem(key){
      if('' !== key){
        this.itemLoading = true;
        setTimeout(() => {
          getEquivalentItem( key).then(response => {
            this.itemLoading = false;
            this.equivalentItemList = response.data;
          });
        }, 200);
      }else{
        this.equivalentItemList = null;
      }

    },
    /** 查询指标集分类列表 */
    getList() {
      this.loading = true;
      listEquivalent(this.queryParams).then(response => {
        this.equivalentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        equivalentDeptCode: null,
        equivalentItemCode: null,
        matchDeptName: null,
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
      let refsElTable = this.$refs.multipleTable;
      if (selection.length > 1) {
        refsElTable.clearSelection();
        refsElTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        );
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      this.ids = selection.map(item => item.id)
      this.bscCodes = selection.map(item => item.bscCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.selectionRow=selection

      if(selection && selection.length == 1){
        this.parentCode = selection[0].bscCode
        // 非末级不能新增
        let isLast = selection[0].isLast
        if(isLast == 0){
          this.addState = false
        }else{
          this.addState = true
        }
        let isStop = selection[0].isStop
        if(isStop == 1){
          // 点停用
          this.stopBtn = false
          this.startBtn = true
        }else{
          this.stopBtn = true
          this.startBtn = false
        }
      }else{
        this.parentCode = null
      }

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
      if (row.isStop == "0") {
        return "warning-row";
      } else {
        if (rowIndex % 2 === 1) {
          return "striped";
        } else {
          return "";
        }
      }
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.open = true;
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    }
  }
};
</script>
