<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="模板名称" prop="formName">
          <el-input
            class="search_w220"
            v-model="queryParams.formName"
            placeholder="请输入模板名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="模板标题" prop="title">
          <el-input
            class="search_w220"
            v-model="queryParams.title"
            placeholder="请输入模板标题"
            clearable
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
            <!-- v-hasPermi="['formDesign:formDesign:add']"    v-hasPermi="['formDesign:formDesign:edit']" v-hasPermi="['formDesign:formDesign:remove']"  -->
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >添加模板</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
          >编辑模板</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
          >删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table v-loading="loading" :height="tableHeight" :data="formDesignList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" highlight-current-row>
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="模板名称" align="left" prop="formName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.formName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="模板标题" align="left" prop="title" />
      <el-table-column label="表名" align="left" prop="genTableName" />
      <el-table-column label="说明" align="left" prop="remark" width="450" show-overflow-tooltip  />
      <el-table-column label="显示顺序" align="center" prop="sortNo" width="170" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加模板对话框 -->
    <el-dialog :title="title" custom-class="el-button--primary" :visible.sync="open" width="30%" append-to-body @close="cancel" :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" class="edit_form" >
        <el-form-item label="模板名称：" prop="formName">
          <el-input class="w90" v-model="form.formName" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="标题：" prop="title">
          <el-input class="w90" v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="显示顺序：" prop="sortNo">
          <el-input-number class="w90" controls-position="right" :min="1" v-model="form.sortNo" disabled placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="生成汇总数据：" prop="isSum">
          <el-radio-group class="w90" v-model="form.isSum">
            <el-radio :label="0" value="是">是</el-radio>
            <el-radio :label="1" value="否">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="科室跟踪：" prop="isTrack">
          <el-radio-group class="w90" v-model="form.isTrack" @change="changeIsTrack">
            <el-radio :label="0" value="是">是</el-radio>
            <el-radio :label="1" value="否">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="表单模板" prop="formType">
          <el-select v-model="form.formType" clearable filterable remote placeholder="请选择表单模板" class="w90">
            <el-option
              v-for="dict in formTypeOption"
              :key="dict.id"
              :label="dict.formName"
              :value="dict.formName"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="说明：" prop="remark">
          <el-input class="w90" v-model="form.remark" type="textarea" rows="4" placeholder="请输入说明" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(true)">保存并继续添加</el-button>
        <el-button type="primary" @click="submitForm(false)">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改模板 -->
    <editModel v-if="open1" :open="open1" :queryParamsData="queryParams" :formDesignId="addEditId" @cancel="cancel1"></editModel>
  </div>
</template>

<script>
import { listFormDesign, listFormSort, delFormDesign, addFormDesign, updateFormDesign, exportFormDesign, checkFormCodeUnique } from "@/api/formDesign/formDesign";
import editModel from "./editModel";
export default {
  name: "FormDesign",
  components: {
    editModel,
  },
  data() {
    return {
      tableHeight:'auto',
      genTableNames:[],
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      itemId:[],
      formNames:[],
      formTypeOptions:[],
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
        isTrack:null,
        compCode: this.$store.state.user.userInfo.deptId,
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        formName: [
          { required: true, message: "模板名称不能为空", trigger: "blur" }
        ],
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        isSum:[
          { required: true, message: "请选择是否生成汇总数据", trigger: "blur" }
        ],
        isTrack:[
          { required: true, message: "请选择是否科室跟踪", trigger: "blur" }
        ],
        // formType:[
        //   { required: true, message: "请选择表单模板", trigger: "blur" }
        // ],
      },
      selectionRow:[],
      formTypeOption:[]
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
    /** 查询自定义表单列表 */
    getList() {
      this.loading = true;
      listFormDesign(this.queryParams).then(response => {
        this.formTypeOptions = JSON.parse(JSON.stringify(response.rows));
      // console.log(this.formTypeOptions,'this.formTypeOptions')
        
        this.formDesignList = JSON.parse(JSON.stringify(response.rows));
        this.total = response.total;
        this.loading = false;
      });
    },
    listFormSort(){
      listFormSort({compCode: this.$store.state.user.userInfo.deptId}).then(res=>{
        if(res.code===200){
          this.form.sortNo=res.msg
        }
      })
    },
    changeIsTrack(val){
      let arr=[]
      arr=this.formTypeOptions.filter(_=>_.isTrack==val);
      this.formTypeOption=arr;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      // this.reset();
    },
    cancel1(editPageSize) {
      this.open1 = false;
      // this.reset();
      this.getList()
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
        remark:null,
        delFlag: null,
        formCode: null,
        genTableName: null,
        formUrl: null,
        formType: null,
        originalCode:null,
        title:null,
        sortNo:null,
        pageSize:null,
        compCode:this.$store.state.user.userInfo.deptId,
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
      this.selectionRow=selection
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
      this.listFormSort();
      this.open = true;
      this.title = "模板管理-添加模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.addEditId = id;
      this.open1 = true; // 弹框点开
    },
    /** 提交按钮 */
    submitForm(type) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.formTypeOptions.forEach(item=>{
            if(item.formName==this.form.formType){
              this.form.formType=item.formJson;
              this.form.formJson=item.formJson;
              this.form.originalCode=item.id
            }
          })
          const saveLoading=this.saveLoading()
          addFormDesign(this.form).then(response => {
            if(type){
              this.reset();
              this.msgSuccess("保存成功");
              this.getList();
              saveLoading.close();
            }else{
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              saveLoading.close();
            }
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const saveLoading=this.saveLoading(' ')
      const ids = row.id || this.ids[0];
      this.$confirm('是否确认删除自定义表单编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delFormDesign(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
         saveLoading.close()
      }).catch(() => {
        saveLoading.close()
      });
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
<style lang="scss" scoped>
.edit_form{
  ::v-deep{
     .el-input-number--mini{
        width: 100%;
      }
      .el-form-item__label{
        width: 140px !important;
      }
      .el-form-item__content{
        margin-left: 140px !important;
      }
  }
}
</style>
