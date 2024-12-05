<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="75px">
      <el-form-item label="类别名称" prop="bscName">
        <el-input
          class="search_w220"
          v-model="queryParams.bscName"
          placeholder="请输入BSC类别名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否停用" prop="isStop">
        <el-select class="search_w220" v-model="queryParams.isStop" placeholder="请选择是否停用" clearable>
          <el-option
            v-for="dict in isStopOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否末级" prop="isLast">
        <el-select class="search_w220" v-model="queryParams.isLast" placeholder="请选择是否末级" clearable>
          <el-option
            v-for="dict in isLastOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['evaluate:bsc:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single "
          @click="handleUpdate"
          v-hasPermi="['evaluate:bsc:edit']"
        >修改</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-remove-outline"
          size="mini"
          :disabled="single||stopBtn"
          @click="handleStop"
          v-hasPermi="['evaluate:bsc:edit']"
        >停用</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-circle-check"
          size="mini"
          :disabled="single||startBtn"
          @click="handleStart"
          v-hasPermi="['evaluate:bsc:edit']"
        >启用</el-button>
      </el-col>


      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['evaluate:bsc:remove']"
        >删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>


    <el-table
      v-loading="loading"
      ref="multipleTable"
      row-key="id"
      :data="bscList"
      :height="tableHeight"
      :row-class-name="rowClassName"
      :row-style="rowStyle"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      default-expand-all
      @selection-change="handleSelectionChange"
      @row-click="rowClick"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="BSC类别名称" align="left" prop="bscName" show-overflow-tooltip>
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.bscName" @click.stop="handleClickTableName(scope.row)" class="text-ellipsis"></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="BSC类别编码" align="left" prop="bscCode" />
      <el-table-column sortable label="是否末级" align="center" prop="isLast"  width="100" :formatter="isLastFormat" />
      <el-table-column sortable label="是否停用" align="center" prop="isStop" width="100" :formatter="isStopFormat" />
    </el-table>

<!--    <pagination-->
<!--      v-show="total>0"-->
<!--      :total="total"-->
<!--      :page.sync="queryParams.pageNum"-->
<!--      :limit.sync="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->

	<addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" :parentCode="parentCode" @success="successSubmit"></addEdit>

  </div>
</template>

<script>
import { listBsc, getBsc, delBsc, addBsc, updateBsc, exportBsc,updateIsStop } from "@/api/evaluate/bsc";
import addEdit from "./addEdit";

export default {
  name: "Bsc",
  components: {
addEdit
  },
  data() {
    return {
      tableHeight: "auto",
      addState:true,
      stopBtn: false,
      startBtn: false,


      //==========================================
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
      // BSC分类表格数据
      bscList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否停用字典
      isStopOptions: [],
      // 是否末级字典
      isLastOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        bscCode: null,
        bscName: null,
        parentCode: null,
        ancestors: null,
        isStop: null,
        isLast: null,
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
    this.getList();
    this.getDicts("hr_is_change").then(response => {
      this.isStopOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isLastOptions = response.data;
    });
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
    /** 查询BSC分类列表 */
    getList() {
      this.loading = true;
      this.bscList = [];
      listBsc(this.queryParams).then(response => {
        this.loading = false;
        this.bscList = this.handleTree(
          response.rows,
          "bscCode",
          "parentCode"
        );
      }).catch(err=>{
        this.loading = false;
      });
    },
    // 是否停用字典翻译
    isStopFormat(row, column) {
      return this.selectDictLabel(this.isStopOptions, row.isStop);
    },
    // 是否末级字典翻译
    isLastFormat(row, column) {
      return this.selectDictLabel(this.isLastOptions, row.isLast);
    },
    //超链接
    handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
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
        bscCode: null,
        bscName: null,
        parentCode: null,
        ancestors: null,
        isStop: null,
        isLast: null,
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
      // if (findRow && this.selectionRow.length == 1) {
      //   refsElTable.toggleRowSelection(row, false);
      //   return;
      // }
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
    rowClassName({ row,rowIndex}) {
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
    /** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加BSC分类";
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
            updateBsc(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBsc(this.form).then(response => {
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
      const ids = row.id || this.ids;      const bscCode = row.bscCode || this.bscCodes;
      this.$confirm('是否确认删除BSC分类编号为"' + bscCode + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBsc(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },

    /** 停用按钮操作 */
    handleStop(row) {
      const ids = row.id || this.ids;
      const bscCode = row.bscCode || this.bscCodes;
      this.$confirm(
        '是否确认停用BSC分类编号为"' + bscCode + '"的数据项?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.form.id = ids[0];
          this.form.isStop = "0";
          updateIsStop(this.form).then(response => {
            if (response.code == 200) {
              this.msgSuccess("停用成功");
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        })
        .catch(() => {});
    },
    /** 启用按钮操作 */
    handleStart(row) {
      const ids = row.id || this.ids;
      const bscCode = row.bscCode || this.bscCodes;
      this.$confirm(
        '是否确认启用BSC分类编号为"' + bscCode + '"的数据项?',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.form.id = ids[0];
          this.form.isStop = "1";
          updateIsStop(this.form).then(response => {
            if (response.code == 200) {
              this.msgSuccess("启用成功");
              this.getList();
            } else {
              this.msgError(response.msg);
            }
          });
        })
        .catch(() => {});
    },


    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有BSC分类数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportBsc(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
