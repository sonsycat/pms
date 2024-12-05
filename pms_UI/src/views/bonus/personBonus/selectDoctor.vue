<template>
  <el-dialog :title="title" :visible.sync="openDialog" width="50%" custom-class="el-button--primary" append-to-body
             :close-on-click-modal="false" @close="handleDialogClose"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="科室" prop="deptCode">
            <el-select v-model="form.deptRoomCode" filterable placeholder="请选择科室" @change="changeDept" style="width: 100%">
              <el-option
                v-for="room in deptList"
                :key="room.deptRoomCode"
                :label="room.deptRoomName"
                :value="room.deptRoomCode"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-card>
            <el-col :span="12">
              <span style="font-size:16px">待选人员</span>
            </el-col>
            <el-table
              style="margin-top:10px"
              v-loading="indexListLoading"
              :data="roleList"
              height="500px"
              :row-class-name="tableRowClassName"
              @select="selectionProject"
              @row-click="rowClickWaitClass"
            >
              <el-table-column label="人员" align="left" prop="docName" show-overflow-tooltip/>
            </el-table>
          </el-card>
        </el-col>
        <el-col :span="12" class="mb10">
          <el-card>
            <el-col :span="12">
              <span style="font-size:16px">已选人员</span>
            </el-col>
            <el-table
              style="margin-top:10px"
              v-loading="roleSelectedListLoading"
              :data="roleSelectedList"
              height="500px"
              :row-class-name="tableRowClassName"
              @row-click="rowClickHasClass"
              @select="selectionProjectDept"
            >
              <el-table-column label="人员" align="left" prop="docName" show-overflow-tooltip/>
            </el-table>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">保 存</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>


import { listDept, listDoctorNotBonus } from '@/api/evaluateData/evaluateDataPerson'

export default {
  name: 'select',
  props: {

    openSelect: {
      type: Boolean,
      default: false
    },
    deptCode: {
      type: String,
      default: null
    },
    actDate: {
      type: String,
      default: null
    },
    doctorList: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      title: '选择新增人员',
      openDialog: this.openSelect,
      roleSelectedListLoading: false,
      indexListLoading: false,
      // 表单参数
      form: { docName: '' },
      // 表单校验
      rules: {},
      //待选角色
      queryParamsIndex: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
      },
      //已选角色
      queryParamsIndexSelected: {
        pageNum: 1,
        pageSize: this.$store.state.settings.pageSize,
        compCode: this.$store.state.user.userInfo.deptId,
        setCode: null
      },
      // 待选角色数据
      roleList: [],
      // 已选角色数据
      roleSelectedList: [],
      deptList: [],
      docCodeList: [],
    }
  },
  created() {
    // this.getSelectedRoleList()
    this.getList(this.deptCode)
    this.getDeptList()
    this.deptRoomCode = "";
  },
  mounted() {
  },
  methods: {
    resetQuery() {
      this.form.deptRoomCode = ''
      this.deptRoomCode = ""
    },
    handleQuery() {
      /* let exitHisCode = this.roleSelectedList.map(item => {
        return item.roleKey
      }) */
      this.getList(this.deptCode)
    },
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex
    },
    // 待选科室多选框选中数据
    selectionProject(selection, row) {
      this.rowClickWaitClass(selection[0])
    },
    //待选物资类型 点击行
    rowClickWaitClass(selection) {
      for (let l in this.roleSelectedList){
        if (selection.docCode == this.roleSelectedList[l].docCode){
          this.msgError('此人员已有奖金数据，无法重复添加！')
          return
        }
      }
      let {
        docCode,
        docName
      } = selection
      this.roleSelectedList.push({
        docCode,
        docName
      })
      this.roleList.splice(selection.index, 1)
    },
    // 已选科室多选框选中数据
    selectionProjectDept(selection, row) {
      this.rowClickHasClass(selection[0])
    },
    // 已经选择点击行
    rowClickHasClass(selection) {
      for (let l in this.roleList){
        if (selection.docCode == this.roleList[l].docCode){
          this.msgError('此人员奖金数据不允许删除！')
          return
        }
      }
      let {
        docCode,
        docName
      } = selection
      this.roleList.push({
        docCode,
        docName
      })
      this.roleSelectedList.splice(selection.index, 1)
    },
    /** 查询人员列表 */
    getList() {
      listDoctorNotBonus({ deptCode: this.deptCode, actDate: this.actDate, deptRoomCode: this.deptRoomCode}).then(response => {
        this.roleList = response.rows
        let codes = this.doctorList.map(item => item.docCode)
        this.roleList = this.roleList.filter((item) => {
          return !codes.includes(item.docCode)
        })
      })
    },
    /** 查询科室列表 */
    getDeptList() {
      this.loading = true;
      listDept().then(response => {
        this.deptList = response.rows;
        this.loading = false;
      });
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit('cancelSelect', false)
    },
    // 取消
    cancel() {
      this.$emit('cancelSelect', false)
    },
    /** 提交按钮 */
    submitForm() {
      this.$emit('docCodeList', this.roleSelectedList)
      this.$emit('cancelSelect', false)
    },
    changeDept(value){
      this.deptRoomCode = value
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .el-dialog {
  max-height: 86%;
  overflow: hidden;

  .el-dialog__body {
    height: calc(100% - 100px);
    overflow: auto;
  }
}
</style>
