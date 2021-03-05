<template >
  <v-container>
    <v-subheader v-if="item.header" :key="item.header" v-text="item.header"></v-subheader>

    <v-divider v-else-if="item.divider" :key="index" :inset="item.inset"></v-divider>

    <v-list-item v-else :key="item.comment.num">
      <v-list-item-avatar @click="goToBlog" style="cursor:pointer">
        <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${userimg}`"></v-img>
      </v-list-item-avatar>

      <v-list-item-content class="pb-0">
        <div @mouseover="display = true" @mouseleave="display = false" @click.stop="onOffComment">
          <v-row>
            <v-col cols="8" style="padding:25px;">
              <v-list-item-title v-html="getName" @click="goToBlog" style="cursor:pointer"></v-list-item-title>
            </v-col>
            <v-col cols="4" v-if="display" style="display: flex; justify-content:flex-end; ">
              <div v-if="isMyComment">
                <v-icon
                  left
                  :style="eButton"
                  @mouseover="displayE = true"
                  @mouseleave="displayE = false"
                  class="mr-3"
                  @click.stop="updateComment"
                >mdi-pencil</v-icon>
                <i
                  class="fas fa-times"
                  :style="xButton"
                  @mouseover="displayX = true"
                  @mouseleave="displayX = false"
                  @click.stop="deleteComment"
                />
              </div>
            </v-col>
          </v-row>
          <v-list-item-subtitle
            v-if=" item.comment.paragraph != null && item.comment.paragraph != ''"
            v-html="'@' + item.comment.paragraph"
          ></v-list-item-subtitle>
          <br />

          <br />
          <v-list-item-subtitle
            v-if="!update && !isSecret"
            v-html="item.comment.content"
            style="color:black"
          ></v-list-item-subtitle>

          <v-list-item-subtitle v-if="!update && isSecret" style="color:black">비밀글입니다.</v-list-item-subtitle>

          <v-textarea
            @click.stop
            v-if="update"
            :label="getUpdateContentLength"
            auto-grow
            outlined
            rows="2"
            row-height="50"
            maxlength="100"
            v-model="updateContent"
            hide-details="false"
            color="teal lighten-3"
          />
          <v-col v-if="update" cols="12" style="display: flex; justify-content:flex-end;">
            <v-btn class="teal lighten-3 mr-3" @click.stop="rewriteComment">수정</v-btn>
            <v-btn @click.stop="closeUpdate">취소</v-btn>
          </v-col>
          <br />
          <br />
          <v-row>
            <v-col cols="5" class="mb-4" style="display: flex; justify-content:flex-end;">
              <v-list-item-subtitle v-html="getFormatDate(item.comment.createdat)" />
            </v-col>
            <v-col cols="2" class="pb-0"></v-col>
            <v-col cols="5" style="display: flex; justify-content:flex-end;" :style="button">
              <i v-if="!openComment" class="fas fa-caret-down mr-2" />
              <i v-if="openComment" class="fas fa-caret-up mr-2" />&nbsp;
              댓글
            </v-col>
          </v-row>
        </div>
        <v-container v-if="openComment" style="width:80%;">
          <v-row>
            <v-col cols="12">
              <v-textarea
                :label="getContentLength"
                auto-grow
                outlined
                rows="2"
                row-height="50"
                maxlength="100"
                v-model="content"
                hide-details="false"
                color="teal lighten-3"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6"></v-col>
            <v-col cols="6" style="display: flex; justify-content:flex-end;">
              <v-btn class="teal lighten-3" @click="submit">작성</v-btn>
            </v-col>
          </v-row>
          <div v-for="(coItem, index) in item.cocomments" :key="coItem.content">
            <cocomment-unit-comp :item="coItem" :index="index" />
          </div>
        </v-container>
      </v-list-item-content>
    </v-list-item>
  </v-container>
</template>

<script>
import moment from "moment";
import { mapGetters, mapState } from "vuex";
import http from "@/util/http-common";
import CocommentUnitComp from "./CocommentUnitComp.vue";
export default {
  name: "CommentUnitComp",
  components: {
    CocommentUnitComp
  },
  data() {
    return {
      contentLength: "0/100",
      content: "",
      display: false,
      displayX: false,
      displayE: false,
      openComment: false,
      userimg: "profile_init.png",
      update: false,
      updateContent: "",
      isSecret: false,
      isMySecret: false
    };
  },
  props: {
    item: Object,
    index: Number,
    writerNum: Number
  },
  methods: {
    goToBlog: function() {
      this.$router.push(`/article/list/${this.item.comment.usernum}`);
    },
    onOffComment() {
      if (this.isSecret) {
        alert("비밀 글 입니다!");
      } else {
        if (this.openComment == true) {
          this.openComment = false;
        } else {
          this.openComment = true;
        }
      }
    },
    deleteComment() {
      let removeContent = this.item.comment.content;

      if (this.item.comment.num == undefined) {
        http
          .delete(`/comment/content/${removeContent}`)
          .then(() => {
            this.$emit("delete-comment");
            this.item.comment.content = "삭제되었습니다.";
          })
          .catch(e => {
            console.log(e);
          });
      } else {
        http
          .delete(`/comment/${this.item.comment.num}`)
          .then(() => {
            this.item.comment.content = "삭제되었습니다.";
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    updateComment() {
      this.update = true;
    },
    closeUpdate() {
      this.update = false;
    },
    rewriteComment() {
      this.item.comment.content = this.updateContent;
      this.update = false;
      http
        .put(`/comment/${this.item.comment.num}`, {
          content: this.updateContent,
          articlenum: this.$route.params.articleNum,
          userimg: this.getUserImg,
          usernum: this.getUserNum,
          usernickname: this.getProfile,
          useremail: this.getEmail
        })
        .then(() => {})
        .catch(e => {
          console.log(e);
        });
    },
    submit() {
      if (this.getUserNum == "") {
        alert("먼저 로그인을 진행해주세요");
      } else {
        if (this.content == "") {
          alert("글을 먼저 적어주세요");
        } else {
          http
            .post(`/comment/${this.item.comment.content}`, {
              content: this.content,
              createdat: new Date(),
              articlenum: this.$route.params.articleNum,
              userimg: this.getUserImg,
              usernum: this.getUserNum,
              usernickname: this.getProfile,
              useremail: this.getEmail
            })
            .then(() => {
              let obj = {
                articlenum: this.$route.params.articleNum,
                usernickname: this.getProfile,
                content: this.content,
                createdat: new Date(),
                useremail: this.getEmail,
                userimg: this.getUserImg,
                usernum: this.getUserNum
              };
              
              this.item.cocomments.unshift(obj);
              this.content = "";
            })
            .catch(e => {
              console.log(e);
            });
        }
      }
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY.MM.DD HH:mm:ss");
    }
  },
  computed: {
    getContentLength: function() {
      if (this.content.length == 0) {
        return "My Comment";
      }
      return this.content.length + "/100";
    },
    getUpdateContentLength: function() {
      if (this.updateContent.length == 0) {
        return "RE_WRITE";
      }
      return this.updateContent.length + "/100";
    },
    xButton: function() {
      if (this.displayX) {
        return "color:red;";
      } else {
        return "color:black;";
      }
    },
    eButton: function() {
      if (this.displayE) {
        return "color:green;";
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
      return `${this.item.comment.usernickname}(${this.item.comment.useremail})`;
    },
    isMyComment: function() {
      if (this.getUserNum == this.item.comment.usernum) {
        return true;
      }
      return false;
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
  created() {
    this.updateContent = this.item.comment.content;
    if (this.item.comment) {
      if (this.item.comment.userimg != "null") {
        this.userimg = this.item.comment.userimg;
      }
      if (this.item.comment.content.slice(0, 9) == "*secret* ") {
        if (
          this.writerNum != this.getUserNum &&
          this.item.comment.usernum != this.getUserNum
        ) {
          this.isSecret = true;
        }
      }
    }
  }
};
</script>

<style>
</style>