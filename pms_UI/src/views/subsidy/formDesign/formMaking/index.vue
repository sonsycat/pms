<template>
  <div class="dynamicForm">
    <!--  高级定义和布局字段 我不需要就设置为空啦   :advance-fields = '[]'   :layout-fields = '[]'  -->
    <fm-making-form
      ref="makingform"
      style="height:600px;display: block;"
      preview
      generate-code
      generate-json
    >
      <!-- 头部内容自定义，添加提交按钮 -->
      <template slot="action">
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </fm-making-form>

    <div class="list-wraper previewForm">
      <fm-generate-form
        :data="jsonData"
        ref="generateForm">
      </fm-generate-form>
      <!--<el-button type="primary" @click="handleSubmit">提交</el-button>-->
    </div>
  </div>
</template>

<script>
import {saveDesign, getForm, getFormDesign} from '@/api/subsidy/formProcess'

export default {
  data() {
    return {
      formName: '',
      jsonData: {
        list: [],
      },
      formDesignId: "",

    }
  },
  components: {},
  created() {
    this.formDesignId = this.$route.query.formDesignId
    let data = {
      formDesignId: this.formDesignId
    }
    this.handleFormReady();
//      getForm(data).then(response => {
//      	console.log(response);
//      	let jsons = JSON.parse(response.data.formJson)
//      	console.log(jsons);
////      	this.jsonData = jsons
//      })
  },
  methods: {
    handleFormReady() {
      const formDesignId = this.formDesignId;
      getFormDesign(formDesignId).then(response => {
        console.log(response)
        const formJson = response.data.formJson;
        if (formJson != null && formJson != '') {
          let data = JSON.parse(formJson);
          data.size = "mini"
          data.list.forEach(_ => {
            _.size = "mini";
            _.options.disabled = _.options.disabled == "false" ? false : true;
            _.options.required = _.options.required == "false" ? false : true;
            _.columns && _.columns.forEach(ite => {
              ite.span = ite.span * 1;
              ite.list.forEach(item => {
                item.size = "mini";
                item.options.disabled = item.options.disabled == "false" ? false : true;
                item.options.required = item.options.required == "false" ? false : true;
              })
            })
          })
          try {
            this.$refs.makingform.setJSON(data)
          } catch (d) {
            console.log(d)
          }
        }
      });
    },
    handleSubmit() {
      const json = this.$refs.makingform.getJSON();
      let saveLoading = this.saveLoading()
      saveDesign(this.formDesignId, json, this.$refs.makingform.getHtml()).then(response => {
        this.msgSuccess("保存成功");
        saveLoading.close()
        this.close();
      })
    },
    close() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push({path: '/clbt/CLBTZDY/CLZDYBD', query: {t: Date.now()}})
    },
  },

}
</script>

<style lang="scss" scoped>
.dynamicForm {
  ::v-deep .el-aside {
    width: 310px !important;

    &.widget-config-container {
      width: 350px !important;

      .el-form-item__content {
        margin-left: 0 !important
      }
    }
  }
}
</style>
