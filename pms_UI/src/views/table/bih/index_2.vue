<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules">
      <el-form-item label="起始年月" prop="month">
        <el-date-picker style="width:250px" clearable
                        v-model="queryParams.month"
                        type="monthrange"
                        range-separator="至"
                        start-placeholder="开始月份"
                        end-placeholder="结束月份">
          value-format="yyyy-MM"
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结算标识:" prop="settleMark">
        <el-select class="search_w220" v-model="queryParams.settleMark" clearable placeholder="请选择结算标识">
          <el-option
            v-for="dict in settleMarkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="科室类型" prop="type">
        <el-select class="search_w220" v-model="queryParams.type" placeholder="请选择项目类型" clearable>
          <el-option
            v-for="dict in typeOptions"
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
    <div v-loading="loading" :style="'height:'+ height" :element-loading-text="loadingText">
      <iframe :src="src" @load="removeMask" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
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
      loadingText: '正在加载数据请稍后！',
      // 查询参数
      typeOptions :[],
      settleMarkOptions: [],
      queryParams: {
        type : '1',
        month : [new Date(),new Date()],
        year : null,
        settleMark: '1'
      },
      rules: {
      },

    };
  },
  created() {
    this.getDicts("DEPT_TYPE_OPC").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("pms_jsbs_type").then(response => {
      this.settleMarkOptions = response.data;
    });
    this.creatMes();
  },
  mounted: function() {
    // setTimeout(() => {
    //   this.loading = false;
    // }, 23000);
    const that = this;
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + "px;";
    };
  },
  methods:{
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.year = null
      this.queryParams.month = [new Date(),new Date()]
      this.queryParams.settleMark = '1'
      this.queryParams.type = '1'
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.loading = true;
      let fxb = "住院收入表.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb + '&timestamp=' + Date.now();;
    },
    creatMes(){
      let fxb = "住院收入表.ureport.xml";
      this.src = "/ureport/preview?_u=mysql:"+fxb + '&timestamp=' + Date.now();;
    },
    removeMask() {
      this.loading = false;
    },
  }
}
</script>
