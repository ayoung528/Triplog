<template>
  <div class="mx-3">
    
    <v-hover v-slot:default="{ hover }">
      
      <v-card :class="{ 'on-hover': hover } " style="background: white;">
        <v-img
          v-if="thumbnail"
          :src="`http://i3b207.p.ssafy.io/res/articleImage/${thumbnail}`"
          class="thumb"
          max-width="310"
          max-height="200"
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
                {{getFormatDate(created_at)}}
              </div>
            </div>
          </v-expand-transition>
          
        </v-img>
        <v-img v-else :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`" class="thumb" @click="moveDetail" max-width="310"
          max-height="200">
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
                {{getFormatDate(created_at)}}
              </div>
            </div>
          </v-expand-transition>
        </v-img>
        
      </v-card>
      
    </v-hover>
    
    <div class="aInfo">
     
      <h3 style="text-overflow:ellipsis;" class="my-1">{{title}}</h3>
      <v-row>
              <v-col cols="6">
              <v-avatar class="d-flex mr-0 ml-2" style="margin:0;"><v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${userimg}`" style="margin:0;" class="d-flex mr-0"></v-img></v-avatar>
              </v-col>
               <v-col style="padding:0;" cols="6">
                <v-card-text class="d-flex" style="padding-right:0; padding-left:0;"><h4 class="pa-0">{{userNickname}}</h4></v-card-text>
              </v-col>
              <!-- <v-col cols="6">
              <v-card-text class="d-flex  pr-0"><h4 class="d-flex">date</h4></v-card-text>
              </v-col> -->
        </v-row>
      
    </div>
  </div>
</template>

<script>
import moment from "moment";


import http from "@/util/http-common";
export default {

  name: "list",
  props: {
    num: { type: Number },
    user_num: { type: Number },
    title: { type: String },
    thumbnail: { type: String },
    created_at: { type: String },
    
  },
  data: function() {
    return {
      userimg: "profile_init.png",
      date: "",
      userNickname:"",
    };
  },

  created(){
   
   http.get(`/${this.user_num}/avatarimage`)
    .then(({data})=>{
      if (data != "null") {
      this.userimg = data; 
      
      }
      
    })
  http.get(`/${this.user_num}/nickname`)
  .then(({data})=>{
    this.userNickname = data;
  })
    this.date = this.setTime();
    if(this.title.length > 20){
      this.title = this.title.substring(0,19)+"...";
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
}
</script>

<style>
.pimage {
  width: 100%;
}
.thumb {
  width: 100%;
  height: 200px;
}

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