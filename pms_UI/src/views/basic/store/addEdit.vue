<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="700px" :close-on-click-modal="false" append-to-body @close="handleDialogClose" custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编码" prop="storeCode">
              <el-input v-model="form.storeCode" placeholder="系统自动生成" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <orgSearch label="单位" :queryParams="form" ></orgSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="storeName">
              <el-input v-model="form.storeName" placeholder="请输入仓库名称" @input="handleSpell"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拼音码" prop="spell">
              <el-input v-model="form.spell" placeholder="请输入拼音码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库房类型" prop="storeClass">
              <el-select v-model="form.storeClass" placeholder="请选择库房类型" style="width: 100%" @change="storeClassChange">
                <el-option
                  v-for="dict in storeClassOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库房分类" prop="storeType">
              <el-select v-model="form.storeType" placeholder="请选择库房分类" style="width: 100%">
                <el-option
                  v-for="dict in storeTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入电话" maxlength="11"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <staffSearch label="仓库负责人"  :staffParams.sync = "form" formItemProp="principalCode"  inputProp="principalCodeName"></staffSearch>
          </el-col>
          <el-col :span="12">
            <staffSearch label="会计"  :staffParams.sync = "form" formItemProp="accountantCode"  inputProp="accountantCodeName"></staffSearch>
          </el-col>
          <el-col :span="12">
            <staffSearch label="仓库保管员"  :staffParams.sync = "form" formItemProp="custodyCode"  inputProp="custodyCodeName"></staffSearch>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否代销" prop="isProxy">
              <el-select v-model="form.isProxy" placeholder="请选择是否代销" style="width: 100%">
                <el-option
                  v-for="dict in isProxyOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否停用" prop="isStop">
              <el-select v-model="form.isStop" placeholder="请选择是否停用" style="width: 100%">
                <el-option
                  v-for="dict in isStopOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" maxlength="100" show-word-limit rows="3" resize="none"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-hasPermi="['basic:store:edit']" :loading="submitLoading">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import {
  listStore,
  getStore,
  delStore,
  addStore,
  addStore1,
  updateStore,
  updateStore1,
  exportStore,
  updateMain
} from "@/api/basic/store";
import { empInfo } from "@/api/until";
import FileUpload from "@/components/FileUpload";
import orgSearch from "@/views/components/orgSearch";
import pinyin from "js-pinyin";
import { validateTel } from '@/utils/validate'
export default {
  name: "addEdit",
  components: {
    orgSearch,
    FileUpload
  },
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
      submitLoading: false,
      title: "仓库管理 / 新增",
      people: [],
      openDialog: this.open,
      form: {},
      popoverD: false,
      // 使用日期范围
      probationRange: [],
      // 表单校验
      rules: {
        compCode: [
          { required: true, message: "单位编码不能为空", trigger: "blur" }
        ],
        compName: [
          { required: true, message: "单位不能为空", trigger: "blur" },
          { required: true, message: "单位不能为空", trigger: "change" }
        ],
        storeName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        storeClass: [
          { required: true, message: "库房类型不能为空", trigger: "blur" }
        ],
        tel: [
          // { required: true,validator:validateTel, trigger: "blur" }
        ],
      },
      // 库房分类字典
      storeTypeOptions: [],
      // 库房类型字典
      storeClassOptions: [],
      // 是否停用字典
      isStopOptions: [],
      // 是否代销字典
      isProxyOptions: [],
      //用户列表
      userOptions: []
    };
  },
  computed: {},
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "仓库管理 / 修改";
          this.getDetailInfo();
          this.popoverD = true;
        } else {
          this.id = "";
          this.popoverD = false;
          this.title = "仓库管理 / 新增";
          this.form.compCode = this.$store.state.user.userInfo.deptId;
          this.form.compName = this.$store.state.user.userInfo.deptName;
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
    "$store.state.dicts": {
      handler(val) {
        // 基础字典
        let baseDicts = val.baseDicts;
        this.isStopOptions = baseDicts.hrIsChange;
        this.isProxyOptions = baseDicts.hrIsChange;
        this.storeTypeOptions = baseDicts.basicStoreType;
        this.storeClassOptions = baseDicts.storeClass;
      },
      deep: true,
      immediate: true
    }
  },
  mounted() {
    this.getListUser();
  },

  methods: {
    getListUser() {
      this.getUserInfo().then(response => {
        if (response.code == 200) {
          this.userOptions = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // 仓库负责人
    handlePrincipal(data) {
      console.log(data, "data");
    },
    /** 拼音码 */
    handleSpell() {
      this.form.spell = pinyin.getCamelChars(this.form.storeName).toUpperCase();
    },
    getDetailInfo() {
      const loading = this.dataLoading()                   
      getStore(this.id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
          this.open = true;
          this.title = "仓库管理 / 修改";
          loading.close()                          
        } else {
          this.msgError(response.msg);
        }
      }).catch(()=>{
        loading.close();            
      })
    },
    reset() {
      this.form = {
        id: null,
        compCode: null,
        compName: null,
        deptCode: null,
        storeCode: null,
        storeName: null,
        spell: null,
        storeType: null,
        storeClass: null,
        address: null,
        tel: null,
        principalCode: null,
        principalCodeName: null,
        custodyCode: null,
        custodyCodeName: null,
        accountantCode: null,
        accountantCodeName: null,
        isStop: "1",
        isProxy: "1",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    // 库房分类字典翻译
    storeTypeFormat(row, column) {
      return this.selectDictLabel(this.storeTypeOptions, row.storeType);
    },
    // 是否停用字典翻译
    isStopFormat(row, column) {
      return this.selectDictLabel(this.isStopOptions, row.isStop);
    },
    // 是否代销字典翻译
    isProxyFormat(row, column) {
      return this.selectDictLabel(this.isProxyOptions, row.isProxy);
    },
    //输入框输入事件
    handlerPageNo(res) {
      this.getEmpInfo2(res);
    },
    getEmpInfo2(infoCode) {
      let data = {
        infoCode: infoCode,
        pageNum: 1,
        pageSize: 30,
        isPost: "1"
      };
      empInfo(data).then(response => {
        if (response.code == 200) {
          this.people = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    //点击单行事件
    handlePeople(data) {
      this.form.empCode = data.empCode;
      this.form.empName = data.empName;
      this.form.deptName = data.deptName;
      this.form.deptCode = data.deptCode;
      this.form.titleCode = data.titleCode;
      this.form.postCode = data.postCode;
      this.form.postName = data.postName;
      this.form.gender = data.gender;
    },
    // 提交
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateStore(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
              this.submitLoading = false;
            }).catch(()=>{
              this.submitLoading = false;
            });
          } else {
            addStore(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
              this.submitLoading = false;
            }).catch(()=>{
              this.submitLoading = false;
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
    },
    //选择库房分类
    storeClassChange(val) {
      if (val == "1") {
        this.getDicts("basic_store_type").then(response => {
          this.storeTypeOptions = response.data;
        });
      } else if (val == "2") {
        this.getDicts("basic_dept_store_type").then(response => {
          this.storeTypeOptions = response.data;
        });
      }
      this.form.storeType = null;
    },
  }
};
</script>

<style lang="scss" scoped>
</style>
