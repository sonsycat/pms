<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="员工编码" prop="staffCode">
          <el-input v-model="form.staffCode" placeholder="请输入员工编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="员工姓名" prop="staffName">
          <el-input v-model="form.staffName" placeholder="请输入员工姓名"  maxlength="50" show-word-limit  />
        </el-form-item>
<!--        <el-form-item label="科室编码" prop="deptCode">-->
<!--          <el-input v-model="form.deptCode" placeholder="请输入科室编码"  maxlength="50" show-word-limit  />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="科室名称" prop="deptName">-->
<!--          <el-input v-model="form.deptName" placeholder="请输入科室名称"  maxlength="50" show-word-limit  />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="员工性别" prop="sex">-->
<!--          <el-input v-model="form.sex" placeholder="请输入员工性别"  maxlength="1" show-word-limit  />-->
<!--        </el-form-item>-->
<!--        <el-col :span="12">-->

<!--        </el-col>-->
          <orgSearch label="单位" :queryParams="form" compCode="orgCode" compName="orgName"
                     @click="handleClickOrg"></orgSearch>
<!--        <el-col :span="12">-->
          <el-form-item label="科室" prop="deptCode">
            <el-select v-model="form.deptCode" placeholder="请选择科室" class="w100" filterable @change="handleChangeDeptRoom">
              <el-option
                v-for="dict in deptList"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        <el-form-item label="用户性别">
          <el-select v-model="form.sex" placeholder="请选择">
            <el-option
              v-for="dict in sexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        </el-col>-->
<!--        <el-form-item label="机构编码" prop="orgCode">-->
<!--          <el-input v-model="form.orgCode" placeholder="请输入机构编码"  maxlength="50" show-word-limit  />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="机构名称" prop="orgName">-->
<!--          <el-input v-model="form.orgName" placeholder="请输入机构名称"  maxlength="50" show-word-limit  />-->
<!--        </el-form-item>-->
        <el-form-item label="证件类型" prop="cardtype">
<!--          <el-input v-model="form.cardtype" placeholder="请输入证件类型"  maxlength="50" show-word-limit  />-->
          <el-select v-model="form.cardType" placeholder="请选择">
            <el-option
              v-for="dict in cardtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号" prop="cardNo">
          <el-input v-model="form.cardNo" placeholder="请输入证件号"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea"  />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>

import { getStaff, addStaff, updateStaff } from "@/api/system/staff";
import {getDeptByComp} from "@/api/system/dict/data";
import orgSearch from "@/views/components/orgSearch";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
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
    // 根据名称筛选部门树
    orgName(val) {
      this.$refs.tree.filter(val);
    },
    "form.deptId":{
      handler(val){
        // 获取科室数据
        this.getDeptByComp(val)
      },
      immediate:true,
      deep:true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "奖金导入-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "奖金导入-新增";
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
    TreeSelect,orgSearch
  },
  data() {
    return {
      title: "员工信息-新增",
      openDialog: this.open,
      deptList:[],
      orgName: undefined,
      // 性别状态字典
      sexOptions: [],
      cardtypeOptions: [],
      // 表单参数
      form: {},
      	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
      rules: {
        staffCode: [
          { required: true, message: "员工编码不能为空", trigger: "blur" }
        ],
        staffName: [
          { required: true, message: "员工姓名不能为空", trigger: "blur" }
        ],
        deptCode: [
          { required: true, message: "科室编码不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "科室名称不能为空", trigger: "blur" }
        ],
        cardNo: [
          {
            pattern: /(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0\d|10|11|12)([0-2]\d|30|31)\d{3}(\d|X|x)$)/,
            message: "请输入正确的证件号码",
            trigger: "blur"
          }
        ],
      }
    };
  },
  created() {
    // this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("CV02.01.101").then(response => {
      this.cardtypeOptions = response.data;
    });
  },
  methods: {

    handleClickOrg(data){
      this.deptList = [];
      this.form.deptCode = null
      this.getDeptByComp(data.orgCode)
    },
    // 获取当前登录用户单位下的科室
    getDeptByComp(compCode){
      let data = {
        compCode,
      }
      getDeptByComp(data).then(res => {
        this.deptList = res.data
      })
    },
    handleChangeDeptRoom(val){
      this.form.deptName = this.selectDictLabel(this.deptList,val);
    },
  	getDetailInfo() {
      getStaff(this.id).then(response => {
        this.form = response.data;
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
        compCode: this.$store.state.user.accountInfo.deptId,
        deptCode: null,
        deptName: null,
        oraName: null,
        oraCode: null,
        enterDate: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateStaff(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addStaff(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              saveLoading.close();
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
    }
  }
</style>
