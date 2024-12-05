<template>
    <el-dialog :title="title" :visible.sync="openDialog" width="500px" custom-class="el-button--primary" append-to-body :close-on-click-modal="false" @close="handleDialogClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="奖金年月" prop="period">
          <el-date-picker clearable :editable="false" size="small" class="w100"
            v-model="form.period" type="month" value-format="yyyy-MM-dd" @change="changePeriod" placeholder="选择奖金年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="奖金名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入奖金名称" maxlength="200" show-word-limit/>
        </el-form-item>
        <el-form-item label="生成进度" prop="name">
          <el-progress :text-inside="true" :percentage="percentage" :stroke-width="18" style="padding-top: 5px;"></el-progress>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" :loading="saveLoading">生 成</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>

<script>
import { listCalculation, getCalculation, addCalculation } from "@/api/bonus/calculationNew";
import {getYearMonth2} from "@/utils/date";

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
          this.title = "奖金核算管理-编辑";
          this.getDetailInfo();
        } else {
          this.id = "";
          this.title = "奖金核算管理-新增";
          this.form.period = getYearMonth2() + '-01';
          this.setName();
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
  },
  data() {
    return {
      title: "奖金核算管理-新增",
      openDialog: this.open,
      saveLoading: false,
      // 表单参数
      form: {},
      auditStateOptions: [],
      percentage: 0,
	  	// 表单校验
      rules: {
        name: [
          { required: true, message: "奖金名称不能为空", trigger: "blur" }
        ],
        period: [
          { required: true, message: "奖金年月不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("asset_aduit_state").then(response => {
      this.auditStateOptions = response.data;
    });
    this.initWS();
  },
  methods: {
  	getDetailInfo() {
      getCalculation(this.id).then(response => {
        console.log(response);
        this.form = response.data;
      });
    },
    /** 查询奖金核算列表 */
    getList() {
      this.loading = true;
      listCalculation(this.queryParams).then(response => {
        this.calculationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 审核状态字典翻译
    auditStateFormat(row, column) {
      return this.selectDictLabel(this.auditStateOptions, row.auditState);
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
        name: null,
        period: null,
        auditState: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    changePeriod(){
      this.setName();
    },
    setName(){
      if(this.form.period){
        let arr = this.form.period.split("-");
        this.form.name = arr[0]+'年'+arr[1]+'月份奖金';
      }
    },
    // websocket message begin
    initWS() {
      if ('WebSocket' in window) {
        // const sysMessageWSUrl = `ws://${this.sysMessageWSUrl}/websocket/message/${this.$store.state.user.userInfo.userId}`
        const sysMessageWSUrl = 'ws://127.0.0.1:5880/websocket/message/'+this.$store.state.user.userInfo.userId;
        this.websocket = new WebSocket(sysMessageWSUrl)
        console.log('websocket1-----' + this.websocket)
        this.initWebSocket()
      } else {
        alert('当前浏览器 Not support websocket')
      }
    },
    initWebSocket() {
      // 连接错误
      this.websocket.onerror = this.onError
      // 连接成功
      this.websocket.onopen = this.onOpen
      // 收到消息的回调
      this.websocket.onmessage = this.onMessage
      // 连接关闭的回调
      this.websocket.onclose = this.onClose
      // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = this.onbeforeunload
    },
    onError() {
      console.log('WebSocket连接发生错误, 状态码：' + this.websocket.readyState)
    },
    onOpen() {
      console.log('WebSocket连接成功, 状态码：' + this.websocket.readyState)
    },
    onMessage(event) {
      console.log('服务端返回：' + event.data)
      if(!isNaN(event.data)){
        this.percentage = parseFloat(event.data);
      }
      // const msg = JSON.parse(event.data)
    },
    onClose() {
      this.initWebSocket()
    },
    onbeforeunload() {
      this.closeWebSocket()
    },
    closeWebSocket() {
      this.websocket.close()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.saveLoading = true;
          addCalculation(this.form).then(response => {
            // this.$emit("success", false);
            setTimeout(() => this.$emit("success", false), 1000);
            this.msgSuccess(response.msg);
            this.saveLoading = false;
            //this.closeWebSocket();
          }).catch((err)=>{
            this.saveLoading = false;
            //this.closeWebSocket();
          });
        }
      });
    }
}};
</script>
<style lang="scss" scoped>
  ::v-deep .el-dialog {
    height: 300px;
    overflow: hidden;
    .el-dialog__body {
      height: calc(100% - 100px);
      overflow: auto;
    }
  }
</style>
