<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
      <el-form-item label="医生" prop="setName">
        <el-input v-model="queryParams.doctorName" class="search_w220"/>
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
        doctorName: ''
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
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      let fxb = "医生超标费用.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb+"&doctorName="+this.queryParams.doctorName;
    }

  }
}
</script>
