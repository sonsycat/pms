<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设置年月" prop="setDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.setDate"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择设置年月">
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
          v-hasPermi="['average:average:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['average:average:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['average:average:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-document-copy"
          size="mini"
          :loading="saveLoading"
          @click="handleCopy"
          v-hasPermi="['average:average:export']"
        >复制
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :data="averageList" :height="tableHeight"
              @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="设置年月" align="center" prop="id" v-if="false"/>
      <el-table-column label="code" prop="code" v-if="false"/>
      <el-table-column sortable label="设置年月" align="center" prop="setDate" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.setDate, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="录入时间" align="center" prop="setTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.setTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column sortable label="录入人" align="center" prop="setPerson" />
<!--      <el-table-column label="操作" align="center" width="300" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['average:average:edit']" title="修改"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['average:average:remove']" title="删除"
          ></el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

	<addEdit v-if="open" :open="open" :id="addEditId" :addEditSetDate="addEditSetDate" @cancel="cancel" @success="successSubmit"></addEdit>
    <addEditDetail v-if="openDetail" :openDetail="openDetail" :id="addEditId" @cancelDetail="cancelDetail" @success="successSubmitDetail"></addEditDetail>

    <el-dialog title="平均奖科人数-复制" class="copyEvaDialog" ref="copyEvaDialog" :visible.sync="openCopy" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="copyCancel">
      <el-form ref="copyForm" :model="copyForm" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="复制年月" prop="setDate">
              <el-date-picker clearable class="w90" v-model="copyForm.setDate" type="month" value-format="yyyy-MM-dd" placeholder="选择年月">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <!-- :picker-options="picOptFun" -->
            <el-form-item label="拷贝年月" prop="copyDate">
              <el-date-picker clearable class="w90" v-model="copyForm.copyDate" type="month" value-format="yyyy-MM-dd"
                              placeholder="选择年月" @change="changeNowDate" :picker-options="picOptFun(copyForm.setDate,copyForm.copyDate)">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCopyForm">保 存</el-button>
        <el-button @click="copyCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAverage,
  getAverage,
  delAverage,
  addAverage,
  updateAverage,
  exportAverage,
  getAverageNewData,
  copyDataByDate
} from '@/api/average/average'
import addEdit from "./addEdit";
import addEditDetail from '@/views/average/average/addEditDetail'
import { getYearMonth2 } from '@/utils/date'

export default {
  name: "Average",
  components: {
addEdit,addEditDetail
  },
  data() {
    return {
      saveLoading: false,
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
      // 平均奖科人数表格数据
      averageList: [],
      selectionRow: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openDetail: false,
      openCopy:false,
      copyForm:{
        // compCode: this.$store.state.user.userInfo.deptId,
        setDate:null,
        copyDate:null
      },
      tableHeight: "auto",
      addEditSetDate: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        setDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        setDate: [
          { required: true, message: "设置年月不能为空", trigger: "blur" }
        ],
        setTime: [
          { required: true, message: "录入时间不能为空", trigger: "blur" }
        ],
        setPerson: [
          { required: true, message: "录入人不能为空", trigger: "blur" }
        ],
      }
    };
  },
  /* computed: {
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
    },
  }, */
  created() {
    this.getList();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    /** 查询平均奖科人数列表 */
    getList() {
      this.loading = true;
      listAverage(this.queryParams).then(response => {
        this.averageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
	successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    successSubmitDetail(flag) {
      this.openDetail = flag;
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancelDetail() {
      this.openDetail = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        setDate: null,
        setTime: null,
        setPerson: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        code: null,
        compCode: null,
        accountCode: null
      };
      this.resetForm("form");
    },
    mounted(){
      this.$nextTick(() => {
        this.resizeHandler();
        window.addEventListener("resize", this.resizeHandler);
      });
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 10;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
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
      this.title = "添加平均奖科人数";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      getAverageNewData().then(response =>{
        this.open = true; // 弹框点开
        this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑
        this.addEditSetDate = response.data.setDate;
        console.log("日期："+this.addEditSetDate);
      })
    },
    /** 编辑按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      this.openDetail = true;
      this.addEditId = id; // id赋值
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAverage(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAverage(this.form).then(response => {
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
      this.$confirm('是否确认删除平均奖科人数编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAverage(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    //复制按钮操作
    handleCopy(){
      this.openCopy = true;
      this.resetCopy();
    },
    resetCopy(){
      this.copyForm={
        // compCode: this.$store.state.user.userInfo.deptId,
        setDate: getYearMonth2() + '-01',
        copyDate: null,
      }
      this.resetForm("copyForm");
    },
    copyCancel(){
      this.resetCopy();
      this.openCopy = false;
      this.setdeptbtn = true
      this.getList();
    },
    subCopy(flag){
      this.resetCopy();
      this.openCopy = flag;
      this.getList();
    },
    //复制保存按钮
    submitCopyForm(){
      this.loading = true;
      copyDataByDate(this.copyForm).then(res=>{
        if(res.code == 200){
          this.msgSuccess(res.msg);
          // this.queryParams.setDate = this.copyForm.setDate;
          this.subCopy(false);
          this.loading = false;
        }else{
          this.msgError(res.msg);
        }
      })
    },
    changeNowDate(val){
      this.picOptFun(val,this.copyForm.setDate)
    },
    picOptFun(time1,time2){
      if(time1 && time2 && time1 == time2){
        this.copyForm.copyDate=null;
        this.msgError("拷贝年月与复制年月不能相同")
      }
    }
  }
};
</script>
