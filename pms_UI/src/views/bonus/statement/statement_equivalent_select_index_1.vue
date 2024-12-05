<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
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
    <div v-loading="loading" :style="'height:'+ height">
      <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
    </div>
  </div>
</template>

<script>
import { getStatementEquivalentType } from "@/api/bonus/statement";

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
        dateTime: '',
        equivalentTypeCode: ''
      },
      equivalentTypeList: [],
      rules: {
      },

    };
  },
  created() {
    this.getStatementEquivalentType();
    // this.handleQuery()
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
      this.queryParams.dateTime = ''
      this.queryParams.equivalentTypeCode = ''
      this.handleQuery()
    },
    getStatementEquivalentType(){
      getStatementEquivalentType().then(response => {
        this.equivalentTypeList = response.data;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      let fxb = "当量查询报表.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.equivalentTypeCode+
        "&dateTime="+this.queryParams.dateTime+"&_i="+1;
    }

  }
}
</script>
