<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-form-item label="指标集名称" prop="disName">
            <el-input v-model="form.disName" placeholder="请输入指标集名称"  maxlength="50" show-word-limit disabled />
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-card>
              <el-form :model="queryParamsDept" ref="queryParamsDept" :inline="true" v-show="showSearchProject" label-width="100px" class="bb0">
                <el-col :span="5">
                  <el-form-item  label="待选指标" prop="">
                  </el-form-item>
                </el-col>
                <el-col :span="19">
                  <el-form-item  label="" prop="queryCode" style="float: right;">
                    <el-input
                      v-model="queryParamsDept.queryCode"
                      placeholder="输入信息（编码、名称）"
                      clearable
                      @keyup.enter.native="getListDept"
                      @input="getListDept"
                    />
                  </el-form-item>
                </el-col>
              </el-form>

              <el-table
                v-loading="deptListLoading"
                :data="deptListNoProject"
                :height="stableHeight"
                :row-class-name="tableRowClassName"
                @select="selectionProject"
                @row-click="rowClickWaitClass"
              >
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="指标编码" align="center" prop="indexCode" />
                <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
              </el-table>
              <pagination
                :total="totalDept"
                :page.sync="queryParamsDept.pageNum"
                :limit.sync="queryParamsDept.pageSize"
                @pagination="getListDept"
              />
            </el-card>
          </el-col>
          <el-col :span="14" class="mb10">
            <el-card>
              <div ref="ssearch">
                <el-form :model="queryParamsDeptProject" ref="queryParamsDeptProject" :inline="true" v-show="showSearchProjectUser" label-width="100px" class="bb0">
                  <el-col :span="5">
                    <el-form-item  label="已选指标" prop="">
                    </el-form-item>
                  </el-col>
                  <el-col :span="19">
                    <el-form-item  label="" prop="queryCode" style="float: right;">
                      <el-input
                        v-model="queryParamsDeptProject.queryCode"
                        placeholder="输入信息（编码、名称）"
                        clearable
                        @keyup.enter.native="getListDeptProject"
                        @input="getListDeptProject"
                      />
                    </el-form-item>
                  </el-col>
                </el-form>
              </div>
              <el-table
                v-loading="deptListProjectLoading"
                :data="deptListProject"
                :height="stableHeight"
                :row-class-name="tableRowClassName"
                @row-click="rowClickHasClass"
                @select="selectionProjectDept"
              >
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="指标编码" align="center" prop="indexCode" />
                <el-table-column label="指标名称" align="left" prop="indexName" show-overflow-tooltip/>
                <el-table-column label="权重" align="center" prop="weights" >
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.weights" :precision="0" :disabled="false" controls-position="right" :min="1" :step="1" @click.native.stop />
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                :total="totalDeptProject"
                :page.sync="queryParamsDeptProject.pageNum"
                :limit.sync="queryParamsDeptProject.pageSize"
                @pagination="getListDeptProject"
              />
            </el-card>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listDistribution, getDistribution, delDistribution, addDistribution, updateDistribution, exportDistribution } from "@/api/second/distribution";
import { listDistributionEvaluate,addDistributionEvaluate } from "@/api/second/distributionDetail";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "二次分配指标集设置 / 指标设置";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "二次分配指标集设置管理-新增";
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    }
  },
  components: {
  },
  data() {
    return {
      tableHeight: "auto",
      stableHeight: "auto",
      title: "二次分配指标集设置管理-新增",
      openDialog: this.open,
      deptListLoading:false,
      deptListProjectLoading:false,
      // 显示已选科室搜索条件
      showSearchProjectUser: true,
      // 显示待选科室搜索条件
      showSearchProject: true,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
      },
      //待选科室
      queryParamsDept: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        isProject: null,
        indexCode: null,
        indexName: null,
        deposit: null,
        delivery: null,
        disCode: null,
        ratio: null,
        firstRatio: null,
        secondRatio: null,
        thirdRatio: null,
        type: null,
      },
      //已选科室
      queryParamsDeptProject: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        isProject: null,
        indexCode: null,
        indexName: null,
        deposit: null,
        delivery: null,
        disCode: null,
        ratio: null,
        firstRatio: null,
        secondRatio: null,
        thirdRatio: null,
        type: null,
      },
      // 待选科室数据
      deptListNoProject: [],
      // 已选科室数据
      deptListProject: [],
      // 待选科室总条数
      totalDept: 0,
      // 已选科室总条数
      totalDeptProject: 0,
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  methods: {
    resizeHandler() {
      let c = this.$refs.ssearch.offsetHeight;
      let sh = this.$store.state.settings.tableHeight  + c*2 + 50
      sh = sh + "px";
      this.stableHeight = `calc((100vh - ${sh})/2)`;
    },
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
    },
    // 待选科室多选框选中数据
    selectionProject(selection, row) {
      this.rowClickWaitClass(selection[0])
    },
    //待选物资类型 点击行
    rowClickWaitClass(selection){
      let {
        indexCode,
        indexName,
        type,
      } = selection;
      this.deptListProject.push({
        indexCode,
        indexName,
        type,
      });
      this.deptListNoProject.splice(selection.index, 1);
    },
    // 已选科室多选框选中数据
    selectionProjectDept(selection, row) {
      this.rowClickHasClass(selection[0])
    },
    // 已经选择点击行
    rowClickHasClass(selection){
      let {
        indexCode,
        indexName,
      } = selection;
      this.deptListNoProject.push({
        indexCode,
        indexName,
      });
      this.deptListProject.splice(selection.index, 1);
    },
  	getDetailInfo() {
      getDistribution(this.id).then(response => {
        console.log(response);
        this.form = response.data;
        this.queryParamsDept.disCode = this.form.disCode;
        this.queryParamsDeptProject.disCode = this.form.disCode;
        this.getListDeptProject();
        this.getListDept();
      });
    },
    /** 查询非用户关联仓库列表 */
    getListDept() {
      this.queryParamsDept.isProject = "2";
      if (this.queryParamsDept.disCode != null) {
        this.deptListLoading = true
        listDistributionEvaluate(this.queryParamsDept).then(response => {
          this.deptListNoProject = response.rows;
          this.totalDept = response.total;
          this.deptListLoading = false
        });
      }
    },
    /** 查询用户关联仓库列表 */
    getListDeptProject() {
      this.queryParamsDeptProject.isProject = "1";
      if (this.queryParamsDeptProject.disCode != null) {
        this.deptListProjectLoading = true;
        listDistributionEvaluate(this.queryParamsDeptProject).then(response => {
          this.deptListProject = response.rows;
          this.totalDeptProject = response.total;
          this.deptListProjectLoading = false
        });
      }
    },
    /** 查询二次分配指标集设置列表 */
    getList() {
      this.loading = true;
      listDistribution(this.queryParams).then(response => {
        this.distributionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: this.$store.state.user.userInfo.deptId,
        disCode: null,
        disName: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      let data = {
        disCode: this.form.disCode,
        pmsSecondDistributionDetails: this.deptListProject
      };
      let saveLoading = this.saveLoading()
      addDistributionEvaluate(data).then(response => {
        if (response.code == 200) {
          saveLoading.close()
          this.msgSuccess("保存成功");
          this.$emit("cancel", false);
        } else {
          saveLoading.close()
          this.msgError(response.msg);
          this.$emit("cancel", false);
        }
      }).catch(()=>{
        saveLoading.close();
      });
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 70%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
