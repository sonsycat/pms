<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="医生编码" prop="docCode">
        <el-input
          v-model="queryParams.docCode"
          placeholder="请输入医生编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医生姓名" prop="docName">
        <el-input
          v-model="queryParams.docName"
          placeholder="请输入医生姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="医生级别" prop="docLevel">
        <el-select v-model="queryParams.docLevel" placeholder="请选择医生级别" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="机构" prop="orgCode">
        <!--<el-input-->
        <!--  v-model="queryParams.orgCode"-->
        <!--  placeholder="请输入机构"-->
        <!--  clearable-->
        <!--  size="small"-->
        <!--  @keyup.enter.native="handleQuery"-->
        <!--/>-->
        <treeselect v-model="queryParams.orgCode" :options="orgOptions" :show-count="true"  placeholder="请选择机构" style="width:250px;"/>

      </el-form-item>
      <!--<el-form-item label="科室" prop="depCode">-->
      <!--  <el-input-->
      <!--    v-model="queryParams.depCode"-->
      <!--    placeholder="请输入科室"-->
      <!--    clearable-->
      <!--    size="small"-->
      <!--    @keyup.enter.native="handleQuery"-->
      <!--  />-->
      <!--</el-form-item>-->
      <el-form-item label="用户性别" prop="sex">
        <!--<el-select v-model="queryParams.sex" placeholder="请选择用户性别" clearable size="small">-->
        <!--  <el-option label="请选择字典生成" value=""/>-->
        <!--</el-select>-->
        <el-select v-model="queryParams.sex" placeholder="请选择用户性别" clearable size="small" >
          <el-option
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:doctor:add']"
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
          v-hasPermi="['system:doctor:edit']"
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
          v-hasPermi="['system:doctor:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-upload2"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:doctor:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="doctorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--<el-table-column label="ID" align="center" prop="id"/>-->
      <el-table-column label="医生编码" align="center" prop="docCode"/>
      <el-table-column label="医生姓名" align="left" prop="docName"/>
      <el-table-column label="医生级别" align="center" prop="docLevel"/>
      <el-table-column label="机构" align="left" prop="orgName"/>
      <el-table-column label="科室" align="left" prop="depCode"/>
      <!--<el-table-column label="用户性别" align="center" prop="sex"/>-->
      <el-table-column  label="用户性别" :formatter="sexFormat" width="80" prop="sex"/>
      <el-table-column label="出生日期" align="center" prop="birthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="证件类型" align="center" prop="cardType"/>
      <!--<el-table-column label="身份证号" align="center" prop="cardNo"/>-->
      <el-table-column label="学历" align="center" prop="education"/>
      <!--<el-table-column label="职业类型" align="center" prop="profType"/>-->
      <!--<el-table-column label="职业级别" align="center" prop="profLevel"/>-->
      <!--<el-table-column label="职业证号" align="center" prop="profNum"/>-->
      <!--<el-table-column label="职业机构" align="center" prop="profOrg"/>-->
      <!--<el-table-column label="职称" align="center" prop="technical"/>-->
      <!--<el-table-column label="电话" align="center" prop="tel"/>-->
      <!--<el-table-column label="email" align="center" prop="email"/>-->
      <el-table-column label="人员状态" align="center" prop="personStatus"/>
      <el-table-column label="入职时间" align="center" prop="entryTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.entryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="招聘方式" align="center" prop="recruitmentMethods"/>-->
      <!--<el-table-column label="家庭地址" align="center" prop="homeAddress"/>-->
      <!--<el-table-column label="备注" align="center" prop="remark"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            title="修改"
            v-hasPermi="['system:doctor:edit']"
          >
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            title="删除"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:doctor:remove']"
          >
          </el-button>
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

    <!-- 添加或修改医生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body custom-class="el-button--primary">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="医生编码" prop="docCode">
              <el-input v-model="form.docCode" placeholder="系统默认生成" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="医生姓名" prop="docName">
              <el-input v-model="form.docName" placeholder="请输入医生姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="医生级别" prop="docLevel">
              <el-select v-model="form.docLevel" placeholder="请选择医生级别" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择用户性别" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="机构" prop="orgCode">
              <!--<el-input v-model="form.orgCode" placeholder="请输入机构"/>-->
              <treeselect v-model="form.orgCode" :options="orgOptions" :show-count="true"  placeholder="请选择机构" style="width:250px;"/>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="科室" prop="depCode">
              <el-input v-model="form.depCode" placeholder="请输入科室"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthday" >
              <el-date-picker clearable size="small"
                              v-model="form.birthday"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择出生日期" style="width:250px;">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件类型" prop="cardType">
              <el-select v-model="form.cardType" placeholder="请选择证件类型" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="身份证号" prop="cardNo">
              <el-input v-model="form.cardNo" placeholder="请输入身份证号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学历" prop="education">
              <el-select v-model="form.education" placeholder="请选择学历" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职业类型" prop="profType">
              <el-select v-model="form.profType" placeholder="请选择职业类型" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职业级别" prop="profLevel">
              <el-select v-model="form.profLevel" placeholder="请选择职业级别" style="width:250px;">
                <el-option label="请选择字典生成" value=""/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职业证号" prop="profNum">
              <el-input v-model="form.profNum" placeholder="请输入职业证号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职业机构" prop="profOrg">
              <el-input v-model="form.profOrg" placeholder="请输入职业机构"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="职称" prop="technical">
              <el-input v-model="form.technical" placeholder="请输入职称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入电话"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="email" prop="email">
              <el-input v-model="form.email" placeholder="请输入email"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员状态">
              <el-radio-group v-model="form.personStatus">
                <el-radio label="1">请选择字典生成</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="入职时间" prop="entryTime">
              <el-date-picker clearable size="small"
                              v-model="form.entryTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择入职时间" style="width:250px;">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item label="招聘方式" prop="recruitmentMethods">
            <el-select v-model="form.recruitmentMethods" placeholder="请选择招聘方式" style="width:250px;">
              <el-option label="请选择字典生成" value=""/>
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="家庭地址" prop="homeAddress">
          <el-input v-model="form.homeAddress" placeholder="请输入家庭地址"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listDoctor, getDoctor, delDoctor, addDoctor, updateDoctor, exportDoctor} from "@/api/system/doctor";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Doctor",
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 医生信息表格数据
      doctorList: [],
      // 机构树选项
      orgOptions: undefined,
      // 性别状态字典
      sexOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        docCode: null,
        docName: null,
        docLevel: null,
        orgCode: null,
        depCode: null,
        sex: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        docName: [
          {required: true, message: "医生姓名不能为空", trigger: "blur"}
        ],
        orgCode: [
          {required: true, message: "机构不能为空", trigger: "blur"}
        ],
        depCode: [
          {required: true, message: "科室不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getTreeselect();
    this.getList();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.orgOptions = response.data;
      });
    },
    /** 查询医生信息列表 */
    getList() {
      this.loading = true;
      listDoctor(this.queryParams).then(response => {
        this.doctorList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        docCode: null,
        docName: null,
        docLevel: null,
        orgCode: null,
        depCode: null,
        sex: null,
        birthday: null,
        cardType: null,
        cardNo: null,
        education: null,
        profType: null,
        profLevel: null,
        profNum: null,
        profOrg: null,
        technical: null,
        tel: null,
        email: null,
        personStatus: "0",
        entryTime: null,
        recruitmentMethods: null,
        homeAddress: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.open = true;
      this.title = "医生信息 / 新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      const id = row.id || this.ids
      getDoctor(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "医生信息 / 修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDoctor(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDoctor(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const ids = row.id || this.ids;
      const docName = row.docName;
      this.$confirm('是否确认删除医生名称为"' + docName + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDoctor(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有医生信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportDoctor(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    },
    // 岗位状态字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
  }
};
</script>
