<template>
  <v-container class="pa-0" style="display:flex; justify-content:center;">
    <v-container class="pa-0 d-none d-sm-block" style="min-width:300px ;   ">
      <v-container class="pb-0" style="display:flex; justify-content:flex-end;">
        <v-list-item-avatar>
          <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${hostImg}`"></v-img>
        </v-list-item-avatar>

        <p
          class="mt-2"
          style=" font-size: 25px; font-family: 'Poor Story'"
        >{{this.hostNickName}}' Blog</p>
      </v-container>
      <v-container class="pt-0 pb-0">
        <hr style="border: solid 1.5px; color: aliceblue;" />
      </v-container>
      <!-- <v-container class="border pa-0" style> -->
      <v-container style="height:350px">
        <v-img
          :src="`http://i3b207.p.ssafy.io/res/blogImage/${titleimg}`"
          style="position: relative; z-index: 1; width: 100%; height: 100%; opacity:0.5;"
          alt
        />
        <v-container
          style=" opacity:1; position: relative; z-index: 2;   height: 325px; top: -325px; display:flex;  flex-direction:column; justify-content:space-between;"
        >
          <v-row style="height: 100%; width: 100%;  margin :0;">
            <v-col>
              <div v-if="isMyBlog" class="font-weight-light mb-2">
                <v-btn class="mx-4" fab small dark color="teal" @click="getNeighborList">
                  <v-icon color="white" size="24px">mdi-account-multiple</v-icon>
                </v-btn>
              </div>
              <div v-else class="font-weight-light mb-2">
                <v-btn
                  class="mx-4"
                  fab
                  small
                  dark
                  color="teal"
                  v-if="isMyNeighbor"
                  @click="removeNeighbor"
                >
                  <v-icon color="white" size="24px">mdi-account-multiple-minus</v-icon>
                </v-btn>
                <v-btn class="mx-4" fab small dark color="teal" v-else @click="addNeighbor">
                  <v-icon color="white" size="24px">mdi-account-multiple-plus</v-icon>
                </v-btn>
              </div>
              <v-card v-if="showNeighborList" width="100px">
                <v-simple-table>
                  <tbody>
                    <NeighborListComp
                      v-for="(neighbor, index) in neighbors"
                      :key="`${index}_neighbors`"
                      :userNum="neighbor.userNum"
                      :neighborNum="neighbor.neighborNum"
                      :neighborNickname="neighbor.neighborNickname"
                    />
                  </tbody>
                </v-simple-table>
              </v-card>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <h1
                class="pa-3"
                style="display:flex; justify-content:center; align-items:flex-end;"
                :style="getColor"
              >{{title}}</h1>
              <h5
                style="margin-bottom : 30px; display:flex; justify-content:center; align-items:flex-end;"
              >{{hostIntro}}</h5>
            </v-col>
          </v-row>
        </v-container>
      </v-container>
    </v-container>

    <!-- 여기서부터 모바일 환경 -->

    <v-container class="pa-0 d-block d-sm-none" :style="mobileSize">
      <v-container class="pb-0" style="display:flex; justify-content:flex-end;">
        <v-list-item-avatar>
          <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${hostImg}`"></v-img>
        </v-list-item-avatar>

        <p
          class="mt-2"
          style=" font-size: 25px; font-family: 'Poor Story'"
        >{{this.hostNickName}}' Blog</p>
      </v-container>
      <v-container class="pt-0 pb-0">
        <hr style="border: solid 1.5px; color: aliceblue;" />
      </v-container>
      <!-- <v-container class="border pa-0" style> -->
      <v-container class="pa-0" style="height:350px">
        <v-img
          :src="`http://i3b207.p.ssafy.io/res/blogImage/${titleimg}`"
          style="position: relative; z-index: 1; width: 852px; height: 325px; opacity:0.5;"
          alt
        />
        <v-container
          style=" opacity:1; position: relative; z-index: 2;   height: 325px; top: -325px; display:flex;  flex-direction:column; justify-content:space-between;"
        >
          <v-row style="height: 100%; width: 100%;  margin :0;">
            <v-col>
              <div v-if="isMyBlog" class="font-weight-light mb-2">
                <v-btn class="mx-4" fab small dark color="teal" @click="getNeighborList">
                  <v-icon color="white" size="24px">mdi-account-multiple</v-icon>
                </v-btn>
              </div>
              <div v-else class="font-weight-light mb-2">
                <v-btn
                  class="mx-4"
                  fab
                  small
                  dark
                  color="teal"
                  v-if="isMyNeighbor"
                  @click="removeNeighbor"
                >
                  <v-icon color="white" size="24px">mdi-account-multiple-minus</v-icon>
                </v-btn>
                <v-btn class="mx-4" fab small dark color="teal" v-else @click="addNeighbor">
                  <v-icon color="white" size="24px">mdi-account-multiple-plus</v-icon>
                </v-btn>
              </div>
              <v-card v-if="showNeighborList" width="100px">
                <v-simple-table>
                  <tbody>
                    <NeighborListComp
                      v-for="(neighbor, index) in neighbors"
                      :key="`${index}_neighbors`"
                      :userNum="neighbor.userNum"
                      :neighborNum="neighbor.neighborNum"
                      :neighborNickname="neighbor.neighborNickname"
                    />
                  </tbody>
                </v-simple-table>
              </v-card>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <h1
                class="pa-3"
                style="display:flex; justify-content:center; align-items:flex-end;"
                :style="getColor"
              >{{title}}</h1>
              <h5
                style="margin-bottom : 30px; display:flex; justify-content:center; align-items:flex-end;"
              >{{hostIntro}}</h5>
            </v-col>
          </v-row>
        </v-container>
      </v-container>
    </v-container>

    <!-- </v-container> -->
  </v-container>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
import NeighborListComp from "@/components/personal/NeighborListComp.vue";

export default {
  name: "PersonalMainComp",
  components: {
    NeighborListComp
  },
  data() {
    return {
      title: "",
      titleColor: "#000000FF",
      visitcount: 0,
      titleimg: "noimage.jpg",
      isMyNeighbor: false,
      neighbors: [],
      alertMsg: "",
      dialog: false,
      showNeighborList: false,
      alert: false
    };
  },
  props: {
    hostNum: { type: Number },
    hostNickName: { type: String },
    hostEmail: { type: String },
    hostImg: { type: String },
    hostIntro: { type: String },
    isMyBlog: { type: Boolean }
  },
  methods: {
    updateProfile: function() {
      this.$emit("update-profile");
    },

    getBlogInfo() {
      http
        .get(`/blog/${this.$route.params.hostNum}`)
        .then(response => {
          this.visitcount = response.data.visitcount;
          if (response.data.title == null) {
            this.title = this.hostNickName + "'s blog";
          } else {
            this.titleColor = response.data.title.slice(0, 9);
            this.title = response.data.title.slice(9);
          }
          if (response.data.titleimg == null) {
            this.titleimg = "adventurealtitude.jpg";
          } else {
            this.titleimg = response.data.titleimg;
          }
        })
        .catch(error => {
          alert(error.data);
        });
    },
    addNeighbor() {
      if (this.getUserNum != "") {
        http
          .post("/neighbor/", {
            userNum: this.getUserNum,
            neighborNum: this.$route.params.hostNum
          })
          .then(({ data }) => {
            let msg = "이웃추가 처리시 문제가 발생했습니다.";
            if (data === "success") {
              msg = "이웃추가가 완료되었습니다.";
            }
            this.alertMsg = msg;
            this.alert = true;
            this.$emit("closeLoginModal", this.alertMsg);
            this.$router.go(this.$router.currentRoute);
          })
          .catch(error => {
            console.log(error.data);
          });
        this.$router.go(this.$router.currentRoute);
      } else {
        alert("먼저 로그인을 진행해주세요");
      }
    },
    removeNeighbor() {
      http
        .delete(`/neighbor/${this.getUserNum}/${this.$route.params.hostNum}`)
        .then(({ data }) => {
          let msg = "이웃해제 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "이웃해제가 완료되었습니다.";
          }
          this.alertMsg = msg;
          this.alert = true;
          this.$emit("closeLoginModal", msg);
          this.$router.go(this.$router.currentRoute);
        })
        .catch(error => {
          console.log(error.data);
        });
    },
    getNeighborList() {
      if (this.showNeighborList == false) {
        this.showNeighborList = true;
      } else {
        this.showNeighborList = false;
      }
    }
  },
  computed: {
    mobileSize: function() {
      let size = window.innerWidth;
      return `width: ${size}px;`;
    },
    getImg: function() {
      return `http://i3b207.p.ssafy.io/res/blogImage/${this.titleimg}`;
    },
    getUserImg: function() {
      if (this.hostImg == null) {
        return `http://i3b207.p.ssafy.io/res/userImage/${this.titleimg}`;
      } else {
        return `http://i3b207.p.ssafy.io/res/userImage/${this.hostImg}`;
      }
    },
    getColor: function() {
      return `color : ${this.titleColor};`;
    },
    ...mapGetters(["isAuthenticated", "isProfileLoaded", "getUserNum"]),
    ...mapState({
      authLoading: state => state.auth.status === "loading"
    }),
    loading: function() {
      return this.authStatus === "loading" && !this.isAuthenticated;
    }
  },
  created() {},
  mounted() {
    http
      .get(`/neighbor/${this.getUserNum}`)
      .then(response => {
        this.neighbors = response.data;
        var list = response.data;
        for (let index = 0; index < list.length; index++) {
          if (list[index].neighborNum == this.$route.params.hostNum) {
            this.isMyNeighbor = true;
          }
        }
      })
      .catch(error => {
        console.log(error.data);
      });
    this.getBlogInfo();
  }
};
</script>

<style>
.border {
  color: aliceblue;
}
.bg::after {
  width: 100%;
  height: 100%;
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  opacity: 0.6;
  background-size: cover;
  border-radius: 10px;
}
.bg-md {
  color: var(--c-olor);
  width: 852px;
  height: 375px;
  border-radius: 10px;
  position: relative;
  z-index: 1;
}
.bg-sm {
  color: var(--c-olor);
  width: 500px;
  height: 375px;
  border-radius: 10px;
  position: relative;
  z-index: 1;
}
.bg {
  color: var(--c-olor);
  width: 300px;
  height: 325px;
  border-radius: 10px;
  position: relative;
  z-index: 1;
}
</style>