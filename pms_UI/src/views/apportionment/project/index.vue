<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="分摊方案编码" prop="projectCode">
          <el-input
            class="search_w220"
            v-model="queryParams.projectCode"
            placeholder="请输入方案编码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="分摊方案名称" prop="projectName">
          <el-input
            class="search_w220"
            v-model="queryParams.projectName"
            placeholder="请输入方案名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="当前年月" prop="nowDate">
          <el-date-picker class="search_w220" clearable
                          v-model="queryParams.nowDate"
                          type="month"
                          value-format="yyyy-MM"
                          placeholder="选择当前年月">
          </el-date-picker>
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
            v-hasPermi="['apportionment:project:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['apportionment:project:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['apportionment:project:remove']"
          >删除</el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleCopy"
            v-hasPermi="['apportionment:project:edit']"
          >拷贝</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="single"
            @click="handleDept"
            v-hasPermi="['apportionment:project:list']"
          >比例设置</el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            :disabled="single"
            @click="handleDept1"
            v-hasPermi="['apportionment:project:add']"
          >科室设置</el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <el-table border stripe v-loading="loading"  :height="tableHeight"  :data="projectList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="方案编码" align="center" prop="projectCode" />
      <el-table-column sortable label="唯一编码" align="center" prop="projectInCode">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.projectInCode" @click.stop="handleClickTableCode(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="方案名称" align="left" prop="projectName" />
      <el-table-column sortable label="拼音码" align="center" prop="spell" />
      <el-table-column sortable label="年月" align="center" prop="nowDate" />
      <el-table-column sortable label="方案类型" align="center" prop="projectType" :formatter="projectTypeFormat" />
      <el-table-column sortable label="方案比例" align="left" prop="rationShow" width="800px" show-overflow-tooltip/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <addEdit v-if="open" :open="open" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>

    <dept v-if="openDept" :open="openDept" :id="addEditId" @cancel="cancelDept" @success="successSubmitDept"></dept>
    <count v-if="openCount" :open="openCount" :id="addEditId" @cancel="cancelCount" @success="successSubmitCount"></count>

    <copy v-if="openCopy" :open="openCopy"  @cancel="cancelCopy" @success="successSubmitCopy"></copy>

    <!-- 科室比例设置对话框 -->
    <el-dialog :title="title2" class="prodCred" :visible.sync="open2" width="50%" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="postForm" :model="postForm" >
        <el-row>
          <el-col :span="24">
            <el-table class ="tableSearchRequired" v-loading="addloading" :data="postForm.costDeptInfos">
              <el-table-column sortable label="比例字典名称" align="center" prop="ratioName" />
              <el-table-column label="一级分摊比例" align="center" prop="firstRatio" class-name ="tbFormItem">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.firstRatio" precision="2" controls-position="right" :min="0" :max="1" :step="0.1" />
                </template>
              </el-table-column>
              <el-table-column label="二级分摊比例" align="center" prop="secondRatio" class-name ="tbFormItem">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.secondRatio" precision="2" controls-position="right" :min="0" :max="1" :step="0.1" />
                </template>
              </el-table-column>
              <el-table-column label="三级分摊比例" align="center" prop="thirdRatio" class-name ="tbFormItem">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.thirdRatio" precision="2" controls-position="right" :min="0" :max="1" :step="0.1" />
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">保 存</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 科室比例设置对话框 -->
    <el-dialog :title="title3" class="prodCred" :visible.sync="open3" width="40%" append-to-body :close-on-click-modal="false" custom-class="el-button--primary">
      <el-form ref="postForm" :model="postForm" >
        <el-row>
          <el-col :span="24">
            <el-table class ="tableSearchRequired" v-loading="addloading" :data="postForm.costDeptInfos">
              <el-table-column sortable label="比例字典名称" align="center" prop="ratioName" />
              <el-table-column label="计算计入分摊比例" align="center" prop="ratio" class-name ="tbFormItem">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.ratio" controls-position="right" :min="0" :max="1" :step="0.1" />
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm3">保 存</el-button>
        <el-button @click="cancel3">取 消</el-button>
      </div>
    </el-dialog>



  </div>
</template>

<script>
  import { listProject, getProject, delProject, addProject, updateProject, exportProject,listDeptProject,listRatio,insertRatio } from "@/api/apportionment/project";
  import addEdit from "./addEdit";
  import dept from "./dept";
  import count from "./count";
  import copy from "./copy";

  export default {
    name: "Project",
    components: {
      addEdit,dept,count,copy
    },
    data() {
      return {
        //科室比例设置
        postForm: {},
        title2: "",
        title3: "",
        openDept: false,
        openCount: false,
        openCopy: false,
        open2: false,
        open3: false,
        addloading: false,
        projectTypes: [],
        projectCodes: [],
        projectInCodes: [],

        //======================
        // 遮罩层
        loading: true,
        // 导出遮罩层
        tableHeight: "auto",
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
        // 分摊方案管理表格数据
        projectList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 方案类型字典
        projectTypeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: this.$store.state.settings.pageSize,
          compCode: this.$store.state.user.accountInfo.deptId,
          accountCode: null,
          projectCode: null,
          projectInCode: null,
          projectName: null,
          nowDate: null,
          spell: null,
          projectType: null,
          rationShow: null,
          rationCount: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        }
      };
    },
    computed: {
      listData() {
        return this.$store.state.app.sidebar.opened;
      }
    },
    created() {
      if(this.$route.query.id){
        this.handleUpdate(this.$route.query)
      }
      this.getList();
      this.getDicts("cost_fa_type").then(response => {
        this.projectTypeOptions = response.data;
      });
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
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
      /** 查询分摊方案管理列表 */
      getList() {
        this.loading = true;
        this.queryParams.projectType ='2';
        listProject(this.queryParams).then(response => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      // 方案类型字典翻译
      projectTypeFormat(row, column) {
        return this.selectDictLabel(this.projectTypeOptions, row.projectType);
      },
      successSubmit(flag) {
        this.open = flag;
        this.getList();
      },
      successSubmitDept(flag) {
        this.openDept = flag;
        this.getList();
      },
      successSubmitCount(flag) {
        this.openCount = flag;
        this.getList();
      },
      successSubmitCopy() {
        this.openCopy = false;
        this.getList();
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.getList()
        this.reset();
      },
      // 取消按钮
      cancelDept() {
        this.openDept = false;
        this.reset();
      },
      // 取消按钮
      cancelCount() {
        this.openCount = false;
        this.reset();
      },
      // 取消按钮
      cancelCopy() {
        this.openCopy = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          projectCode: null,
          projectInCode: null,
          projectName: null,
          nowDate: null,
          spell: null,
          projectType: null,
          rationShow: null,
          rationCount: null,
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
        this.projectType = selection.map(item => item.projectType);
        this.projectCode = selection.map(item => item.projectCode);
        this.projectInCode = selection.map(item => item.projectInCode);
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
      /** 比例设置操作 */
      handleDept(row) {
        // 11111111111111
        const projectCodes = row.projectCode || this.projectCode;
        const projectInCodes = row.projectInCode || this.projectInCode;
        const projectTypes = row.projectType || this.projectType;
        let data = {
          "ratioType":projectTypes[0],
          "projectCode" : projectInCodes[0],
          "compCode": this.$store.state.user.accountInfo.deptId,
        }
        console.log(data)
        listRatio(data).then(response => {
          this.postForm = response.data;
          if (projectTypes[0] == '1') {
            this.open2 = true;
            this.title2 = "分摊方案管理/ 比例设置";
          } else if (projectTypes[0] == '2') {
            this.open3 = true;
            this.title3 = "分摊方案管理/ 比例设置";
          }
        });

      },
      handleDept1(row) {
        this.reset();
        const id = row.id || this.ids[0];
        const projectType = row.projectType || this.projectType[0];
        if (projectType == '1') {
          this.openDept = true;
        } else if (projectType == '2') {
          this.openCount = true;
        }
        this.addEditId = id; // id赋值
        console.log("addEditId---" + addEdit);
      },

      handleCopy() {
        this.reset();
        this.openCopy = true;
      },
      submitForm2() {
        let saveLoading = this.saveLoading();
        let num = 0;
        for (let i=0; i<this.postForm.costDeptInfos.length; i++){
          num += this.postForm.costDeptInfos[i].firstRatio;
        }
        if (num != 1) {
          saveLoading.close();
          this.msgError("一级分摊比例加起来不为1");
          return true;
        }
        num = 0;
        for (let i=0; i<this.postForm.costDeptInfos.length; i++){
          num += this.postForm.costDeptInfos[i].secondRatio;
        }
        if (num != 1) {
          saveLoading.close();
          this.msgError("二级分摊比例加起来不为1");
          return true;
        }
        num = 0;
        for (let i=0; i<this.postForm.costDeptInfos.length; i++){
          num += this.postForm.costDeptInfos[i].thirdRatio;
        }
        if (num != 1) {
          saveLoading.close();
          this.msgError("三级分摊比例加起来不为1");
          return true;
        }
        insertRatio(this.postForm).then(response => {
          this.open2 = false;
          saveLoading.close();
          this.msgSuccess("保存成功");
          this.getList();
        });
      },
      submitForm3() {
        let saveLoading = this.saveLoading();
        let num = 0;
        for (let i=0; i<this.postForm.costDeptInfos.length; i++){
          num += this.postForm.costDeptInfos[i].ratio;
        }
        if (num != 1) {
          saveLoading.close();
          this.msgError("计算计入分摊比例加起来不为1");
          return true;
        }
        insertRatio(this.postForm).then(response => {
          this.open3 = false;
          saveLoading.close();
          this.msgSuccess("保存成功");
          this.getList();
        });
      },
      cancel2() {
        this.open2 = false;
      },
      cancel3() {
        this.open3 = false;
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
      this.title = "添加分摊方案管理";
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
              updateProject(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addProject(this.form).then(response => {
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
        this.$confirm('是否确认删除分摊方案管理编号为"' + ids + '"的数据项?', "警告", {
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
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有分摊方案管理数据项?', "警告", {
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
      }
    }
  };
</script>
