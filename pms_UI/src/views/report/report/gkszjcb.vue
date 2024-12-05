<template>
  <!--全院固定资产折旧汇总表  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" :rules="rules" label-width="80px">
      <el-form-item label="报表类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择分摊级别" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年月" v-if="queryParams.type=='2'" prop="month">
        <el-date-picker clearable size="small"
                        v-model="queryParams.month"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="请输入年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="年" v-if="queryParams.type=='1'" prop="year">
        <el-date-picker clearable size="small"
                        v-model="queryParams.year"
                        type="year"
                        value-format="yyyy"
                        placeholder="请输入年">
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
        typeOptions :[],
        queryParams: {
          type : '1',
          month : null,
          year : null
        },
        rules: {
          year: [
            {
              required: true,
              message: "年不能为空",
              trigger: ["blur", "change"]
            }
          ],
          month: [
            {
              required: true,
              message: "年月不能为空",
              trigger: ["blur", "change"]
            }
          ],
        },

      };
    },
    created() {
      this.getDicts("cost_report_type").then(response => {
        this.typeOptions = response.data;
      });
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
        this.queryParams.year = null
        this.queryParams.month = null
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.$refs["queryForm"].validate(valid => {
          if (valid) {
            var fxb = "各科室直接成本表.ureport.xml";
            var fxb1 = "各科室直接成本表1.ureport.xml";
            var str;
            if(this.queryParams.type=='1'){
              this.src = "/ureport/preview?_u=mysql:"+fxb+"&dateStr="+this.queryParams.year+"&date="+this.queryParams.year+"&compCode="+this.$store.state.user.accountInfo.deptId+"&compName="+this.$store.state.user.accountInfo.deptName;
            }else if(this.queryParams.type=='2'){
              this.src = "/ureport/preview?_u=mysql:"+fxb1+"&dateStr="+this.queryParams.month+"&date="+this.queryParams.month+"&compCode="+this.$store.state.user.accountInfo.deptId+"&compName="+this.$store.state.user.accountInfo.deptName;
            }
          }
        });
      }

    }
  }
</script>
