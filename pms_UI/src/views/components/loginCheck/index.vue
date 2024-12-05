<template>
  <div>
    <el-theme-dialog title="提示" :visible.sync="checkOpen" width="300px" append-to-body @close="close">
      <span>系统未授权，请激活！</span>
      <span slot="footer">
        <el-button type="primary" @click="submitCheck">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </span>
    </el-theme-dialog>
    <!-- 填写授权码对话框 -->
    <el-theme-dialog title="授权激活" :visible.sync="dialogOpen" width="500px" append-to-body>
      <el-form ref="activeForm" :model="activeForm" :rules="activeFormRules" label-width="100px">
        <el-form-item label="机器码" prop="mcode">
          <el-input v-model="activeForm.mcode" readonly="readonly" />
        </el-form-item>
        <el-form-item label="授权码" prop="code">
          <el-input type="textarea" v-model="activeForm.code" placeholder="请输入授权码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitActiveForm">确认激活</el-button>
        <el-button @click="closeActiveForm">关 闭</el-button>
      </div>
    </el-theme-dialog>
  </div>
</template>

<script>
  import {active} from '@/api/license';

  export default {
    props:{
      open:{
        type:Boolean,
        default:false
      },
      mcode:{
        type:[String,Number],
        default:""
      }
    },
    data() {
      return {
        checkOpen:this.open,
        dialogOpen:false,
        activeFormRules: {
          code: [
            { required: true, trigger: "blur", message: "授权码不能为空" }
          ]
        },
        // 授权定义
        activeForm: {
          mcode:"",
          code:""
        },

      };
    },
    methods: {
      // 确定激活
      submitCheck(){
        this.dialogOpen = true;
        this.resetForm('activeForm');
        this.activeForm.mcode = this.mcode;
      },
      // 授权激活 确认
      submitActiveForm() {
        this.$refs.activeForm.validate(valid => {
          if (valid) {
            active(this.activeForm.code).then(r => {
              console.log('active return code:' + r.code);
              if(r.code == 0) {
                this.$emit("success")
                this.dialogOpen = false;
              } else {
                this.$emit("error", r.msg)
                this.dialogOpen = false;
              }
            });
          }
        });
      },
      // 提示关闭
      close() {
        this.$emit('cancel');
      },
      // 授权激活关闭
      closeActiveForm(){
        this.dialogOpen = false;
      }
    }
  };
</script>
