<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="24">
        <el-col :span="12">
          <el-form-item label="指标名称" prop="indexName">
            <el-input v-model="form.indexName" placeholder="请输入指标名称" maxlength="100" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="主管部门" prop="deptCode">
            <el-select clearable remote v-model="form.deptCode" placeholder="请选择主管部门" class="w100" filterable>
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
            <el-input-number controls-position="right" :min="0" :max="100" :precision="2" v-model="form.indexScore"
                             placeholder="请输入指标分值"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="模板编码" prop="tempCode">
            <el-select clearable filterable remote v-model="form.tempCode" placeholder="请选择模板编码" @change="getTempList">
              <el-option
                v-for="dict in modelCodeOption"
                :key="dict.id"
                :label="dict.formName"
                :value="dict.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="时间列" prop="dateColName">
            <el-input v-model="form.dateColName" placeholder="请输入时间列" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门列" prop="deptColName">
            <el-input v-model="form.deptColName" placeholder="请输入部门列" disabled/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="数值列" prop="numColName">
            <el-input v-model="form.numColName" placeholder="请输入数值列" disabled/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm(true)" :loading="addSaveLoading">保存并继续添加</el-button>
      <el-button type="primary" @click="submitForm(false)" :loading="saveLoading">保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getDeptByComp } from '@/api/system/dict/data'
import { addIndex, getIndex, getTempCode, updateIndex } from '@/api/quality/index'
import { listFormDesign } from '@/api/formDesign/formDesign'

export default {
  name: 'addEdit',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ''
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset()
        if (val) {
          this.id = val
          this.title = '质量考评指标管理-编辑'
          this.getDetailInfo()
        } else {
          this.id = ''
          this.title = '质量考评指标管理-新增'
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val
      },
      deep: true
    }
  },
  components: {},
  data() {
    return {
      title: '质量考评指标管理-新增',
      openDialog: this.open,
      saveLoading: false,
      addSaveLoading: false,
      deptList: [],
      //模板数据
      modelCodeOption: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        indexName: [
          { required: true, message: '指标名称不能为空', trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '主管部门不能为空', trigger: ['blur', 'change'] }
        ],
        indexScore: [
          { required: true, message: '指标分值不能为空', trigger: 'blur' }
        ]
        // tempCode:[
        //   { required: true, message: "模板编码不能为空", trigger: ["blur","change"] }
        // ],
        // dateColName:[
        //   { required: true, message: "时间列不能为空", trigger: ["blur","change"] }
        // ],
        // deptColName:[
        //   { required: true, message: "部门列不能为空", trigger: ["blur","change"] }
        // ],
        // numColName:[
        //   { required: true, message: "数值列不能为空", trigger: ["blur","change"] }
        // ],
      },
      dateColId: null,
      deptColId: null,
      numColId: null,
      // 查询参数
      queryParams: {
        // pageNum: 1,
        // pageSize: 30,
        compCode: this.$store.state.user.userInfo.deptId
      }
    }
  },
  created() {
    this.getDeptByComp()
    this.getTempList()
  },
  methods: {
    getDetailInfo() {
      getIndex(this.id).then(response => {
        this.form = response.data
      })
    },
    // 获取当前登录用户单位下的科室
    getDeptByComp(compCode) {
      let data = {
        compCode: this.$store.state.user.userInfo.deptId
      }
      getDeptByComp(data).then(res => {
        this.deptList = res.data
      })
    },
    //获取模板编码信息
    getTempList(id) {
      let obj = { formDesignId: id }
      listFormDesign(this.queryParams).then(res => {
        if (res.code == 200) {
          this.modelCodeOption = res.rows
        }
      })
      this.changeTempFun(obj)
    },
    //获取模板编码相关列信息
    changeTempFun(id) {
      getTempCode(id).then(res => {
        if (res.code == 200) {
          res.data.forEach(item => {
            if (item.fieldName == 'cs_date') {
              this.form.dateColName = item.fieldDesc
              this.dateColId = item.id
            } else if (item.fieldName == 'cs_dept') {
              this.form.deptColName = item.fieldDesc
              this.deptColId = item.id
            } else {
              this.form.numColName = item.fieldDesc
              this.numColId = item.id
            }
          })
        }
      })

    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancel', false)
    },
    // 取消
    cancel() {
      this.$emit('cancel', false)
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compCode: null,
        accountCode: null,
        deptCode: null,
        deptName: null,
        indexCode: null,
        indexName: null,
        indexScore: null,
        tempCode: null,
        dateColName: null,
        deptColName: null,
        numColName: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 提交按钮 */
    submitForm(type) {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.dateColName = this.dateColId
          this.form.deptColName = this.deptColId
          this.form.numColName = this.numColId
          this.form.compCode = this.$store.state.user.userInfo.deptId
          if (this.form.id != null) {
            if (type) {
              this.addSaveLoading = true
              updateIndex(this.form).then(response => {
                this.msgSuccess('修改成功')
                this.reset()
                this.addSaveLoading = false
                this.$emit('success', true)
              })
            } else {
              this.saveLoading = true
              updateIndex(this.form).then(response => {
                this.msgSuccess('修改成功')
                this.$emit('success', false)
                this.saveLoading = false
              })
            }
          } else {
            if (type) {
              this.addSaveLoading = true
              addIndex(this.form).then(response => {
                this.msgSuccess('新增成功')
                this.addSaveLoading = false
                this.reset()
                this.$emit('success', true)
              })
            } else {
              this.saveLoading = true
              addIndex(this.form).then(response => {
                this.msgSuccess('新增成功')
                this.$emit('success', false)
                this.saveLoading = false
              })
            }
          }
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  min-height: 20%;
  overflow: hidden;

  .el-dialog__body {
    height: calc(100% - 100px);
    overflow: auto;

    .el-select {
      width: 100%;
    }

    .el-input-number--mini {
      width: 100%;
    }
  }
}
</style>
