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
        <h1><span style="background-color:teal; color:white;" class="px-3 rounded">{{rank+1}}</span></h1>
          <v-expand-transition>
            <div
              v-if="hover"
              class="d-flex transition-fast-in-fast-out teal v-card--reveal display-0 white--text"
              style="height: 100%;"
            >
              <div align="center">
                <h2>{{title}}</h2>
                
              </div>
            </div>
          </v-expand-transition>
          
        </v-img>
        <v-img v-else :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`" class="thumb" @click="moveDetail"  max-width="310"
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
              </div>
            </div>
          </v-expand-transition>
        </v-img>
        
      </v-card>
      
    </v-hover>
    
    <div class="aInfo">
     
      <h3 style="text-overflow:ellipsis;">{{title}}</h3>
      <v-row>
              <v-col cols="2" style="padding:0;">
              <v-avatar style="margin-right:0;"><v-img :src="`http://i3b207.p.ssafy.io/res/userImage/${userimg}`" style="margin:0;" class="d-flex mr-0"></v-img></v-avatar>
              </v-col>
              <v-col cols="2" style="padding:0;">
                <v-card-text class="d-flex" style="padding-right:0;padding-left:0;"><h4 class="pa-0">{{userNickname}}</h4></v-card-text>
              </v-col>
              <v-col cols="8" style="padding:0;">
              <v-card-text class="float-right"><h4 class="float-right">{{commentNum}}개의 댓글</h4></v-card-text>
              </v-col>
        </v-row>
      
    </div>
  </div>
</template>



<script>
import http from "@/util/http-common";
export default {
    name:"list",
    data: function() {
    return {
      userimg: "profile_init.png",
      userNickname:"",
    };
  },
    props:{
        num:{type:Number},
        user_num: {type:Number},
        title: { type: String },
        thumbnail: { type: String },
        commentNum: {type:Number},
        rank: {type:Number},
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
    },
    methods: {
        moveDetail:function() {
            this.$router.push(`/article/detail/${this.num}`);
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