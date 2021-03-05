<template>
  <tr @click="moveBlog" align="center">{{neighborNickname}}</tr>
</template>

<script>
export default {
  name: "neighborList",
  props: {
    userNum: { type: Number },
    neighborNum: { type: Number },
    neighborNickname: { type: String }
  },
  methods: {
    moveBlog() {
      this.$router.replace(`/article/list/${this.neighborNum}`);
      this.$router.go(this.$router.currentRoute);
    }
  }
};
</script>

<style>
</style>