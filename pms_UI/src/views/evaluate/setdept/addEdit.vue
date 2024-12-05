<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="年月" prop="nowDate">
              <el-date-picker class="w80" disabled clearable
                v-model="form.nowDate"
                type="month"
                value-format="yyyy-MM-dd"
                placeholder="选择年月">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="科室名称" prop="deptName">
              <el-input class="w80" v-model="form.deptName" placeholder="科室名称" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="指标集" prop="setCode">
              <el-select class="w80" :disabled="isAllowEdit" clearable filterable remote v-model="form.setCode" placeholder="请选择指标集" @change="getBSCSort()">
                <el-option
                  v-for="dict in setList"
                  :key="dict.setCode"
                  :label="dict.setName"
                  :value="dict.setCode"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-for="(item,index) in bscList" :key="index" :label="item.bscName+'总分'" :prop="item.bscCode">
              <el-input-number controls-position="right" class="w80" :min="0" v-model="item.score" :disabled="isAllowEditBsc"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import {
  listSetdept,
  getSetdept,
  delSetdept,
  addSetdept,
  updateSetdept,
  exportSetdept,
  getBSCSort
} from '@/api/evaluate/setdept'
import { listSetdeptdetail } from "@/api/evaluate/setdeptdetail";
import {getYearMonth2} from "@/utils/date";
import { listSet } from "@/api/evaluate/set";

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    datas: {
      type: [Object],
      default: null
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
          this.title = "指标集与科室管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.form.deptName=this.datas.deptName;
          this.title = "指标集与科室管理-新增";
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
      title: "单科室多指标管理-编辑",
      openDialog: this.open,
      isAllowEdit:false,
      isAllowEditBsc:false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nowDate:[
          {required:true,message:'年月不能为空',trigger:['change','blur']}
        ],
        setCode:[
          {required:true,message:'指标集不能为空',trigger:['change','blur']}
        ],
        internalScore:[
          {required:true,message:'内部管理分值不能为空',trigger:['change','blur']}
        ],
        financeScore:[
          {required:true,message:'财务管理分值不能为空',trigger:['change','blur']}
        ],
        serviceScore:[
          {required:true,message:'服务分值不能为空',trigger:['change','blur']}
        ],
        efficientScore:[
          {required:true,message:'效率分值不能为空',trigger:['change','blur']}
        ]
      },
      setList:[],
      bscList:[],
      bscScore:{},
      queryParams:{
        compCode:this.$store.state.user.userInfo.deptId,
      }
    };
  },
  created() {
    this.getChecked()
    this.getDeptList();
    this.form.nowDate = this.datas.nowDate
  },
  methods: {
    /** 指标集*/
    getDeptList() {
      this.loading = true;
      listSet(this.queryParams).then(response => {
        this.setList = response.rows;
        this.loading = false;
      });
    },
    getChecked(){
      if(this.id){
        let params={
          compCode:this.datas.compCode,
          setDeptCode:this.datas.setDeptCode,
          nowDate:this.datas.nowDate,
        }
        listSetdeptdetail(params).then(response => {
          if(response.data.length==0){
            this.isAllowEdit=false
            this.isAllowEditBsc = false;
          }else{
            this.isAllowEdit=true;
            this.isAllowEditBsc = true;
          }
        })
      }
    },
    getDetailInfo() {
      getSetdept(this.id).then(response => {
        this.form = response.data;
        this.bscList = JSON.parse(response.data.internalScore)
      });
    },
    getBSCSort(){
      getBSCSort(this.form.setCode).then(response => {
        this.bscList = response.data
      })
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
        setDeptCode: null,
        setCode: null,
        deptCode: null,
        internalScore: null,
        nowDate: getYearMonth2(),
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
      this.$refs["form"].validate(valid => {
        if (valid) {
        let saveLoading=this.saveLoading()
        this.form.compCode=this.$store.state.user.userInfo.deptId;
        this.form.deptCode=this.datas.deptCode
          this.form.internalScore = JSON.stringify(this.bscList)
          if (this.form.id != null) {
            updateSetdept(this.form).then(response => {
              this.msgSuccess("编辑成功");
              this.$emit("success", false);
              saveLoading.close()
            });
          } else {
            addSetdept(this.form).then(response => {
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
    min-height: 10%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
