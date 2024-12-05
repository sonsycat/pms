<template>
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="时间" prop="dateTime">
          <el-date-picker clearable size="small"
                          v-model="queryParams.dateTime"
                          type="month"
                          value-format="yyyy-MM"
                          placeholder="选择时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="类别名称" prop="typeNameCode">
          <el-select class="search_w220" v-model="queryParams.equivalentTypeCode" filterable clearable placeholder="请选择类别名称" >
            <el-option
              v-for="item in equivalentTypeList"
              :key="item.equivalentTypeCode"
              :label="item.equivalentTypeName"
              :value="item.equivalentTypeCode"
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
            type="warning"
            icon="el-icon-upload2"
            size="mini"
            :loading="exportLoading"
            @click="handleExportMes"
            v-hasPermi="['bonus:sEquivalent:exportDetail']"
          >导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>

    <el-table border :height="tableHeight" stripe v-loading="loading" :data="enterList" ref="multipleTable"    >
      <el-table-column sortable label="病区代码" align="center" prop="illnessCode" width="160" v-if="illnessCodeShow"
                       key="1"/>
      <el-table-column sortable label="病区名称" align="center" prop="illnessName" width="160" v-if="illnessNameShow"
                       key="2"/>

      <el-table-column  sortable label="人事考勤科室" align="center" prop="hrDeptName" width="160" v-if="hrDeptNameShow"
                       key="7"/>
      <el-table-column sortable label="医技考核组" align="center" prop="dtDeptName" width="160" v-if="dtDeptNameShow"
                       key="8"/>
      <el-table-column sortable label="住院科室代码" align="center" prop="zyDeptCode" width="160" v-if="zyDeptCodeShow"
                       key="3"/>
      <el-table-column sortable label="住院科室名称" align="center" prop="zyDeptName" width="160" v-if="zyDeptNameShow"
                       key="4"/>
      <el-table-column sortable label="医嘱科室" align="center" prop="yzDeptCode" width="160" v-if="yzDeptCodeShow"
                       key="9"/>
      <el-table-column sortable label="医嘱科室名称" align="center" prop="yzDeptName" width="160" v-if="yzDeptNameShow"
                       key="10"/>
      <el-table-column sortable label="科室代码" align="center" prop="orderedByDept" width="160" v-if="orderedByDeptShow"
                       key="5"/>
      <el-table-column sortable label="科室名称" align="center" prop="deptName" width="160" v-if="deptNameShow" key="6"/>

      <el-table-column sortable label="医生工号" align="center" prop="orderedByDoctor" width="160"
                       v-if="orderedByDoctorShow" key="11"/>
      <el-table-column sortable label="医生姓名" align="center" prop="userName" width="160" v-if="userNameShow" key="12"/>
      <el-table-column sortable label="医疗组代码" align="center" prop="zlCode" width="160" v-if="zlCodeShow" key="13"/>
      <el-table-column sortable label="医疗组名称" align="center" prop="zlName" width="160" v-if="zlNameShow" key="14"/>
      <el-table-column sortable label="核算代码" align="center" prop="classOnReckoning" width="160"
                       v-if="classOnReckoningShow" key="15"/>
      <el-table-column sortable label="核算名称" align="center" prop="className" width="160" v-if="classNameShow" key="16"/>

      <el-table-column sortable label="项目代码" align="center" prop="itemCode" width="160" v-if="itemCodeShow" key="17"/>
      <el-table-column sortable label="项目名称" align="center" prop="itemName" width="160" v-if="itemNameShow" key="18"/>
      <el-table-column sortable label="物价" align="center" prop="itemPrice" width="160" v-if="itemPriceShow" key="19"/>
      <el-table-column sortable label="数量" align="center" prop="number" width="160" v-if="numberShow" key="20"/>
      <el-table-column sortable label="标准当量" align="center" prop="bzNum" width="160" v-if="bzNumShow" key="21"/>
      <el-table-column sortable label="绩效当量" align="center" prop="jxNum" width="160" v-if="jxNumShow" key="22"/>
      <el-table-column sortable label="总当量" align="center" prop="totalNum" width="160" v-if="totalNumShow" key="23"/>
      <el-table-column sortable label="状态" align="center" prop="state" v-if="stateShow" key="24"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getStatementEquivalentType ,listEquivalent,exportDetail} from "@/api/bonus/statement";

export default {
  name: "enter",
  components: {

  },
  data() {
    return {
      tableHeight: "auto",
      illnessCodeShow: false,
      illnessNameShow: false,
      yzDeptCodeShow: false,
      yzDeptNameShow: false,
      hrDeptNameShow: false,
      dtDeptNameShow: false,
      orderedByDeptShow: false,
      deptNameShow: false,
      orderedByDoctorShow: false,
      userNameShow: false,
      zlCodeShow: false,
      zlNameShow: false,
      classOnReckoningShow: false,
      classNameShow: false,
      itemCodeShow: false,
      itemNameShow: false,
      itemPriceShow: false,
      numberShow: false,
      bzNumShow: false,
      jxNumShow: false,
      totalNumShow: false,
      stateShow: false,
      zyDeptCodeShow: false,
      zyDeptNameShow: false,

      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,

      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // HIS收入类别表格数据
      enterList: [],
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        dateTime: new Date(),
        equivalentTypeCode: '1'
      },
      equivalentTypeList: [],
      // 表单校验
      rules: {
        dateTime: [
          { required: true, message: "时间不能为空", trigger: "blur" }
        ],
        equivalentTypeCode: [
          { required: true, message: "类别不能为空", trigger: "blur" }
        ],
      },

    };
  },
  created() {
    this.getStatementEquivalentType();
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
    getStatementEquivalentType(){
      getStatementEquivalentType().then(response => {
        this.equivalentTypeList = response.data;
      });
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      if(this.queryParams.equivalentTypeCode == 1){
        this.illnessCodeShow=false;
        this.illnessNameShow=false;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=true;
        this.dtDeptNameShow=false;
        this.orderedByDeptShow=true;
        this.deptNameShow=true;
        this.zyDeptCodeShow=false;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=true;
        this.userNameShow=true;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=true;
      }else if(this.queryParams.equivalentTypeCode == 2){
        this.illnessCodeShow=false;
        this.illnessNameShow=false;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=true;
        this.orderedByDeptShow=true;
        this.deptNameShow=true;
        this.zyDeptCodeShow=false;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=true;
        this.userNameShow=true;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=false;
      }else if(this.queryParams.equivalentTypeCode == 3){
        this.illnessCodeShow=true;
        this.illnessNameShow=true;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=false;
        this.orderedByDeptShow=false;
        this.deptNameShow=true;
        this.zyDeptCodeShow=true;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=false;
        this.userNameShow=false;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=false;
      }else if(this.queryParams.equivalentTypeCode == 4){
        this.illnessCodeShow=true;
        this.illnessNameShow=true;
        this.yzDeptCodeShow=true;
        this.yzDeptNameShow=true;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=false;
        this.orderedByDeptShow=false;
        this.deptNameShow=false;
        this.zyDeptCodeShow=true;
        this.zyDeptNameShow=true;
        this.orderedByDoctorShow=false;
        this.userNameShow=false;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=false;
        this.totalNumShow=true;
        this.stateShow=true;
      }else if(this.queryParams.equivalentTypeCode == 5){
        this.illnessCodeShow=false;
        this.illnessNameShow=false;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=true;
        this.orderedByDeptShow=true;
        this.deptNameShow=true;
        this.zyDeptCodeShow=false;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=false;
        this.userNameShow=false;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=false;
      }else if(this.queryParams.equivalentTypeCode == 6){
        this.illnessCodeShow=true;
        this.illnessNameShow=true;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=false;
        this.orderedByDeptShow=false;
        this.deptNameShow=false;
        this.zyDeptCodeShow=false;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=true;
        this.userNameShow=true;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=false;
      }else if(this.queryParams.equivalentTypeCode == 7){
        this.illnessCodeShow=true;
        this.illnessNameShow=true;
        this.yzDeptCodeShow=false;
        this.yzDeptNameShow=false;
        this.hrDeptNameShow=false;
        this.dtDeptNameShow=false;
        this.orderedByDeptShow=true;
        this.deptNameShow=true;
        this.zyDeptCodeShow=false;
        this.zyDeptNameShow=false;
        this.orderedByDoctorShow=false;
        this.userNameShow=false;
        this.zlCodeShow=true;
        this.zlNameShow=true;
        this.classOnReckoningShow=true;
        this.classNameShow=true;
        this.itemCodeShow=true;
        this.itemNameShow=true;
        this.itemPriceShow=true;
        this.numberShow=true;
        this.bzNumShow=true;
        this.jxNumShow=true;
        this.totalNumShow=false;
        this.stateShow=false;
      }
      listEquivalent(this.queryParams).then(response => {
        this.enterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        enterDate: null,
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




    /** 导出按钮操作 */
    handleExportMes() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportDetail(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
  }
};
</script>
