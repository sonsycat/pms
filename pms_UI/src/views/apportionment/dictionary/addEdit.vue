<template>
  <div>
    <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="比例编码" prop="ratioCode">
              <el-input v-model="form.ratioCode" placeholder="系统自动生成"  maxlength="50" show-word-limit disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="比例名称" prop="ratioName">
              <el-input v-model="form.ratioName" placeholder="请输入比例名称"  maxlength="100" show-word-limit  />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="比例类型" prop="ratioType">
              <el-select v-model="form.ratioType" placeholder="请选择比例类型" class="w100" @change="handleChangeType" disabled>
                <el-option
                  v-for="dict in ratioTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据源" prop="coonectCode">
              <el-select v-model="form.coonectCode" placeholder="请选择数据源" class="w100">
                <el-option
                  v-for="dict in coonectOptions"
                  :key="dict.code"
                  :label="dict.name"
                  :value="dict.code"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="计算计入成本分摊SQL" prop="countSql" v-if="form.ratioType == '2'">
              <el-input v-model="form.countSql" type="textarea" rows="4" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="一级分摊SQL" prop="firstLevelSql" v-if="form.ratioType == '1'">
              <el-input v-model="form.firstLevelSql" type="textarea" rows="4" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="二级分摊SQL" prop="secondLevelSql" v-if="form.ratioType == '1'">
              <el-input v-model="form.secondLevelSql" type="textarea" rows="4" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="三级分摊SQL" prop="thirdLevelSql" v-if="form.ratioType == '1'">
              <el-input v-model="form.thirdLevelSql" type="textarea" rows="4" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" rows="4" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="submitCs" class="dialog-footer">SQL测试</el-button>
        <el-button type="primary" @click="submitForm(false)">保存并新增</el-button>
        <el-button type="primary" @click="submitForm(true)" >保  存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title1" class="dialog" :visible.sync="openDialog2" width="50%" append-to-body :close-on-click-modal="false"  custom-class="el-button--primary"  @close="cancel2">
      <div>
        <el-row>
          <!-- 计算计入成本分摊方案 -->
          <el-tabs v-model="activeName2">
            <el-tab-pane label="计算计入成本分摊方案" name="sql4List">
              <el-form  ref="form" :model="form">
                <el-table border class="mt20" v-loading="sql4Loading" :data="form.costApportionmentDictionaryDetailList4" ref="cardListRef" stripe>
                  <el-table-column label="比例编码" align="center" prop="ratioCode" show-overflow-tooltip/>
                  <el-table-column label="科室编码" align="center" prop="deptCode" show-overflow-tooltip/>
                  <el-table-column label="科室名称" align="left" prop="deptName" show-overflow-tooltip/>
                  <el-table-column label="使用人数" align="center" prop="num" show-overflow-tooltip/>
                  <el-table-column label="使用占比" align="center" prop="useRatio" show-overflow-tooltip/>
                  <el-table-column label="年月" align="center" prop="ny" show-overflow-tooltip/>
                </el-table>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-row>
        <el-row>
          <el-col :span="24">
            <p :style="style" class="">{{form.exception}}</p>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel2" class="m20">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listDictionary, listDictionarys, getDictionary, addDictionary, updateDictionary } from "@/api/apportionment/dictionary";
  import { listDatabase, getDatabase, delDatabase, addDatabase, updateDatabase, exportDatabase } from "@/api/apportionment/database";
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
            this.title = "分摊比例字典管理-编辑";
            this.getDetailInfo();
          } else {
            this.id = "";
            this.form.ratioType = '2';
            this.title = "分摊比例字典管理-新增";
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
        style:{color: 'green',textAlign: 'right',fontSize: '18px'},
        title: "分摊比例字典管理-新增",
        title1: "分摊比例字典管理/ 测试SQL",
        openDialog: this.open,
        openDialog1: false,
        openDialog2: false,
        activeName1: "sql1List",
        activeName2: "sql4List",
        // 表单参数
        form: {},
        ratioTypeOptions: [],
        coonectOptions: [],
        sql1Loading: false,
        // 使用科室明细
        sql2Loading: false,
        // 资产文档
        sql3Loading: false,
        sql4Loading: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 30,
          code: null,
          name: null,
          type: null,
          url: null,
          userName: null,
          passWord: null,
        },
        // 表单校验
        rules: {
          ratioName: [
            { required: true, message: "比例名称不能为空", trigger: "change" }
          ],
          coonectCode: [
            { required: true, message: "数据源不能为空", trigger: "change" }
          ],
          countSql: [
            { required: true, message: "计算计入成本分摊SQL不能为空", trigger: "change" }
          ],
          firstLevelSql: [
            { required: true, message: "一级分摊SQL不能为空", trigger: "change" }
          ],
          secondLevelSql: [
            { required: true, message: "二级分摊SQL不能为空", trigger: "change" }
          ],
          thirdLevelSql: [
            { required: true, message: "三级分摊SQL不能为空", trigger: "change" }
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("cost_scale_type").then(response => {
        this.ratioTypeOptions = response.data;
      });

    },
    methods: {
      getDetailInfo() {
        getDictionary(this.id).then(response => {
          this.form = response.data;
        });
      },
      getList() {
        listDatabase(this.queryParams).then(response => {
          this.coonectOptions = response.rows;
        });
      },
      // 比例类型字典翻译
      ratioTypeFormat(row, column) {
        return this.selectDictLabel(this.ratioTypeOptions, row.ratioType);
      },
      // 弹框关闭
      handleDialogClose() {
        this.$emit("cancel", false);
      },
      // 取消
      cancel() {
        this.$emit("cancel", false);
      },
      cancel1() {
        this.openDialog1 = false;
      },
      cancel2() {
        this.openDialog2 = false;
      },
      handleChangeType(val){
        this.$confirm('是否确认更换比例类型，如果更换类型SQL数据全部清空！', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.form.firstLevelSql = null,
            this.form.secondLevelSql = null,
            this.form.thirdLevelSql = null,
            this.form.countSql = null,
            this.form.costApportionmentDictionaryDetailList1 = [],
            this.form.costApportionmentDictionaryDetailList2 = [],
            this.form.costApportionmentDictionaryDetailList3 = [],
            this.form.costApportionmentDictionaryDetailList4 = []
        }).catch(() => {});
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          compCode: this.$store.state.user.userInfo.deptId,
          accountCode: null,
          ratioCode: null,
          ratioName: null,
          ratioType: '1',
          countSql: null,
          firstLevelSql: null,
          secondLevelSql: null,
          thirdLevelSql: null,
          costApportionmentDictionaryDetailList1: [],
          costApportionmentDictionaryDetailList2: [],
          costApportionmentDictionaryDetailList3: [],
          costApportionmentDictionaryDetailList4: [],
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
        };
        this.resetForm("form");
      },
      /** SQL测试 */
      submitCs(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            let saveLoading = this.saveLoading();
            let ratioType = this.form.ratioType;
            this.form.exception = null;
            if (ratioType == "1") {
              listDictionarys(this.form).then(response => {
                if(response.data.costApportionmentDictionaryDetailList1.length ==0 ||response.data.costApportionmentDictionaryDetailList2.length ==0 ||response.data.costApportionmentDictionaryDetailList3 ==0){
                  this.msgError("数据库连接异常或sql异常!");
                  return
                }
                this.form.costApportionmentDictionaryDetailList1 = response.data.costApportionmentDictionaryDetailList1;
                this.form.costApportionmentDictionaryDetailList2 = response.data.costApportionmentDictionaryDetailList2;
                this.form.costApportionmentDictionaryDetailList3 = response.data.costApportionmentDictionaryDetailList3;
                if (response.data.exception) {
                  this.style = {color: 'red',textAlign: 'right',fontSize: '18px'};
                  this.form.exception = "测试失败: " + response.data.exception;
                } else {
                  this.form.exception = "测试成功"
                }
                this.openDialog1 = true; // 弹框点开sql1
              });
              saveLoading.close();
            } else if (ratioType == "2") {
              listDictionarys(this.form).then(response => {
                if(response.data.costApportionmentDictionaryDetailList4.length ==0){
                  this.msgError("数据库连接异常或sql异常!");
                  return
                }
                this.form.costApportionmentDictionaryDetailList4 = response.data.costApportionmentDictionaryDetailList4;
                if (response.data.exception) {
                  this.style = {color: 'red',textAlign: 'right',fontSize: '18px'};
                  this.form.exception = "测试失败: " + response.data.exception;
                } else {
                  this.form.exception = "测试成功"
                }
                this.openDialog2 = true; // 弹框点开
              });
              saveLoading.close();
            }
          }
        });
      },

      /** 提交按钮 */
      submitForm(type) {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if(this.form.costApportionmentDictionaryDetailList1.length != 0||this.form.costApportionmentDictionaryDetailList4.length != 0){
              let saveLoading = this.saveLoading();
              if (this.form.exception != '测试成功') {
                this.msgError("SQL测试失败");
                return
              }
              if (this.form.id != null) {
                updateDictionary(this.form).then(response => {
                  this.msgSuccess("修改成功");
                  if (!type) {
                    this.reset();
                    this.title = "分摊比例字典管理-新增";
                  } else {
                    this.$emit("success", false);
                  }
                  saveLoading.close();
                });
              } else {
                addDictionary(this.form).then(response => {
                  this.msgSuccess("新增成功");
                  if (!type) {
                    this.reset();
                  } else {
                    this.$emit("success", false);
                  }
                  saveLoading.close();
                });
              }
            }else{
              this.msgError("SQL测试数据不能为空");
              return
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
