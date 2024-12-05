<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="科室编码" prop="deptRoomCode">
          <el-input v-model="form.deptRoomCode" readonly placeholder="请输入科室编码"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="科室名称" prop="deptRoomName">
          <el-input v-model="form.deptRoomName" readonly placeholder="请输入科室名称"  maxlength="50" show-word-limit  />
        </el-form-item>
        <el-form-item label="核算科室" prop="parentCode">
          <el-select v-model="form.parentCode" filterable placeholder="请选择科室" @change="changeParentDept" style="width: 100%">
            <el-option
              v-for="room in accountDeptList"
              :key="room.deptRoomCode"
              :label="room.deptRoomName"
              :value="room.deptRoomCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否核算" prop="isAccount">
          <el-select v-model="form.isAccount" placeholder="请选择是否核算" style="width: 100%" disabled>
            <el-option
              v-for="dict in isAccountOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listAccountDept, getAccountDept, editAccountDept} from "@/api/bonus/accountDept";
import {getYearMonth2} from "@/utils/date";

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
    name: {
      type: [String, Number],
      default: ""
    },
    nowDate: {
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
          this.title = "核算科室设置管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "核算科室设置管理-新增";
        }
      },
      deep: true,
      immediate: true
    },
    name: {
      handler(val) {
        this.form.deptRoomName = val;
        // this.deptRoomName = val;
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
    nowDate: {
      handler(val) {
        this.nowDate = val;
      },
      deep: true
    }
  },
  components: {
  },
  data() {
    return {
      title: "核算科室设置管理-新增",
      openDialog: this.open,
      saveLoading: false,
      parentCode: '',
      deptRoomName: '',
      accountDeptList: [],
      isAccountOptions: [],
      queryParams: {
        compCode: this.$store.state.user.userInfo.deptId,
        nowDate: getYearMonth2() + '-01',
        dataType: 2
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentCode: [
          { required: true, message: "核算科室不能为空", trigger: "blur" }
        ],
        isAccount: [
          { required: true, message: "是否核算不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("yes_or_no").then(response => {
      this.isAccountOptions = response.data;
    });
  },
  methods: {
  	getDetailInfo() {
      getAccountDept(this.id, this.nowDate).then(response => {
        if(response.data != null){
          this.form.parentCode = response.data.parentCode;
          this.form.isAccount = response.data.isAccount;
        }
        this.form.deptRoomCode = this.id;
        this.form.nowDate = this.nowDate;
      });
    },
    /** 查询核算科室设置列表 */
    getList() {
      this.loading = true;
      this.queryParams.nowDate = this.nowDate;
      listAccountDept(this.queryParams).then(response => {
        this.accountDeptList = response;
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
        parentCode: null,
        deptRoomCode: null,
        deptRoomName: null,
        isAccount: null,
        nowDate: null,
        compCode: null,
        accountCode: null
      };
      this.resetForm("form");
    },
    changeParentDept(value){
      if(value == this.form.deptRoomCode){
        this.form.isAccount = '0';
      }else{
        this.form.isAccount = '1';
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.saveLoading = true;
          editAccountDept(this.form).then(response => {
            this.msgSuccess("修改成功");
            this.$emit("success", false);
            this.saveLoading = false;
          }).catch((err)=>{
            this.saveLoading = false;
          });
        }
      });
    },
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 40%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
