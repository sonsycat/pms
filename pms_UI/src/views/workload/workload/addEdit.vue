<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="年月" prop="workloadDate">
            <el-date-picker clearable size="mini"
                            v-model="form.workloadDate"
                            type="month"
                            value-format="yyyy-MM-dd"
                            placeholder="选择年月"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目编码" prop="projectCode">
            <el-input v-model="form.projectCode" placeholder="请输入项目编码" maxlength="50" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目名称" prop="projectName">
            <el-input v-model="form.projectName" placeholder="请输入项目名称" maxlength="50" show-word-limit @blur="getPym"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="类别" prop="type">
            <el-select v-model="form.type" placeholder="请选择类别">
              <el-option label="请选择字典生成" :value="item.typeCode" v-for="item in typeList" :label="item.typeName"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="拼音码" prop="spell">
            <el-input v-model="form.spell" placeholder="请输入拼音码" disabled maxlength="50" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="住院积分" prop="hospitalPoints">
            <el-input-number :precision="2" :step="0.01" :min="0" controls-position="right" v-model="form.hospitalPoints"
                             placeholder="请输入住院积分" show-word-limit/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="门诊积分" prop="outpatientPoints">
            <el-input-number :precision="2" :step="0.01" :min="0" controls-position="right" v-model="form.outpatientPoints"
                             placeholder="请输入门诊积分" show-word-limit/>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addWorkload, getWorkload, listWorkload, updateWorkload } from '@/api/workload/workload'
import { listType } from '@/api/workload/type'
import pinyin from 'js-pinyin'

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
          this.title = '工作量管理-编辑'
          this.getDetailInfo()
        } else {
          this.id = ''
          this.title = '工作量管理-新增'
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
      title: '工作量管理-新增',
      openDialog: this.open,
      typeList: [],
      saveLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workloadDate: [
          { required: true, message: '年月', trigger: ['blur', 'change'] }
        ],
        projectCode: [
          {required: true, message: '项目编码', trigger: ['blur', 'change'] }
        ],
        projectName: [
          { required: true, message: '项目名称', trigger: ['blur', 'change'] }
        ],
        type: [
          { required: true, message: '类型', trigger: ['blur', 'change'] }
        ],
        hospitalPoints: [
          { required: true, message: '住院积分', trigger: ['blur', 'change'] }
        ],
        outpatientPoints: [
          { required: true, message: '门诊', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  created() {

    listType(null).then(response => {
      this.typeList = response.rows
    })
    this.getList()
  },
  methods: {
    getDetailInfo() {
      getWorkload(this.id).then(response => {
        console.log(response)
        this.form = response.data
      })
    },
    /** 查询工作量列表 */
    getList() {
      this.loading = true
      listWorkload(this.queryParams).then(response => {
        this.workloadList = response.rows
        this.total = response.total
        this.loading = false
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
        projectCode: null,
        projectName: null,
        type: null,
        spell: null,
        hospitalPoints: null,
        outpatientPoints: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        workloadDate: null
      }
      this.resetForm('form')
    },
    getPym() {
      // 全是字母的情况下,拼音码等于所有字母
      if (/^[a-zA-Z]*$/.test(this.form.projectName)) {
        this.form.spell = this.form.projectName
        return
      }
      // 有汉字的情况下,转为首字母
      let fullName = pinyin.getCamelChars(this.form.projectName).toUpperCase()
      let pym = ''
      for (var i = 0; i < fullName.length; i++) {
        var c = fullName.charAt(i)
        if (/^[A-Z]+$/.test(c)) {
          pym += c
        }
      }
      this.form.spell = pym
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.saveLoading = true
          if (this.form.id != null) {
            updateWorkload(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.$emit('success', false)
              this.saveLoading = false
            }).catch((err)=>{
              this.saveLoading = false;
            });
          } else {
            addWorkload(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.$emit('success', false)
              this.saveLoading = false
            }).catch((err)=>{
              this.saveLoading = false;
            });
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
    .el-input--mini,.el-input-number--mini,.el-select--mini,.el-date-picker--mini{
      width: 100%;
    }
  }
}
</style>
