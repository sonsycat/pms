<template>
  <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" :inline="true" @submit.native.prevent v-show="showSearch" label-width="90px">
    <!--      <el-form-item label="分类编码" prop="kindCode">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.kindCode"-->
    <!--          placeholder="请输入分类编码"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
          <el-form-item label="分类名称" prop="kindName">
            <el-input

              v-model="queryParams.kindName"
              placeholder="请输入分类名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
        </el-form-item>
    <!--      <el-form-item label="是否计量" prop="isMeasure">-->
    <!--        <el-select v-model="queryParams.isMeasure" placeholder="请选择是否计量" clearable size="small">-->
    <!--          <el-option-->
    <!--            v-for="dict in isMeasureOptions"-->
    <!--            :key="dict.dictValue"-->
    <!--            :label="dict.dictLabel"-->
    <!--            :value="dict.dictValue"-->
    <!--          />-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="是否折旧" prop="isDepreciation">-->
    <!--        <el-select v-model="queryParams.isDepreciation" placeholder="请选择是否折旧" clearable size="small">-->
    <!--          <el-option-->
    <!--            v-for="dict in isDepreciationOptions"-->
    <!--            :key="dict.dictValue"-->
    <!--            :label="dict.dictLabel"-->
    <!--            :value="dict.dictValue"-->
    <!--          />-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="是否末级" prop="isLast">-->
    <!--        <el-select v-model="queryParams.isLast" placeholder="请选择是否末级" clearable size="small">-->
    <!--          <el-option-->
    <!--            v-for="dict in isLastOptions"-->
    <!--            :key="dict.dictValue"-->
    <!--            :label="dict.dictLabel"-->
    <!--            :value="dict.dictValue"-->
    <!--          />-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="是否停用" prop="isStop">-->
    <!--        <el-select v-model="queryParams.isStop" placeholder="请选择是否停用" clearable size="small">-->
    <!--          <el-option-->
    <!--            v-for="dict in isStopOptions"-->
    <!--            :key="dict.dictValue"-->
    <!--            :label="dict.dictLabel"-->
    <!--            :value="dict.dictValue"-->
    <!--          />-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
  	<div ref="search"></div>
  	<div ref="btns">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['assets:assetskind:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['assets:assetskind:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['assets:assetskind:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    </div>

    <el-table
    	v-loading="loading"
    	:data="assetskindList"
    	row-key="kindCode"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      @selection-change="handleSelectionChange"
      ref="requirementTable"
    	stripe highlight-current-row @row-click="handleRowClick"
    	:height="tableHeight"
    	:default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="分类编码" align="center" prop="kindCode" min-width="120"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="分类名称" align="left" min-width="140" show-overflow-tooltip prop="kindName"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="是否计量" align="center" prop="isMeasure" width="100" :formatter="isMeasureFormat"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="是否折旧" align="center" prop="isDepreciation" width="100"  :formatter="isDepreciationFormat"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="折旧年限(院)" align="center" width="120"  prop="yearsInstitutes"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="折旧年限(科)" align="center" width="120"  prop="yearsDept"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="折旧方法" align="left" min-width="120" prop="depreciationWay" :formatter="depreciationWayFormat"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="是否末级" align="center" width="100"  prop="isLast" :formatter="isLastFormat"/>
      <el-table-column sortable="custom" :sort-orders="['descending', 'ascending']" label="是否停用" align="center" width="100"  prop="isStop" :formatter="isStopFormat"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改固定资产分类对话框 -->
    <addEdit v-if="open" :open="open" :id="addEditId"  @cancel="cancel" @success="successSubmit"></addEdit>
  </div>
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
import addEdit from "./addEdit";

export default {
  name: "Assetskind",
  components: { addEdit },
  data() {
    return {
      defaultSort: { prop: "yearsInstitutes", order: "descending" },
      // 当前选中的行
      selectionRow: [],
      tableHeight: "auto",
      kiCode: "",
      // 新增修改id
      addEditId: "",
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      kindCodes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 固定资产分类表格数据
      assetskindList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 资产类型字典
      // assetTypeOptions: [],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        kindCode: null,
        kindName: null,
        isMeasure: null,
        isDepreciation: null,
        isLast: null,
        isStop: null
      },
      // 表单参数
      form: {},
      // 表单校验
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
      }
    };
  },
  created() {
    this.getList();
    // this.assetsTypeInfo().then(response => {
    //   this.assetTypeOptions = response.data;
    // });
    this.getDicts("hr_is_change").then(response => {
      this.isMeasureOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isDepreciationOptions = response.data;
    });
    this.getDicts("hr_asset_depreciation_plan").then(response => {
      this.depreciationWayOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isCostOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isLastOptions = response.data;
    });
    this.getDicts("hr_is_change").then(response => {
      this.isStopOptions = response.data;
    });
  },
  mounted() {
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
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 提交成功 */
    successSubmit(flag) {
      this.open = flag;
      this.getList();
    },
    resizeHandler() {
      let a = this.$refs.search.offsetHeight;
      let b = this.$refs.btns.offsetHeight;
      let h = a + b + this.$store.state.settings.tableHeight + 30;
      h = h + "px";
      this.tableHeight = `calc(100vh - ${h})`;
    },
    // 点击行
    handleRowClick(row, column, event) {
      let requirementTable = this.$refs.requirementTable;
      let findRow = this.selectionRow.find(c => c.id == row.id);
      if (findRow) {
        requirementTable.toggleRowSelection(row, false);
        return;
      }
      requirementTable.clearSelection(); //清空列表的选中
      requirementTable.toggleRowSelection(row, true);
    },
    /** 查询固定资产分类列表 */
    getList() {
      this.loading = true;
      listAssetskind(this.queryParams).then(response => {
        if (response.code == 200) {
          console.log(response);
          this.assetskindList = this.handleTree(
            response.rows,
            "kindCode",
            "parentCode"
          );
          this.total = response.total;
        } else {
          this.msgError(response.msg);
        }
        this.loading = false;
      });
    },
    // 资产类型字典翻译
    // assetTypeFormat(row, column) {
    //   return this.selectDictLabel(this.assetTypeOptions, row.assetType);
    // },
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
    // 取消按钮
    cancel(flag) {
      this.open = flag;
    },
    // 表单重置
    reset() {
      this.form = {
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
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 30;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection, row) {
      let requirementTable = this.$refs.requirementTable;
      if (selection.length > 1) {
        requirementTable.clearSelection(); //清空列表的选中
        requirementTable.toggleRowSelection(
          selection[selection.length - 1],
          true
        );
        selection = Array(selection[selection.length - 1]);
      } else if (selection.length === 1) {
        selection = Array(selection[selection.length - 1]);
      } else {
        selection = [];
      }
      // 方便点击行取消选中
      this.selectionRow = selection;
      // 设置行高亮
      requirementTable.setCurrentRow(selection[0]);

      this.ids = selection.map(item => item.id);
      this.kindCodes = selection.map(item => item.kindCode);
      this.single = selection.length !== 1;
      this.multiple = selection.length !== 1;

      let isLast = selection.map(item => item.isLast);
      if (isLast[0] == "0") {
        this.multiple = false;
      } else if (isLast[0] == "1") {
        this.multiple = true;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      //      this.kiCode = "系统自动生成";
      this.reset();
      this.addEditId = "";
      this.open = true;
      this.title = "固定资产分类 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      this.addEditId = this.ids[0];
      this.open = true;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAssetskind(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addAssetskind(this.form).then(response => {
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
      const ids = row.id || this.ids;
      const kindCodes = row.kindCode || this.kindCodes;
      this.$confirm(
        '是否确认删除固定资产分类编号为"' + kindCodes + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delAssetskind(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有固定资产分类数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.exportLoading = true;
          return exportAssetskind(queryParams);
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
    }
  }
};
</script>
