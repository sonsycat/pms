<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
      <el-form-item label="时间选择" prop="month">
        <el-date-picker style="width:250px" clearable
                        v-model="queryParams.day"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始时间"
                        end-placeholder="结束时间">
          value-format="yyyy-MM-dd"
        </el-date-picker>
      </el-form-item>
      <el-form-item label="出院科室" prop="dischargeDept">
        <el-input v-model="queryParams.dischargeDept" class="search_w220"/>
      </el-form-item>
      <el-form-item label="医生姓名" prop="doctorName">
        <el-input v-model="queryParams.doctorName" class="search_w220"/>
      </el-form-item>
      <el-form-item label="病案号" prop="recordNum">
        <el-input v-model="queryParams.recordNum" class="search_w220"/>
      </el-form-item>
      <el-form-item label="诊断名称" prop="diagnosticName">
        <el-input v-model="queryParams.diagnosticName" class="search_w220"/>
      </el-form-item>
      <el-form-item label="手术名称" prop="opt">
        <el-input v-model="queryParams.opt" class="search_w220"/>
      </el-form-item>
      <el-form-item label="DRGs" prop="drgs">
        <el-input v-model="queryParams.drgs" class="search_w220"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div v-loading="loading" :style="'height:'+ height">
      <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
    </div>
  </div>
</template>

<script>
export default {
  name: "UreportDisplay",
  components: {
  },
  data() {
    return {
      src: "",
      height: document.documentElement.clientHeight - 210 + "px;",
      loading: true,
      // 查询参数
      queryParams: {
        doctorName: '',
        dischargeDept: '',
        recordNum: '',
        diagnosticName: '',
        drgs: '',
        opt: '',
      },
      rules: {
      },

    };
  },
  created() {
    this.handleQuery()
  },
  mounted: function() {
    setTimeout(() => {
      this.loading = false;
    }, 230);
    const that = this;
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + "px;";
    };
  },
  methods:{
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.doctorName = ''
      this.queryParamsdoctorName =''
      this.queryParams.dischargeDept =''
      this.queryParams.recordNum =''
      this.queryParams.diagnosticName =''
      this.queryParams.drgs =''
      this.queryParams.opt =''
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      let fxb = "入组病案.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb+"&doctorName="+this.queryParams.doctorName;
    }

  }
}
</script>
