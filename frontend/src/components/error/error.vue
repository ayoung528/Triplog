<template>
  <v-container>
    <h1>
      <v-icon large color="red">mdi-close-network-outline</v-icon>
      {{code}} :{{msg}}
    </h1>
    <br />
    <p>
      죄송합니다.
      요청하신 페이지를 찾을 수 없습니다.
    </p>
    <br />
    <p>방문하시려는 페이지의 주소가 잘못 입력되었거나, 페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다. 입력하신 주소가 정확한지 다시 한번 확인해 주시기 바랍니다.</p>
  </v-container>
</template>

<script>
export default {
  name: "error",
  data() {
    return {
      code: "404",
      msg: "PAGE NOT FOUND"
    };
  }
};
</script>
