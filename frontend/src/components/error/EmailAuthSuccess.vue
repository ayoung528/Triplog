<template>
  <v-container style=" margin-top:100px;">
    <div style="justify-content:center; display:flex; margin-bottom: 50px;">
      <h1 style="color: #7fff54; font-size:10em; text-shadow: 1px 1px 5px #000;">
        TRIPLOG
        <v-icon x-large color="#7fff54">mdi-compass-outline</v-icon>
      </h1>
    </div>

    <hr />
    <div style="justify-content:center; display:flex; margin-top: 50px; color:red;">
      <h1>인증이 완료되었습니다.</h1>
    </div>
    <br />

    <div style="justify-content:center; display:flex; margin-top: 50px;">
      <h3>이제 당신의 여행을 기록하고 공유하세요!</h3>
    </div>
    <div style="justify-content:center; display:flex; margin-top: 50px;">
      <v-btn x-large outlined color="black" @click="$router.push('/')">메인으로</v-btn>
    </div>
  </v-container>
</template>

<script>
export default {
  name: "EmailAuth",
  data() {
    return {};
  },
  methods: {}
};
</script>

<style>
</style>