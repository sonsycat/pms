<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
      <el-form-item label="时间选择" prop="dateTime">
        <el-date-picker style="width:250px" clearable
                        v-model="queryParams.dateTime"
                        type="monthrange"
                        range-separator="至"
                        start-placeholder="开始月份"
                        end-placeholder="结束月份">
          value-format="yyyy-MM"
        </el-date-picker>
      </el-form-item>
      <el-form-item label="类别名称" prop="typeNameCode">
        <el-select class="search_w220" v-model="queryParams.labourTypeCode" filterable clearable placeholder="请选择类别名称" >
          <el-option
            v-for="item in labourTypeList"
            :key="item.labourTypeCode"
            :label="item.labourTypeName"
            :value="item.labourTypeCode"
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
import { getStatementLabourType } from "@/api/bonus/statement";

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
        dateTime:  [new Date(),new Date()],
        labourTypeCode: '1'
      },
      labourTypeList: [],
      rules: {
      },

    };
  },
  created() {
    this.getStatementLabourType();
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
      this.queryParams.dateTime =  [new Date(),new Date()],
      this.queryParams.labourTypeCode = '1'
      this.handleQuery()
    },
    getStatementLabourType(){
      getStatementLabourType().then(response => {
        this.labourTypeList = response.data;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      var startTime = null;
      let endTime = null;
      var nowDate = this.queryParams.dateTime[0];
      if (nowDate.length != 10) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        startTime = year + month ;
        nowDate = this.queryParams.dateTime[1];
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
        endTime = year + month ;
      }

      if(this.queryParams.labourTypeCode == 1){
        let fxb = "手术费导管费治疗费劳务数据报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;      }else if(this.queryParams.labourTypeCode == 2){
        let fxb = "门诊住院各项劳务数据查询报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;      }else if(this.queryParams.labourTypeCode == 3){
        let fxb = "中医科各项数据查询报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;      }else if(this.queryParams.labourTypeCode == 4){
        let fxb = "骨科急诊清创数据查询报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;      }else if(this.queryParams.labourTypeCode == 5){
        let fxb = "妇科门诊超劳务数据查询报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;      }else if(this.queryParams.labourTypeCode == 6){
        let fxb = "名医门诊数据查询报表.ureport.xml";
        this.src = "/ureport/preview?_u=mysql:"+fxb+"&typeCode="+this.queryParams.labourTypeCode+
          "&startTime="+startTime+"&endTime="+endTime;
      }
    }

  }
}
</script>
