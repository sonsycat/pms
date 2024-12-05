<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="项目编码" prop="projectCode">
          <el-input
            class="search_w220"
            v-model="queryParams.projectCode"
            placeholder="请输入项目编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="项目名称" prop="projectName">
          <el-input
            class="search_w220"
            v-model="queryParams.projectName"
            placeholder="请输入项目名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="项目级别" prop="projectLevel">
          <el-select class="search_w220" v-model="queryParams.projectLevel" placeholder="请选择项目级别" clearable >
            <el-option
              v-for="dict in projectLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="项目类型" prop="projectType">
          <el-select class="search_w220" v-model="queryParams.projectType" placeholder="请选择项目类型" clearable >
            <el-option
              v-for="dict in projectTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="成本属性" prop="costProperty">
          <el-select class="search_w220" v-model="queryParams.costProperty" placeholder="请选择成本属性" clearable >
            <el-option
              v-for="dict in costPropertyOptions"
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
            v-hasPermi="['project:project:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['project:project:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['project:project:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="single"
            @click="handleDept"
            v-hasPermi="['project:project:add']"
          >科室设置</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-upload2"
            size="mini"
            :loading="exportLoading"
            @click="handleExport"
            v-hasPermi="['project:project:export']"
          >导出模板</el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
          >导入Excel</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border stripe :height="tableHeight" v-loading="loading" :data="projectList"
              @selection-change="handleSelectionChange"
              ref="multipleTable"
              @row-click="rowClick"
              :row-style="rowStyle"
              row-key="projectCode"
              default-expand-all
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
              :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="项目编码" width="160" align="center" prop="projectCode" />
      <el-table-column sortable label="项目名称" width="160" align="left" prop="projectName">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.projectName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="项目级别" width="100" align="center" prop="projectLevel" :formatter="projectLevelFormat" />
      <el-table-column sortable label="项目类型" width="100" align="center" prop="projectType" :formatter="projectTypeFormat" />
      <el-table-column sortable label="成本属性" width="100" align="center" prop="costProperty" :formatter="costPropertyFormat" />
      <el-table-column sortable label="是否可控" width="100" align="center" prop="isControl" :formatter="isControlFormat" />
      <el-table-column sortable label="是否核算" width="100" align="center" prop="isAccount" :formatter="isAccountFormat" />
      <el-table-column sortable label="是否资本性成本"  min-width="90"  align="center" prop="isCostCapital" :formatter="isCostCapitalFormat" />
      <el-table-column sortable label="是否医疗成本" align="center" prop="isCostHealth" :formatter="isCostHealthFormat" />
      <el-table-column sortable label="是否计算计入成本"  min-width="100"  align="center" prop="isCostCalculate" :formatter="isCostCalculateFormat" />
      <el-table-column sortable label="计算计入成本方案" align="left" prop="costScheme" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit" :levelOptions="levelOptions" ></addEdit>

    <!-- 导入待盘点明细对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" custom-class="el-button--primary" :close-on-click-modal="false" >
      <el-upload
        ref="upload"
        :limit="1"
        :data="uploadDate"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog custom-class="el-button--primary" :title="title" :close-on-click-modal="false" :visible.sync="open1" width="40%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编码" prop="projectCode">
              <el-input v-model="form.projectCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="projectName">
              <el-input v-model="form.projectName" placeholder="请输入项目名称"  maxlength="100" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-transfer
              filterable
              :filter-method="dataFilter"
              v-model="selectedDept"
              :titles="['待选择科室信息','已选择科室信息']"
              :data="deptList"
              :props="{
                key: 'dictValue',
                label:'dictLabel'}">
              <span slot-scope="{ option }" :title="option.dictValue">{{ option.dictLabel }} - {{option.dictValue}}</span>
            </el-transfer>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm()" >保 存</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import { listProject, getProject, delProject, addProject, updateProjectDept, exportProject,getDictList } from "@/api/project/project";
  import addEdit from "./addEdit";
  import { getToken } from "@/utils/auth";
  import { getDeptList } from "@/api/project/project";

  export default {
    name: "Project",
    components: {
      addEdit
    },
    watch: {
      listData(val) {
        var that = this;
        setTimeout(function() {
          that.resizeHandler();
        }, 300);
      }
    },
    data() {
      return {
        // 遮罩层
        loading: true,
        tableHeight: "auto",
        // 导出遮罩层
        exportLoading: false,
        // 选中数组
        ids: [],
        isCostCalculates: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 成本项目管理表格数据
        projectList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        open1: false,
        // 方案级别字典
        levelOptions: [],
        // 项目级别字典
        projectLevelOptions: [],
        // 项目类型字典
        projectTypeOptions: [],
        // 成本属性字典
        costPropertyOptions: [],
        // 是否可控字典
        isControlOptions: [],
        // 是否核算字典
        isAccountOptions: [],
        // 是否资本性成本字典
        isCostCapitalOptions: [],
        // 是否医疗成本字典
        isCostHealthOptions: [],
        // 是否计算计入成本字典
        isCostCalculateOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: this.$store.state.settings.pageSize,
          compCode: this.$store.state.user.accountInfo.deptId,
          projectCode: null,
          projectName: null,
          projectLevel: null,
          projectType: null,
          costProperty: null,
        },
        uploadDate: {
          compCode: this.$store.state.user.accountInfo.deptId,
        },
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
          url: process.env.VUE_APP_BASE_API + "/project/project/importData"
        },

        // 表单参数
        form: {},
        // 表单校验
        rules: {
        },
        deptList: [],
        selectedDept: [],
      };

    },
    computed: {
      listData() {
        return this.$store.state.app.sidebar.opened;
      }
    },
    created() {
      this.getDictList()
      this.getList();
      this.getDicts("cost_xm_jb").then(response => {
        this.projectLevelOptions = response.data;
      });
      this.getDicts("cost_xm_lx").then(response => {
        this.projectTypeOptions = response.data;
      });
      this.getDicts("cost_cb_sx").then(response => {
        this.costPropertyOptions = response.data;
      });
      this.getDicts("hr_is_change").then(response => {
        this.isCostCalculateOptions = response.data;
        this.isControlOptions = response.data;
        this.isCostHealthOptions = response.data;
        this.isCostCapitalOptions = response.data;
        this.isAccountOptions = response.data;
      });
      this.getDeptList(this.$store.state.user.userInfo.deptId,"");
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
    },
    methods: {
      handleClickTableName(data) {
        // 选中当前行
        this.rowClick(data, false);
        this.handleUpdate(data);
      },
      /** 查询成本项目管理列表 */
      getList() {
        this.loading = true;
        listProject(this.queryParams).then(response => {
          // console.log(this.levelOptions,'leve')
          response.rows.forEach(ite=>{
            this.levelOptions.forEach(item=>{
              if(ite.costScheme==item.dictValue){
                ite.costScheme=item.dictLabel
              }
            })
          })
          this.projectList = this.handleTree(
            response.rows,
            "projectCode",
            "parentProject"
          );
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询收入项目管理列表 */
      getDeptList(compCode,isCostCalculate) {
        this.loading = true;
        getDeptList(compCode,isCostCalculate).then(response => {
          console.log("compCode",compCode)
          this.deptList = response.data;
          this.loading = false;
        });
      },
      // 项目级别字典翻译
      projectLevelFormat(row, column) {
        return this.selectDictLabel(this.projectLevelOptions, row.projectLevel);
      },
      // 项目类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.projectType);
      },
      // 成本属性字典翻译
      costPropertyFormat(row, column) {
        return this.selectDictLabel(this.costPropertyOptions, row.costProperty);
      },
      // 是否可控字典翻译
      isControlFormat(row, column) {
        return this.selectDictLabel(this.isControlOptions, row.isControl);
      },
      // 是否核算字典翻译
      isAccountFormat(row, column) {
        return this.selectDictLabel(this.isAccountOptions, row.isAccount);
      },
      // 是否资本性成本字典翻译
      isCostCapitalFormat(row, column) {
        return this.selectDictLabel(this.isCostCapitalOptions, row.isCostCapital);
      },
      // 是否医疗成本字典翻译
      isCostHealthFormat(row, column) {
        return this.selectDictLabel(this.isCostHealthOptions, row.isCostHealth);
      },
      // 是否计算计入成本字典翻译
      isCostCalculateFormat(row, column) {
        return this.selectDictLabel(this.isCostCalculateOptions, row.isCostCalculate);
      },
      //分摊方案级别
      getDictList(){
        let query={
          compCode:this.$store.state.user.userInfo.deptId,
        }
        getDictList(query).then(response=>{
          if(response.code===200){
            this.levelOptions=response.data
          }
        })
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
      cancel1() {
        this.open1 = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          projectCode: null,
          projectName: null,
          parentProject: null,
          projectLevel: null,
          projectType: null,
          costProperty: null,
          isControl: null,
          isAccount: null,
          isCostCapital: null,
          isCostHealth: null,
          isCostCalculate: null,
          costScheme: null,
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
        this.isCostCalculate = selection.map(item => item.isCostCalculate)[0]
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
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      /** 新增按钮操作
       handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加成本项目管理";
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
            this.form.deptList = this.selectedDept;
            console.log("form",this.form)
            let saveLoading = this.saveLoading();
            if (this.form.id != null) {
              updateProjectDept(this.form).then(response => {
                this.msgSuccess("项目科室设置成功");
                this.open1 = false;
                saveLoading.close();
              });
            }
          }
        });
      },
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除成本项目管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delProject(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "成本项目导入";
        this.upload.open = true;
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
      handleFileError(err,file,fileList){
        this.upload.isUploading = false;
        this.msgError("上传失败！");
//  	console.log(err);
//  	console.log(file);
//  	console.log(fileList);
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否导出成本项目管理模板?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportProject(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
      },
      handleDept(row) {
        this.reset();
        this.open1 = false;
        this.title = "收入项目管理-科室设置";
        const id = row.id || this.ids[0];
        let isCostCalculate = this.isCostCalculate;
        if (isCostCalculate == '0') {
          this.getDeptList(this.$store.state.user.userInfo.deptId,"0");
        } else if (isCostCalculate == '1') {
          this.getDeptList(this.$store.state.user.userInfo.deptId,"1");
        }
        getProject(id).then(response => {
          console.log(response);
          this.form = response.data;
          this.selectedDept = this.form.deptList?this.form.deptList:[]
          console.log(this.selectedDept,"this.selectedDept")
          this.open1 = true;
        });
      },
      /** 搜索方法 */
      dataFilter(query,item) {
        return item.dictValue.indexOf(query) > -1 || item.dictLabel.indexOf(query) > -1;
        // if (val) { //val存在
        //   // this.options = this.optionsCopy.filter((item) => {
        //   //   if (!!~item.label.indexOf(val) || !!~item.label.toUpperCase().indexOf(val.toUpperCase())) {
        //   //     return true
        //   //   }
        //   // })
        // } else { //val为空时，还原数组
        //   return true;
        //   // this.options = this.optionsCopy;
        // }
      },
    }
  };
</script>
<style>
 .el-transfer-panel{
    width: 42%;
    height: 80%;
  }
 .el-transfer-panel__list.is-filterable{
    height: 80%;
  }
</style>
