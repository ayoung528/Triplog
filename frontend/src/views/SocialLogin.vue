<template>
  <v-container>
    <v-card v-if="onSignupPage" class="px-4">
      <v-card-text>
        <v-form action method="post" id="_frmForm" name="frmForm" @submit.prevent="registryMyself">
          <v-row>
            <v-col cols="2" style="padding-right:0px;">
              <p
                align="center"
                style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
              >Email(ID) :</p>
            </v-col>
            <v-col cols="10" style="display:flex; align-items:center;">
              <h3 style="font-family: 'Sunflower';">{{customer.email}}</h3>
            </v-col>
            <v-col cols="2" style="padding-right:0px;">
              <p
                align="center"
                style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
              >Nickname :</p>
            </v-col>
            <v-col cols="10">
              <v-text-field
                v-model="customer.nickname"
                data-msg="별명"
                outlined
                :counter="5"
                :rules="nickNameRules"
                name="nickname"
                id="_nickname"
                placeholder="별명을 입력하세요"
                label="Nickname"
                color="teal"
                style="font-family: 'Nanum Gothic';"
              ></v-text-field>
            </v-col>
            <v-col cols="2" style="padding-right:0px;">
              <p
                align="center"
                style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
              >Name :</p>
            </v-col>
            <v-col cols="10">
              <v-text-field
                v-model="customer.cname"
                label="Name"
                required
                placeholder="이름을 입력하세요"
                id="_cname"
                outlined
                name="cname"
                data-msg="이름"
                style="font-family: 'Nanum Gothic';"
                color="teal"
              ></v-text-field>
            </v-col>
            <v-col cols="2" style="padding-right:0px;">
              <p
                align="center"
                style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
              >Password :</p>
            </v-col>
            <v-col cols="10">
              <v-text-field
                block
                v-model="customer.password"
                type="password"
                data-msg=" 패스워드"
                outlined
                name="password"
                id="_password"
                placeholder="패스워드를 입력하세요"
                label="Password"
                color="teal"
              ></v-text-field>
            </v-col>
            <v-col cols="2" style="padding-right:0px;">
              <p
                align="center"
                style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "
              >PW Confirm :</p>
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
            <v-spacer></v-spacer>
            <v-col class="d-flex ml-auto" cols="12">
              <v-btn type="submit" @click="registryMyself" x-large block color="cyan darken-4">
                <p style="color:white; margin-bottom:0px;">SignUp</p>
              </v-btn>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
// import http from "@/util/http-common";

import localHttp from "@/util/http-common";
import { AUTH_REQUEST_SOCIAL } from "@/store/actions/auth";
export default {
  name: "SocialLogin",
  data() {
    return {
      customer: {
        email: "",
        nickname: "",
        cname: "",
        password: "",
        nickNameRules: [
        v => (v && v.length <= 5) || "별명은 5글자 이내만 가능합니다."
      ],
      },
      onSignupPage: false
    };
  },
  methods: {
    registryMyself() {
      if (this.customer.email == "") {
        this.alertMsg = "이메일을 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.customer.nickname == "") {
        this.alertMsg = "별명을 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.customer.cname == "") {
        this.alertMsg = "이름을 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.customer.password == "") {
        this.alertMsg = "패스워드를 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.customer.password != this.password_confirm) {
        this.alertMsg = "재입력한 패스워드가 다릅니다.";
        this.alert = true;
        return;
      }
      if (!/^[a-zA-Z0-9]{8,20}$/.test(this.customer.password)) {
        this.alertMsg =
          "비밀번호는 숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.";
        this.alert = true;
        return;
      }
      var chk_num = this.customer.password.search(/[0-9]/g);
      var chk_eng = this.customer.password.search(/[a-z]/gi);
      if (chk_num < 0 || chk_eng < 0) {
        this.alertMsg = "비밀번호는 숫자와 영문자를 혼용하여야 합니다.'";
        this.alert = true;
        return;
      }

      localHttp
        .post("/auth/signup", {
          nickname: this.customer.nickname,
          name: this.customer.cname,
          email: this.customer.email,
          password: this.customer.password,
          valid: true
        })
        .then(response => {
          if (
            response.data.success == true ||
            response.data.success == "true"
          ) {
            this.alertMsg = "성공적으로 가입 하였습니다. ";
            this.alert = true;
            this.visablelogin = true;
            this.submitted = true;
            this.newCustomer();
            this.$router.push("/");
          } else {
            this.alertMsg = response.data.message;
            this.alert = true;
            this.visablelogin = false;
            this.submitted = false;
          }
        })
        .catch(e => {
          if (e.request.status === 404) {
            this.alertMsg = "회원가입에 실패하였습니다.";
            this.alert = true;
          } else {
            this.visablelogin = false;
            this.submitted = false;
            this.newCustomer();
            this.$router.push(`/apierror/${e.request.status}/`);
          }
        });
    },
    newCustomer() {
      this.customer = {
        nickname: "",
        cname: "",
        email: "",
        password: ""
      };
    }
  },

  created() {
    localHttp
      .get(
        `/social/${this.$route.params.site}/login?code=${this.$route.query.code}`
      )
      .then(({ data }) => {
        if (data.token != undefined) {
          this.$store
            .dispatch(AUTH_REQUEST_SOCIAL, data)
            .then(() => {
              this.$router.push(`${this.$route.query.state}`);
            })
            .catch(e => {
              alert(e);
            });
        } else {
          this.onSignupPage = true;
          this.customer.nickname = data.nickname;
          this.customer.email = data.email;
        }
      })
      .catch(error => {
        alert(error);
      });
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");
</style>