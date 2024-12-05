<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="60%" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <div>
          <el-form ref="form" :model="form" inline :rules="rules">
              <el-form-item label="分摊编码" prop="apportCode">
                <el-input class="search_w220" v-model="form.apportCode" placeholder="请输入分摊编码"  maxlength="50" show-word-limit disabled />
              </el-form-item>
              <el-form-item label="分摊年月" prop="nowDate">
                <el-date-picker class="search_w220" clearable
                                v-model="form.nowDate"
                                type="month"
                                value-format="yyyy-MM"
                                placeholder="选择年月" disabled>
                </el-date-picker>
              </el-form-item>
          </el-form>
        <el-row>
          <!-- 一级分摊方案 -->
          <el-tabs v-model="activeName1">
            <el-tab-pane label="分摊前" name="sql1List">
              <el-table :data="tableData1"
                        v-loading="loading"
                        size='small'
                        ref="requirementTable" stripe highlight-current-row :min-height="tableHeight" max-height="500px" border>
                <template v-for="(item,i) in tableColumns1">
                  <template v-if="item.prop=='deptCode'">
                    <el-table-column
                      align="left"
                      :prop="item.prop"
                      :key="i"
                      :label="item.label">
                    </el-table-column>
                  </template>
                  <template v-else>
                    <el-table-column align="right" :prop="item.prop" :key="i" :label="item.label"></el-table-column>
                  </template>
                </template>
              </el-table>
            </el-tab-pane>
            <!-- 计算分摊 -->
            <el-tab-pane label="计算分摊" v-if="form.apportType>='1'" name="sql2List">
              <el-table :data="tableData2"
                        v-loading="loading"
                        size='small'
                        ref="requirementTable" stripe highlight-current-row :min-height="tableHeight" max-height="500px" border>
                <template v-for="(item,i) in tableColumns2">
                  <template v-if="item.prop=='deptCode'">
                    <el-table-column
                      align="left"
                      :prop="item.prop"
                      :key="i"
                      :label="item.label">
                    </el-table-column>
                  </template>
                  <template v-else>
                    <el-table-column align="right" :prop="item.prop" :key="i" :label="item.label"></el-table-column>
                  </template>
                </template>
              </el-table>
            </el-tab-pane>
            <!-- 一级分摊 -->
            <el-tab-pane label="一级分摊" v-if="form.apportType>='2'" name="sql3List">
              <el-table :data="tableData3"
                        v-loading="loading"
                        size='small'
                        ref="requirementTable" stripe highlight-current-row :min-height="tableHeight" max-height="500px" border>
                <template v-for="(item,i) in tableColumns3">
                  <template v-if="item.prop=='deptCode'">
                    <el-table-column
                      align="left"
                      :prop="item.prop"
                      :key="i"
                      :label="item.label">
                    </el-table-column>
                  </template>
                  <template v-else>
                    <el-table-column align="right" :prop="item.prop" :key="i" :label="item.label"></el-table-column>
                  </template>
                </template>
              </el-table>
            </el-tab-pane>
            <!-- 二级分摊 -->
            <el-tab-pane label="二级分摊" v-if="form.apportType>='3'" name="sql4List">
              <el-table :data="tableData4"
                        v-loading="loading"
                        size='small'
                        ref="requirementTable" stripe highlight-current-row :min-height="tableHeight" max-height="500px" border>
                <template v-for="(item,i) in tableColumns4">
                  <template v-if="item.prop=='deptCode'">
                    <el-table-column
                      align="left"
                      :prop="item.prop"
                      :key="i"
                      :label="item.label">
                    </el-table-column>
                  </template>
                  <template v-else>
                    <el-table-column align="right" :prop="item.prop" :key="i" :label="item.label"></el-table-column>
                  </template>
                </template>
              </el-table>
            </el-tab-pane>
            <!-- 三级分摊 -->
            <el-tab-pane label="三级分摊" v-if="form.apportType>='4'" name="sql5List">
              <el-table :data="tableData5"
                        v-loading="loading"
                        size='small'
                        ref="requirementTable" stripe highlight-current-row :min-height="tableHeight" max-height="500px" border>
                <template v-for="(item,i) in tableColumns5">
                  <template v-if="item.prop=='deptCode'">
                    <el-table-column
                      align="left"
                      :prop="item.prop"
                      :key="i"
                      :label="item.label">
                    </el-table-column>
                  </template>
                  <template v-else>
                    <el-table-column align="right" :prop="item.prop" :key="i" :label="item.label"></el-table-column>
                  </template>
                </template>
              </el-table>
            </el-tab-pane>

          </el-tabs>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" class="m20">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { validEmail } from'@/utils/validate'
import { listApport, getApport, addApportFtjs, addApport, updateApport, exportApport } from "@/api/apport/apport";

export default {
  name: "detailEdit",
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
          this.title = "成本分摊 / 明细";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "成本分摊 / 明细";
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
      title: "分摊明细管理-新增",
      openDialog: this.open,
      saveLoading: false,
      activeName1: "sql1List",
      tableHeight:"100px",
      // 使用科室明细
      tableColumns1: [],
      tableData1: [],
      tableColumns2: [],
      tableData2: [],
      tableColumns3: [],
      tableData3: [],
      tableColumns4: [],
      tableData4: [],
      tableColumns5: [],
      tableData5: [],
      // 表单参数
      form: {},
      apportTypeOptions: [],
	  	// 表单校验
      rules: {
      }
    };
  },
  created() {
  },
  mounted() {
  },
  methods: {
    getDetailInfo() {
      this.loading = true;
      getApport(this.id).then(response => {
        this.form = response.data;
        this.tableColumns1 = response.data.tableColumns1;
        this.tableColumns2 = response.data.tableColumns1;
        this.tableColumns3 = response.data.tableColumns1;
        this.tableColumns4 = response.data.tableColumns1;
        this.tableColumns5 = response.data.tableColumns1;

        this.tableData1 = response.data.tableData1;
        this.tableData2 = response.data.tableData2;
        this.tableData3 = response.data.tableData3||[];
        this.tableData4 = response.data.tableData4||[];
        this.tableData5 = response.data.tableData5||[];
        for (let i = 0; i < this.tableData1.length; i++) {
          for (var j in this.tableData1[i]) {
            if ((typeof this.tableData1[i][j]) == "number" && this.tableData1[i][j] != 0) {
              this.tableData1[i][j] = this.moneyFormat(this.tableData1[i][j])
            }
          }
        }
        for (let i = 0; i < this.tableData2.length; i++) {
          for (var j in this.tableData2[i]) {
            if ((typeof this.tableData2[i][j]) == "number" && this.tableData2[i][j] != 0) {
              this.tableData2[i][j] = this.moneyFormat(this.tableData2[i][j])
            }
          }
        }
        for (let i = 0; i < this.tableData3.length; i++) {
          for (var j in this.tableData3[i]) {
            if ((typeof this.tableData3[i][j]) == "number" && this.tableData3[i][j] != 0) {
              this.tableData3[i][j] = this.moneyFormat(this.tableData3[i][j])
            }
          }
        }
        for (let i = 0; i < this.tableData4.length; i++) {
          for (var j in this.tableData4[i]) {
            if ((typeof this.tableData4[i][j]) == "number" && this.tableData4[i][j] != 0) {
              this.tableData4[i][j] = this.moneyFormat(this.tableData4[i][j])
            }
          }
        }
        for (let i = 0; i < this.tableData5.length; i++) {
          for (var j in this.tableData5[i]) {
            if ((typeof this.tableData5[i][j]) == "number" && this.tableData5[i][j] != 0) {
              this.tableData5[i][j] = this.moneyFormat(this.tableData5[i][j])
            }
          }
        }
        this.loading = false;
      });
    },
    /** 查询分摊计算列表 */
    getList() {
      this.loading = true;
      listApport(this.queryParams).then(response => {
        this.apportList = response.rows;
        this.total = response.total;
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
        compCode: this.$store.state.user.userInfo.deptId,
        accountCode: this.$store.state.user.accountInfo.accountCode,
        apportCode: null,
        nowDate: null,
        apportType: null,
        forward: null,
        back: null,
        apportTime: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
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
      .el-form-item__content{
        margin-left: 0 !important
      }
    }
  }
</style>
