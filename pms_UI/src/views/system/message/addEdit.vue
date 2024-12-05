<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="700px" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主键" prop="id">
          <el-input v-model="form.id" placeholder="请输入主键" />
        </el-form-item>
        <el-form-item label="消息标题" prop="title">
          <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="消息内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="消息访问路径" prop="msgUrl">
          <el-input v-model="form.msgUrl" placeholder="请输入消息访问路径" />
        </el-form-item>
        <el-form-item label="接收用户id" prop="receiveUserId">
          <el-input v-model="form.receiveUserId" placeholder="请输入接收用户id" />
        </el-form-item>
        <el-form-item label="发送用户id" prop="sendUserId">
          <el-input v-model="form.sendUserId" placeholder="请输入发送用户id" />
        </el-form-item>
        <el-form-item label="发送时间" prop="sendTime">
          <el-date-picker clearable size="small"
            v-model="form.sendTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发送时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否已读：1-已读,0-未读" prop="isRead">
          <el-input v-model="form.isRead" placeholder="请输入是否已读：1-已读,0-未读" />
        </el-form-item>
        <el-form-item label="是否已读：1-已读,0-未读" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入是否已读：1-已读,0-未读" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { listMessage, getMessage, delMessage, addMessage, updateMessage, exportMessage } from "@/api/system/message";
import Editor from '@/components/Editor';

export default {
  name: "addEdit",
  props: {
    open: {
      type: Boolean,
      default: false
    },
    id: {
      type: [String, Number],
      default: ""
    }
  },
  watch: {
    id: {
      handler(val) {
        this.reset();
        if (val) {
          this.id = val;
          this.title = "消息管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "消息管理-新增";
        }
      },
      deep: true,
      immediate: true
    },
    open: {
      handler(val) {
        this.openDialog = val;
      },
      deep: true
    }
  },
  components: {
    Editor,
  },
  data() {
    return {
      title: "消息管理-新增",
      openDialog: this.open,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
  	getDetailInfo() {
      getMessage(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询消息管理列表 */
    getList() {
      this.loading = true;
      listMessage(this.queryParams).then(response => {
        this.messageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 弹框关闭
    handleDialogClose() {
      this.$emit("cancel", false);
    },
    // 取消
    cancel() {
      this.$emit("cancel", false);
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        content: null,
        msgUrl: null,
        receiveUserId: null,
        sendUserId: null,
        sendTime: null,
        isRead: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMessage(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.$emit("success", false);
            });
          } else {
            addMessage(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.$emit("success", false);
            });
          }
        }
      });
    },
}};
</script>
