<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="40%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="类别编码" prop="typeCode">
        <el-input v-model="form.typeCode" placeholder="系统自动生成" disabled maxlength="50" show-word-limit/>
      </el-form-item>
      <el-form-item label="类别名称" prop="typeName">
        <el-input v-model="form.typeName" placeholder="请输入类别名称" maxlength="50" show-word-limit/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" :loading="saveLoading">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addType, getType, listType, updateType } from '@/api/workload/type'

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
          this.title = '工作量-类别设置管理-编辑'
          this.getDetailInfo()
        } else {
          this.id = ''
          this.title = '工作量-类别设置管理-新增'
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
      title: '工作量-类别设置管理-新增',
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getDetailInfo() {
      getType(this.id).then(response => {
        console.log(response)
        this.form = response.data
      })
    },
    /** 查询工作量-类别设置列表 */
    getList() {
      this.loading = true
      listType(this.queryParams).then(response => {
        this.typeList = response.rows
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
        projectCode: null,
        typeName: null,
        typeCode: null,
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
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.saveLoading = true
          if (this.form.id != null) {
            updateType(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.$emit('success', false)
              this.saveLoading = false
            })
          } else {
            addType(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.$emit('success', false)
              this.saveLoading = false
            })
          }
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  height: 222px!important;
  overflow: hidden;
  .el-dialog__body {
    height: calc(100% - 100px)!important;
    overflow: auto;
  }
}
</style>
