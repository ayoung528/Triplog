<template>
  <v-container>
    <v-form>
      <v-row justify="center" no-gutters>
        <v-col cols="9">
          <v-textarea
            :label="getContentLength"
            auto-grow
            outlined
            rows="2"
            row-height="50"
            maxlength="100"
            v-model="content"
            hide-details="false"
            color="teal"
          ></v-textarea>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col lg="5" md="auto" class="pa-0">
          <v-checkbox style="font-family: 'Nanum Gothic';" color="teal" v-model="secret" label="비밀글" />
        </v-col>
        <v-col lg="4" md="auto" class="text-right">
          <v-btn style="font-family: 'Nanum Gothic';" class="teal" dark @click="writeComment">작성</v-btn>
        </v-col>
      </v-row>
    </v-form>
    
    <v-list three-line>
      <CommentAsistUnitComp v-if="items[0]" :item="header" />
      <div
        v-for="(item, index) in items"
        :key="item.comment.content + item.comment.usernickname + index"
      >
        <CommentUnitComp
          :writerNum="writerNum"
          :item="item"
          :index="index"
          @delete-comment="$emit('delete-comment')"
        />
        <CommentAsistUnitComp :item="{ divider: true, inset: true }" />
      </div>
    </v-list>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import http from "@/util/http-common";
import CommentUnitComp from "./CommentUnitComp";
import CommentAsistUnitComp from "./CommentAsistUnitComp";
export default {
  name: "CommentComp",
  components: {
    CommentUnitComp,
    CommentAsistUnitComp
  },
  data: function() {
    return {
      contentLength: "0/100",
      content: "",
      secret: false,
      header: { header: "Comment" }
    };
  },
  props: {
    items: Array,
    writerNum: Number
  },
  methods: {
    writeComment() {
      let submitContent = this.content;
      if (this.getUserNum == "") {
        alert("로그인 먼저 진행해주세요");
      } else {
        if (this.content == "") {
          alert("글을 먼적 작성해주세요");
        } else {
          if (this.content != "") {
            if (this.secret) {
              submitContent = "*secret* " + this.content;
            }
          }
          http
            .post(`/comment/`, {
              content: submitContent,
              createdat: new Date(),
              articlenum: this.$route.params.articleNum,
              userimg: this.getUserImg,
              usernum: this.getUserNum,
              usernickname: this.getProfile,
              useremail: this.getEmail
            })
            .then(() => {
              let obj = {
                avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
                usernickname: this.getProfile,
                useremail: this.getEmail,
                content: submitContent,
                createdat: String(new Date()),
                userimg: this.getUserImg,
                usernum: this.getUserNum
              };
              this.items.unshift({ comment: obj, cocomments: [] });
              this.$emit("write-comment");
              this.content = "";
            })
            .catch(e => {
              console.log(e);
            });
        }
      }
    }
  },
  computed: {
    getContentLength: function() {
      if (this.content.length == 0) {
        return "My Comment";
      }
      return this.content.length + "/100";
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
  created() {}
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");
</style>