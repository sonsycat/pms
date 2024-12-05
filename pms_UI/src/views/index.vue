<template>
    <div class="image-container">
      <img src="@/assets/images/home_page.jpg" alt="light" class="leftImg">
    </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
};
</script>

<style scoped>
html,body{
  padding: 0;
  margin: 0;
}

.image-container {
  width: 100%; /* 或者指定宽度 */
  padding-bottom: 50%; /* 根据图片宽高比例设置 */
  position: relative;
  background-color: #f2f2f2; /* 可选背景色 */

}

.leftImg{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保图片覆盖整个容器，可以改变成contain以保持图片比例 */
  margin: 0px;
  display: block;
}
</style>
