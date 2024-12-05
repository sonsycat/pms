<template>
<el-dialog custom-class="el-button--primary" :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" append-to-body @close="handleDialogClose">
  <el-form ref="form" :model="form" :rules="rules" label-width="120px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="分类编码" prop="kindCode">
          <el-input v-model="form.kindCode" placeholder="系统自动生成" disabled/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="分类名称" prop="kindName">
          <el-input v-model="form.kindName" placeholder="请输入分类名称"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="上级分类" prop="parentCode">
          <!-- <el-input v-model="form.parentCode" placeholder="请输入上级分类"/> -->
          <treeselect @select="handleTreeSelect"  :normalizer="normalizer"  v-model="form.parentCode" :show-count="true" :options="assetsKindOpts" placeholder="请选择上级分类" no-options-text="暂无数据"/>

        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="资产类型" prop="assetType">
          <el-select v-model="form.assetType" placeholder="请选择资产类型" class="w100">
            <el-option
              v-for="dict in assetTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否计量" prop="isMeasure">
          <el-select v-model="form.isMeasure" placeholder="请选择是否计量" class="w100">
            <el-option
              v-for="dict in isMeasureOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否折旧" prop="isDepreciation">
          <el-select v-model="form.isDepreciation" placeholder="请选择是否折旧" class="w100" @change="handleChangeIsDep">
            <el-option
              v-for="dict in isDepreciationOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="折旧年限(院)" :prop="form.isDepreciation == '0'?'yearsInstitutes':''">
          <el-input v-model="form.yearsInstitutes" type="number" :min="0" placeholder="请输入折旧年限(院)" :disabled="form.isDepreciation != '0'"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="折旧年限(科)" :prop="form.isDepreciation == '0'?'yearsDept':''">
          <el-input v-model="form.yearsDept" type="number" :min="0" placeholder="请输入折旧年限(科)" :disabled="form.isDepreciation != '0'"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="折旧方法" :prop="form.isDepreciation == '0'?'depreciationWay':''">
          <el-select v-model="form.depreciationWay" placeholder="请选择折旧方法" class="w100" :disabled="form.isDepreciation != '0'">
            <el-option
              v-for="dict in depreciationWayOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否分摊" prop="isCost">
          <el-select v-model="form.isCost" placeholder="请选择是否分摊" class="w100">
            <el-option
              v-for="dict in isCostOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否末级" prop="isLast">
          <el-select v-model="form.isLast" placeholder="请选择是否末级" class="w100">
            <el-option
              v-for="dict in isLastOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="是否停用" prop="isStop">
          <el-select v-model="form.isStop" placeholder="请选择是否停用" class="w100">
            <el-option
              v-for="dict in isStopOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import {
  listAssetskind,
  getAssetskind,
  delAssetskind,
  addAssetskind,
  updateAssetskind,
  exportAssetskind
} from "@/api/assets/assetskind";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "addEdit",
  components: { Treeselect },
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    },
    proclnsIds: {
      type: [String, Number],
      default: ""
    }
  },
  data() {
    return {
      title: "固定资产分类 / 新增",
      people: [],
      openDialog: this.open,
      form: {
        id: null,
        kindCode: null,
        kindName: null,
        speel: null,
        parentCode: null,
        assetType: null,
        isMeasure: "1",
        isDepreciation: "0",
        yearsInstitutes: null,
        yearsDept: null,
        depreciationWay: null,
        ancestors: null,
        isCost: "1",
        isLast: "0",
        isStop: "1"
      },
      popoverD: false,
      // 使用日期范围
      probationRange: [],
      rules: {
        kindName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        assetType: [
          { required: true, message: "资产类型不能为空", trigger: "change" }
        ],
        isDepreciation: [
          { required: true, message: "是否折旧不能为空", trigger: "change" }
        ],
        yearsInstitutes: [
          { required: true, message: "折旧年限(院)不能为空", trigger: "blur" }
        ],
        yearsDept: [
          { required: true, message: "折旧年限(科)不能为空", trigger: "blur" }
        ],
        depreciationWay: [
          { required: true, message: "折旧方法不能为空", trigger: "change" }
        ],
        isLast: [
          { required: true, message: "是否末级不能为空", trigger: "change" }
        ],
        isStop: [
          { required: true, message: "是否停用不能为空", trigger: "change" }
        ]
      },
      // 资产类型字典
      assetTypeOptions: [],
      // 是否计量字典
      isMeasureOptions: [],
      // 是否折旧字典
      isDepreciationOptions: [],
      // 折旧方法字典
      depreciationWayOptions: [],
      // 是否分摊字典
      isCostOptions: [],
      // 是否末级字典
      isLastOptions: [],
      // 是否停用字典
      isStopOptions: [],
      assetsKindOpts: []
    };
  },
  computed: {},
  watch: {
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.assetTypeOptions = baseDicts.hrAssetType;
        this.isMeasureOptions = baseDicts.hrIsChange;
        this.isDepreciationOptions = baseDicts.hrIsChange;
        this.depreciationWayOptions = baseDicts.hrAssetDepreciationPlan;
        this.isCostOptions = baseDicts.hrIsChange;
        this.isLastOptions = baseDicts.hrIsChange;
        this.isStopOptions = baseDicts.hrIsChange;
        // 特殊
        // this.supplierOptions = val.supplierInfo;
        // this.userOptions = val.userOptsInfo;
      },
      deep: true,
      immediate: true
    },
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "固定资产分类 / 修改";
          this.getDetailInfo(val);
        } else {
          this.id = "";
          this.form.id = null;
          this.title = "固定资产分类 / 新增";
          this.popoverD = false;
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
  mounted() {
    this.initDic();
  },

  methods: {
    handleTreeSelect(val) {
      this.form.depreciationWay = val.depreciationWay;
      this.form.assetType = val.assetType;
    },
    handleChangeIsDep(val) {
      if (val == 0) {
        this.form.yearsInstitutes = "";
        this.form.yearsDept = "";
      } else {
        this.form.yearsInstitutes = 0;
        this.form.yearsDept = 0;
      }
    },
    normalizer(node) {
      return {
        id: node.kindCode,
        label: node.kindName,
        children: node.children
      };
    },
    getDetailInfo(id) {
      this.reset();
      getAssetskind(id).then(response => {
        if (response.code == 200) {
          this.popoverD = true;
          this.form = response.data;
          if (this.form.parentCode == "0") {
            this.form.parentCode = null;
          }
        } else {
          this.msgError(response.msg);
        }
      });
    },
    reset() {
      this.resetForm("form");
    },
    initDic() {
      listAssetskind().then(response => {
        console.log(response, "response");
        if (response.code == 200) {
          let data = response.rows;
          this.assetsKindOpts = this.handleTree(data, "kindCode", "parentCode");
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // 资产类型字典翻译
    assetTypeFormat(row, column) {
      return this.selectDictLabel(this.assetTypeOptions, row.assetType);
    },
    // 是否计量字典翻译
    isMeasureFormat(row, column) {
      return this.selectDictLabel(this.isMeasureOptions, row.isMeasure);
    },
    // 是否折旧字典翻译
    isDepreciationFormat(row, column) {
      return this.selectDictLabel(
        this.isDepreciationOptions,
        row.isDepreciation
      );
    },
    // 折旧方法字典翻译
    depreciationWayFormat(row, column) {
      return this.selectDictLabel(
        this.depreciationWayOptions,
        row.depreciationWay
      );
    },
    // 是否分摊字典翻译
    isCostFormat(row, column) {
      return this.selectDictLabel(this.isCostOptions, row.isCost);
    },
    // 是否末级字典翻译
    isLastFormat(row, column) {
      return this.selectDictLabel(this.isLastOptions, row.isLast);
    },
    // 是否停用字典翻译
    isStopFormat(row, column) {
      return this.selectDictLabel(this.isStopOptions, row.isStop);
    },
    // 提交
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAssetskind(this.form).then(response => {
              if (response.code == "200") {
                this.msgSuccess("修改成功!");
                this.$emit("success", false);
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addAssetskind(this.form).then(response => {
              if (response.code == "200") {
                this.msgSuccess("新增成功!");
                this.$emit("success", false);
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    }
  }
};
</script>

<style scoped>
::v-deep .vue-treeselect__value-container,
::v-deep .vue-treeselect__control {
  height: 27px;
  line-height: 26px;
}
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  line-height: 26px;
}
::v-deep .vue-treeselect__input-container {
  font-size: 13px;
}
</style>
