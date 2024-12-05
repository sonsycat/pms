<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="30%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules">
      <el-row>
        <el-col :span="24">
          <el-form-item label="一级分类" prop="oneCode">
            <el-select class="w90" :disabled="id!=''" clearable filterable remote v-model="form.oneCode" placeholder="请选择一级分类" @change="setType">
              <el-option
                v-for="dict in oneCodeOptions"
                :key="dict.bscCode"
                :label="dict.bscName"
                :value="dict.bscCode"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="二级分类" prop="secondCode">
            <el-select class="w90" :disabled="id!=''" clearable filterable remote v-model="form.secondCode" placeholder="请选择二级分类">
              <el-option
                v-for="dict in secondCodeOptions"
                :key="dict.bscCode"
                :label="dict.bscName"
                :value="dict.bscCode"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="指标集名称" prop="setName">
            <el-input class="w90" v-model="form.setName" placeholder="请输入指标集名称"  maxlength="100" show-word-limit  />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="适用年份" prop="partYear">
            <el-select class="w90" v-model="form.partYear" placeholder="请选择适用年份">
              <el-option
                v-for="dict in partYearOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="适用对象" prop="orgType">
            <el-select class="w90" v-model="form.orgType" placeholder="请选择适用对象">
              <el-option
                v-for="dict in orgTypeOptions"
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
  import { validEmail } from'@/utils/validate'
  import { listBonus, getSet, delSet, addBonus, updateSet, exportSet, setType } from "@/api/evaluate/set";

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
            this.title = "奖金指标集管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            this.title = "奖金指标集管理-新增";
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
        title: "指标集管理-新增",
        openDialog: this.open,
        // 表单参数
        form: {},
        orgTypeOptions: [],
        partYearOptions: [],
        // 表单校验
        rules: {
          oneCode:[
            { required:true,message:'一级分类不能为空',trigger:['blur','change'] }
          ],
          secondCode:[
            { required:true,message:'二级分类不能为空',trigger:['blur','change'] }
          ],
          setName:[
            { required:true,message:'指标集名称不能为空',trigger:['blur','change'] }
          ],
          orgType:[
            { required:true,message:'适用对象不能为空',trigger:['blur','change'] }
          ],
          partYear:[
            { required:true,message:'适用年份不能为空',trigger:['blur','change'] }
          ],

        },
        //一级分类
        oneCodeOptions:[],
        secondCodeOptions:[],
      };
    },
    created() {
      this.getList();
      this.getDicts("pms_zb_zz").then(response => {
        this.orgTypeOptions = response.data;
      });
      this.getDicts("pms_zbj_nf").then(response => {
        this.partYearOptions = response.data;
      });
      this.setType()
    },
    methods: {
      getDetailInfo() {
        getSet(this.id).then(response => {
          // console.log(response);
          this.form = response.data;
          this.setType(this.form.oneCode)
        });
      },
      setType(val){
        let params={
          parentCode:val?val:0
        }
        if(!val){
          this.form.secondCode=null;
          this.oneCodeOptions=[];
          this.secondCodeOptions=[];
        }

        setType(params).then(res=>{
          if(res.code==200){
            if(val){
              this.secondCodeOptions=res.data;
            }else{
              this.oneCodeOptions=res.data;
            }
          }
        })
      },
      /** 查询指标集管理列表 */
      getList() {
        this.loading = true;
        listBonus(this.queryParams).then(response => {
          this.setList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 组织类别(适用对象)字典翻译
      orgTypeFormat(row, column) {
        return this.selectDictLabel(this.orgTypeOptions, row.orgType);
      },
      // 适用年份字典翻译
      partYearFormat(row, column) {
        return this.selectDictLabel(this.partYearOptions, row.partYear);
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
          compCode:this.$store.state.user.userInfo.deptId,
          accountCode: null,
          setCode: null,
          setName: null,
          oneCode: null,
          secondCode: null,
          deptType: null,
          orgType: null,
          partYear: null,
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
            const saveLoading=this.saveLoading()
            if (this.form.id != null) {
              updateSet(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.$emit("success", false);
              });
              saveLoading.close()
            } else {
              addBonus(this.form).then(response => {
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
