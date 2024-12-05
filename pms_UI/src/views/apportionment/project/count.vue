<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="75%"  custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-form-item label="方案名称" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入方案名称"  maxlength="50" show-word-limit @blur="getPym" disabled />
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-card>
              <el-form :model="queryParamsDept" ref="queryParamsDept" :inline="true" v-show="showSearchProject" label-width="100px" class="bb0">
                <el-col :span="5">
                  <el-form-item  label="待选科室" prop="">
                  </el-form-item>
                </el-col>
                <el-col :span="19">
                  <el-form-item  label="" prop="queryCode" style="float: right;" label-width="0px">
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
                <el-table-column label="科室编码" align="center" prop="deptCode" />
                <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
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
                    <el-form-item  label="已选科室" prop="">
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
                <el-table-column label="科室编码" align="center" prop="deptCode" />
                <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                <el-table-column label="计算计入分摊比例" align="center" prop="ratio" >
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.ratio" controls-position="right" :min="0" :max="1" :step="0.1" @click.native.stop />
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
import { listProject, getProject, delProject, addProjectDept, updateProject, listProjectDept2 } from "@/api/apportionment/project";
import pinyin from "js-pinyin";

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
          this.title = "分摊方案管理-科室设置";
          this.getDetailInfo();
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
    },
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  components: {
  },
  data() {
    return {
      tableHeight: "auto",
      stableHeight: "auto",
      title: "分摊方案管理-新增",
      openDialog: this.open,
      deptListProjectLoading:false,
      deptListLoading:false,
      pym: "",
      // 显示已选科室搜索条件
      showSearchProjectUser: true,
      // 显示待选科室搜索条件
      showSearchProject: true,
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  		  projectTypeOptions: [],
	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        projectCode: [
          { required: true, message: "方案编码不能为空", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "方案名称不能为空", trigger: "blur" }
        ],
        nowDate: [
          { required: true, message: "当前年月不能为空", trigger: "blur" }
        ],
        projectType:[
          { required: true, message: "方案类型不能为空", trigger: "blur" }
        ],
      },
      //待选科室
      queryParamsDept: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        isProject: null,
        deptCode: null,
        deptName: null,
        deposit: null,
        delivery: null,
        projectCode: null,
        ratio: null,
      },
      //已选科室
      queryParamsDeptProject: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        isProject: null,
        deptCode: null,
        deptName: null,
        deposit: null,
        delivery: null,
        projectCode: null,
        ratio: null,
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
    this.getDicts("cost_fa_type").then(response => {
      this.projectTypeOptions = response.data;
    });
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
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
      console.log("height",this.stableHeight)
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
        deptCode,
        deptName,
      } = selection;
      this.deptListProject.push({
        deptCode,
        deptName,
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
        deptCode,
        deptName,
      } = selection;
      this.deptListNoProject.push({
        deptCode,
        deptName,
      });
      this.deptListProject.splice(selection.index, 1);
    },
  	getDetailInfo() {
      getProject(this.id).then(response => {
        this.form = response.data;
        this.queryParamsDept.projectCode = this.form.projectInCode;
        this.queryParamsDeptProject.projectCode = this.form.projectInCode;
        this.getListDeptProject();
        this.getListDept();
      });
    },
    // 获取拼音码
    getPym() {
      // 全是字母的情况下,拼音码等于所有字母
      if (/^[a-zA-Z]*$/.test(this.form.projectName)) {
        this.form.spell = this.form.projectName;
        return;
      }
      // 有汉字的情况下,转为首字母
      let fullName = pinyin.getCamelChars(this.form.projectName).toUpperCase();
      let pym = "";
      for (var i = 0; i < fullName.length; i++) {
        var c = fullName.charAt(i);
        if (/^[A-Z]+$/.test(c)) {
          pym += c;
        }
      }
      this.form.spell = pym;
    },
    /** 查询分摊方案管理列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询非用户关联仓库列表 */
    getListDept() {
      this.queryParamsDept.isProject = "2";
      if (this.queryParamsDept.projectCode != null) {
        this.deptListLoading = true
        listProjectDept2(this.queryParamsDept).then(response => {
          this.deptListNoProject = response.rows;
          console.log(this.deptListNoProject, "store");
          this.totalDept = response.total;
          this.deptListLoading = false
        });
      }
    },
    /** 查询用户关联仓库列表 */
    getListDeptProject() {
      this.queryParamsDeptProject.isProject = "1";
      if (this.queryParamsDeptProject.projectCode != null) {
        this.deptListProjectLoading = true;
        listProjectDept2(this.queryParamsDeptProject).then(response => {
          this.deptListProject = response.rows;
          console.log(this.deptListProject, "deptProject");
          this.totalDeptProject = response.total;
          this.deptListProjectLoading = false
        });
      }
    },
    // 方案类型字典翻译
    projectTypeFormat(row, column) {
      return this.selectDictLabel(this.projectTypeOptions, row.projectType);
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
        compCode: this.$store.state.user.accountInfo.deptId,
        accountCode: null,
        projectCode: null,
        projectName: null,
        nowDate: null,
        spell: null,
        projectType: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      // let num = 0;
      // for (let i=0; i<this.deptListProject.length; i++){
      //   num += this.deptListProject[i].ratio;
      // }
      // if (num != 1) {
      //   this.msgError("分摊比例加起来不为1");
      //   return true;
      // }
          let data = {
            projectCode: this.form.projectInCode,
            deptList: this.deptListProject
          };
          let saveLoading = this.saveLoading()
          addProjectDept(data).then(response => {
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
    min-height: 20%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
