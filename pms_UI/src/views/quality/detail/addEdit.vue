<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="60%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="36">
          <el-col :span="12">
            <el-form-item label="指标名称" prop="indexName">
              <el-input disabled v-model="form.indexName" placeholder="请输入指标名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="主管部门" prop="deptName">
              <el-select disabled clearable remote v-model="form.deptName" placeholder="请选择主管部门" class="w100" filterable>
                <el-option
                  v-for="dict in deptList"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="指标分值" prop="indexScore">
              <el-input-number disabled controls-position="right" :min="0" :max="100" :precision="2" v-model="form.indexScore" placeholder="请输入指标分值" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="考评内容" prop="evaCont">
              <el-input v-model="form.evaCont" type="textarea" rows="4" maxlength="100" show-word-limit placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="考评标准" prop="evaCriteria">
              <el-input v-model="form.evaCriteria" type="textarea" rows="4" maxlength="100" show-word-limit placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="考评办法" prop="evaMethod">
              <el-input v-model="form.evaMethod" type="textarea" rows="4" maxlength="100" show-word-limit placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否KPI">
              <el-radio-group v-model="form.isKpi">
                <el-radio
                  v-for="dict in isKpiOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate';
// import { treeRoom } from "@/api/until";
import {getDeptByComp} from "@/api/system/dict/data";
import { listDetail, getDetail, delDetail, addDetail, updateDetail, exportDetail } from "@/api/quality/detail";

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
    indexCodeDatas:{
      type:Array,
      default:""
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        this.form.indexCode=this.indexCodeDatas[0].indexCode;
        this.form.indexName=this.indexCodeDatas[0].indexName;
        this.form.deptName=this.indexCodeDatas[0].deptName;
        this.form.indexScore=this.indexCodeDatas[0].indexScore;
        if (val) {
          this.id = val;
          this.title = "质量考评指标明细管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "质量考评指标明细管理-新增";
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
  },
  components: {
  },
  data() {
    return {
      title: "质量考评指标明细管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      isKpiOptions: [],
      deptList:[],
	  	// 表单校验
      rules: {
        evaCont:[
          { required: true, message: "考评内容不能为空", trigger: "blur" }
        ],
        evaCriteria:[
          { required: true, message: "考评指标不能为空", trigger: "blur" }
        ],
        evaMethod:[
          { required: true, message: "考评办法不能为空", trigger: "blur" }
        ],

      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 30,
        compCode:this.$store.state.user.userInfo.deptId,
        indexCode: null,
        evaCont: null,
        evaCriteria: null,
        evaMethod: null,
        isKpi: null,
      },
    };
  },
  created() {
    this.getDicts("hr_is_change").then(response => {
      this.isKpiOptions = response.data;
    });
    this.getDeptByComp();
  },
  methods: {
    // 获取当前登录用户单位下的科室
    getDeptByComp(compCode){
      let data = {
        compCode:this.$store.state.user.userInfo.deptId,
      }
      getDeptByComp(data).then(res => {
        this.deptList = res.data
      })
    },
  	getDetailInfo() {
      getDetail(this.id).then(response => {
        this.form = response.data;
      });
    },
    // 是否KPI字典翻译
    isKpiFormat(row, column) {
      return this.selectDictLabel(this.isKpiOptions, row.isKpi);
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
        deptName:null,
        deptCode:null,
        indexCode: null,
        indexName:null,
        indexScore:null,
        evaCont: null,
        evaCriteria: null,
        evaMethod: null,
        isKpi: "0",
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
          this.saveLoading = true;
          if (this.form.id != null) {
            updateDetail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.saveLoading = false;
            });
          } else {
            addDetail(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              this.saveLoading = false;
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
      .el-input-number--mini{
        width: 100%;
      }
    }
  }
</style>