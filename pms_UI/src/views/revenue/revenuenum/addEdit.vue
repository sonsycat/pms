<template>
    <el-dialog :title="title"  :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" v-loading="loading" :model="form" :rules="rules" label-width="100px">
        <el-row >
          <el-col :span="12">
            <el-form-item label="收入项目:" prop="revenueCode"   >
              <el-select v-model="form.revenueCode" filterable clearable placeholder="请选择收入项目" @change="changeProject">
                <el-option
                  v-for="dict in projectList"
                  :key="dict.revenueCode"
                  :label="dict.revenueName"
                  :value="dict.revenueCode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应收金额:" prop="receiveNum">
              <el-input-number controls-position="right" :min="0" :precision="2" v-model="form.receiveNum"  placeholder="请输入应收金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际金额:" prop="actualNum">
              <el-input-number controls-position="right" :min="0" :precision="2" v-model="form.actualNum"  placeholder="请输入实际金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开单科室:" prop="billDept">
              <el-select v-model="form.billDept" filterable placeholder="请选择开单科室" clearable>
                <el-option
                  v-for="dict in billDeptList"
                  :key="dict.deptRoomId"
                  :label="dict.deptRoomName"
                  :value="dict.deptRoomId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执行科室:" prop="execDept">
              <el-select v-model="form.execDept" placeholder="请选择执行科室" filterable clearable>
                <el-option
                  v-for="dict in execDeptList"
                  :key="dict.deptRoomId"
                  :label="dict.deptRoomName"
                  :value="dict.deptRoomId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="护理单元:" prop="careDept">
              <el-select v-model="form.careDept" placeholder="请选择护理单元" filterable clearable :disabled="disableCare">
                <el-option
                  v-for="dict in careDeptList"
                  :key="dict.deptRoomId"
                  :label="dict.deptRoomName"
                  :value="dict.deptRoomId"
                />
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开单医生:" prop="billDoc">
              <el-input v-model="form.billDoc" placeholder="请输入开单医生"  maxlength="20" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收入类别:" prop="revenueType">
              <el-select v-model="form.revenueType" clearable placeholder="请输入收入类别" @change="changeRevenueType">
              <el-option
                v-for="dict in revenueTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结算标识:" prop="settleMark">
            <el-select v-model="form.settleMark" clearable placeholder="请选择结算标识">
              <el-option
                v-for="dict in settleMarkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发生时间:" prop="occureTime">
              <el-date-picker clearable
                v-model="form.occureTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择发生时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注:" prop="remark">
              <el-input v-model="form.remark" type="textarea" maxlength="100" show-word-limit rows="4" placeholder="请输入内容" resize="none" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listRevenuenum, getRevenuenum, delRevenuenum, addRevenuenum, updateRevenuenum, exportRevenuenum } from "@/api/revenue/revenuenum";
import {listRevenue} from "@/api/revenue/revenue";
import {treeRoom} from '@/api/until';
import revenuePop from "@/views/components/revenuePop";
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
    },
    queryParams:{
      type:Object,
      default:null
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "收入录入管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "收入录入管理-新增";
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
    revenuePop
  },
  data() {
    return {
      disableCare: false,
      title: "收入录入管理-新增",
      openDialog: this.open,
      loading:false,
      // 表单参数
      form: {},
      revenueTypeOptions: [],
      settleMarkOptions: [],
      projectList:[],
      billDeptList:[],//开单科室
      execDeptList:[],//执行科室
      careDeptList:[],//护理单元
	    // 表单校验
      rules: {
        revenueCode: [
          { required: true, message: "收入项目不能为空", trigger: "blur" }
        ],
        revenueName: [
          { required: true, message: "收入项目不能为空", trigger: "blur" }
        ],
        receiveNum: [
          { required: true, message: "应收金额不能为空", trigger: "blur" }
        ],
        actualNum: [
          { required: true, message: "实际金额不能为空", trigger: "blur" }
        ],
        billDept: [
          { required: true, message: "开单科室不能为空", trigger: "blur" }
        ],
        execDept: [
          { required: true, message: "执行科室不能为空", trigger: "blur" }
        ],
        careDept: [
          { required: true, message: "护理单元不能为空", trigger: "blur" }
        ],
        billDoc: [
          { required: true, message: "开单医生不能为空", trigger: "blur" }
        ],
        revenueType: [
          { required: true, message: "请选择收入类别", trigger: ["blur","change"] }
        ],
        settleMark: [
          { required: true, message: "请选择结算标识", trigger: ["blur","change"] }
        ],
        occureTime: [
          { required: true, message: "请选择发生日期", trigger: ["blur","change"] }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("pms_srlb_type").then(response => {
      this.revenueTypeOptions = response.data;
    });
    this.getDicts("pms_jsbs_type").then(response => {
      this.settleMarkOptions = response.data;
    });
    this.getRevenueList();
    this.getDeptList(this.$store.state.user.userInfo.deptId);
  },
  methods: {
    changeProject(projectCode) {
      let flag = false;
      for (let i in this.projectList) {
        if (this.projectList[i].revenueCode == projectCode) {
          if (this.form.revenueType == '2') {
            if (this.projectList[i].inpCare == '0') {
              this.form.careDept = null;
              this.disableCare = true;
              flag = true;
            }
          } else {
            if (this.projectList[i].outpCare == '0') {
              this.form.careDept = null;
              this.disableCare = true;
              flag = true;
            }
          }
        }
      }
      if (flag == false) {
        this.disableCare = false;
        this.rules.careDept =  [
          { required: true, message: "护理单元不能为空", trigger: "blur" }
        ];
      } else {
        this.rules.careDept =  [
        ];
      }
    },
    changeRevenueType() {
      this.changeProject(this.form.revenueCode);
    },
  	getDetailInfo() {
      this.loading=true
      getRevenuenum(this.id).then(response => {
        console.log(response);
        this.loading=false
        this.form = response.data;
        this.disableCare = false;
        let flag = false;
        this.getRevenueList();
        listRevenue(this.queryParams).then(response=>{
          this.projectList=response.rows
          for (let i in this.projectList) {
            if (this.projectList[i].revenueCode == this.form.revenueCode) {
              if (this.form.revenueType == '2') {
                if (this.projectList[i].inpCare == '0') {
                  this.form.careDept = null;
                  this.disableCare = true;
                  flag = true;
                }
              } else {
                if (this.projectList[i].outpCare == '0') {
                  this.form.careDept = null;
                  this.disableCare = true;
                  flag = true;
                }
              }
            }
          }
          if (flag == false) {
            this.disableCare = false;
            this.rules.careDept =  [
              { required: true, message: "护理单元不能为空", trigger: "blur" }
            ];
          } else {
            this.rules.careDept =  [
            ];
          }
        })
      });
    },
    /** 查询收入录入列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeOccureTime && '' != this.daterangeOccureTime) {
        this.queryParams.params["beginOccureTime"] = this.daterangeOccureTime[0];
        this.queryParams.params["endOccureTime"] = this.daterangeOccureTime[1];
      }
      listRevenuenum(this.queryParams).then(response => {
        this.revenuenumList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取开单科室
    getDeptList(deptId){
      let data={deptId:deptId}
      treeRoom(data).then(response=>{
        if(response.code==200){
          this.billDeptList=JSON.parse(JSON.stringify(response.rows))
          this.execDeptList=JSON.parse(JSON.stringify(response.rows))
          this.careDeptList=JSON.parse(JSON.stringify(response.rows))
        }
      })
    },
    //获取收入核算收入项目列表
    getRevenueList(){
      listRevenue(this.queryParams).then(response=>{
        this.projectList=response.rows
      })
    },
    // 收入类别(1门诊，2住院)字典翻译
    revenueTypeFormat(row, column) {
      return this.selectDictLabel(this.revenueTypeOptions, row.revenueType);
    },
    // 结算标识(1收付实现，2责权发生)字典翻译
    settleMarkFormat(row, column) {
      return this.selectDictLabel(this.settleMarkOptions, row.settleMark);
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
        revenueName:null,
        receiveNum: null,
        actualNum: null,
        billDept: null,
        execDept: null,
        careDept: null,
        billDoc: null,
        execDeptName: null,
        careDeptName: null,
        billDocName: null,
        occureTime: null,
        revenueType: '1',
        settleMark: null,
        revenueKind: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    handleClickOrgPop(idx,row,datas){
      let data = ["revenueCode","revenueName","revenueType","money"];
      data.forEach(key => {
        this.$set(row, key, datas[key]);
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        let saveLoading=this.saveLoading();
          if (this.form.id != null) {
            updateRevenuenum(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close()
            });
          } else {
            addRevenuenum(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              saveLoading.close()
            });
          }
        }
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
      .el-input--mini,.el-input-number--mini,.el-select--mini{
        width: 100%;
      }
    }
  }
</style>
