<template>
    <!-- 基础信息 - 年度账期间管理 -->
  <div class="app-container">
    <div ref="search">
      <el-form :model="queryParams" ref="queryForm" :rules="queryParamsRules" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="账套" prop="accountCode">
          <el-select v-model="queryParams.accountCode" placeholder="请选择账套" >
            <el-option
              v-for="dict in accountCodeOptions"
              :key="dict.id"
              :label="dict.accountName"
              :value="dict.accountCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年度" prop="accountYear">
          <el-date-picker 
            v-model="queryParams.accountYear"
            type="year"
            value-format="yyyy"
            placeholder="选择年度">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  </div>
  <div ref="btns">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"

            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['basic:period:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"

            icon="el-icon-delete"
            size="mini"
            @click="handleDelete"
            v-hasPermi="['basic:period:remove']"
          >删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  </div>
    <el-table v-loading="loading" :height="tableHeight" ref="periodListRef" :data="periodList" @selection-change="handleSelectionChange" stripe :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row @row-click="handleRowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账套编码" align="center" prop="accountCode" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="期间名称" align="center" prop="periodName" sortable="custom" :sort-orders="['descending', 'ascending']"/>
      <el-table-column label="账套名称" align="left" prop="accountName" show-overflow-tooltip sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
           <el-link type="primary" :underline="false" v-html="scope.row.accountName" @click.stop="handleClickTableName(scope.row)" class="displayBlock text-ellipsis "></el-link>
        </template>
      </el-table-column>
      <el-table-column label="年度"  width="120" align="center" prop="accountYear" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="期间"  width="120" align="center" prop="periodNum" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="开始日期"  width="120" align="center" prop="startDate" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" width="120" align="center" prop="endDate" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改年度帐期间管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="30%" append-to-body
:close-on-click-modal="false"  custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="账套" prop="accountCode">
              <el-select v-model="form.accountCode" placeholder="请选择账套编码" style="width: 80%" >
                <el-option
                  v-for="dict in accountCodeOptions"
                  :key="dict.id"
                  :label="dict.accountName"
                  :value="dict.accountCode"
                  @click.native="changeOption(dict)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="年度" prop="accountYear" >
              <el-date-picker style="width: 80%"
                v-model="form.accountYear"
                type="year"
                placeholder="选择年度"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item>
              <el-radio-group v-model="form.type" @change="agreeChange">
                <el-radio label="0">自然月</el-radio>
                <el-radio label="1">自定义</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="期间" prop="periodNum" >
              <el-select v-model="form.periodNum" placeholder="请选择期间" :disabled="periodNumDisabled" style="width: 80%">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="当前期间" prop="nowPeriod" >
              <el-select v-model="form.nowPeriod" placeholder="请选择期间" clearable style="width: 80%">
                <el-option
                  v-for="item in options2"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="开始日期" prop="startDate" v-if="addEditId" >
              <el-date-picker
                v-model="form.startDate"
                type="date"
                placeholder="选择开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="结束日期" prop="endDate" v-if="addEditId" >
              <el-date-picker
                v-model="form.endDate"
                type="date"
                placeholder="选择结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm"  v-if="!addEditId">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPeriod,
  getPeriod,
  delPeriodAll,
  delPeriod,
  addPeriod,
  updatePeriod,
  exportPeriod
} from "@/api/basic/period";
import { listAccount } from "@/api/basic/account";
export default {
  name: "Period",
  components: {},
  data() {
    return {
      options: [
        {
          value: "01",
          label: "01"
        },
        {
          value: "02",
          label: "02"
        },
        {
          value: "03",
          label: "03"
        },
        {
          value: "04",
          label: "04"
        },
        {
          value: "05",
          label: "05"
        },
        {
          value: "06",
          label: "06"
        },
        {
          value: "07",
          label: "07"
        },
        {
          value: "08",
          label: "08"
        },
        {
          value: "09",
          label: "09"
        },
        {
          value: "10",
          label: "10"
        },
        {
          value: "11",
          label: "11"
        },
        {
          value: "12",
          label: "12"
        },
        {
          value: "13",
          label: "13"
        },
        {
          value: "14",
          label: "14"
        },
        {
          value: "15",
          label: "15"
        },
        {
          value: "16",
          label: "16"
        },
        {
          value: "17",
          label: "17"
        },
        {
          value: "18",
          label: "18"
        },
        {
          value: "19",
          label: "19"
        },
        {
          value: "20",
          label: "20"
        },
        {
          value: "21",
          label: "21"
        },
        {
          value: "22",
          label: "22"
        },
        {
          value: "23",
          label: "23"
        },
        {
          value: "24",
          label: "24"
        },
        {
          value: "25",
          label: "25"
        },
        {
          value: "26",
          label: "26"
        },
        {
          value: "27",
          label: "27"
        }
      ],
      options2:[
        {
          value: "1",
          label: "1"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "3",
          label: "3"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "5",
          label: "5"
        },
        {
          value: "6",
          label: "6"
        },
        {
          value: "7",
          label: "7"
        },
        {
          value: "8",
          label: "8"
        },
        {
          value: "9",
          label: "9"
        },
        {
          value: "10",
          label: "10"
        },
        {
          value: "11",
          label: "11"
        },
        {
          value: "12",
          label: "12"
        },
      ],
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      accountCodes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 年度帐期间管理表格数据
      periodList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 账套名称
      accountCodeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        accountName: null,
        accountYear: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        accountCode: [
          { required: true, message: "账套编码不能为空", trigger: "blur" }
        ],
        accountYear: [
          { required: true, message: "年度不能为空", trigger: "blur" }
        ],
        periodNum: [
          { required: true, message: "期间不能为空", trigger: "blur" }
        ]
      },
      queryParamsRules: {
        accountCode: [
          { required: true, message: "账套不能为空", trigger: "change" }
        ],
        accountYear: [
          { required: true, message: "年度不能为空", trigger: "change" }
        ]
      },
      //期间不能选择
      periodNumDisabled: true,
      defaultSort: { prop: "createTime", order: "descending" },
      selectionRow: [],
      tableHeight: "auto",
      addEditId: "",
      year: Date.now(),
      pickerOptions: {
        // 限制收货时间不让选择今天以前的
        disabledDate: time => {
          return time.getTime() < this.year;
        }
      }
    };
  },
  created() {
    this.getList();
    this.getListAccount();
    this.$nextTick(() => {
      this.resizeHandler();
      window.addEventListener("resize", this.resizeHandler);
    });
  },
  computed: {
    listData() {
      return this.$store.state.app.sidebar.opened;
    }
  },
  watch: {
    listData(val) {
      var that = this;
      setTimeout(function() {
        that.resizeHandler();
      }, 300);
    }
  },
  methods: {
    handleClickTableName(data) {
      // 选中当前行
      this.handleRowClick(data, false);
      this.handleUpdate(data);
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + 18 + 45 + 84 + 40 + 20;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    /** 查询年度帐期间管理列表 */
    getList() {
      this.loading = true;
      listPeriod(this.queryParams).then(response => {
        if (response.code == 200) {
          this.periodList = response.rows;
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    getListAccount() {
      listAccount().then(response => {
        if (response.code == 200) {
          this.accountCodeOptions = response.rows;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        accountCode: null,
        accountName: null,
        accountYear: null,
        startDate: null,
        endDate: null,
        periodNum: "1",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        type: "0",
        periodNumDisabled: true,
        nowPeriod: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    handleRowClick(row, flag = true) {
      let periodListRef = this.$refs.periodListRef;
      if (flag) {
        let findRow = this.selectionRow.find(c => c.id == row.id);
        if (findRow) {
          periodListRef.toggleRowSelection(row, false);
          return;
        }
      }
      periodListRef.clearSelection(); //清空列表的选中
      periodListRef.toggleRowSelection(row, true);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      let periodListRef = this.$refs.periodListRef;
      if (selection.length > 1) {
        periodListRef.clearSelection(); //清空列表的选中
        periodListRef.toggleRowSelection(selection[selection.length - 1], true); //只显示选中最后一个 这时selection还是多选的列表(就是你选中的几个数据)
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      periodListRef.setCurrentRow(selection[0]);
      this.ids = selection.map(item => item.id);
      this.accountCodes = selection.map(item => item.accountCode);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.periodListRef.clearSelection();
      this.reset();
      this.open = true;
      this.title = "年度帐期间管理 / 新增";
      this.addEditId = "";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      this.addEditId = id;
      const loading = this.dataLoading()
      getPeriod(id).then(response => {
        if (response.code == 200) {
          this.form = response.data;
          this.open = true;
          this.title = "年度帐期间管理 / 查看";
          loading.close()
        } else {
          this.msgError(response.msg);
        }
      }).catch(()=>{
        loading.close();            
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePeriod(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addPeriod(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      const accountCode = row.accountCode || this.accountCodes;
      if (id != "") {
        this.$confirm(
          "是否确认删除账套编码为" + accountCode + "的年度帐期间管理的数据项?",
          "警告",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        )
          .then(() => {
            return delPeriod(id);
          })
          .then(() => {
            this.getList();
            this.msgSuccess("删除成功");
          })
          .catch(() => {});
      } else {
        this.$refs["queryForm"].validate(valid => {
          if (valid) {
            this.$confirm(
              "是否确认根据账套及年度删除年度帐期间管理的数据项?",
              "警告",
              {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }
            )
              .then(() => {
                return delPeriodAll(this.queryParams);
              })
              .then(() => {
                this.getList();
                this.msgSuccess("删除成功");
              })
              .catch(() => {});
          }
        });
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有年度帐期间管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportPeriod(queryParams);
        })
        .then(response => {
          if (response.code == 200) {
            this.download(response.msg);
            this.exportLoading = false;
          } else {
            this.msgError(response.msg);
          }
        })
        .catch(() => {});
    },
    //自然月，自定义切换
    agreeChange(res) {
      if (res == 0) {
        this.periodNumDisabled = true;
        this.init();
      } else {
        this.periodNumDisabled = false;
        this.resetInit();
      }
    },
    changeOption(data) {
      this.year = new Date(data.startYear).getTime();
    },
    init() {
      this.form.periodNum = 1;
    },
    resetInit() {
      this.form.periodNum = "";
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep{
  .el-select>.el-input{
    width: 220px;
  }
  .el-dialog{
    .el-select{
      width: 90% !important;
    }
    .el-select>.el-input{
      width: 100% !important;
    }
    .el-date-editor.el-input, .el-date-editor.el-input__inner{
      width: 90% !important;
    }
    .el-form-item--mini .el-form-item__label {
      width: 80px !important;
    }
    .el-form-item--mini .el-form-item__content {
      margin-left: 80px !important;
    }
  }
}
</style>


