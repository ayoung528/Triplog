<template>
  <v-container id="main_wrap" class="pa-0" style="max-width:740px;">
    <v-container id="middle_wrap" class="pa-0">
      
      <v-container class="pa-0">
        <v-container class="pa-0">
          <v-tabs v-model="tab" show-arrows="mobile" background-color="teal" color="white" icons-and-text grow>
          <v-tabs-slider></v-tabs-slider>
          <v-tab style="font-family: 'Sunflower';" v-for="i in tabs" :key="i.name">
            <v-icon large>{{i.icon}}</v-icon>
            <v-container class="caption py-1">{{i.name}}</v-container> 
            </v-tab>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form @submit.prevent="login" >
                    <v-row>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Email(ID) :</p></v-col>
                      <v-col cols="10">
                         <v-text-field v-model="email" label="E-mail" required
                        type="text"
                        outlined
                        value
                        placeholder="이메일을 입력하세요"
                        color="teal"
                        style="font-family: 'Nanum Gothic';"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Password :</p></v-col>
                      <v-col cols="10">
                        <v-text-field  label="Password"
                        type="password"
                       v-model="password"
                        value
                        outlined
                        id="_pwd"
                        name="pwd"
                        required
                        color="teal"
                        placeholder="패스워드를 입력하세요"
                        style="font-family: 'Nanum Gothic';"
                        title="패스워드"></v-text-field>
                      </v-col>

                        <v-col class="d-flex" cols="12" align-center>
                        <v-btn type="submit" x-large block color="teal"><p style="font-family: 'Sunflower'; font-weight: bold; color:white; margin-bottom:0px;"> Login</p></v-btn>
                      </v-col>
                   
                    </v-row>
                 
               
                  
               
                 
                  </v-form>
                       <hr class="mb-5 mt-5">
                       <v-row>
                         <v-col cols="1"></v-col>
                      <v-col class="d-flex pa-0" cols="10" sm="5"  style="justify-content:center; ">
                        <a style="width:100%" :href="kakaoUrl">
                            <v-img :src= "`http://i3b207.p.ssafy.io/res/kakao.png`"  />
                        </a>
                         </v-col>
                         <v-col cols="1" class="d-sm-none"></v-col>
                         <v-col cols="1" class="d-sm-none"></v-col>
                         <v-col class="d-flex pa-0"  cols="10" sm="5"  style="justify-content:center;">
                        <a  style="width:100%" :href="googleUrl"
                        ><v-img :src="`http://i3b207.p.ssafy.io/res/google.png`"  />
                        </a>
                     
                         </v-col>
                         <v-col cols="1"></v-col>
                    </v-row>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form action
                    v-model="mValid"
                    method="post"
                    id="_frmForm"
                    name="frmForm"
                    @submit.prevent="registryMyself">
                    <v-row>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Email(ID) :</p></v-col>
                      <v-col cols="10">
                        <v-text-field v-model="customer.email" label="E-mail" required
                        placeholder="이메일을 입력하세요"
                        id="_email"
                        name="email"
                        type="email"
                        outlined
                        color="teal"
                        style="font-family: 'Nanum Gothic';"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Nickname :</p></v-col>
                      <v-col cols="10">
                        <v-text-field v-model="customer.nickname"
                          :counter="5"
                          :rules="nickNameRules"
                          data-msg="별명"
                          outlined
                          name="nickname"
                          id="_nickname"
                          color="teal"
                          style="font-family: 'Nanum Gothic';"
                          placeholder="별명을 입력하세요"
                          label="Nickname"></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Name :</p></v-col>
                      <v-col cols="10">
                        <v-text-field v-model="customer.cname"
                        label="Name"
                        required
                        placeholder="이름을 입력하세요"
                        id="_cname"
                        outlined
                        color="teal"
                        style="font-family: 'Nanum Gothic'"
                        name="cname"
                        data-msg="이름"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Password :</p></v-col>
                      <v-col cols="10">
                        <v-text-field block v-model="customer.password"
                          type="password"
                          data-msg=" 패스워드"
                          outlined
                          color="teal"
                          style="font-family: 'Nanum Gothic';"
                          name="password"
                          id="_password"
                        placeholder="패스워드를 입력하세요"
                        label="Password">

                        </v-text-field>

                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> PW Confirm :</p></v-col>
                      <v-col cols="10">
                        <v-text-field
                        data-msg="패스워드 재확인"
                        type="password"
                        outlined
                        color="teal"
                        style="font-family: 'Nanum Gothic';"
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
                          <v-btn :disabled="!mValid" type="submit" x-large block color="teal"><p style="font-family: 'Sunflower'; color:white; margin-bottom:0px;">SignUp</p> </v-btn>
                      </v-col>
                    </v-row>

                  </v-form>
                </v-card-text>
              </v-card>
              <br>
                    <br>
            </v-tab-item>
            <v-tab-item>
              <v-card class="px-4">
                <v-card-text>
                  <v-form @submit.prevent="authSearch" >
                    <v-row>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Email(ID) :</p></v-col>
                      <v-col cols="10">
                         <v-text-field v-model="search.email" label="E-mail" required
                        type="text"
                        outlined
                        value
                        color="teal"
                        style="font-family: 'Nanum Gothic';"
                        placeholder="이메일을 입력하세요"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="2" style="padding-right:0px;"><p align="center" style="font-family: 'Sunflower'; font-size:16px; margin-top:16px;font-weight:bold; margin-right:0px; align-text:conter; "> Name :</p></v-col>
                      <v-col cols="10">
                        <v-text-field  label="Name"
                       v-model="search.name"
                        value
                        outlined
                        id="_name"
                        name="name"
                        required
                        color="teal"
                        style="font-family: 'Nanum Gothic';"
                        placeholder="이름을 입력하세요"
                        title="이름"></v-text-field>
                      </v-col>

                        <v-col class="d-flex" cols="12" align-center>
                        <v-btn type="submit" x-large block color="teal"><p style="font-family: 'Sunflower'; color:white; margin-bottom:0px;"> Find Account</p></v-btn>
                      </v-col>
                   
                    </v-row>
                 
               
                  
               
                 
                  </v-form>
                       
                </v-card-text>
              </v-card>
            </v-tab-item>
           
      </v-tabs>
        </v-container>
        <v-snackbar
        color="teal"
        dark
        shaped
        v-model="alert"
        timeout="5000"
      >
        <v-icon color="white">mdi-close</v-icon>
        {{ alertMsg }}
        
        <template v-slot:action="{ attrs }">
          <v-btn
            color="white"
            text
            v-bind="attrs"
            @click="alert = false"
          >
            Close
          </v-btn>
        </template>
      </v-snackbar>
      </v-container>
      
      
      

    </v-container>
   
  </v-container>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import { AUTH_REQUEST } from "@/store/actions/auth";
import http2 from "@/util/http-common2.js";

export default {
  name: "login",
  data() {
    return {
      
      tab: 0,
      tabs: [
          {name:"Login", icon:"mdi-account"},
          {name:"Sign Up", icon:"mdi-account-plus"},
          {name:"Find Account", icon:"mdi-account-question"}
       ],
       show1:false,
      nowlogin: false,
      visablelogin: true,
      email: "",
      password: "",
      customer: {
        nickname: "",
        cname: "",
        email: "",
        password: ""
      },
      search:{
        email:"",
        name:"",
      },
      submitted: false,
      alert: false,
      alertMsg: "",
      password_confirm: "",
      kakaoUrl:`https://kauth.kakao.com/oauth/authorize?client_id=4b566a63a487519e52bcd20aec5f9326&redirect_uri=http://i3b207.p.ssafy.io:8080/api/social/kakao/code&response_type=code&state=${this.$route.query.redirect}`,
      googleUrl:`https://accounts.google.com/o/oauth2/v2/auth?scope=email%20profile%20openid&access_type=offline&include_granted_scopes=true&state=${this.$route.query.redirect}&redirect_uri=http://i3b207.p.ssafy.io:8080/api/social/google/code&response_type=code&client_id=692091835929-e5bhto8anq0j3v7k21kb4f87gfn2gt6s.apps.googleusercontent.com`,
      mValid: true,
      nickNameRules: [
        v => (v && v.length <= 5) || "별명은 5글자 이내만 가능합니다."
      ],
    };
  },
 
  methods: {

    login: function() {
      //model에 바인딩된 데이터 모두 ==> this
      const { email, password } = this;
      this.$store
        .dispatch(AUTH_REQUEST, { email, password })
        .then(() => {
          this.email = "";
          this.password = "";
          this.nowlogin = !this.nowlogin
          this.$router.push("/");
            
        })
        .catch((e) => {
          if (e.request.status === 404){
            this.alertMsg = "로그인에 실패하였습니다. 아이디와 비밀번호를 확인하세요.";
            this.alert = true;
          } else{
            this.$emit("closeLoginModal");
            this.$router.push(`/apierror/${e.request.status}/`)
          }
          
        });
    },
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
      if(!/^[a-zA-Z0-9]{8,20}$/.test(this.customer.password))
      { 
        this.alertMsg = "비밀번호는 숫자와 영문자 조합으로 8~20자리를 사용해야 합니다.";
        this.alert = true;
        return;
      }
      var chk_num = this.customer.password.search(/[0-9]/g);
      var chk_eng = this.customer.password.search(/[a-z]/ig);
      if(chk_num < 0 || chk_eng < 0)
      {
        this.alertMsg = "비밀번호는 숫자와 영문자를 혼용하여야 합니다.'";
        this.alert = true;
        return;
      }

      http2
        .post("/signup", {
          nickname: this.customer.nickname,
          name: this.customer.cname,
          email: this.customer.email,
          password: this.customer.password,
          valid: false
        })
        .then(response => {
          if (
            response.data.success == true ||
            response.data.success == "true"
          ) {
            this.alertMsg = "성공적으로 가입 하였습니다. 이메일인증을 해주세요.";
            this.alert = true;
            this.visablelogin = true;
            this.submitted = true;
            this.newCustomer();
            this.$router.push('/emailauth')
          } else {
            this.alertMsg = response.data.message;
            this.alert = true;
            this.visablelogin = false;
            this.submitted = false;
          } 
        }).catch((e) => {
          if (e.request.status === 404){
            this.alertMsg = "회원가입에 실패하였습니다.";
            this.alert = true;
          } else{
            this.visablelogin = false;
            this.submitted = false;
            this.newCustomer();
            this.$router.push(`/apierror/${e.request.status}/`)
          }
          console.log(e.request.status)
        });
    },
    authSearch() {
      if (this.search.email == "") {
        this.alertMsg = "이메일을 입력하세요.";
        this.alert = true;
        return;
      }
      if (this.search.name == "") {
        this.alertMsg = "별명을 입력하세요.";
        this.alert = true;
        return;
      }

      http2
        .post("/authsearch", {
          name: this.search.name,
          email: this.search.email,
        })
        .then(response => {
          if (
            response.data.success == true ||
            response.data.success == "true"
          ) {
            this.alertMsg = "발송된 이메일을 확인해주세요";
            this.alert = true;
          } else {
            this.alertMsg = response.data.message;
            this.alert = true;
          } 
        }).catch((e) => {
          if (e.request.status === 404){
            this.alertMsg = "찾기를 실패했습니다.";
            this.alert = true;
          } else{

            this.$router.push(`/apierror/${e.request.status}/`)
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
    },
    close() {
      this.newCustomer();
      this.email="";
      this.password="";
      this.visablelogin= true;
      this.submitted= false;
      this.$emit("closeLoginModal");
    },
    moveToSignUp() {
      this.visablelogin = !this.visablelogin;
      this.email = "";
      this.password = "";
    },
    moveToSignIn() {
      this.visablelogin = !this.visablelogin;
      this.newCustomer();
    },
    

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
    }),
    },

};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");
th {
  background-color:#eeeeee; 
  color:#3e5fba;
}
</style>
