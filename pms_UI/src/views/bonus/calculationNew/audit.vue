<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="500px" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose" v-loading="saveLoading"
  >
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="奖金名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入奖金名称" maxlength="200" show-word-limit disabled/>
      </el-form-item>
      <el-form-item label="奖金年月" prop="period">
        <el-input v-model="form.period"  maxlength="200" show-word-limit disabled/>
<!--        <el-date-picker clearable :editable="false" size="small" class="w100"-->
<!--                        v-model="form.period" type="month" value-format="yyyy-MM-dd" @change="changePeriod"-->
<!--                        placeholder="选择奖金年月" disabled-->
<!--        >-->
<!--        </el-date-picker>-->
      </el-form-item>
      <el-form-item label="奖金状态" prop="auditState">
        <el-select v-model="form.auditState" filterable clearable  class="w100">
          <el-option
            v-for="dict in auditStateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注"/>
        </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" >审 批</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { bonusAudit } from '../../../api/bonus/calculationNew'

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
    auditName: {
      type: [String],
      default: ''
    },
    auditDate: {
      type: [String],
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
      },
      deep: true
    },
    auditName: {
      handler(val) {
        this.auditName = val
      },
      deep: true
    },
    auditDate: {
      handler(val) {
        this.auditDate = val
      },
      deep: true
    }
  },
  data() {
    return {
      title: '奖金审核管理',
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      auditStateOptions: [
        {dictValue:"2",dictLabel:"同意"},
        {dictValue:"3",dictLabel:"驳回"},
      ],
      datas: {}
    }
  },
  created() {
    this.form.name = this.auditName
    this.form.period = this.auditDate
  },
  methods: {
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
      this.form.id = this.id
      bonusAudit(this.form).then(response => {
        if(response.code == 200){
          this.msgSuccess('审批成功')
          this.$emit('cancel', false)
        }else{
          this.msgError(res.msg);
        }
      })
    },
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
