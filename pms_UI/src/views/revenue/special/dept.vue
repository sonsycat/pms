<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="75%"  custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
                  <el-col :span="10">
                    <el-form-item  label="" prop="clinicType" >
                      <el-select v-model="queryParamsDeptProject.clinicType" placeholder="请选择临床科室类别" clearable class="w100"
                                 @change="getListDeptProject">
                        <el-option
                          v-for="dict in clinicTypeOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="dict.dictValue"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="9">
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
                <el-table-column label="分摊比例" align="center" prop="ratio" >
                  <template slot-scope="scope">
                    <el-input-number :precision="2" v-model="scope.row.ratio" controls-position="right" :min="0" :max="100" @click.native.stop />
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
import {  addSpecialDept, listSpecialDept } from "@/api/revenue/specialDept";
import {  getSpecialTemp } from "@/api/revenue/specialTemp";
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
        specialCode: [
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
      isAllowSure:false,
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
        specialCode: null,
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
        specialCode: null,
        ratio: null,
        clinicType: null,
      },
      // 待选科室数据
      deptListNoProject: [],
      // 已选科室数据
      deptListProject: [],
      // 待选科室总条数
      totalDept: 0,
      // 已选科室总条数
      totalDeptProject: 0,
      //临床科室类别字典
      clinicTypeOptions:[],
    };
  },
  created() {
    this.getDicts("cost_fa_type").then(response => {
      this.projectTypeOptions = response.data;
    });
    this.getDicts("clinic_type").then(response => {
      this.clinicTypeOptions = response.data;
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
        ratio
      } = selection;
      this.deptListProject.push({
        deptCode,
        deptName,
        ratio:ratio?ratio:0
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
      getSpecialTemp(this.id).then(response => {
        this.form = response.data;
        this.queryParamsDept.specialCode = this.form.specialCode;
        this.queryParamsDeptProject.specialCode = this.form.specialCode;
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
    /** 查询非用户关联仓库列表 */
    getListDept() {
      this.queryParamsDept.isProject = "2";
      if (this.queryParamsDept.specialCode != null) {
        this.deptListLoading = true
        listSpecialDept(this.queryParamsDept).then(response => {
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
      if (this.queryParamsDeptProject.specialCode != null) {
        this.deptListProjectLoading = true;
        listSpecialDept(this.queryParamsDeptProject).then(response => {
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
        specialCode: null,
        projectName: null,
        nowDate: null,
        spell: null,
        projectType: null
      };
      this.resetForm("form");
    },
    checkedNum(){
      try {
        let num=0,num1=0,type="";
        this.deptListProject.forEach(_=>{
          type=_.deptName.split("-")[1];
          if(_.ratio == 0){
            this.msgError("已选科室分摊比例不能为0");
            throw new Error("")
          }
          if(type==' 门诊'){
            num+=_.ratio
          }
          if(type==' 住院'){
            num1+=_.ratio
          }
        })
        if(num>100){
          this.msgError("门诊已选科室分摊比例的总和应小于或等于100");
          this.isAllowSure=false
        }
        if(num1>100){
          this.msgError("住院已选科室分摊比例的总和应小于或等于100");
          this.isAllowSure=false
        }
        if(num<=100 && num1<=100){
          this.isAllowSure=true
        }
      }catch (e) {
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.checkedNum()
      let data = {
        specialCode: this.form.specialCode,
        deptList: this.deptListProject
      };
      if(this.isAllowSure){
        let saveLoading = this.saveLoading()
        addSpecialDept(data).then(response => {
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
      }
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
