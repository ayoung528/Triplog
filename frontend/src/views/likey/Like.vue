<template>
  <v-container style="width: 90%;">
    <v-row>
      <v-col cols="12">
        <slot />
      </v-col>
      <div v-if="likeArticle == 0" style="text-align: center; width: 400px">
        <h1 class="teal--text" style="font-family: 'Nanum Gothic'"> (*≧∀≦*) </h1>
        <br>
        <h3 style="color: gray; font-family: 'Nanum Gothic'">좋아요 게시물 목록이 텅! 비었습니다.</h3>
        <h5 style="color: gray; font-family: 'Nanum Gothic'">TRIPLOGGER 들의 게시물들을 보면서 좋아요 버튼을 눌러보세요!</h5>
      </div>
      <Card v-else
        v-for="likeArticle in likeArticle"
        :key="likeArticle.title"
        :size="3"
        :value="likeArticle"
      />
    </v-row>
    <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import http from "../../util/http-common";
import Card from "../../components/likey/Card.vue";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "Like",
  components: {
    Card,
    InfiniteLoading
  },
  data() {
    return {
      likeArticle: [],
      limit: 0
    };
  },

  methods: {
    getLikeList: function() {
      http
        .post("/article/likelist/", {
          usernum: this.getUserNum,
          limit: this.limit
        })
        .then(response => {
          this.likeArticle = response.data;
        })
        .catch(error => {
          console.log(error.data);
        });
    },
    infiniteHandler($state) {
      http
        .post("/article/likelist/", {
          usernum: this.getUserNum,
          limit: this.limit + 9
        })
        .then(response => {
          setTimeout(() => {
            if (response.data.length) {
              this.likeArticle = this.likeArticle.concat(response.data);
              $state.loaded();
              this.limit += 9;
              if (this.likeArticle.length / 9 == 0) {
                $state.complete();
              }
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch(error => {
          console.error(error);
        });
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
    }
  },
  created: function() {
    this.getLikeList();
  }
};
</script>

<style>
</style>