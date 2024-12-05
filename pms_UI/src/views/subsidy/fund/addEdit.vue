<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="时间" prop="fundTime">
          <el-date-picker clearable size="small" class="w100"
                          v-model="form.fundTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="科室信息" prop="deptName">
          <el-select class="w100" v-model="form.deptName" filterable clearable placeholder="请选择科室" >
            <el-option
              v-for="item in deptList"
              :key="item.deptRoomId"
              :label="item.deptRoomName"
              :value="item.deptRoomName">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室id" prop="deptCode" v-show="false">
          <el-input v-model="form.deptCode" v-show="false" />
        </el-form-item>
        <el-form-item label="科基金金额" prop="fundMoney">
          <el-input-number controls-position="right" v-model="form.fundMoney" :precision="2" placeholder="请输入科基金金额"
                           class="w100" show-word-limit  />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" >确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { listFund, getFund, addFund, updateFund } from "@/api/subsidy/fund";
import {getYear} from "@/utils/date";
import {treeRoom} from '@/api/until';
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
    "$store.state.dicts": {
      handler(val) {
        console.log('val'+val );
        // 基础字典
        let baseDicts = val.baseDicts;
        this.deptList = val.deptInfo;
      },
      deep: true,
      immediate: true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "科基金-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "科基金-新增";
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
      title: "科基金-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      adjustTypeOptions: [],
      adjustDirectionOptions: [],
      //科室字典
      deptList: [],
      // 表单校验
      rules: {
        fundTime: [
          { required: true, message: "时间不能为空", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "科室不能为空", trigger: "blur" }
        ],
        fundMoney: [
          { required: true, message: "科基金金额不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    // this.getList();
    this.getDeptList(this.$store.state.user.userInfo.deptId)
  },
  methods: {
    getDeptList(deptId){
      let data={deptId:deptId}
      treeRoom(data).then(response=>{
        if(response.code==200){
          this.deptList=JSON.parse(JSON.stringify(response.rows))
        }
      })
    },
  	getDetailInfo() {
      getFund(this.id).then(response => {
        this.form = response.data;
      });
    },
    /** 查询科室效益调整列表 */
    // getList() {
    //   this.loading = true;
    //   listFund(this.queryParams).then(response => {
    //     this.benefitList = response.rows;
    //     this.total = response.total;
    //     this.loading = false;
    //   });
    // },
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
        deptName: null,
        deptCode: null,
        fundTime: null,
        fundMoney: null,
        fundBalance: null,
        remark: null,
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      let obj = {};
      obj = this.deptList.find((item) => {return item.deptRoomName === this.form.deptName});
      if (obj != null) {
        this.form.deptCode = obj.deptRoomId;
        this.form.deptName = obj.deptRoomName;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
        let saveLoading = this.saveLoading();
          if (this.form.id != null) {
            updateFund(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              saveLoading.close();
            });
          } else {
            addFund(this.form).then(response => {
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
    height: 50%;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
