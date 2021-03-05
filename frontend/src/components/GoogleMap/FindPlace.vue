<template>
  <div id="app">
    <v-list align = "center">
    <h1>장소 찾기</h1>
    <input v-model="vm.searchPlace" v-gmaps-searchbox:location="vm" />
    <v-btn small @click="returnPlace">확인</v-btn>
    </v-list>
  </div>
</template>

<script>
export default {
  name: "FindPlace.vue",
  data: function() {
    return {
      msg: "장소 찾기",
      vm: {
        searchPlace: "",
        location: {}
      },
      place: {
        addressDialog: false,
        name: "",
        lat: 0,
        lng: 0
      }
    };
  },
  methods: {
    returnPlace() {
      this.place.name = this.vm.location.name;
      this.place.lat = this.vm.location.geometry.location.lat();
      this.place.lng = this.vm.location.geometry.location.lng();
      this.$emit('childs-event', this.place);
    }
  }
};
</script>

<style>
</style>