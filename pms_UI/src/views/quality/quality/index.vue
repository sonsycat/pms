<template>
  <div class="app-container">
  <div ref="search">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="科室" prop="deptCode">
        <el-select class="search_w220" v-model="queryParams.deptCode" clearable filterable placeholder="请选择科室分类">
            <el-option
              v-for="item in deptList"
              :key="item.deptRoomId"
              :label="item.deptRoomName"
              :value="item.deptRoomId">
            </el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="qDate">
        <el-date-picker :clearable="false"
            v-model="queryParams.qDate"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择年月"
            >
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
          v-hasPermi="['quality:quality:add']"
        >生成质量数据</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-search"
          size="mini"
          :disabled="single"
          @click="handleDetail"
          v-hasPermi="['quality:quality:edit']"
        >查看详情</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
	</div>

    <el-table border stripe v-loading="loading" :height="tableHeight" :data="qualityList" @selection-change="handleSelectionChange" ref="multipleTable" @row-click="rowClick" :row-style="rowStyle" :row-class-name="rowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column sortable label="年月" align="center" prop="occTime" width="180">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.occTime" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column sortable label="科室名称" align="center" prop="deptName" />
      <el-table-column sortable label="公共质量得分" align="center" prop="actualScore" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  <!-- 详情 -->
	<detailEdit v-if="detailOpen" :open="detailOpen" :datas="selectionRow[0]" @cancel="detailCancel" @success="successSubmit"></detailEdit>
  <!-- 新增 -->
		<el-dialog :title="title" :close-on-click-modal="false" custom-class="el-button--primary" :visible.sync="open" width="26%" append-to-body @close="cancel">
      <el-form class="add_form" :model="form" :rules="rules" ref="form" :inline="true" >
        <el-row>
          <el-col :span="24">
            <el-form-item label="年月:" prop="occureTime">
              <el-date-picker
                v-model="form.occureTime "
                value-format="yyyy-MM-dd"
                type="month"
                placeholder="年/月"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="subLoading">确 认</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
		</el-dialog>
  </div>
</template>

<script>
import { listQuality, delQuality, addQuality, updateQuality, exportQuality, checkQuality} from "@/api/quality/quality";
import detailEdit from "./detailEdit";
import { treeRoom } from "@/api/until";

export default {
  name: "Quality",
  components: {
  detailEdit
  },
  data() {
    return {
      tableHeight: "auto",
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      subLoading:false,
      // 选中数组
      ids: [],
      addEditId:null,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 质量数据生成表格数据
      qualityList: [],
      selectionRow: [],
      // 弹出层标题
      title: "生成质量数据",
      // 是否显示弹出层
      open: false,
      //详情弹出框
      detailOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: null,
        deptCode: null,
        indexCode: null,
        tempCode: null,
        indexScore: null,
        occureTime: null,
        actualScore: null,
        nowDate:null,
        qDate:null
      },
      // 表单参数
      form: {

      },
      // 表单校验
      rules: {
        occureTime:[{ required: true, message: "请选择年月", trigger: ["blur","change"] }]
      },
      deptList:[],
    };
  },
  created() {
    this.getList();
    this.getTreeDept();
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
    //获取科室列表
    getTreeDept(deptId) {
      let data = {
        deptId: deptId
      };
      treeRoom(data).then(response => {
        if (response.code == 200) {
          this.deptList = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    handleClickTableName(data){
      // 选中当前行
        this.rowClick(data, false);
        this.handleDetail(data);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询质量数据生成列表 */
    getList() {
      this.loading = true;
      // this.queryParams.params = {};
      listQuality(this.queryParams).then(response => {
        this.qualityList = response.rows;
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
        compCode:this.$store.state.user.userInfo.deptId,
        accountCode: null,
        deptCode: null,
        indexCode: null,
        tempCode: null,
        indexScore: null,
        occureTime: null,
        actualScore: null,
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
      this.daterangeOccureTime = [];
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
    /** 新增按钮操作
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加质量数据生成";
    },
     */
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true; // 弹框点开
      // this.addEditId = ""; // addEditId 是新增  去子组件方便区分新增编辑

    },
    //详情按钮操作
    handleDetail(){
      this.detailOpen=true;
    },
    detailCancel(){
      this.detailOpen=false;
    },
    checkQuality(data){
      checkQuality(data).then(res=>{
        if(res.code==200){
          this.checkedAllow = res.data
        }
      })
    },
    addQualityFun(data){
      let saveLoading=this.saveLoading()
        addQuality(data).then(response => {
          if(response.code==200){
             this.msgSuccess("新增成功");
            this.open = false;
            this.getList();
            saveLoading.close();
          }

        });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let params={
            occureTime:this.form.occureTime,
            compCode:this.$store.state.user.userInfo.deptId,
          }
          checkQuality(params).then(res=>{
            if(res.code==200){
              if(!res.data){
                this.$confirm('当月已有数据，是否确认覆盖原有数据?', "警告", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "warning"
                }).then(()=> {
                  return this.addQualityFun(params);
                }).catch(() => {
                  this.open = false;
                  this.getList();
                });
              }else{
                this.addQualityFun(params);
              }
            }
          })
        }
      });
    },
  }
};
</script>
<style lang="scss" scoped>
.add_form{
  ::v-deep{
    .el-form-item__content{
      margin-left: 0 !important;
    }
  }
}
</style>

