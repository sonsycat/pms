<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
    <el-form ref="form" :model="form" :rules="rules">
      <el-row>
        <el-col :span="24">
          <p class="lit-title" >指标类别信息</p>
        </el-col>
        <el-col :span="12">
          <el-form-item label="BSC一级分类:" prop="bscOneCode">
            <el-select class="w90" clearable filterable remote v-model="form.bscOneCode" placeholder="请选择BSC一级分类"  @change="getBscList" >
              <el-option
                v-for="dict in isBounsSBCYs"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="BSC二级分类:" prop="bscSecondCode">
            <el-select class="w90" clearable filterable remote v-model="form.bscSecondCode" placeholder="请选择BSC二级分类">
              <el-option
                v-for="dict in isBounsSBCEs"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门分类" prop="deptType">
            <el-select class="w90" v-model="form.deptType" placeholder="请选择部门分类">
              <el-option
                v-for="dict in deptOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组织类别" prop="orgType">
            <el-select class="w90" v-model="form.orgType" placeholder="请选择组织类别">
              <el-option
                v-for="dict in orgOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <p class="lit-title" >指标基本信息</p>
        </el-col>
        <el-col :span="12">
          <el-form-item label="指标代码" prop="indexCode">
            <el-input class="w90" v-model="form.indexCode" placeholder="系统自动生成指标代码" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="指标名称" prop="indexName">
            <el-input class="w90" v-model="form.indexName" placeholder="请输入指标名称"  maxlength="100" show-word-limit @change="addSetIndexCode" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否表达式" prop="isExpress">
            <el-select class="w90" v-model="form.isExpress" placeholder="请选择是否表达式" @change="expressSelect" :disabled="id!=''">
              <el-option
                v-for="dict in isExpressOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="" v-if="isExpressBool">
            <el-button type="primary" size="small" @click="setIndicatorExp" :disabled="form.isExpress!='0'">设置指标表达式</el-button>
          </el-form-item>
          <el-form-item label="" v-else>
            <el-button type="primary" size="small" :disabled="form.isExpress!='1'" @click="setAlgorithm">设置指标算法</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否停用状态" prop="isStop">
            <el-select class="w90" v-model="form.isStop" placeholder="请选择是否停用状态" clearable>
              <el-option
                v-for="dict in isStopOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否高优指标" prop="isCouns">
            <el-select class="w90" v-model="form.isCouns" placeholder="请选择是否高优指标">
              <el-option
                v-for="dict in isCounsOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="综合评价指标" prop="isComp">
            <el-select class="w90" v-model="form.isComp" placeholder="请选择是否综合评价指标">
              <el-option
                v-for="dict in isCompOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否绝对值" prop="isAbsolute">
            <el-select class="w90" v-model="form.isAbsolute" placeholder="请选择是否绝对值">
              <el-option
                v-for="dict in isAbsoluteOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="指标类型" prop="indexType">
            <el-select class="w90" v-model="form.indexType" placeholder="请选择指标类型">
              <el-option
                v-for="dict in indexOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否固定分值" prop="isRegular">
            <el-select class="w90" v-model="form.isRegular" placeholder="请选择是否固定分值">
              <el-option
                v-for="dict in isRegularOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="指标阈值比" prop="indexRatio">
            <el-input class="w90" v-model="form.indexRatio" placeholder="请输入指标阈值比"  maxlength="20" show-word-limit  />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="必须指标" prop="isRegular">
            <el-select class="w90" v-model="form.isNecessary" placeholder="请选择是否必须指标">
              <el-option
                v-for="dict in isNecessarys"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="指标说明" prop="indexContent">
            <!-- <editor v-model="form.indexContent" :min-height="192"/> -->
            <el-input class="w90" type="textarea" v-model="form.indexContent" placeholder="请输入指标说明" :rows="4" maxlength="255" show-word-limit  />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="顺序号" prop="indexSort">
            <el-input-number class="w90" controls-position="right" v-model="form.indexSort" placeholder="请输入顺序号"   />
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
  import { validEmail } from'@/utils/validate';
  import { listIndex, getIndex, addBonus, updateIndex, getBsc } from "@/api/evaluate/index";
  import Editor from '@/components/Editor';
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
      datas:{
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
            this.title = "奖金指标管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            this.title = "奖金指标管理-新增";
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
      Editor,

    },
    data() {
      return {
        title: "指标管理-新增",
        openDialog: this.open,
        // 表单参数
        form: {}, 	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	  	        // 表单校验
        rules: {
          bscOneCode:[
            { required: true, message: "BSC一级分类不能为空", trigger: ["blur","change"] }
          ],
          bscSecondCode:[
            { required: true, message: "BSC二级分类不能为空", trigger: ["blur","change"] }
          ],
          deptType:[
            { required: true, message: "部门分类不能为空", trigger: ["blur","change"] }
          ],
          orgType:[
            { required: true, message: "组织类别不能为空", trigger: ["blur","change"] }
          ],
          indexName:[
            { required: true, message: "指标名称不能为空", trigger: "blur" }
          ],
          isExpress:[
            { required: true, message: "是否表达式不能为空", trigger: ["blur","change"] }
          ],
          isStop:[
            { required: true, message: "是否停用不能为空", trigger: ["blur","change"] }
          ],
          isCouns:[
            { required: true, message: "是否高优指标不能为空", trigger: ["blur","change"] }
          ],
          isComp:[
            {required: true, message: "是否综合评价指标不能为空", trigger: ["blur","change"]}
          ],
          isAbsolute:[
            {required: true, message: "是否绝对值不能为空", trigger: ["blur","change"]}
          ],
          indexType:[
            {required: true, message: "指标类型不能为空", trigger: ["blur","change"]}
          ],
          isRegular:[
            {required: true, message: "是否固定分值不能为空", trigger: ["blur","change"]}
          ],
          indexRatio:[
            {required: true, message: "指标阈值比不能为空", trigger: "blur"}
          ],
          indexSort:[
            {required: true, message: "顺序号不能为空", trigger: ["blur","change"]}
          ],
        },
        isExpressBool:false,
        //指标类型
        indexOptions:[],
        //指标部门分类
        deptOptions:[],
        //指标组织分类
        orgOptions:[],
        //高优指标
        isCounsOptions:[],
        //bsc一级分类
        isBounsSBCYs:[],
        //bsc二级分类
        isBounsSBCEs:[],
        //是否字典
        isStopOptions:[],
        isExpressOptions:[],
        isCompOptions:[],
        isAbsoluteOptions:[],
        isRegularOptions:[],
        isNecessarys:[],
        bscIList:[],//bsc一级列表
        bscIIList:[],//bsc二级列表
        setedIndexName:''
      };
    },
    created() {
      this.getList();
      //指标类型字典
      this.getDicts("pms_zb_lx").then(response=>{
        this.indexOptions = response.data;
      })
      //是否高优指标字典
      this.getDicts("pms_zb_sfgy").then(response=>{
        this.isCounsOptions = response.data;
      })
      //bsc一级
      this.getDicts("PMS_JJBSC_YJFL").then(response=>{
        this.isBounsSBCYs=response.data;
      })
      //bsc二级
      this.getDicts("PMS_JJBSC_EJFL").then(response=>{
        this.isBounsSBCEs=response.data;
      })
      //指标组织字典
      this.getDicts("pms_zb_zz").then(response=>{
        this.orgOptions = response.data;
      })
      //是否停用字典
      this.getDicts("pms_zb_ty").then(response=>{
        this.isStopOptions = response.data;
      })
      //指标部门分类字典
      this.getDicts("pms_bm_fl").then(response=>{
        this.deptOptions=response.data;
      })
      //是否字典类型
      this.getDicts("hr_is_change").then(response => {
        this.isExpressOptions = response.data;
        this.isCompOptions = response.data;
        this.isAbsoluteOptions = response.data;
        this.isRegularOptions = response.data;
        this.isNecessarys = response.data;
      });
      this.getBscList()
    },
    methods: {
      getDetailInfo() {
        getIndex(this.id).then(response => {
          this.form = response.data;
          if(this.form.isExpress=='0'){
            this.isExpressBool=true
          }else{
            this.isExpressBool=false
          }
          this.getBscList(this.form.bscOneCode)
        });
      },
      /** 查询指标管理列表 */
      getList() {
        this.loading = true;
        listIndex(this.queryParams).then(response => {
          this.indexList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      //获取bsc下拉列表数据
      getBscList(val){
        let params={
          parentCode:val?val:0
        }
        if(!val){
         // this.form.bscSecondCode=null;
          this.bscIList=[];
          this.bscIIList=[];
        }
        getBsc(params).then(res=>{
          if(res.code==200){
            if(val){
              this.bscIIList=res.data;
            }else{
              this.bscIList=res.data;
            }
          }
        })
      },
      expressSelect(val){
        if(val==0){
          this.isExpressBool=true;
        }else{
          this.isExpressBool=false;
        }
      },
      addSetIndexCode(val){
        this.setedIndexName=val;
      },
      setIndicatorExp(){
        if(!this.form.orgType){
          this.msgError("请先选择组织类别");
        }else{
          this.$emit("openIndExp",this.setedIndexName,this.form.orgType)
        }
      },
      setAlgorithm(){
        this.$emit("openAlgorithm",this.setedIndexName)
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
          bscOneCode: "1",
          bscSecondCode: "1",
          deptType: null,
          orgType: null,
          indexCode: null,
          indexName: null,
          isExpress: "1",
          formulaCode: null,
          formulaName: null,
          isSection: null,
          indexSql: null,
          sectionStart: null,
          sectionEnd: null,
          isCouns: "1",
          isStop: "0",
          isComp: "1",
          isAbsolute: "1",
          indexType: "1",
          isRegular: "1",
          indexRatio: null,
          isNecessary:null,
          indexContent: null,
          indexSort: null,
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
            this.form.formulaCode=this.datas.formulaCode;
            this.form.formulaName=this.datas.formulaName;
            this.form.isSection=this.datas.isSection;
            this.form.indexSql=this.datas.indexSql;
            this.form.sectionStart=this.datas.sectionStart;
            this.form.sectionEnd=this.datas.sectionEnd;
            let saveLoading=this.saveLoading()
            if (this.form.id != null) {
              updateIndex(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.$emit("success", false);
                saveLoading.close()
              });
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
    height: 80%;
    overflow: hidden;
    .el-dialog__body {
      // height: calc(100% - 100px);
      overflow: auto;
      .lit-title{
        font-size: 16px;
        padding-bottom: 8px;
        border-bottom: 1px dashed #cccccc;
      }
    }
  }
</style>
