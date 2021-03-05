<template>
  <v-col cols="12" :md="size === 2 ? 6 : size === 3 ? 4 : undefined">
    <v-hover v-slot:default="{ hover }">
      <v-card
        :class="{ 'on-hover': hover }"
        v-bind="$attrs"
        v-on="$listeners"
        :height="value.prominent ? 450 : 350"
        color="grey lighten-1"
        dark
        :elevation="hover ? 12 : 2"
        href="#!"
      >
        <v-img v-if="value.article.thumbnail"
          :src="`http://i3b207.p.ssafy.io/res/articleImage/${value.article.thumbnail}`"
          height="100%"
          @click.stop="dialog = true"
        >
          <!-- modal str-->
          <!-- <v-dialog v-model="dialog" max-width="590"> -->
          <!-- <v-card @click="dialog = false" style="opacity: 1; height:300px;">
              <v-card-title class="headline">{{value.title}}</v-card-title>

              <br />
              <v-card-text>{{value.content}}</v-card-text>
          </v-card>-->
          <v-dialog v-model="dialog" max-width="688">
            <v-card @click="dialog = false" class="mx-auto" style="opacity: 1; ">
              <v-list-item>
                <v-list-item-avatar color="grey">
                  <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${value.writer.imageSrc}`"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="headline">
                    {{value.article.title}}
                    <small>by {{value.writer.nickname}}</small>
                  </v-list-item-title>
                  <v-list-item-subtitle>{{value.article.created_at}}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-img :src="`http://i3b207.p.ssafy.io/res/articleImage/${value.article.thumbnail}`" height="388"></v-img>
              <br />
              <v-card-text class="skipText">{{value.article.place}}</v-card-text>
              <br />
              <hr />
              <v-card-actions>
                <v-btn text color="deep-purple accent-4" @click="showDetail">Read Detail</v-btn>
                <v-spacer></v-spacer>

                <i
                  v-if="like"
                  @click.stop="updateLike"
                  class="fas fa-heart icon-4x"
                  style="color : red;"
                />
                <i
                  v-else
                  @click.stop="updateLike"
                  class="far fa-heart icon-4x"
                  style="color : red;"
                />
              </v-card-actions>
            </v-card>
          </v-dialog>
          <!-- modal end -->

          <v-row v-if="!value.prominent" class="fill-height text-right ma-0">
            <v-col cols="12">
              <!-- <v-chip
                label
                class="mx-0 mb-2 text-uppercase"
                color="grey darken-3"
                text-color="white"
                small
                @click.stop="movePackage"
              >-패키지로 넘어가는 버튼-</v-chip>-->

              <h3
                class="title font-weight-bold mb-2"
                style="text-shadow: 1px 1px 2px #383838;"
              >{{value.article.title}}</h3>
              <div
                class="caption"
                style="text-shadow: 1px 1px 2px #383838;"
              >{{value.writer.nickname}}</div>
              <div class="caption" style="text-shadow: 1px 1px 2px #383838;">{{date}}</div>
            </v-col>

            <v-col align-self="end">
              <h1 class="mr-1 mb-1">
                <i
                  v-if="like"
                  @click.stop="updateLike"
                  class="fas fa-heart icon-4x"
                  style="color : red;"
                />
                <i
                  v-else
                  @click.stop="updateLike"
                  class="far fa-heart icon-4x"
                  style="color : red;"
                />
              </h1>

              <v-chip
                class="text-uppercase ma-0"
                color="primary"
                label
                small
                style="height : 40px"
                @click.stop="showDetail"
              >Read Detail</v-chip>
            </v-col>
          </v-row>
        </v-img>

        <v-img v-else
          :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`"
          height="100%"
          @click.stop="dialog = true"
        >
          
          <v-dialog v-model="dialog" max-width="688">
            <v-card @click="dialog = false" class="mx-auto" style="opacity: 1; ">
              <v-list-item>
                <v-list-item-avatar color="grey">
                  <v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${value.writer.imageSrc}`"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="headline">
                    {{value.article.title}}
                    <small>by {{value.writer.nickname}}</small>
                  </v-list-item-title>
                  <v-list-item-subtitle>{{value.article.created_at}}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-img :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`" height="388">
                <v-layout column align-center justify-center class="white--text" fill-height>
                  <h1 class="grey--text font-weight-bold text-center">No Image</h1>
                </v-layout>
              </v-img>
              <br />
              <v-card-text class="skipText">{{value.article.place}}</v-card-text>
              <br />
              <hr />
              <v-card-actions>
                <v-btn text color="deep-purple accent-4" @click="showDetail">Read Detail</v-btn>
                <v-spacer></v-spacer>

                <i
                  v-if="like"
                  @click.stop="updateLike"
                  class="fas fa-heart icon-4x"
                  style="color : red;"
                />
                <i
                  v-else
                  @click.stop="updateLike"
                  class="far fa-heart icon-4x"
                  style="color : red;"
                />
              </v-card-actions>
            </v-card>
          </v-dialog>

          <v-row v-if="!value.prominent" class="fill-height text-right ma-0">
            <v-col cols="12">
              <h3
                class="title font-weight-bold mb-2"
                style="text-shadow: 1px 1px 2px #383838;"
              >{{value.article.title}}</h3>
              <div
                class="caption"
                style="text-shadow: 1px 1px 2px #383838;"
              >{{value.writer.nickname}}</div>
              <div class="caption" style="text-shadow: 1px 1px 2px #383838;">{{date}}</div>
              <br>
              <br>
              <v-layout column align-center justify-center class="white--text">
                <h1 class="grey--text font-weight-bold text-center">No Image</h1>
              </v-layout>
            </v-col>
            
            <v-col align-self="end">
              <h1 class="mr-1 mb-1">
                <i
                  v-if="like"
                  @click.stop="updateLike"
                  class="fas fa-heart icon-4x"
                  style="color : red;"
                />
                <i
                  v-else
                  @click.stop="updateLike"
                  class="far fa-heart icon-4x"
                  style="color : red;"
                />
              </h1>

              <v-chip
                class="text-uppercase ma-0"
                color="primary"
                label
                small
                style="height : 40px"
                @click.stop="showDetail"
              >Read Detail</v-chip>
            </v-col>
          </v-row>
        </v-img>
      </v-card>
    </v-hover>
  </v-col>
</template>
<script>
import http from "../../util/http-common";
export default {
  name: "Card",
  data() {
    return {
      like: true,
      dialog: false,
      date: false
    };
  },
  props: {
    size: {
      type: Number,
      required: true
    },
    value: {
      type: Object,
      default: () => ({})
    }
  },
  methods: {
    updateLike: function() {
      if (this.like === true) {
        http
          .delete(
            `article/likelist/${this.$store.state.user.userNum}/${this.value.article.num}`,
            null
          )
          .then(response => {
            this.like = response.data;
            this.like = false;
          })
          .catch(error => {
            console.log(error.data);
          });
      } else {
        http
          .put(
            `article/likelist/${this.$store.state.user.userNum}/${this.value.article.num}`,
            null
          )
          .then(response => {
            this.like = response.data;
            this.like = true;
          })
          .catch(error => {
            console.log(error.data);
          });
      }
    },

    showDetail: function() {
      this.dialog = false;
      this.$router.push(`/article/detail/${this.value.article.num}`);
    },

    movePackage: function() {},

    setTime: function() {
      let today = new Date();
      let timeValue = new Date(
        Number(this.value.article.created_at.slice(0, 4)),
        Number(this.value.article.created_at.slice(5, 7) - 1),
        Number(this.value.article.created_at.slice(8, 10)),
        Number(this.value.article.created_at.slice(11, 13)),
        Number(this.value.article.created_at.slice(14, 16)),
        Number(this.value.article.created_at.slice(17, 19)),
        0
      );

      timeValue.setHours(timeValue.getHours() + 9);
      let betweenTime = Math.floor(
        (today.getTime() - timeValue.getTime()) / 1000 / 60
      );
      if (betweenTime < 1) return "방금전";
      if (betweenTime < 60) {
        return `${betweenTime}분전`;
      }

      const betweenTimeHour = Math.floor(betweenTime / 60);
      if (betweenTimeHour < 24) {
        return `${betweenTimeHour}시간전`;
      }

      const betweenTimeDay = Math.floor(betweenTime / 60 / 24);
      if (betweenTimeDay < 365) {
        return `${betweenTimeDay}일전`;
      }
      return this.value.article.created_at;
    }
  },
  computed: {
    getImg: function() {
      return `http://localhost:8080/img/mountain.jpg`;
    }
  },
  created() {
    this.value.article.created_at =
      this.value.article.created_at.slice(0, 10) +
      " " +
      this.value.article.created_at.slice(11, 19);

    this.date = this.setTime();
  }
};
</script>

<style>
.v-card {
  transition: opacity 0.4s ease-in-out;
}

.v-card:not(.on-hover) {
  opacity: 0.8;
}

.skipText {
  display: inline-block;
  width: 688px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  line-height: 1.2;
  height: 3.5em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
</style>
