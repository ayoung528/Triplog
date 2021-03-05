<template>
  <v-app>
    <v-footer fixed padless class="d-flex d-sm-none" v-if="getUserNum">
      <v-card flat tile width="100%" class="lighten-1 text-center">
        <v-card-text>
          <v-btn class="mx-4" icon @click="info">
            <v-icon color="teal" size="24px">mdi-account</v-icon>
          </v-btn>
          <v-btn class="mx-4" icon @click="$router.push('/like')">
            <v-icon color="teal" size="24px">mdi-charity</v-icon>
          </v-btn>
          <v-btn class="mx-4" icon @click="moveBlog">
            <v-icon color="teal" size="24px">mdi-blogger</v-icon>
          </v-btn>
          <v-btn class="mx-4" icon @click="goWrite">
            <v-icon color="teal" size="24px">mdi-file-edit</v-icon>
          </v-btn>
          <v-btn class="mx-4" icon @click="logout" v-if="getUserNum !=''">
            <v-icon color="teal" size="24px">fas fa-unlock</v-icon>
          </v-btn>
        </v-card-text>

        <v-divider></v-divider>
      </v-card>
    </v-footer>
    <div>
      <v-navigation-drawer
        class="d-none d-sm-flex"
        clipped
        app
        permanent
        v-if="this.getProfile"
        expand-on-hover
        floating
        color="teal"
      >
        <v-list-item class="px-2 mb-6" style="padding: 10px;">
          <v-list-item-avatar>
            <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${userimg}`"></v-img>
          </v-list-item-avatar>

          <v-list-item-title
            class="white--text"
            style="font-size: 25px; font-family: 'Poor Story'"
          >{{this.getProfile}}님</v-list-item-title>
        </v-list-item>
        <v-divider color="white"></v-divider>

        <v-list>
          <v-list-item to="/" @click.stop class="mb-5">
            <v-list-item-icon>
              <v-icon color="white">mdi-home-city</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title
                style="font-size: 20px; font-family: 'Sunflower'"
                class="white--text"
              >Home</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click.stop="info" class="mb-5">
            <v-list-item-icon>
              <v-icon color="white">mdi-account</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title
                style="font-size: 20px; font-family: 'Sunflower'"
                class="white--text"
              >My Account</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item to="/like" @click.stop class="mb-5">
            <v-list-item-icon>
              <v-icon color="white">mdi-charity</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title
                style="font-size: 20px; font-family: 'Sunflower'"
                class="white--text"
              >Like</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item class="mb-5" @click.stop="moveBlog">
            <v-list-item-icon>
              <v-icon color="white">mdi-blogger</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title
                style="font-size: 20px; font-family: 'Sunflower'"
                class="white--text"
              >My Blog</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item class="mb-5" @click.stop="goWrite">
            <v-list-item-icon>
              <v-icon color="white">mdi-file-edit</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title
                style="font-size: 20px; font-family: 'Sunflower'"
                class="white--text"
              >Posting</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon></v-list-item-icon>

            <v-list-item-content>
              <div class="pa-2">
                <v-btn
                  @click.stop="logout"
                  class="white teal--text"
                  style=" font-family: 'Poor Story'; font-weight: bold"
                >LOGOUT</v-btn>
              </div>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-app-bar app dense clipped-left>
        <v-app-bar-nav-icon class="mx-0">
          <v-img src="/icons8-around-the-globe-100.png" width="15px"></v-img>
        </v-app-bar-nav-icon>

        <v-toolbar-title
          style="font-size: 56px; font-family: 'Poor Story'"
          class="cursor teal--text"
          @click="goMain"
        >TRIPLOG</v-toolbar-title>
        <v-spacer></v-spacer>

        <v-form action="/article/ArticleSearch">
          <v-expand-transition style="display: flex;">
            <v-text-field
              placeholder="Search"
              background-color="white"
              color="teal"
              outlined
              single-line
              v-show="searchtoggle"
              class="mt-6 shrink"
              dense
              clearable
              name="keyword"
              style="font-size: 20px; font-family: 'Poor Story'"
            />
          </v-expand-transition>
        </v-form>

        <v-btn
          x-large
          icon
          @click="searchtoggle = !searchtoggle"
          v-if="(isAuthenticated && isProfileLoaded)"
        >
          <v-icon color="teal">mdi-text-search</v-icon>
        </v-btn>

        <v-btn @click="goLoginPage()" v-if="!(isAuthenticated && isProfileLoaded)" icon>
          <v-icon color="teal">mdi-account-arrow-right</v-icon>
        </v-btn>
      </v-app-bar>
    </div>

    <v-main style="margin:0 auto;">
      <v-container>
        <div>
          <router-view @update-profile="info"></router-view>
        </div>
      </v-container>
    </v-main>

    <v-snackbar style="font-family: 'Nanum Gothic';" shaped color="teal" elevation="24" v-model="loginSuccess" timeout="3000">
      <v-icon color="white">mdi-emoticon-excited-outline</v-icon>
      {{ getProfile + "님 반갑습니다." }}
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="loginSuccess = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-snackbar style="font-family: 'Nanum Gothic';" shaped color="teal" elevation="24" v-model="logoutSuccess" timeout="3000">
      <v-icon color="white">mdi-check-bold</v-icon>
      정상적으로 로그아웃 되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="logoutSuccess = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-snackbar style="font-family: 'Nanum Gothic';" shaped color="teal" elevation="24" v-model="alert" timeout="3000">
      <v-icon color="white">mdi-home</v-icon>
      {{alertMsg}}
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="alert = false">Close</v-btn>
      </template>
    </v-snackbar>

    <div style="position: fixed; right: 20px; bottom: 20px;" class="d-none d-sm-flex">
      <v-fab-transition>
        <v-btn v-show="hidden" color="teal" fab dark @click="$vuetify.goTo(0, 0);">
          <v-icon>mdi-chevron-up</v-icon>
        </v-btn>
      </v-fab-transition>
    </div>
    <div style="position: fixed; right: 20px; bottom: 80px;" class="d-sm-none d-flex">
      <v-fab-transition>
        <v-btn v-show="hidden" color="teal" small fab dark @click="$vuetify.goTo(0, 0);">
          <v-icon>mdi-chevron-up</v-icon>
        </v-btn>
      </v-fab-transition>
    </div>
  </v-app>
</template>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "./store/actions/auth";
import Login from "@/components/account/Login.vue";
import http from "@/util/http-common";
import $ from "jquery";

export default {
  name: "App",
  data: () => ({
    drawer: false,
    ranColor: "red",
    clientHeight: 1000,
    loginSuccess: false,
    logoutSuccess: false,
    loginModalToggle: false,
    alert: false,
    alertMsg: "",
    defaultSelected: "제목",
    items: ["제목", "작성자"],
    titleSearch: "",
    title: "UnTitled",
    navitems: [
      { title: "Home", icon: "mdi-home-city" },
      { title: "My Account", icon: "mdi-account" },
      { title: "좋아요 목록", icon: "mdi-account-group-outline" },
      { title: "게시물 목록", icon: "mdi-account-group-outline" }
    ],
    searchtoggle: false,
    userimg: "profile_init.png",
    hidden: false,
    scrolled: false,
    headerTop: 0
  }),
  components: {
    Login
  },
  methods: {
    goMain: function() {
      var para = document.location.href.split("http://i3b207.p.ssafy.io");
      // 8081 뒤에 / 붙이면 안되요!

      if (para[1] == "/") {
        this.$router.go();
      } else {
        this.$router.push("/");
      }
    },
    goToMyBlog: function() {
      this.$router.push(`/${this.getUserNum}`);
    },
    logout: function() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.drawer = false;
        const path = "/";
        // if (this.$route.path !== path) this.$router.push(path);
      });
      this.$router.push("/");
    },
    info: function() {
      this.$router.push(`/userSetting/${this.getUserNum}`);
    },
    avatarName: function(name) {
      var tempName = name.split(/(?=[A-Z])/);
      if (tempName.length == 1)
        return tempName[0].substring(0, 2).toUpperCase();
      else
        return (
          tempName[0].substring(0, 1) + tempName[1].substring(0, 1)
        ).toUpperCase();
    },
    randomColorGenerateor: function() {
      var letters = "0123456789ABCDEF";
      var color = "#";
      for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      this.ranColor = color;
    },
    handleResize: function() {
      this.clientHeight = document.documentElement.clientHeight;
    },

    gitPage: function() {
      window.open(
        "https://lab.ssafy.com/edonc700/happyhousefinal",
        "PopupGit",
        "width=1000,height=900"
      );
    },
    goWrite: function() {
      if (this.getValid) {
        this.$router.push("/article/write");
      } else {
        this.$router.push(`/emailAuth`);
      }
    },
    useSnackBar: function(msg) {
      if (msg != null) {
        this.alertMsg = msg;
        this.alert = true;
      }
    },
    notice: function() {
      this.$router.push("noticeList");
    },
    moveBlog() {
      if (this.getValid) {
        this.$router.push(`/article/list/${this.getUserNum}`);
        this.$router.go(this.$router.currentRoute);
      } else {
        this.$router.push(`/emailAuth`);
      }
    },
    goLoginPage() {
      var para = document.location.href.split("http://i3b207.p.ssafy.io");
      this.$router.push(`/login?redirect=${para[1]}`);
    },
    detectWindowScrollY() {
      this.scrolled = window.scrollY > this.headerTop ? true : false;
      if (this.scrolled) {
        this.hidden = true;
      } else {
        this.hidden = false;
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
      "getUserNum",
      "getValid",
      "getUserImg"
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userEmail: state => `${state.user.getEmail}`,
      userNum: state => `${state.user.getUserNum}`,

      valid: state => `${state.user.getValid}`
    }),
    loading: function() {
      return this.authStatus === "loading" && !this.isAuthenticated;
    }
  },
  mounted() {
    this.randomColorGenerateor();
    this.handleResize();
    window.addEventListener("resize", this.handleResize);
    window.addEventListener("scroll", this.detectWindowScrollY);
    this.header = this.$refs.pageHeader;
    if (typeof header != "undefined") {
      this.headerTop = this.header.offsetTop;
    }
  },
  beforeDestory() {
    window.removeEventListener("scroll", this.detectWindowScrollY);
  },
  watch: {
    getProfile: function() {
      if (this.getProfile == "") {
        this.logoutSuccess = true;
      } else {
        this.loginSuccess = true;
      }
    },
    getUserImg: function() {
      if (this.getUserImg != "null" && this.getUserImg != null) {
        this.userimg = this.getUserImg;
      } else {
        this.userimg = "profile_init.png";
      }
    }
  },
  created() {
    
    if (this.getUserImg != "null" && this.getUserImg != null) {
      this.userimg = this.getUserImg;
    } else {
      this.userimg = "profile_init.png";
    }
  }
};
</script>
<style  scoped>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");

.cursor:hover {
  cursor: pointer;
}
</style>