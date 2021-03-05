<template>
  <div>
    <v-hover v-slot:default="{ hover }">
      <v-card :class="{ 'on-hover': hover }" width="310px" style="background: white;">
        <v-img
          v-if="thumbnail"
          :src="`http://i3b207.p.ssafy.io/res/articleImage/${thumbnail}`"
          class="thumb"
          @click="moveDetail"
        >
          <v-expand-transition>
            <div
              v-if="hover"
              class="d-flex transition-fast-in-fast-out teal v-card--reveal display-0 white--text"
              style="height: 100%;"
            >
              <div align="center">
                <h2>{{title}}</h2>
                <p>{{getFormatDate(created_at)}}</p>
              </div>
            </div>
          </v-expand-transition>
          <!-- <v-card-subtitle>{{title}}</v-card-subtitle> -->
        </v-img>
        <v-img v-else :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`" class="thumb" @click="moveDetail">
        <v-layout column align-center justify-center class="white--text" fill-height>
          <h1 class="grey--text font-weight-bold text-center">No Image</h1>
        </v-layout>
          <v-expand-transition>
            <div
              v-if="hover"
              class="d-flex transition-fast-in-fast-out teal v-card--reveal display-0 white--text"
              style="height: 100%;"
            >
              <div align="center">
                <h2>{{title}}</h2>
                <p>{{getFormatDate(created_at)}}</p>
              </div>
            </div>
          </v-expand-transition>
        </v-img>
      </v-card>
    </v-hover>
    <div class="aInfo">
      <b>{{artTitle}}</b>
      <p>{{view}} views · {{date}}</p>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "list",
  props: {
    num: { type: Number },
    user_num: { type: Number },
    title: { type: String },
    thumbnail: { type: String },
    created_at: { type: String },
    views: { type: Number }
  },
  data: function() {
    return {
      view: "",
      date: "",
      artTitle: "",
    };
  },
  created(){
    if(this.views == null){
      this.view = 0;
    }else{
      this.view = this.views;
    }
    this.date = this.setTime();
    if(this.title.length > 20){
      this.artTitle = this.title.substring(0,19)+"...";
    }else{
      this.artTitle = this.title;
    }
  },
  methods: {
    moveDetail: function() {
      this.$router.push(`/article/detail/${this.num}`);
    },
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
    setTime: function() {
      let today = new Date();
      let timeValue = new Date(
        Number(this.created_at.slice(0, 4)),
        Number(this.created_at.slice(5, 7) - 1),
        Number(this.created_at.slice(8, 10)),
        Number(this.created_at.slice(11, 13)),
        Number(this.created_at.slice(14, 16)),
        Number(this.created_at.slice(17, 19)),
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
      return this.created_at;
    }
  }
};
</script>

<style>
.pimage {
  width: 300px;
}
.thumb {
  width: 100%;
  height: 200px;
}
/* .v-card:hover {
   filter: drop-shadow(3px 3px 5px rgb(136, 136, 136)); 
   transition: opacity 0.4s ease-in-out; 
} */
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}
.aInfo {
  margin-top: 5px;
  margin-left: 10px;
}
</style>