<template>
  <div>
    <div id="app">
      <v-container style="max-width: 700px;">
        <v-form ref="form" v-model="valid">
        <v-row>
          <v-col>
            <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Blog Image</p>
            <div align="center">
                <div v-if="firstImage">
                <v-img
                  v-if="titleimg"
                  :src="`http://i3b207.p.ssafy.io/res/blogImage/${titleimg}`"
                  class="img"
                  width="250"
                  height="130"
                >
                  <template v-slot:placeholder>
                    <v-row class="fill-height ma-0" align="center" justify="center">
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
                <v-img
                  v-else
                  :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`"
                  class="img"
                  width="250"
                  height="130"
                >
                  <template v-slot:placeholder>
                    <v-row class="fill-height ma-0" align="center" justify="center">
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </div>
              <div v-else>
                <v-img v-if="imageUrl" :src="imageUrl" class="img" width="150" height="120">
                  <template v-slot:placeholder>
                    <v-row class="fill-height ma-0" align="center" justify="center">
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
                <v-img
                  v-else
                  :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`"
                  class="img"
                  width="250"
                  height="130"
                >
                  <template v-slot:placeholder>
                    <v-row class="fill-height ma-0" align="center" justify="center">
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </div>
              <input ref="imageInput" type="file" hidden @change="onChangeImages" />
              <v-btn color="teal" class="mt-2" dark style="font-family: 'Nanum Gothic';" type="button" @click="onClickImageUpload">이미지 업로드</v-btn>
            </div>
          </v-col>
          <v-col >
            <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Visited</p>
            <p style="font-family: 'Nanum Gothic';">{{visitCount}} 명이 다녀갔습니다.</p>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Blog Title</p>
            <v-container fluid class="pa-0">
              <v-row>
                <v-col cols="10">
                  <v-textarea
                    :counter="20"
                    :rules="blogNameRules"
                    auto-grow
                    outlined
                    rows="1"
                    row-height="15"
                    label="블로그 명을 입력하세요"
                    v-model="title"
                    style="font-family: 'Nanum Gothic';"
                    color="teal"
                  ></v-textarea>
                </v-col>
                <v-col cols="2">
                  <v-btn
                    label
                    :style="getColor"
                    v-on="on"
                    v-bind="attrs"
                    icon
                    @click="onOffDialog"
                  >
                    <v-icon>fas fa-square</v-icon>
                  </v-btn>
                </v-col>
                <v-list v-if="dialog">
                  <v-list-item>
                    <v-color-picker
                      hide-inputs
                      v-model="titleColor"
                    ></v-color-picker>
                  </v-list-item>
                </v-list>
              </v-row>

            </v-container>
          </v-col>
        </v-row>

        <v-row>
          <v-col class="text-right">
            <v-btn inline-block :disabled="!valid" @click="regist" color="teal" dark style="font-family: 'Nanum Gothic';">수정</v-btn>
          </v-col>
        </v-row>
        </v-form>
      </v-container>
    </div>

    <v-snackbar shaped color="teal" v-model="alert" timeout="5000">
      <v-icon color="white">mdi-check-bold</v-icon>
      {{ alertMsg }}
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="alert = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import http from "@/util/http-common";
import http3 from "@/util/http-common3";
import { mapGetters, mapState } from "vuex";
export default {
  name: "SetBlogComp",
  props: {},
  data: function() {
    return {
      title: "",
      titleColor: "#000000FF",
      sendTitle: "",
      titleimg: "",
      visitCount: null,
      alert: false,
      alertMsg: "",
      imageUrl: "",
      num: null,
      dialog: false,
      firstImage: true,
      fileInfo: null,
      valid: true,
      blogNameRules: [
        v => (v && v.length <= 20) || "블로그 이름은 20글자 이내만 가능합니다."
      ],
    };
  },
  methods: {
    getBlogInfo() {
      http
        .get(`/blog/${this.$route.params.hostNum}`)
        .then(response => {
          this.visitCount = response.data.visitcount;
          this.titleColor = response.data.title.slice(0, 9);
          this.title = response.data.title.slice(9);
          this.num = response.data.num;
          this.titleimg = response.data.titleimg;
          
        })
        .catch(error => {
          console.log(error);
        });
    },
    regist() {
      this.sendTitle = this.sendTitle.concat(this.titleColor, this.title);
      if (this.fileInfo != null) {
        var formData = new FormData();
        formData.append("img", this.fileInfo);
        http3
          .put(`/blog/img`, formData)
          .then(({ data }) => {
            http
              .put(`/blog/`, {
                usernum: this.getUserNum,
                title: this.sendTitle,
                titleimg: data
              })
              .then(({ data }) => {
                let msg = "수정 처리시 문제가 발생했습니다.";
                if (data === "success") {
                  this.registSuccess = true;
                  msg = "수정이 완료되었습니다.";
                }
                this.alertMsg = msg;
                this.alert = true;
                this.registSuccess = true;
                this.$emit("closeSetBlogModal");
              })
              .catch(e => {
                if (e.request.status === 404) {
                  this.alertMsg = "수정 처리시 에러가 발생했습니다.";
                  this.alert = true;
                } else {
                  this.$router.push(`/apierror/${e.request.status}/`);
                }
                console.log(e.request.status);
              });
          })
          .catch(e => {
            if (e.request.status === 404) {
              this.alertMsg = "등록 처리시 에러가 발생했습니다.";
              this.alert = true;
            } else {
              this.$router.push(`/apierror/${e.request.status}/`);
            }
            console.log(e.request.status);
          });
      } else {
        http
          .put(`/blog/`, {
            usernum: this.getUserNum,
            title: this.sendTitle
          })
          .then(({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            if (data === "success") {
              this.registSuccess = true;
              msg = "수정이 완료되었습니다.";
            }
            this.alertMsg = msg;
            this.alert = true;
            this.registSuccess = true;
            this.$emit("closeSetBlogModal");
          })
          .catch(e => {
            if (e.request.status === 404) {
              this.alertMsg = "수정 처리시 에러가 발생했습니다.";
              this.alert = true;
            } else {
              this.$router.push(`/apierror/${e.request.status}/`);
            }
            console.log(e.request.status);
          });
      }
    },
    closeSetBlogMadal2() {
      this.$emit("closeSetBlogModal2");
    },

    onChangeImages(e) {
      const file = e.target.files[0];
      this.fileInfo = file;
      this.imageUrl = URL.createObjectURL(file);
      this.firstImage = false;
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onOffDialog() {
      this.dialog = !this.dialog;
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
    getColor: function() {
      return `color : ${this.titleColor};`;
    }
  },
  created: function() {
    this.getBlogInfo();
    

  },
  
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");
th {
  background-color: #eeeeee;
  color: #3e5fba;
}
</style>
