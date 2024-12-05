<template>
  <div class="app-container">
  <el-form :model="queryParams" ref="queryForm" :inline="true"  label-width="68px">
    <el-form-item label="用户性别" prop="sex">
      <el-select v-model="queryParams.sex" placeholder="请选择用户性别" clearable size="small">
        <el-option
          v-for="dict in sexOptions"
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
  <div v-loading="loading" :style="'height:'+ height">
    <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
  </div>
  </div>
</template>

<script>
export default {
  name: "UreportDisplay",
  data() {
    return {
      src: "/ureport/preview?_u=mysql:doc.ureport.xml",
      height: document.documentElement.clientHeight - 94.5 + "px;",
      loading: true,
      // 查询参数
      queryParams: {
        sex: null
      },
      //性别字典
      sexOptions:[]
    };
  },
  created() {
    this.getDicts('sys_user_sex').then(response => {
      this.sexOptions = response.data
    })
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
      this.resetForm('queryForm')
      this.queryParams.sex = null
      this.handleQuery()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.src = "/ureport/preview?_u=mysql:doc.ureport.xml&sex="+this.queryParams.sex
    }
  }
}
</script>
