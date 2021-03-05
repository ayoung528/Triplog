<template>
  <div>
    <v-card width="280px">
      <v-list two-line>
        <v-subheader>
          {{name}}
          <v-spacer></v-spacer>
          <v-btn icon v-if="this.num != 0" @click="deleteTrip">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </v-subheader>
        <draggable v-model="items" group="posts" style="min-height: 10px">
          <template>
            <TripPost
              v-for="(item, index) in items"
              :key="`${index}_items`"
              :num="item.num"
              :user_num="item.user_num"
              :title="item.title"
              :thumbnail="item.thumbnail"
              :created_at="item.created_at"
            />
          </template>
        </draggable>
      </v-list>
    </v-card>

    <v-snackbar v-model="alert" timeout="5000">
      <v-icon color="deep-orange darken-3">mdi-home</v-icon>
      {{alertMsg}}
      <template v-slot:action="{ attrs }">
        <v-btn color="red" text v-bind="attrs" @click="alert = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import http from "@/util/http-common";
import draggable from "vuedraggable";
import TripPost from "@/components/tripPackage/TripPost.vue";

export default {
  name: "TripList",
  components: {
    draggable,
    TripPost
  },
  props: {
    num: { type: Number },
    userNum: { type: Number },
    name: { type: String }
  },
  data: function() {
    return {
      items: [],
      alert: false,
      alertMsg: ""
    };
  },
  created() {
    if (this.num == 0) {
      http
        .get(`/article/tripPackage/default/${this.userNum}`)
        .then(({ data }) => {
          this.items = data;
        });
    } else {
      http.get(`/article/tripPackage/${this.num}`).then(({ data }) => {
        this.items = data;
      });
    }
  },
  watch: {
    items: function() {
      for (let index = 0; index < this.items.length; index++) {
        if (this.num == 0) {
          if (this.items[index].trippackage_num != null) {
            http.put(`/article/tripPackage/0/${this.items[index].num}`);
          }
        } else {
          if (this.items[index].trippackage_num != this.num) {
            http.put(
              `/article/tripPackage/${this.num}/${this.items[index].num}`
            );
          }
        }
      }
    }
  },
  methods: {
    deleteTrip() {
      if (this.items.length == 0) {
        http.delete(`/tripPackage/${this.num}`).then(({ data }) => {
          this.alertMsg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            this.alertMsg = "삭제가 완료되었습니다.";
            // this.alert = true;
          }
        });
      } else {
        for (let index = 0; index < this.items.length; index++) {
          http.put(`/article/tripPackage/0/${this.items[index].num}`);
        }
        http.delete(`/tripPackage/${this.num}`).then(({ data }) => {
          this.alertMsg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            this.alertMsg = "삭제가 완료되었습니다.";
            // this.alert = true;
          }
        });
      }
      this.$emit('childs-event', true);
    }
  }
};
</script>

<style>
</style>