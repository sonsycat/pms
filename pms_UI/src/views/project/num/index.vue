<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
        <el-form-item label="年月" prop="nowDate">
          <el-date-picker class="search_w220" clearable
                          v-model="queryParams.nowDate"
                          type="month"
                          value-format="yyyy-MM"
                          placeholder="选择年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目级别" prop="projectLevel">
          <el-select class="search_w220" v-model="queryParams.projectLevel" placeholder="请选择项目级别" clearable>
            <el-option
              v-for="dict in projectLevelOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="科室分类" prop="deptType">
          <el-select class="search_w220" v-model="queryParams.deptType" clearable placeholder="请选择科室分类" @change="getDeptQuery">
            <el-option
              v-for="dict in deptList"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="Number(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室"  prop="deptCode" >
          <el-select class="search_w220" v-model="queryParams.deptCode" clearable automatic-dropdown="" :filterable="true" :remote="true" placeholder="请选择科室">
            <el-option
              v-for="item in deptDatas"
              :key="item.dictValue || item.deptRoomCode"
              :label="item.dictLabel || item.deptRoomName"
              :value="item.dictValue || item.deptRoomCode"
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
            v-hasPermi="['project:num:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['project:num:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="single"
            @click="handleDelete"
            v-hasPermi="['project:num:remove']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            icon="el-icon-upload2"
            size="mini"
            @click="handleExtract"
            v-hasPermi="['project:project:export']"
          >成本提取</el-button>
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

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getHeadList"></right-toolbar>
      </el-row>
    </div>

    <el-table :data="tableData"
              v-loading="loading"
              size='small'
              ref="requirementTable" border stripe stripe highlight-current-row @selection-change="handleSelectionChange" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName" :height="tableHeight">
        <el-table-column type="selection" width="55" align="center" />
        <template v-for="(item,i) in tableColumns">
          <template>
            <el-table-column
              min-width="180px"
              :align="item.defaultAlign"
              :prop="item.prop"
              :key="i"
              :label="item.label">
            </el-table-column>
          </template>
        </template>
      </el-table>
       <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getHeadList"
    />
    <addEdit v-if="open" :open="open" :editData="editDatas" :id="addEditId" @cancel="cancel" @success="successSubmit"></addEdit>
    <extractForm v-if="openExtract" :open="openExtract" :isInCome="true" :form="form"  @cancel="cancel1" @success="successSubmit1"></extractForm>

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

  </div>
</template>

<script>
  import {listNum, getNum, delNum, addNum, updateNum, exportNum, headerList, resultList,searchList} from "@/api/project/num";
  import { treeRoom } from "@/api/until";
  import {getYearMonth2} from "@/utils/date";
  import addEdit from "./addEdit";
  import extractForm from "@/views/components/extractForm";
  import { getToken } from "@/utils/auth";
  import { importTemplate } from "@/api/system/user";

  export default {
    name: "Num",
    components: {
      addEdit,
      extractForm
    },
    data() {
      return {
        //---------动态表头
        tableColumns: [],
        tableData: [],
        tableHeight: "auto",
        editDatas:{},
        deptList: [],
        deptDatas:[],
        // 项目级别字典
        projectLevelOptions: [],
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
        // 成本金额管理表格数据
        numList: [],
        selectionRow: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //是否显示提取弹出
        openExtract:false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: this.$store.state.settings.pageSize,
          compCode: this.$store.state.user.accountInfo.deptId,
          accountCode: null,
          projectCode: null,
          deptCode: null,
          deptName:null,
          deptType:null,
          nowDate:  getYearMonth2(),
          projectLevel:'2',
          money: null,
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
          url: process.env.VUE_APP_BASE_API + "/project/num/importData"
        },


        // 表单参数
        form: {},
        // 表单校验
        rules: {},
      };
    },
    computed: {
      listData() {
        return this.$store.state.app.sidebar.opened;
      }
    },
    watch: {
      "queryParams.compCode": {
          handler(val) {
            this.getTreeDept(val);
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
    created() {
      this.getHeadList();
      this.getDicts("cost_xm_jb").then(response => {
        this.projectLevelOptions = response.data;
      });
      this.getDicts("cost_dept_type").then(response => {
        this.deptList = response.data;
      });
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
    },
    methods: {
      //科室类型查询科室
      getDeptQuery(value){
        let queryDatas={
          compCode: this.queryParams.compCode,
          type:value
        }
        if(value){
           searchList(queryDatas).then(response=>{
            if(response.code===200){
              this.deptDatas=response.data
            }
          })
        }else{
          this.getTreeDept(this.queryParams.compCode)
        }
      },
      //获取科室列表
      getTreeDept(deptId) {
        let data = { deptId: deptId };
        treeRoom(data).then(response => {
          if (response.code == 200) {
            this.deptDatas = response.rows;
          } else {
            this.msgError(response.msg);
          }
        });
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then(response => {
          if (response.code == 200) {
            this.download(response.msg);
          } else {
            this.msgError(response.msg);
          }
        });
      },
      //导入
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
        if(response.msg.indexOf(".xls")>0){
          this.download(response.msg);
        }
        this.getHeadList();
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
        this.$confirm('是否导出成本录入模板?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportNum(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
      },
      // 提取按钮操作
      handleExtract(){
        this.openExtract=true
      },
      resizeHandler() {
        let a = this.$refs.search.offsetHeight;
        let b = this.$refs.btns.offsetHeight;
        let h = a + b + 18 + 45 + 84 + 40 + 20;
        h = h + "px";
        this.tableHeight = `calc(100vh - ${h})`;
      },
      getHeadList() {
        this.loading = true;
        headerList(this.queryParams).then(response => {
          let datas=response.map(ite=>{
            if(ite.prop=="deptName"){
              ite.defaultAlign="left"
            }else if(ite.prop=="nowDate"||ite.prop=="deptCode"){
              ite.defaultAlign="center"
            }else{
              ite.defaultAlign="right"
            }
            return ite
          })
          this.tableColumns = datas.filter(_=>_.prop!=="deptCode");
          this.getBodyList();
          this.loading = false;
        });
      },
      getBodyList() {
        resultList(this.queryParams).then(response => {
          if(response.code==200){
            this.tableData = response.data;
            this.total=response.data&&response.data.length
            for (let i = 0; i < this.tableData.length; i++) {
              for (var j in this.tableData[i]) {
                if ((typeof this.tableData[i][j]) == "number" && this.tableData[i][j] != 0 && j!="deptCode") {
                  this.tableData[i][j] = this.moneyFormat(this.tableData[i][j])
                }
              }
            }
          }
        });
      },
      successSubmit(flag) {
        this.open = flag;
        this.openExtract=flag;
        this.getHeadList();
        this.resetQuery()
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.openExtract=false;
        this.resetQuery()
        this.reset();
      },
       successSubmit1(flag) {
        this.openExtract=flag;
        this.getHeadList();
        this.resetQuery()      
      },
      // 取消按钮
      cancel1() {
        this.openExtract=false;
        this.resetQuery()      
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: null,
          accountCode: null,
          projectCode: null,
          deptCode: null,
          deptName:null,
          deptType:null,
          nowDate:null,
          money: null,
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.tableColumns = [];
        this.tableData = [];
        this.getHeadList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
        this.getTreeDept(this.$store.state.user.accountInfo.deptId)
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        //改id
        this.ids = selection.map(item =>item.rowIndex * 1 + 1)
        this.single = selection.length !== 1
        this.multiple = !selection.length
        this.selectionRow = selection
        this.editDatas=selection[0]
      },
      rowClick(row, column, event) {
        let refsElTable = this.$refs.requirementTable;
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
      },
      projectLevelFormat(row, column) {
        return this.selectDictLabel(this.projectLevelOptions, row.projectLevel);
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateNum(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getHeadList();
              });
            } else {
              addNum(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getHeadList();
              });
            }
            this.resetForm("queryForm");
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
         const delData = {
            deptCode:this.editDatas.deptCode,
            nowDate:this.editDatas.nowDate
          };
        this.$confirm('是否确认删除成本金额"' + this.editDatas.deptName+","+delData.nowDate +  '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delNum(delData);
        }).then(() => {
          this.getHeadList();
          this.msgSuccess("删除成功");
          this.resetQuery()
        }).catch(() => {
        });
      },


    }
  };
</script>
<style lang="scss" scoped>
::v-deep{
  .el-table__body-wrapper.is-scrolling-left{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
    .el-table__body-wrapper.is-scrolling-middle{
      max-height:calc(100vh - 326px) !important;
      height:auto !important;
    }
    .el-table__body-wrapper.is-scrolling-right{
      height:auto !important;
      max-height:calc(100vh - 326px) !important;
    }
}
</style>

