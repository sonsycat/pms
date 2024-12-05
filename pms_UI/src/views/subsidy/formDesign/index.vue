<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="表单编码" prop="formCode">
          <el-input
            v-model="queryParams.formCode"
            placeholder="请输入表单编码"
            clearable
            class="search_w220"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="表单名称" prop="formName">
          <el-input
            v-model="queryParams.formName"
            placeholder="请输入表单名称"
            clearable
            class="search_w220"
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
      <!-- v-hasPermi="['formDesign:formDesign:add']" -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增</el-button>
        </el-col>
        <!-- v-hasPermi="['formDesign:formDesign:edit']" -->
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
          >修改</el-button>
        </el-col>
        <!-- v-hasPermi="['formDesign:formDesign:remove']" -->
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            :disabled="multiple"
            @click="formDesign"
          >设计表单</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="multiple"
            @click="deploy"
          >表单配置</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="see"
          >功能测试</el-button>
        </el-col>
  <!--      <el-col :span="1.5">-->
  <!--        <el-button-->
  <!--          type="warning"-->
  <!--          plain-->
  <!--          icon="el-icon-download"-->
  <!--          size="mini"-->
  <!--          :loading="exportLoading"-->
  <!--          @click="formData"-->
  <!--          v-hasPermi="['formDesign:formDesign:edit']"-->
  <!--        >表单数据</el-button>-->
  <!--      </el-col>-->

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table v-loading="loading" :height="tableHeight" :data="formDesignList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" highlight-current-row>
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="表单编码" align="center" prop="formCode">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.formCode" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="表单名称" align="left" prop="formName" />
      <el-table-column label="表名" align="center" prop="genTableName" />
      <el-table-column label="菜单URL" align="left" prop="formUrl" width="450" />
      <el-table-column label="修改时间" align="center" prop="updateDate" width="170">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="80">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['formDesign:formDesign:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['formDesign:formDesign:remove']" title="删除"
          ></el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改自定义表单对话框 -->
    <el-dialog :title="title" custom-class="el-button--primary" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表单编码" prop="formCode">
          <el-input v-model="form.formCode" placeholder="系统自动生成" disabled/>
        </el-form-item>
        <el-form-item label="表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入表单名称" />
        </el-form-item>
        <!-- <el-form-item label="表单模板" prop="formType">
          <el-select v-model="form.formType" placeholder="请选择类型" class="w100">
            <el-option
              v-for="dict in formTypeOptions"
              :key="dict.formJson"
              :label="dict.formName"
              :value="dict.formJson"
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <addEdit v-if="open1" :open="open1" :formDesignId="addEditId" @cancel="cancel1"></addEdit>

  </div>
</template>

<script>
import { listFormDesign, getFormDesign, delFormDesign, addFormDesign, updateFormDesign, exportFormDesign, checkFormCodeUnique } from "@/api/subsidy/formDesign";
import addEdit from "./addEdit";
export default {
  name: "FormDesign",
  components: {
    addEdit
  },
  data() {
    return {
      tableHeight:'auto',
      genTableNames:[],
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      formNames:[],
      // formTypeOptions:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 自定义表单表格数据
      formDesignList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      addEditId: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        formName: null,
        formCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        formName: [
          { required: true, message: "表单名称不能为空", trigger: "blur" }
        ],
      },
      selectionRow:[],
    };
  },
  created() {
    this.getList();
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
    handleClickTableCode(data) {
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
    /** 查询自定义表单列表 */
    getList() {
      this.loading = true;
      listFormDesign(this.queryParams).then(response => {
        this.formDesignList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancel1() {
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        formName: null,
        formJson: null,
        createBy: null,
        createDate: null,
        updateBy: null,
        updateDate: null,
        remarks: null,
        delFlag: null,
        formCode: null,
        genTableName: null,
        formUrl: null,
        formType: null
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
      this.formNames = selection.map(item => item.formName)
      this.genTableNames = selection.map(item => item.genTableName)
      this.single = selection.length!==1
      this.multiple = selection.length!==1
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
      this.reset();
      this.open = true;
      this.title = "添加自定义表单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFormDesign(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改自定义表单";
      });
    },
    /** 打开表单设计页面 */
    formDesign(row) {
      this.reset();
      const id = row.id || this.ids;
      this.$router.push({
        path: '/subsidy/formMaking',
        query: {
          formDesignId: id[0]
        }
      });
    },
    see(row){
      const tableName = this.genTableNames;
      console.log(tableName);
      if(tableName[0] == null || tableName[0] == undefined || tableName[0] == ''|| tableName[0] == []) {
        this.msgInfo("请先设计表单");
        return;
      }
    	const id = row.id || this.ids;
    	const formName = row.formName || this.formNames
      this.$router.push({
        path: '/subsidy/formProcess',
        query: {
          formDesignId: id[0],
          formName:formName[0]
        }
      });
    },
    deploy(row){
      const tableName = this.genTableNames;
      console.log(tableName);
      if(tableName[0] == null || tableName[0] == undefined || tableName[0] == ''|| tableName[0] == []) {
        this.msgInfo("请先设计表单");
        return;
      }
    	const id = row.id || this.ids[0];
      this.open1 = true; // 弹框点开
      this.addEditId = id; // addEditId 是新增  去子组件方便区分新增编辑
      /*this.$router.push({
        path: '/tool/formDeploy',
        query: {
          formDesignId: id[0]
        }
      });*/
    },
    /** 打开表单数据页面 */
    formData(row) {
      this.reset();
      const id = row.id || this.ids;
      this.$router.push({
        path: '/subsidy/formDesignData',
        query: {
          formDesignId: id
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFormDesign(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 验证编码唯一性
            checkFormCodeUnique(this.form.formCode).then(response => {
              if(response.data == true) {
                this.msgError("表单编码已存在");
                return;
              } else {
                addFormDesign(this.form).then(response => {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                });
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除自定义表单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delFormDesign(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有自定义表单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportFormDesign(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
