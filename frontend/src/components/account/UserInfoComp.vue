<template>
  <div>
    <div id="app">
      <v-container style="max-width: 700px;">
        <v-form ref="form" v-model="mValid">
          <v-row>
            <v-col>
              <p class="teal--text" style="font-family: 'Sunflower'; font-weight: bold;">Email (ID)</p>
              <p style="font-family: 'Sunflower';">{{email}}</p>
            </v-col>
            <v-col class="teal--text">
              <div align="center">
                <div v-if="firstImage">
                  <v-img
                    v-if="imagesrc"
                    :src="`http://i3b207.p.ssafy.io/res/userImage/${imagesrc}`"
                    class="img"
                    width="150"
                    height="120"
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
                    width="150"
                    height="120"
                  >
                    <template v-slot:placeholder>
                      <v-row class="fill-height ma-0" align="center" justify="center">
                        <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                      </v-row>
                    </template>
                  </v-img>
                </div>
              <div v-else>
                <v-img v-if="imageUrl" :src="imageUrl" class="img" width="200" height="100">
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
                  width="150"
                  height="120"
                >
                  <template v-slot:placeholder>
                    <v-row class="fill-height ma-0" align="center" justify="center">
                      <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                    </v-row>
                  </template>
                </v-img>
              </div>
              <input ref="imageInput" type="file" hidden @change="onChangeImages" />
              <v-btn class="mt-2" color="teal" dark style="font-family: 'Nanum Gothic';" small type="button" @click="onClickImageUpload">프로필 이미지 변경</v-btn>
            </div>
          </v-col>
        </v-row>

          <v-row>
            <v-col>
              <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Name</p>
              <v-textarea
                auto-grow
                outlined
                rows="1"
                row-height="15"
                label="이름을 입력하세요"
                v-model="name"
                color="teal"
                style="font-family: 'Nanum Gothic';"
              ></v-textarea>
            </v-col>
            <v-col>
              <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">NickName</p>
              <v-textarea
                :counter="5"
                :rules="nickNameRules"
                auto-grow
                outlined
                rows="1"
                row-height="15"
                label="별명을 입력하세요"
                v-model="nickName"
                color="teal"
                style="font-family: 'Nanum Gothic';"
              ></v-textarea>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Self-introduction</p>
              <v-textarea
                :counter="40"
                :rules="introRules"
                auto-grow
                outlined
                rows="2"
                row-height="30"
                label="소개글을 입력하세요"
                v-model="intro"
                color="teal"
                style="font-family: 'Nanum Gothic';"
              >></v-textarea>
            </v-col>
          </v-row>

        <v-row>
          <v-col>
            <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Authority</p>
            <p style="font-family: 'Nanum Gothic';" v-if="!valid">
              권한이 없습니다.
              이메일 인증을 통해 권한을 얻으세요!
            </p>
            <p style="font-family: 'Nanum Gothic';" v-else>인증된 사용자입니다.</p>
          </v-col>
          <v-col>
            <p style="font-family: 'Sunflower'; font-weight: bold;" class="teal--text">Joined At</p>
            <p style="font-family: 'Sunflower';">{{getFormatDate(joinedAt)}}</p>
          </v-col>
        </v-row>

          <v-row>
            <v-col>
              <v-btn @click="confirmSignOut" color="teal" dark style="font-family: 'Nanum Gothic';">탈퇴</v-btn>
            </v-col>
            <v-col class="text-right">
              <v-btn :disabled="!mValid" @click="modify" color="teal" dark style="font-family: 'Nanum Gothic';">수정</v-btn>
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

    <v-dialog color="teal" dark v-model="dialog" max-width="400">
      <v-card class="pa-0">
        <v-card-title style="font-family: 'Nanum Gothic';" class="headline">
          <v-icon color="red" class="mr-2">mdi-information</v-icon>정말 탈퇴하시겠습니까?</v-card-title>

        <v-card-text style="font-family: 'Nanum Gothic';">탈퇴하면 계정 정보의 복구가 불가능합니다.</v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn style="font-family: 'Nanum Gothic';" color="white" text @click="dialog = false">취소</v-btn>

          <v-btn style="font-family: 'Nanum Gothic'; font-weight: bold;" color="teal" text @click="signOut">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import http from "@/util/http-common";
import http3 from "@/util/http-common3";
import moment from "moment";
import { AUTH_LOGOUT } from "@/store/actions/auth";

import { USER_UPDATE } from "@/store/actions/user";

export default {
  name: "userInfoComp",
  props: {
    propImage: { type: String },
    propEmail: { type: String },
    propNickname: { type: String },
    propIntro: { type: String },
    propValid: { type: Boolean },
    propName: { type: String },
    propJoinedAt: { type: String }
  },
  data: function() {
    return {
      email: this.propEmail,
      name: this.propName,
      nickName: this.propNickname,
      valid: this.propValid,
      intro: this.propIntro,
      imagesrc: this.propImage,
      joinedAt: this.propJoinedAt,
      imageUrl: "",
      alert: false,
      alertMsg: "",
      dialog: false,
      fileInfo: null,
      firstImage: true,
      commentList: [],
      mediaList: [],
      mValid: true,
      nickNameRules: [
        v => (v && v.length <= 5) || "별명은 5글자 이내만 가능합니다."
      ],
      introRules: [
        v => (v && v.length <= 40) || "소개글은 40글자 이내만 가능합니다."
      ]
    };
  },
  created() {
    this.$store.dispatch(USER_UPDATE, this.getEmail).then(() => {});
  },
  computed: {
    computeEmail() {
      return this.propEmail;
    },
    computeName() {
      return this.propName;
    },
    computeJoinedAt() {
      return this.propJoinedAt;
    },
    computeNickName() {
      return this.propNickname;
    },
    computeValid() {
      return this.propValid;
    },
    computeIntro() {
      return this.propIntro;
    },
    computeImagesrc() {
      return this.propImage;
    },
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
  methods: {
    onChangeImages(e) {
      const file = e.target.files[0];
      this.fileInfo = file;
      this.imageUrl = URL.createObjectURL(file);
      this.firstImage = false;
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    getFormatDate(joinedAt) {
      return moment(new Date(joinedAt)).format("YYYY.MM.DD HH:mm:ss");
    },
    modify() {
      let err = true;
      let msg = "";
      !this.name &&
        ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      !this.nickName &&
        ((msg = "별명을 입력해주세요"), (err = false), this.$refs.nick.focus());

      if (!err) {
        this.alertMsg = msg;
        this.alert = true;
      } else this.modifyHandler();
    },
    modifyHandler: function() {
      if (this.fileInfo != null) {
        var formData = new FormData();
        formData.append("img", this.fileInfo);
        http3
          .post(`/user/img`, formData)
          .then(({ data }) => {
            http
              .put(`/users/${this.getUserNum}`, {
                name: this.name,
                nickname: this.nickName,
                intro: this.intro,
                imagesrc: data
              })
              .then(({ data }) => {
                let msg = "수정 처리시 문제가 발생했습니다.";
                if (data === "success") {
                  msg = "수정이 완료되었습니다.";
                  this.$router.go();
                }
                this.alertMsg = msg;
                this.alert = true;
              })
              .catch(e => {
                if (e.request.status === 404) {
                  this.alertMsg = "탈퇴 처리시 에러가 발생했습니다.";
                  this.alert = true;
                } else {
                  this.$emit("closeLoginModal");
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
          .put(`/users/${this.getUserNum}`, {
            name: this.name,
            nickname: this.nickName,
            intro: this.intro,
            imagesrc: this.imagesrc
          })
          .then(({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "수정이 완료되었습니다.";
              this.$store.dispatch(USER_UPDATE, this.getEmail).then(() => {});
            }
            this.alertMsg = msg;
            this.alert = true;
          })
          .catch(e => {
            if (e.request.status === 404) {
              this.alertMsg = "탈퇴 처리시 에러가 발생했습니다.";
              this.alert = true;
            } else {
              this.$emit("closeLoginModal");
              this.$router.push(`/apierror/${e.request.status}/`);
            }
            console.log(e.request.status);
          });
      }
    },
    signOut() {
      http
        .delete(`/users/delete/${this.getUserNum}`)
        .then(() => {
          let msg = "탈퇴가 완료되었습니다.";
          this.alertMsg = msg;
          this.alert = true;
          this.dialog = false;
          this.$emit("closeUserInfoModal", this.alertMsg);
          this.logout();
          this.$router.push(`/`);
        })
        .catch(e => {
          if (e.request.status === 404) {
            this.alertMsg = "탈퇴 처리시 에러가 발생했습니다.";
            this.alert = true;
          } else {
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
    },
    confirmSignOut() {
      this.dialog = true;
    },
    logout: function() {
      this.$store.dispatch(AUTH_LOGOUT);
    },
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
