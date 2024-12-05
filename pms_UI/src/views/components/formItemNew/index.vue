<template>
<div>
  <el-dialog
    title="提示"
    :visible.sync="dialogOpen"
    v-if="dialogOpen"
    width="500px"
    :close-on-click-modal="false"
    custom-class="el-button--primary"
    append-to-body
    :before-close="onClose">
    <ul>当前节点：{{currActivity}}</ul>
    <el-row>
      <el-form ref="formItem" :model="comment"  label-width="80px">
        <el-col :span="24">
          <el-form-item label="审核意见" v-if="openDialog" prop="comment">
            <el-input v-model="comment.comment" type="textarea" placeholder="请输入审核意见" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <staffSearch v-if="openDialog2" label="下节点审核人" :rules="assigneeRules"  :staffParams.sync = "comment" formItemProp="assignee"  inputProp="assigneepName" :userInfoList = "userOptions"></staffSearch>
        </el-col>
      </el-form>
    </el-row>
    <span slot="footer" class="dialog-footer">
      <el-button @click="onClose">取 消</el-button>
      <el-button type="primary" @click="sureStaffClick">确 定</el-button>
    </span>
  </el-dialog>
  <el-card  v-if="showBaseInfoCard" class="mb20 mt10">
    <ul>当前节点：{{currActivity}}</ul>
    <el-row>
      <el-form ref="formItem" :model="comment"  label-width="80px">
        <el-col :span="24">
          <el-form-item label="审核意见" v-if="openDialog" prop="comment">
            <el-input v-model="comment.comment" type="textarea" placeholder="请输入审核意见" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <staffSearch v-if="openDialog2" label="下节点审核人" :rules="assigneeRules" @handleStaff="handleStaff"  :staffParams.sync = "comment" formItemProp="assignee"  inputProp="assigneepName" :userInfoList = "userOptions"></staffSearch>
        </el-col>
      </el-form>
    </el-row>
  </el-card>
</div>
</template>
<script>
import { currActivityName } from "@/api/process/model";
export default {
  name: "addEdit",
  components: {},
  props: {
    open: {
      type: Boolean,
      default: false
    },
    open2: {
      type: Boolean,
      default: false
    },
    comment: {
      type: Object,
      default: {}
    },
    userOptions: {
      type: Array,
      default: {}
    },
    // 保存并提交显示弹框下级审核人
    showDialog:{
      type: Boolean,
      default: false
    },
    // 其余审核状态显示基本信息
    showBaseInfo:{
      type: Boolean,
     default: false
    }
  },
  data() {
    return {
      showBaseInfoCard:false,
      dialogOpen:this.showDialog,
      openDialog: this.open,
      openDialog2: this.open2,
      userOptions2: this.userOptions,
      currActivity: null,
      proclnsId: null,
      assigneeRules: [
        {
          required: true,
          message: "下节点审核人不能为空",
          trigger: ["change", "blur"]
        }
      ]
    };
  },
  watch: {
    showBaseInfo:{
      handler(val) {
        this.showBaseInfoCard = val;
      },
      deep: true,
      immediate:true
    },
    showDialog:{
      handler(val) {
        this.dialogOpen = val;
      },
      deep: true,
      immediate:true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    },
    open2: {
      handler(val) {
        this.openDialog2 = val;
      },
      deep: true
    },
    userOptions: {
      handler(val) {
        this.userOptions2 = val;
      },
      deep: true
    },
    "comment.procedureId": {
      handler(val) {
        this.proclnsId = val;
        this.getList();
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
  	handleStaff(res){
  		console.log(res);
  		this.$emit("formItem", res);
  	},
    getList() {
      if (this.proclnsId == null || this.proclnsId == "") {
      } else {
        let data = {
          procInsId: this.proclnsId,
          userId: this.$store.state.user.userInfo.userId,
        };
        currActivityName(data).then(response => {
          if (response.code == 200) {
            this.currActivity = response.data.activityName;
          } else {
            this.msgError(response.msg);
          }
        });
      }
    },
    onClose(){
      this.$emit('update:showDialog', false)
    },
    sureStaffClick(){
      this.$refs.formItem.validate(valid => {
        if(valid){
          this.$emit('success', this.comment);
          this.onClose()
        }
      })
    }
  },
  mounted() {}
};
</script>

<style>
</style>
