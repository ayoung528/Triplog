<template>
  <v-container id="main_wrap" class="pa-0" style="max-width:740px;">
    <v-container id="middle_wrap" class="pa-0">
      <v-container class="pa-0">
        <v-container class="pa-0">
          <v-tabs
            v-model="tab"
            show-arrows="mobile"
            background-color="teal"
            color="white"
            icons-and-text
            grow
          >
            <v-tabs-slider></v-tabs-slider>
            <v-tab v-for="i in tabs" :key="i.name">
              <v-icon large>{{i.icon}}</v-icon>
              <v-container class="caption py-1">{{i.name}}</v-container>
            </v-tab>

            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form @submit.prevent="updatePassword">
                    <v-row>
                      <v-col cols="2" style="padding-right:0px;">
                        <p
                          align="center"
                          style="font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
                        >비밀번호 :</p>
                      </v-col>
                      <v-col cols="10">
                        <v-text-field
                          block
                          v-model="password"
                          type="password"
                          data-msg=" 패스워드"
                          outlined
                          name="password"
                          id="_password"
                          placeholder="패스워드를 입력하세요"
                          label="Password"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;">
                        <p
                          align="center"
                          style="font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
                        >비밀번호 확인 :</p>
                      </v-col>
                      <v-col cols="10">
                        <v-text-field
                          data-msg="패스워드 재확인"
                          type="password"
                          outlined
                          name="password_confirm"
                          id="_password_confirm"
                          placeholder="패스워드를 다시 입력하세요"
                          v-model="password_confirm"
                          block
                          label="Confirm-password"
                        ></v-text-field>
                      </v-col>

                      <v-col class="d-flex" cols="12" align-center>
                        <v-btn type="submit" x-large block color="teal">
                          <p style="color:white; margin-bottom:0px;">Update Password</p>
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-form>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-container>
        <v-snackbar shaped color="teal" dark v-model="alert" timeout="3000">
          <v-icon color="white">mdi-close</v-icon>
          {{ alertMsg }}
          <template v-slot:action="{ attrs }">
            <v-btn color="white" text v-bind="attrs" @click="alert = false">Close</v-btn>
          </template>
        </v-snackbar>
      </v-container>
    </v-container>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import http2 from "@/util/http-common2.js";

export default {
  name: "AuthSearch",
  data() {
    return {
      tab: 0,
      tabs: [{ name: "Find Account", icon: "mdi-account-question" }],
      password: "",

      alert: false,
      alertMsg: "",
      password_confirm: ""
    };
  },

  methods: {
    updatePassword() {
      if (this.password == "") {
        this.alertMsg = "패스워드를 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.password != this.password_confirm) {
        this.alertMsg = "재입력한 패스워드가 다릅니다.";
        this.alert = true;
        return;
      }
      if (!/^[a-zA-Z0-9]{8,20}$/.test(this.password)) {
        this.alertMsg =
          "비밀번호는 숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.";
        this.alert = true;
        return;
      }
      var chk_num = this.password.search(/[0-9]/g);
      var chk_eng = this.password.search(/[a-z]/gi);
      if (chk_num < 0 || chk_eng < 0) {
        this.alertMsg = "비밀번호는 숫자와 영문자를 혼용하여야 합니다.'";
        this.alert = true;
        return;
      }

      http2
        .post("/authsearch/update/", {
          code: this.$route.query.code,
          password: this.password
        })
        .then(response => {
          if (
            response.data.success == true ||
            response.data.success == "true"
          ) {
            this.alertMsg = response.data.message;
            this.alert = true;
          } else {
            this.alertMsg = response.data.message;
            this.alert = true;
          }
        })
        .catch(e => {
          if (e.request.status === 404) {
            this.alertMsg = "변경에 실패하였습니다.";
            this.alert = true;
          } else {
            this.visablelogin = false;
            this.submitted = false;
            this.newCustomer();
            this.$router.push(`/apierror/${e.request.status}/`);
          }
        });
    }
  },
  computed: {
    passwordMatch() {
      return () => this.password === this.verify || "Password must match";
    },

    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getEmail",
      "getUserNum",
      "getValid"
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userEmail: state => `${state.user.getEmail}`,
      userNum: state => `${state.user.getUserNum}`,

      valid: state => `${state.user.getValid}`
    })
  }
};
</script>

<style scoped>
th {
  background-color: #eeeeee;
  color: #3e5fba;
}
</style>
