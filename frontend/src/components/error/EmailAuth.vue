<template>
  <v-container style=" margin-top:100px;">
    <div style="justify-content:center; display:flex; margin-bottom: 50px;">
      <img :src="`http://i3b207.p.ssafy.io/res/mail.png`" alt />
      <h1 style="line-height:167px">회원가입 인증 메일을 확인해주세요.</h1>
    </div>
    <hr />
    <div style="justify-content:center; display:flex; margin-top: 50px; color:red;">
      <h3>인증 전에는 블로그 생성을 포함한 다양한 활동이 제한됩니다.</h3>
    </div>
    <div style="justify-content:center; display:flex; margin-top: 50px;">
      <p>회원가입한 메일로 인증 후 진정한 'TRIPLOGGER'가 되어주세요!</p>
    </div>
    <div style="justify-content:center; display:flex; margin-top: 50px;" v-if="getUserNum != ''">
      <v-btn x-large outlined color="black" @click="sendMail">인증 메일 재발송</v-btn>
    </div>
  </v-container>
</template>

<script>
import http from "@/util/http-common";

import { mapGetters, mapState } from "vuex";
export default {
  name: "EmailAuth",
  data() {
    return {};
  },
  methods: {
    sendMail() {
      http
        .get(`/emailValid/re/${this.getUserNum}`)
        .then(() => {
          alert("전송완료");
        })
        .catch(() => {
          alert("전송실패");
        });
    }
  },
  computed: {
    ...mapGetters(["getUserNum"]),
    ...mapState({
      userNum: state => `${state.user.getUserNum}`
    })
  }
};
</script>

<style>
</style>