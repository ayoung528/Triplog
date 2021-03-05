<template>
<v-app id="inspire" style="max-width: 1100px;">
  <div>
    <div>
      <PersonalMainComp
        :hostNum="item.num"
        :hostNickName="item.nickname"
        :hostEmail="item.email"
        :hostIntro="item.intro"
        :hostImg="item.imagesrc"
        :isMyBlog="isMyBlog"
        @update-profile="updateProfile"
      />
    </div>
    <div>
      <v-container fluid fill-width>
        <v-container>
          
            <v-tabs centered grow color="cyan darken-2" v-model="tab">
              <v-tab :href="`#All`">
                <v-icon left>mdi-format-list-bulleted</v-icon>All Posts
              </v-tab>
              <v-tab v-for="(tripTitle,i) in tripList" :key="i" :href="`#${tripTitle.name}`">
                <v-icon left>mdi-airplane-takeoff</v-icon>
                {{tripTitle.name}}
              </v-tab>
              <v-tab v-if="isMyBlog">
                <v-icon left>mdi-cogs</v-icon>TripPackage
              </v-tab>
            </v-tabs>
        
            <v-tabs-items v-model="tab">
              <v-tab-item :id="`All`">
                  <v-card v-if="items.length != 0">
                    <GmapMap
                      :center="mapCenter"
                      :zoom="zoom"
                      style="width: 100%; height: 400px"
                    >
                      <GmapMarker
                        :key="m.id"
                        v-for="m in markers"
                        :position="m.position"
                        :clickable="true"
                        :draggable="true"
                        @click="moveCategory(m.id)"
                      />
                    </GmapMap>
                  </v-card><br/>
                  <v-container fluid v-if="items.length != 0">
                    <v-row>
                      <v-col cols="12">
                        <v-row align="stretch" justify="space-around">
                          <ArticleListComp
                            v-for="(item, index) in items"
                            :key="index"
                            :num="item.num"
                            :user_num="item.user_num"
                            :title="item.title"
                            :thumbnail="item.thumbnail"
                            :created_at="item.created_at"
                            :views="item.views"
                          />
                        </v-row>
                      </v-col>
                    </v-row>
                  </v-container>
                  <div v-else style="text-align: center;" class="mt-5">
                    <h1 class="teal--text" style="font-family: 'Nanum Gothic'"> (*≧∀≦*) </h1>
                    <br>
                    <h3 style="color: gray; font-family: 'Nanum Gothic'">나의 게시물 목록이 텅! 비었습니다.</h3>
                    <h5 style="color: gray; font-family: 'Nanum Gothic'">게시물을 작성하면서 인기있는 TRIPLOGGER가 되어보세요!</h5>
                  </div>
                <infinite-loading @infinite="infiniteHandler" spinner="waveDots"></infinite-loading>
              </v-tab-item>
        
              <v-tab-item v-for="(tripItem,i) in tripList" :key="i" :id="`${tripItem.name}`">
                <Category :num="tripItem.num" :userNum="tripItem.userNum" :name="tripItem.name" />
              </v-tab-item>
        
              <v-tab-item v-if="isMyBlog">
                <v-app id="inspire" style="max-width: 1100px">
                  <v-container fluid>
                    <v-row>
                      <v-col cols="12">
                        <v-row align="stretch" justify="space-around">
                          <TripPackageComp @childs-event="reTripList" />
                        </v-row>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-app>
              </v-tab-item>
            </v-tabs-items>
        </v-container>
      </v-container>
    </div>
  </div>
</v-app>
</template>

<script>
import http from "@/util/http-common";
import ArticleListComp from "@/components/article/ArticleListComp.vue";
import InfiniteLoading from "vue-infinite-loading";
import { mapGetters, mapState } from "vuex";
import PersonalMainComp from "@/components/personal/PersonalMainComp.vue";
import TripPackageComp from "@/components/tripPackage/TripPackageComp.vue";
import Category from "@/components/tripPackage/Category.vue";
// import Axios from 'axios';

export default {
  name: "ArticleList",
  components: {
    ArticleListComp,
    InfiniteLoading,
    PersonalMainComp,
    TripPackageComp,
    Category,
  },
  data: function() {
    return {
      items: [],
      limit: 0,
      item: {},
      isMyBlog: false,
      tripList: [],
      visitCount: 0,
      markers: [],
      mapCenter: { lat: 0, lng: 180 },
      zoom: 1,
      tab: "All",
    };
  },
  created() {
    http
      .get(`/blog/visit/${this.$route.params.hostNum}`)
      .then(({ data }) => {
        this.visitCount = data.visitcount;
      })
      .catch(err => {
        console.log(err);
      }),
      http
        .post("/article/getList/", {
          usernum: this.$route.params.hostNum,
          limit: this.limit
        })
        .then(({ data }) => {
          this.items = data;
        });
    http.get(`/users/get/${this.$route.params.hostNum}`).then(({ data }) => {
      this.item = data;
      if (this.getUserNum == this.item.num) {
        this.isMyBlog = true;
      }
    });
    http.get(`/tripPackage/${this.$route.params.hostNum}`).then(({ data }) => {
      this.tripList = data;
      for (let index = 0; index < this.tripList.length; index++) {
        http
          .get(`/article/tripPackage/${this.tripList[index].num}`)
          .then(({ data }) => {
            var items = data;
            var y = new Array(items.length);
            var x = new Array(items.length);

            for (let i = 0; i < items.length; i++) {
              y[i] = items[i].lat;
              x[i] = items[i].lng;
            }

            var x1 = Math.min.apply(null, x);
            var x2 = Math.max.apply(null, x);
            var y1 = Math.min.apply(null, y);
            var y2 = Math.max.apply(null, y);

            var marker = {
              id: this.tripList[index].name,
              position: {
                lat: parseInt(y1 + (y2 - y1) / 2),
                lng: parseInt(x1 + (x2 - x1) / 2)
              }
            };
            this.markers.push(marker);
          });
      }
    });
  },
  methods: {
    infiniteHandler($state) {
      http
        .post("/article/getList/", {
          usernum: this.$route.params.hostNum,
          limit: this.limit + 10
        })
        .then(response => {
          setTimeout(() => {
            if (response.data.length) {
              this.items = this.items.concat(response.data);
              $state.loaded();
              this.limit += 10;
              if (this.items.length / 10 == 0) {
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
    },
    updateProfile: function() {
      this.$emit("update-profile");
    },
    reTripList() {
      http
        .get(`/tripPackage/${this.$route.params.hostNum}`)
        .then(({ data }) => {
          this.tripList = data;
        });
    },
    moveCategory(tab) {
      this.tab = tab;
    }
  },
  computed: {
    ...mapGetters(["getUserNum"]),
    ...mapState({
      userNum: state => `${state.user.getUserNum}`
    }),
  },
  watch: {
    getUserNum: function() {
      this.$router.go(this.$router.currentRoute);
    }
  }
};
</script>

<style lang="scss" scoped>
.travel-map {
  height: 400px;
}
.border {
  border-style: groove;
  border-color: gainsboro;
}
</style>