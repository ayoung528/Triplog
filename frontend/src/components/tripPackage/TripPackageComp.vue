<template>
    <div>
      <v-container>
        <v-card width="1100px" height="130px">
          <v-row class="mx-auto">
            <v-list>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-airplane-takeoff</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                  <v-textarea
                    auto-grow
                    outlined
                    rows="1"
                    cols="100"
                    label="여행 묶음 이름을 입력하세요"
                    v-model="TripName"
                  ></v-textarea>
                </v-list-item-content>
                <v-list-item-icon>
                  <v-btn @click="addTripPackage">여행 묶음 추가</v-btn>
                </v-list-item-icon>
              </v-list-item>
            </v-list>
          </v-row>
        </v-card>

        <v-container fluid>
          <v-row>
            <v-col cols="12">
              <v-row align="stretch" justify="space-around">
                <TripList :num="0" :userNum="userNum" name="default" />
                <TripList
                  v-for="(item, index) in tripList"
                  :key="index"
                  :num="item.num"
                  :userNum="item.userNum"
                  :name="item.name"
                  @childs-event="deleteTrip"
                />
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-container>
    <v-snackbar style="font-family: 'Nanum Gothic';" shaped color="teal" v-model="alert" timeout="3000">
      <v-icon color="white">mdi-check-bold</v-icon>
      {{alertMsg}}
      <template v-slot:action="{ attrs }">
        <v-btn style="font-family: 'Sunflower';" color="white" text v-bind="attrs" @click="alert=false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import http from "@/util/http-common";
import TripList from "@/components/tripPackage/TripList.vue";

export default {
  name: "TripPackageComp",
  components: {
    TripList
  },
  data: function() {
    return {
      TripName: "",
      tripList: [],
      userNum: 0,
      alert: false,
      alertMsg: "",
      refresh: false
    };
  },
  created() {
    http.get(`/tripPackage/${this.$route.params.hostNum}`).then(({ data }) => {
      this.tripList = data;
    });
    this.userNum = Number(this.$route.params.hostNum);
  },
  methods: {
    addTripPackage() {
      http
        .post("/tripPackage/", {
          userNum: this.$route.params.hostNum,
          name: this.TripName
        })
        .catch(error => {
          console.error(error);
        });
      this.alertMsg = "추가가 완료되었습니다.";
      this.alert = true;
      this.refresh = true;
    },
    deleteTrip() {
      this.alertMsg = "삭제가 완료되었습니다.";
      this.alert = true;
      this.refresh = true;
    }
  },
  watch: {
    refresh() {
      http
        .get(`/tripPackage/${this.$route.params.hostNum}`)
        .then(({ data }) => {
          this.tripList = data;
        });
      this.refresh = false;
      this.$emit("childs-event", true);
    }
  }
};
</script>

<style>
</style>