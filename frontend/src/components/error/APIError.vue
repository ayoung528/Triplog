<template>
  <v-container>
    <h1><v-icon large color="red">mdi-close-network-outline</v-icon> {{code}}</h1>
    <p>{{msg}}</p>
</v-container>
</template>

<script>
// import { AUTH_REQUEST } from "../store/actions/auth";
export default {
  name: "APIError",
  data() {
    return {
      code: null,
      msg: "",
    };
  },
  created (){
    this.code =  this.$route.params.errorCode
    if (this.code > 500) {
      this.msg = "서버의 문제로 서버가 해당 요청을 수행하지 못했습니다."
    } else {
      this.msg = "클라이언트의 요청이 유효하지 않아 서버가 해당 요청을 수행하지 못했습니다."
    };
    
  }
}
</script>
