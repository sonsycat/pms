<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="500px" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose" v-loading="saveLoading"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="奖金名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入奖金名称" maxlength="200" show-word-limit disabled/>
      </el-form-item>
      <el-form-item label="奖金年月" prop="period">
        <el-date-picker clearable :editable="false" size="small" class="w100"
                        v-model="form.period" type="month" value-format="yyyy-MM-dd" @change="changePeriod"
                        placeholder="选择奖金年月" disabled
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="奖金状态" prop="auditState">
        <el-select v-model="form.auditState" filterable clearable disabled class="w100">
          <el-option
            v-for="dict in auditStateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="form.createTime" maxlength="200" show-word-limit disabled/>
      </el-form-item>
      <formItem :showDialog.sync="showDialog" :showBaseInfo="btn3" :open="btn4" :open2="btn5" :comment="form"
                :userOptions="userOptions" @success="formItemSuccess"
      ></formItem>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <btns v-if="btn3" @success="successSubmit" :open="btn3" :id="id" :proclnsIds="proclnsIds" :comment="form.comment"
            :assignee="form.assignee"
      ></btns>
      <el-button v-if="btn2" type="primary" @click="submitForm" :loading="saveLoading">审 批</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getCalculation, listCalculation } from '@/api/bonus/calculation'
import { getEmpList } from '@/api/until'
import btns from '@/views/bonus//btns'
import formItem from '@/views/components/formItemNew'
import { commitAudit } from '../../../api/bonus/calculation'

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
    },
    proclnsIds: {
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
          this.getDetailInfo()
        } else {
          this.id = ''
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val
        this.getDetailInfo()
      },
      deep: true
    }
  },
  components: { btns, formItem },
  data() {
    return {
      title: '奖金审核管理',
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      auditStateOptions: [],
      showDialog: false,
      // 下级节点办理人
      nextAssignee: [],
      //用户列表
      userOptions: [],
      // 按钮编码
      btn1: false,
      btn2: false,
      btn3: false,
      btn4: false,
      btn5: false,
      datas: {}
    }
  },
  created() {
    this.getList()
    this.getDicts('asset_aduit_state').then(response => {
      this.auditStateOptions = response.data
    })
  },
  methods: {
    getDetailInfo() {
      getCalculation(this.id).then(response => {
        this.form = response.data
        console.log(response.data)
        let that = this
        let proclnsIds = response.data.procedureId
        // this.proclnsIds = response.data.procedureId
        this.datas = {}
        getEmpList(
          that,
          this.$store.state.user.name,
          proclnsIds,
          this.userOptions,
          this.btn1,
          this.btn2,
          this.btn3,
          this.btn4,
          this.btn5
        )
      })
    },
    /** 查询奖金核算列表 */
    getList() {
      this.loading = true
      listCalculation(this.queryParams).then(response => {
        this.calculationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState)
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancel', false)
    },
    // 取消
    cancel() {
      this.openDialog=false
      this.$emit('cancel', false)
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        period: null,
        auditState: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    changePeriod() {
      this.setName()
    },

    /** 提交按钮 */
    submitForm() {
      this.showDialog = true
    },
    formItemSuccess(data) {
      this.form = JSON.parse(JSON.stringify(data))
      this.form.compCode = this.$store.state.user.userInfo.deptId
      this.saveLoading = true
      commitAudit(this.form).then(response => {
        this.msgSuccess('提交成功')
        this.$emit('cancel', false)
        this.saveLoading = false
      })
    },
    successSubmit() {
      this.$emit('success', false)
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 500px;
  overflow: hidden;

  .el-dialog__body {
    height: calc(100% - 100px);
    overflow: auto;
  }
}
</style>
