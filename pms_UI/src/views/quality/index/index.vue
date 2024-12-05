<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="指标名称" prop="indexName">
        <el-input
          class="search_w220"
          v-model="queryParams.indexName"
          placeholder="请输入指标名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主管部门" prop="deptCode">
        <el-select class="search_w220" v-model="queryParams.deptCode" placeholder="请选择主管部门" clearable filterable>
          <el-option
            v-for="dict in deptList"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          v-hasPermi="['quality:index:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['quality:index:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['quality:index:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          :disabled="single"
		      :loading="exportLoading"
          @click="handleDetail"
          v-hasPermi="['quality:index:edit']"
        >编辑指标内容</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>
	
    <el-table border stripe v-loading="loading" :height="tableHeight" :data="indexList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="指标名称" align="left" prop="indexName" >
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.indexName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="指标分值" align="center" prop="indexScore" />
      <el-table-column sortable label="主管部门" align="center" prop="deptName" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
	<detail v-if="detailOpen" :open="detailOpen" :id="addEditId" :datas="selectionRow" @cancel="cancelDetail" @success="successSubmitDetail"></detail>
	
  </div>
</template>

<script>
import { listIndex, getIndex, delIndex, addIndex, updateIndex, exportIndex } from "@/api/quality/index";
import {getDeptByComp} from "@/api/system/dict/data";
import addEdit from "./addEdit";
import detail from "../detail";

export default {
  name: "Index",
  components: {
    addEdit,
    detail
  },
  data() {
    return {
      tableHeight:'auto',
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      detailIndexCode:null,
      deptList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 质量考评指标表格数据
      indexList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      detailOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        deptCode: null,
        indexName: null,
        compCode:this.$store.state.user.userInfo.deptId,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      indexCode:null,
    };
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  created() {
    this.getList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  watch:{
    "queryParams.compCode":{
      handler(val){
        // 获取科室数据
        this.getDeptByComp(val)
      },
      immediate:true,
      deep:true
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods: {
    handleClickTableName(data){
      this.rowClick(data);
      this.handleDetail(data);
    },
    resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询质量考评指标列表 */
    getList() {
      this.loading = true;
      listIndex(this.queryParams).then(response => {
        if(response.code===200){
          this.indexList = response.rows;
          this.total = response.total;
          for (let i = 0; i < this.indexList.length; i++) {
              for (var j in this.indexList[i]) {
                if (j == "indexScore" && this.indexList[i][j] != 0 && j!="id" && j!="deptCode") {
                  this.indexList[i][j] = this.moneyFormat(this.indexList[i][j])
                }
              }
          }
          this.loading = false;
        }
        
      });
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
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.getList();
      this.reset();
    },
    successSubmitDetail(flag) {
      this.detailOpen = flag;
      this.getList();
    },
    // 取消按钮
    cancelDetail() {
      this.detailOpen = false;
      this.getList();
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        deptCode: null,
        indexCode: null,
        indexName: null,
        indexScore: null,
        tempCode: null,
        dateCol: null,
        deptCol: null,
        numCol: null,
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
      this.title = "添加质量考评指标";
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
    // 编辑指标内容
    handleDetail(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.detailOpen = true;
      this.detailIndexCode = this.selectionRow[0].indexCode
      this.addEditId = id; // id赋值
      console.log("addEditId---" + addEdit);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateIndex(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIndex(this.form).then(response => {
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
      this.$confirm('是否确认删除质量考评指标编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delIndex(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有质量考评指标数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportIndex(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  },
};
</script>
