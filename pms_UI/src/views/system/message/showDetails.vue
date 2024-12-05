<template>
  <div class="app-container">
    <h3 class="common">标题</h3>
    <div class="common">
      <div>发送人：张三</div>
      <div>(2021-12-11)</div>
    </div>
    <div class="cont" v-html='desc'></div>

  </div>
</template>

<script>
import { showDetails } from "@/api/system/message";

export default {
  data() {
    return {
      title:"",
      name:"",
      date:"",
      desc: ""
    };
  },
  created() {
    this.showDetails(this.$route.query.id)
  },
  methods: {
    showDetails(res) {
      showDetails(res).then(response => {
        console.log(response);
        this.title = response.data.title;
        this.name = response.data.sendUserName;
        this.date = response.data.sendTime;
        this.desc = response.data.content
      });
    }

  }
};
</script>
<style scoped>
  .common{
    text-align: center;
  }
  .cont{
    padding: 10px 20px;
    text-indent: 2rem;
  }
</style>
