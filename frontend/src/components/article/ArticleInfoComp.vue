<template>
  <v-container fluid style="width:1000px;">
    <div>
      <h1 class="text-center" style="font-family: 'Nanum Gothic';">{{articleTitle}}</h1>
      <div style="width:100%; text-align:right;">
        <h4 style="display: inline-block; font-family: 'Nanum Gothic'; cursor:pointer" @click="goToBlog">by {{blogMasterName}}
        </h4>
      </div>
      <h4 v-if="articlePlace" class="text-right" style="font-family: 'Nanum Gothic';">장소 : {{ articlePlace }}</h4>
      <h4 class="text-right" style="font-family: 'Nanum Gothic'; color:gray">{{getFormatDate(articleCreatedAt)}}</h4>
    </div>
    <br />
    <v-sheet class="pa-3" >
      <div @click="clickThis" @mouseover="hoverThis" @mouseout="nonHoverThis" :style="cursorStyle">
        <article-content-comp :content="realContent" v-if="realContent" />
      </div>
    </v-sheet>
    <hr>
    
    <v-row class="ma-1" align="center">
      <v-btn class="ml-6" icon :disabled="likeBtnFlag" :loading="likeBtnFlag">
        <v-chip @click="likeBtnClick" outlined color="pink">
          <v-avatar left color="white" class="darken-4">{{likeCount}}</v-avatar>

          <v-icon color="pink" v-if="isLoginedUserLikeThisArticleprop">mdi-heart</v-icon>
          <v-icon color="pink" v-else>mdi-heart-outline</v-icon>
        </v-chip>
      </v-btn>

      <v-chip class="ml-6" color="light-green" outlined>
        <v-avatar right class="white ml-0 mr-2">{{articleViews}}</v-avatar>
        <v-icon>mdi-eye-outline</v-icon>
      </v-chip>

      <v-btn class="ml-6" icon>
        <v-chip @click="commentBtnClick" outlined color="black">
          <v-avatar left color="white" class="darken-4">{{commentNum}}</v-avatar>

          <v-icon v-if="!openComment">mdi-comment-text-multiple-outline</v-icon>
          <v-icon v-else>mdi-comment-text-multiple</v-icon>
        </v-chip>
      </v-btn>
      <v-spacer></v-spacer>

      <v-sheet>
        <v-btn
          :to="{ name: 'articleModify', params: { articleNum: articleNum }}"
          v-if="btnToggle"
          tile
          outlined
        >
          <v-icon left>mdi-pencil</v-icon>
          <span>수정</span>
        </v-btn>
        <v-btn @click="confirmDelete" v-if="btnToggle" tile outlined class="ma-2">
          <v-icon left>mdi-delete</v-icon>삭제
        </v-btn>
      </v-sheet>
    </v-row>
    <v-row justify="end" class="ma-1">
      <v-btn tile outlined class="mr-2" @click="goBack">
        <v-icon left>mdi-arrow-left</v-icon>뒤로
      </v-btn>
    </v-row>

    <br />
    <br />
    <v-dialog color="teal" dark v-model="dialog" max-width="400">
      <v-card>
        <v-card-title style="font-family: 'Nanum Gothic';" class="headline">
          <v-icon class="mr-2" color="red">mdi-information</v-icon>정말 삭제하시겠습니까?</v-card-title>

        <v-card-text style="font-family: 'Nanum Gothic';">삭제하면 게시글 복구가 불가능합니다.</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn style="font-family: 'Nanum Gothic';" class="white--text" text @click="dialog = false">취소</v-btn>

          <v-btn style="font-family: 'Nanum Gothic'; font-weight: bold;" class="teal--text" text @click="deleteArticle">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import ArticleContentComp from "@/components/article/ArticleContentComp.vue";
import { mapGetters, mapState } from "vuex";
import moment from "moment";
import http from "@/util/http-common";

export default {
  name: "articleInfoComp",
  components: {
    ArticleContentComp
  },
  props: {
    articleNum: { type: Number },
    articleUserNum: { type: Number },
    articleThumbnail: { type: String },
    articleTemp: { type: Boolean },
    articleTitle: { type: String },
    articlePlace: { type: String },
    articleDateStart: { type: String },
    articleDateEnd: { type: String },
    articleCreatedAt: { type: String },
    articleContent: { type: String },
    blogMasterName: { type: String },
    articleLikeCount: { type: Number },
    isLoginedUserLikeThisArticle: { type: Boolean },
    articleViews: { type: Number },
    onOffParagraph: { type: Boolean },
    commentNum: { type: Number }
  },
  data: function() {
    return {
      openComment: false,
      isLoginedUserLikeThisArticleprop: this.isLoginedUserLikeThisArticle,
      cursorEventOn: false,
      paragraphInfo: {
        paragraph: "",
        choiceId: null
      },
      alert: false,
      alertMsg: "",
      dialog: false,
      realContent: "",
      btnToggle: false,
      likeBtnFlag: false,
      views: 0
    };
  },
  mounted() {
    if (this.getUserNum == this.articleUserNum) this.btnToggle = true;
    // this.$refs.calendar.checkChange();
  },
  created: function() {
    this.likeCount = this.articleLikeCount;
    this.openContentFile();
  },
  methods: {
    commentBtnClick() {
      this.openComment = !this.openComment;
      this.$emit("open-comment", this.openComment);
    },
    clickThis() {
      var e = window.event,
        btn = e.target || e.srcElement;
      this.paragraphInfo.paragraph = btn.innerText;
      if (btn.id != "") {
        this.paragraphInfo.choiceId = btn.id;
      }
      this.$emit("send-paragraph-info", this.paragraphInfo);
    },
    hoverThis() {
      this.cursorEventOn = true;
    },
    nonHoverThis() {
      this.cursorEventOn = false;
    },
    goToBlog: function() {
      this.$router.push(`/article/list/${this.articleUserNum}`);
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = "";
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => (this.selectedOpen = true), 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      const events = [];

      const min = new Date(`${start.date}T00:00:00`);
      const max = new Date(`${end.date}T23:59:59`);
      const days = (max.getTime() - min.getTime()) / 86400000;
      const eventCount = this.rnd(days, days + 20);

      for (let i = 0; i < eventCount; i++) {
        const allDay = this.rnd(0, 3) === 0;
        const firstTimestamp = this.rnd(min.getTime(), max.getTime());
        const first = new Date(firstTimestamp - (firstTimestamp % 900000));
        const secondTimestamp = this.rnd(2, allDay ? 288 : 8) * 900000;
        const second = new Date(first.getTime() + secondTimestamp);

        events.push({
          name: this.names[this.rnd(0, this.names.length - 1)],
          start: first,
          end: second,
          color: this.colors[this.rnd(0, this.colors.length - 1)],
          timed: !allDay
        });
      }

      this.events = events;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
    // 여기까지 캘린더 메소드
    goBack: function() {
      window.history.back();
    },
    openContentFile: function() {
      var url = "http://i3b207.p.ssafy.io/res/content/registered/" + this.articleContent;
      var xhr = new XMLHttpRequest();
      xhr.responseType = "text";
      var setRealContent = val => {
        this.realContent = val;
      };
      xhr.onload = function(e) {
        var resp = xhr.responseText || e.target.responseText;
        setRealContent(resp);
      };
      xhr.open("GET", url);
      xhr.send();
    },
    confirmDelete() {
      this.dialog = true;
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY년MM월DD일 HH시mm분ss초");
    },
    deleteArticle: function() {
      http
        .delete(`/article/${this.articleNum}`)
        .then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "삭제가 완료되었습니다.";
          }
          this.alertMsg = msg;
          this.dialog = false;
          // this.$emit("useSnackBar", this.alertMsg);
          this.$router.push(`/article/list/${this.getUserNum}`);
        })
        .catch(() => {});
    },
    likeBtnClick: function() {
      if (this.getUserNum != "") {
        this.likeBtnFlag = true;
        if (this.isLoginedUserLikeThisArticleprop) {
          this.likeCount--;
        } else {
          this.likeCount++;
        }
        this.isLoginedUserLikeThisArticleprop = !this
          .isLoginedUserLikeThisArticleprop;

        http
          .put(
            `/article/${this.articleNum}/${this.getUserNum}/${this.isLoginedUserLikeThisArticleprop}`,
            {
              num: this.articleNum,
              user_num: this.articleUserNum,
              trippackage_num: this.articleTripPackageNum,
              title: this.articleTitle,
              place: this.articlePlace,
              content: this.articleContent,
              thumbnail: this.articleThumbnail,
              temp: this.articleTemp,
              created_at: this.articleCreatedAt,
              date_start: this.articleDateStart,
              date_end: this.articleDateEnd,
              likeCount: this.likeCount,
              viws: this.articleViews
            }
          )
          .then(({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "수정이 완료되었습니다.";
            }
            this.alertMsg = msg;
            this.alert = true;
            this.likeBtnFlag = false;
          })
          .catch(() => {
            this.alertMsg = "수정 처리시 에러가 발생했습니다.";
            this.alert = true;
          });
      } else {
        alert("먼저 로그인을 진행해주세요");
      }
    }
  },
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getEmail",
      "getUserNum"
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userEmail: state => `${state.user.getEmail}`,
      userNum: state => `${state.user.getUserNum}`
    }),
    loading: function() {
      return this.authStatus === "loading" && !this.isAuthenticated;
    },
    cursorStyle() {
      if (this.cursorEventOn && this.onOffParagraph) {
        return "cursor: pointer;";
      } else {
        return "";
      }
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");

.img {
  max-width: 500px;
}
</style>