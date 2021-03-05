<template>
  <v-container fluid style="width:1000px;">
    <article-info-comp
      :articleNum="item.num"
      :articleUserNum="item.user_num"
      :articleTripPackageNum="item.trippackage_num"
      :articleThumbnail="item.thumbnail"
      :articleTemp="item.temp"
      :articleTitle="item.title"
      :articlePlace="item.place"
      :articleDateStart="item.date_start"
      :articleDateEnd="item.date_end"
      :articleCreatedAt="item.created_at"
      :articleContent="item.content"
      :blogMasterName="item.userNickname"
      :articleLikeCount="item.likeCount"
      :isLoginedUserLikeThisArticle="isLike"
      :articleViews="item.views"
      :onOffParagraph="onOffParagraph"
      :commentNum="commentNum"
      v-if="likeLoaded & itemLoaded"
      @open-comment="openComment"
      @send-paragraph-info="sendParagraphInfo"
    />
    <comment-paragraph-comp
      :paragraphInfo="paragraphInfo"
      @on-off-paragraphcomment="onOffParagraphcomment"
      @para-write-comment="writeParagraphComment"
    />

    <comment-comp
      :items="comments"
      @write-comment="commentNum = commentNum +1"
      @delete-comment="commentNum = commentNum -1"
      v-if="commentLoaded && isOpenComment"
      :writerNum="item.user_num"
    />
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import ArticleInfoComp from "@/components/article/ArticleInfoComp.vue";
import CommentComp from "@/components/comment/CommentComp.vue";
import CommentParagraphComp from "@/components/comment/CommentParagraphComp.vue";
import http from "@/util/http-common";

export default {
  name: "ArticleInfo",
  components: {
    ArticleInfoComp,
    CommentComp,
    CommentParagraphComp
  },
  data: function() {
    return {
      commentNum: 0,
      isOpenComment: false,
      paragraphInfo: {
        paragraph: "",
        choiceId: null
      },
      item: {},
      isLike: null,
      likeLoaded: false,
      itemLoaded: false,
      commentLoaded: false,
      comments: [],
      onOffParagraph: false
    };
  },
  methods: {
    openComment(value) {
      this.isOpenComment = value;
    },
    onOffParagraphcomment(value) {
      this.onOffParagraph = value;
    },
    sendParagraphInfo(paragraphInfo) {
      this.paragraphInfo.paragraph = paragraphInfo.paragraph;
      this.paragraphInfo.choiceId = paragraphInfo.choiceId;
    },
    writeParagraphComment(obj) {
      if (obj != null) {
        this.comments.unshift({
          comment: obj,
          cocomments: []
        });
        this.commentNum = this.commentNum + 1;
      }
    }
  },
  created() {
    let num = 0;
    if (this.getUserNum != "") {
      num = this.getUserNum;
    }
    http
      .get(`/article/${this.$route.params.articleNum}/${num}`)
      .then(({ data }) => {
        this.item = data;

        this.itemLoaded = true;
      });

    if (this.getUserNum != "") {
      http
        .get(
          `/article/like/${this.$route.params.articleNum}/${this.getUserNum}`
        )
        .then(({ data }) => {
          this.isLike = data;
          this.likeLoaded = true;
        });
    } else {
      this.isLike = false;
      this.likeLoaded = true;
    }
    http.get(`/comment/${this.$route.params.articleNum}`).then(({ data }) => {
      this.comments = data;
      this.commentLoaded = true;
      this.commentNum = this.comments.length;
    });
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
    }
  }
};
</script>
