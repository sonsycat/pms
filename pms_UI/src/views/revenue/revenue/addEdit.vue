<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
         <el-row>
           <el-col :span="24">
             <p class="lit-title" >基本信息</p>
           </el-col>
          <el-col :span="10">
            <el-form-item label="项目编码" prop="revenueCode">
            <el-input v-model="form.revenueCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
          </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="项目名称" prop="revenueName">
              <el-input v-model="form.revenueName" placeholder="请输入项目名称"  maxlength="100" show-word-limit @blur="getPym"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="拼音码" prop="spell">
            <el-input v-model="form.spell" :placeholder="pym"  maxlength="30" disabled />
          </el-form-item>
          </el-col>
          <el-col :span="24">
            <p class="lit-title" >分摊比例</p>
          </el-col>
          <el-col :span="12">
            <el-form-item label="住院开单(%)" prop="inpBill">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.inpBill" placeholder="请输入住院开单(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门诊开单(%)" prop="outpBill">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.outpBill" placeholder="请输入门诊开单(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="住院执行(%)" prop="inpExec">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.inpExec" placeholder="请输入住院执行(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门诊执行(%)" prop="outpExec">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.outpExec" placeholder="请输入门诊执行(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="住院护理(%)" prop="inpCare">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.inpCare" placeholder="请输入住院护理(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门诊护理(%)" prop="outpCare">
              <el-input-number controls-position="right" :disabled="checkedNumFlag2" :precision="2" v-model="form.outpCare" placeholder="请输入门诊护理(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <p class="lit-title" >其他信息</p>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合作医疗(%)" prop="coopMed">
              <el-input-number controls-position="right" :precision="2" v-model="form.coopMed" placeholder="请输入合作医疗(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收入类别" prop="hisRevenumTypede">
              <el-select v-model="form.hisRevenumTypede" :filterable="true" :remote="true" clearable placeholder="请选择收入类别">
                <el-option
                  v-for="dict in hisRevenumTypedeOptions"
                  :key="dict.revenueCode"
                  :label="dict.revenueName"
                  :value="dict.revenueCode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="核算类型" prop="accountType">
              <el-select v-model="form.accountType" :filterable="true" :remote="true" clearable placeholder="请选择核算类型">
                <el-option
                  v-for="dict in accountTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="固定折算(%)" prop="fixConv">
              <el-input-number controls-position="right" :precision="2" v-model="form.fixConv" placeholder="请输入固定折算(%)" :min="0" :max="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="成本对照" prop="projectCode">
              <el-select v-model="form.projectCode" :filterable="true" :remote="true" clearable placeholder="请选择成本对照">
                <el-option
                  v-for="dict in projectCodeOptions"
                  :key="dict.projectCode"
                  :label="dict.projectName"
                  :value="dict.projectCode"
                ></el-option>
              </el-select>
            </el-form-item>
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
import { validEmail } from'@/utils/validate';
import { listRevenue, getRevenue, delRevenue, addRevenue, updateRevenue, exportRevenue } from "@/api/revenue/revenue";
import { listProject } from "@/api/project/project";
import pinyin from "js-pinyin";
import { listIncome } from "@/api/basic/income";
import {getConfigKeyApi} from "@/api/system/config";

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
          this.title = "收入核算方案管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "收入核算方案管理-新增";
          this.pym = "系统自动生成";
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
      title: "收入核算方案管理-新增",
      openDialog: this.open,
      checkedNum:null,
      checkedNumFlag:false,
      checkedNumFlag1:false,
      checkedNumFlag2:false,
      hisRevenumTypedeOptions:[],//收入类别
      projectCodeOptions:[],//成本项目
      // 表单参数
      form: {},
      pym: "",
      accountTypeOptions: [],
	  	// 表单校验
      rules: {
        revenueName:[
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        inpBill:[
          { required: true, message: "住院开单比例不能为空", trigger: ["blur","change"] }
        ],
        outpBill:[
          { required: true, message: "门诊开单比例不能为空", trigger: ["blur","change"] }
        ],
        inpExec:[
          { required: true, message: "住院执行比例不能为空", trigger: ["blur","change"] }
        ],
        outpExec:[
          { required: true, message: "门诊执行比例不能为空", trigger: ["blur","change"] }
        ],
        inpCare:[
          { required: true, message: "出院护理比例不能为空", trigger: ["blur","change"] }
        ],
        outpCare:[
          { required: true, message: "门诊护理比例不能为空", trigger: ["blur","change"] }
        ],
        coopMed:[
          { required: true, message: "合作医疗比例不能为空", trigger: ["blur","change"] }
        ],
        hisRevenumTypede:[
          { required: true, message: "请选择收入类别", trigger: "blur" }
        ],
        accountType:[
          { required: true, message: "请选择核算类型", trigger: "blur" }
        ],
        // fixConv:[
        //   { required: true, message: "固定折算比例不能为空", trigger: "blur" }
        // ],
        // projectCode:[
        //   { required: true, message: "请选择成本对照", trigger: "blur" }
        // ],
      },
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode:this.$store.state.user.userInfo.deptId,
      }
    };
  },
  created() {
    this.getProjectList()
    this.getHisType()
    this.getList();
    this.getDicts("pms_hs_type").then(response => {
      this.accountTypeOptions = response.data;
    });
    getConfigKeyApi("SFSRSBFT").then(res=>{
      this.checkedNum=res
      if (this.checkedNum == '0') {
        this.form.inpBill = '100';
        this.form.inpExec = '100';
        this.form.inpCare = '0';
        this.form.outpBill = '100';
        this.form.outpExec = '100';
        this.form.outpCare = '0';
        this.checkedNumFlag2 = true;
      }
    })
  },
  methods: {
  	getDetailInfo() {
      getRevenue(this.id).then(response => {
        this.form = response.data;
      });
    },
    /** 查询收入核算方案列表 */
    getList() {
      this.loading = true;
      listRevenue(this.queryParams).then(response => {
        this.revenueList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //his收入类别
    getHisType(){
      listIncome(this.queryParams).then(res=>{
        if(res.code==200){
          this.hisRevenumTypedeOptions=res.rows
        }
      })
    },
    /** 查询分摊方案管理列表 */
    getProjectList() {
      let params=Object.assign({
        isLast:0,
        ...this.queryParams,
      })
      listProject(params).then(response => {
        this.projectCodeOptions = response.rows;
      });
    },
    // 核算类型字典翻译
    accountTypeFormat(row, column) {
      return this.selectDictLabel(this.accountTypeOptions, row.accountType);
    },
    // 获取拼音码
    getPym() {
      // 全是字母的情况下,拼音码等于所有字母
      if (/^[a-zA-Z]*$/.test(this.form.revenueName)) {
        this.form.spell = this.form.revenueName;
        return;
      }
      // 有汉字的情况下,转为首字母
      let fullName = pinyin.getCamelChars(this.form.revenueName).toUpperCase();
      let pym = "";
      for (var i = 0; i < fullName.length; i++) {
        var c = fullName.charAt(i);
        if (/^[A-Z]+$/.test(c)) {
          pym += c;
        }
      }
      this.form.spell = pym;
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
        accountCode: null,
        revenueCode: null,
        revenueName: null,
        spell: null,
        inpBill: null,
        inpExec: null,
        inpCare: null,
        outpBill: null,
        outpExec: null,
        outpCare: null,
        coopMed: null,
        accountType: null,
        convRatio: null,
        fixConv: null,
        projectCode: null,
        projectName: null,
        hisRevenumTypede: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    checkedCount(){
      let inpNum,
          outpNum,
          unNum;
      if(this.checkedNum==1){
        inpNum=this.form.inpBill + this.form.inpExec + this.form.inpCare
        outpNum=this.form.outpBill + this.form.outpExec + this.form.outpCare
        if(inpNum==100 && outpNum==100){
          this.checkedNumFlag=true
        }else{
          this.checkedNumFlag=false
          this.msgError("分摊比例设置系统参数设置为否，门诊和住院开单+执行+护理应分别为100，请重新设置分摊比例")
        }
      }else{
        // unNum=this.form.inpBill + this.form.outpBill + this.form.inpExec + this.form.outpExec
        // if(unNum==100){
          this.checkedNumFlag=true
        // }else{
        //   this.checkedNumFlag=false
        //   this.msgError("分摊比例设置系统参数设置值为是，门诊和住院开单+执行应为100，请重新设置分摊比例")
        // }
      }
    },
    checkedCountOther(){
      if(this.form.fixConv && this.form.fixConv>0){
        this.rules.projectCode=[
            { required: true, message: "请选择成本对照", trigger: "blur" }
          ]
      }
      let sum;
      sum=this.form.coopMed + this.form.fixConv
      if(sum<=100){
        this.checkedNumFlag1=true
      }else{
        this.checkedNumFlag1=false
        this.msgError("合作医疗与固定折算总和应小于或等于100，请重新设置比例")
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.checkedCount();
      if(this.checkedNumFlag){
        this.checkedCountOther()
        if(this.checkedNumFlag1){
          this.$refs["form"].validate(valid => {
            if (valid) {
              let saveLoading=this.saveLoading();
                if (this.form.id != null) {
                  updateRevenue(this.form).then(response => {
                    this.msgSuccess("修改成功");
                    this.$emit("success", false);
                    saveLoading.close()
                  });
                } else {
                  addRevenue(this.form).then(response => {
                    this.msgSuccess("新增成功");
                    this.$emit("success", false);
                    saveLoading.close()
                  });
                }
            }
          });
        }
      }
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
      .el-select{
        width: 70%;
      }
      .el-input-number--mini{
        width: 70% !important;
      }
    }
    .lit-title{
      font-size: 16px;
      padding-bottom: 8px;
      border-bottom: 1px dashed #cccccc;
    }
  }
</style>
