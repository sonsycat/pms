<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
      <el-form-item label="科室" prop="setName">
        <el-input v-model="queryParams.deptName" class="search_w220"/>
      </el-form-item>
      <el-form-item label="周期:" prop="settleMark">
        <el-select class="search_w220" v-model="queryParams.period" clearable placeholder="请选择结算标识">
          <el-option value="年度" label="年度"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年份" prop="month">
        <el-date-picker class="search_w220" clearable type="year" v-model="queryParams.year">
          value-format="yyyy"
        </el-date-picker>
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
        deptName: '',
        period: '',
        year: '',
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
      this.queryParams.deptName = ''
      this.queryParams.period = ''
      this.queryParams.year = ''
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      let fxb = "病例难度系数分布.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb+"&deptName="+this.queryParams.deptName;
    }

  }
}
</script>
