<template>
  <!--全院固定资产折旧汇总表  -->
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
    <div v-loading="loading" :style="'height:'+ height">
      <iframe :src="src" frameborder="no" style="width: 100%;height: 100%" scrolling="auto" />
    </div>
  </div>
</template>

<script>

  import {getYear} from "@/utils/date";

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
      this.getDicts("cost_dept_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("pms_jsbs_type").then(response => {
        this.settleMarkOptions = response.data;
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
        this.queryParams.month = [new Date(),new Date()]
        this.queryParams.settleMark = '1'
        this.queryParams.type = '1'
      },
      /** 搜索按钮操作 */
      handleQuery() {
        var nowDate = this.queryParams.month[0];
        if (nowDate.length != 10) {
          var year = nowDate.getFullYear();
          var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
          var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
          this.queryParams.month[0] = year + "-" + month + "-" + day;
          nowDate = this.queryParams.month[1];
          var year = nowDate.getFullYear();
          var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
          var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
          this.queryParams.month[1] = year + "-" + month + "-" + day;
        }
        console.log(this.queryParams.month[0].length)
        console.log(this.queryParams.month[1].length)
        this.$refs["queryForm"].validate(valid => {
          if (valid) {
            var fxb = "成本汇总表.ureport.xml";
            this.src = "/ureport/preview?_u=mysql:"+fxb+"&dateStr="+this.queryParams.year+"&date="+this.queryParams.year
              +"&compCode=" +this.$store.state.user.accountInfo.deptId+"&compName="+this.$store.state.user.accountInfo.deptName
              +"&type="+this.queryParams.type
              +"&startTime="+this.queryParams.month[0] +"&endTime="+this.queryParams.month[1];
          }
        });
      }

    }
  }
</script>
