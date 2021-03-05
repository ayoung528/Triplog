<template>
  <v-container height="10000">
    <div
      style="position: fixed; right: 20px; bottom:160px; display: flex;justify-content: flex-end;"
      v-show="fab"
    >
      <div style="width:200px">
        <v-textarea
          hide-details="false"
          style="background-color:white; "
          outlined
          color="black"
          v-model="content"
          :label="setLabel"
        ></v-textarea>
      </div>
    </div>
    <div
      class="d-none d-sm-flex"
      style="position: fixed; right: 20px; bottom:90px; display: flex;justify-content: flex-end;"
    >
      <v-btn @click="writeComment" dark fab v-show="fab" color="green" style="margin-right: 10px">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn @click="fab=!fab" :color="commentWriteBtnColor" dark fab>
        <v-icon v-if="fab">mdi-close</v-icon>
        <v-icon v-else>mdi-comment-edit-outline</v-icon>
      </v-btn>
    </div>
    <div
      class="d-sm-none d-flex"
      style="position: fixed; right: 20px; bottom: 130px; display: flex;justify-content: flex-end;"
    >
      <v-btn
        @click="writeComment"
        dark
        fab
        v-show="fab"
        small
        color="green"
        style="margin-right: 10px"
      >
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn @click="fab=!fab" small :color="commentWriteBtnColor" dark fab>
        <v-icon v-if="fab">mdi-close</v-icon>
        <v-icon v-else>mdi-pencil</v-icon>
      </v-btn>
    </div>
  </v-container>
</template>


<script>
import { mapGetters, mapState } from "vuex";
import http from "@/util/http-common";
export default {
  name: "CommentParagraphComp",
  data: function() {
    return {
      content: "",
      fab: false
    };
  },
  props: {
    paragraphInfo: Object
  },
  methods: {
    writeComment() {
      if (this.getUserNum == "") {
        alert("먼저 로그인을 진행해주세요.");
      } else {
        if (this.content == "") {
          this.fab = true;
        } else {
          http
            .post(`/comment/`, {
              content: this.content,
              createdat: new Date(),
              articlenum: this.$route.params.articleNum,
              userimg: this.getUserImg,
              usernum: this.getUserNum,
              usernickname: this.getProfile,
              useremail: this.getEmail,
              paragraph: this.paragraphInfo.paragraph
            })
            .then(() => {
              let obj = {
                avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
                usernickname: this.getProfile,
                useremail: this.getEmail,
                content: this.content,
                createdat: new Date(),
                userimg: this.getUserImg,
                usernum: this.getUserNum,
                paragraph: this.paragraphInfo.paragraph
              };
              this.$emit("para-write-comment", obj);
              // this.items.unshift({ comment: obj, cocomments: [] });
              this.content = "";
              this.fab = false;
            })
            .catch(e => {
              console.log(e);
            });
        }
      }
    }
  },
  computed: {
    setLabel: function() {
      if (this.paragraphInfo.paragraph == "") {
        return "문단을 선택해주세요";
      } else {
        return "@" + this.paragraphInfo.paragraph;
      }
    },
    commentWriteBtnColor: function() {
      if (!this.fab) {
        return "";
      } else {
        return "red darken-2";
      }
    },
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
    })
  },
  watch: {
    fab: function() {
      this.$emit("on-off-paragraphcomment", this.fab);
    }
  }
};
</script>

<style>
</style>
