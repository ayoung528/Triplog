<template>
  <v-container>
    <v-list-item
      :key="item.content"
      @mouseover="display = true"
      @mouseleave="display = false"
      v-if="!isDelete"
    >
      <v-list-item-avatar @click="goToBlog" style="cursor:pointer">
        <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${userimg}`"></v-img>
      </v-list-item-avatar>

      <v-list-item-content class="pb-0">
        <v-row>
          <v-col cols="8">
            <v-list-item-title v-html="getName" @click="goToBlog" style="cursor:pointer"></v-list-item-title>
          </v-col>
          <v-col cols="4" v-if="display" style="display: flex; justify-content:flex-end;">
            <i
              v-if="isMyComment"
              class="fas fa-times"
              :style="xButton"
              @mouseover="displayX = true"
              @mouseleave="displayX = false"
              @click="deleteComment"
            />
          </v-col>
        </v-row>
        <br />
        <v-list-item-subtitle v-html="item.content"></v-list-item-subtitle>
        <br />
        <br />
        <v-row>
          <v-col cols="5" class="mb-4" style="display: flex; justify-content:flex-end;">
            <v-list-item-subtitle v-html="getFormatDate(item.createdat)" />
          </v-col>
          <v-col cols="2" class="pb-0"></v-col>
        </v-row>
      </v-list-item-content>
    </v-list-item>
  </v-container>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common";

import { mapGetters, mapState } from "vuex";
export default {
  name: "CocommentUnitComp",
  data: function() {
    return {
      display: false,
      displayX: false,
      isDelete: false,
      userimg: "profile_init.png"
    };
  },
  props: {
    item: Object,
    index: Number
  },
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getEmail",
      "getUserNum",
      "getUserImg"
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userEmail: state => `${state.user.getEmail}`,
      userNum: state => `${state.user.getUserNum}`,
      userImg: state => `${state.user.getUserImg}`
    }),
    xButton: function() {
      if (this.displayX) {
        return "color:red;";
      } else {
        return "color:black;";
      }
    },
    button: function() {
      if (this.display) {
        return "color:blue;";
      } else {
        return "color:black;";
      }
    },
    getName: function() {
      return `${this.item.usernickname}(${this.item.useremail})`;
    },
    isMyComment: function() {
      if (this.getUserNum == this.item.usernum) {
        return true;
      }
      return false;
    }
  },
  methods: {
    goToBlog: function() {
      this.$router.push(`/article/list/${this.item.usernum}`);
    },
    deleteComment() {
      this.isDelete = true;
      http
        .delete(`/comment/${this.item.num}`)
        .then(() => {
        })
        .catch(e => {
          console.log(e);
        });
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD HH:mm:ss");
    }
  },
  created() {
    if (this.item.userimg) {
      if (this.item.userimg != "null") {
        this.userimg = this.item.userimg;
      }
    }
  }
};
</script>

<style>
</style>