<template>
    <v-row class="justify-space-around"  v-if="chatbot">
        <v-col sm="5" cols="12" v-for="item in chatbotList" :key="item.num">
            <v-card max-height="240">
            <v-img :src="item.media" max-height="160">
                <v-btn color="teal" dark small @click="deleteChatbotCard(item.num)">
                <v-icon>fas fa-trash</v-icon>
                </v-btn>
            </v-img>
            <p>{{item.comment}}</p>
            </v-card>
        </v-col>
    </v-row>

    <v-row v-else>
        <v-dialog
            v-model="dialog"
            max-width="700"
        >
            <template v-slot:activator="{ on, attrs }">
                <v-btn
                    color="teal"
                    dark
                    v-bind="attrs"
                    v-on="on"
                    class="mx-auto my-3"
                    style=" font-family: 'Nanum Gothic';"
                >
                간단저장 설명서
                </v-btn>
            </template>  
            <v-stepper v-model="e1" color="teal">
                <v-stepper-header>
                    <v-stepper-step style=" font-family: 'Sunflower';" color="teal" :complete="e1 > 1" step="1">챗봇 찾기</v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step style=" font-family: 'Sunflower';" color="teal" :complete="e1 > 2" step="2">이메일 등록하기</v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step style=" font-family: 'Sunflower';" color="teal" :complete="e1 > 3" step="3">사진과 글올리기</v-stepper-step>
                    <v-divider></v-divider>
                    <v-stepper-step style=" font-family: 'Sunflower';" color="teal" step="4">완료</v-stepper-step>
                </v-stepper-header>
            
                <v-stepper-items>
                    <v-stepper-content step="1">
                        <v-card
                            class="mb-12"
                            color="white"
                        >
                            <h2 style=" font-family: 'Sunflower';">카카오톡 검색에서 "TRIPLOG"를 검색합니다</h2>  
                            <br>
                            <v-img :src="`http://i3b207.p.ssafy.io/res/TRIPLOG검색.jpg`" max-height="500px"></v-img>
                        </v-card>
                
                        <v-btn
                        color="teal"
                        @click="e1 = 2"
                        style=" font-family: 'Sunflower'; font-weight: bold;"
                        dark
                        >
                        Continue
                        </v-btn>
                
                        <v-btn
                            color="teal"
                            text
                            @click="dialog = false"
                            style=" font-family: 'Sunflower'; font-weight: bold;"
                        >cancel</v-btn>
                    </v-stepper-content>
                
                    <v-stepper-content step="2">
                        <v-card
                        class="mb-12"
                        color="white"
                        >
                            <h2 style=" font-family: 'Sunflower';">TRIPLOG에 가입된 E-mail을 적어줍니다.</h2>  
                            <br>
                            <v-img :src="`http://i3b207.p.ssafy.io/res/emailvalid.jpg`"></v-img>
                        </v-card>
                        <h3 style=" font-family: 'Sunflower';">가입된 이메일이라면 확인 메세지가 나옵니다</h3>
                        <br>
                        <v-btn
                        color="teal"
                        @click="e1 = 3"
                        style=" font-family: 'Sunflower'; font-weight: bold;"
                        dark
                        >
                        Continue
                        </v-btn>
                
                        <v-btn
                            color="teal"
                            text
                            @click="dialog = false"
                            style=" font-family: 'Sunflower'; font-weight: bold;"
                        >cancel</v-btn>
                    </v-stepper-content>
                
                    <v-stepper-content step="3">
                        <v-card
                        class="mb-12"
                        color="white"
                        >
                            <h2 style=" font-family: 'Sunflower';">기록하고싶은 사진과 코멘트를 하나씩 적어줍니다</h2>  
                            <br>
                            <v-img :src="`http://i3b207.p.ssafy.io/res/media.jpg`" max-width="400"  max-height="500" class="mx-auto"></v-img>
                            <br>
                        </v-card>
                
                        <v-btn
                        color="teal"
                        @click="e1 = 4"
                        style=" font-family: 'Sunflower'; font-weight: bold;"
                        dark
                        >
                        Continue
                        </v-btn>
                
                        <v-btn
                            color="teal"
                            text
                            @click="dialog = false"
                            style=" font-family: 'Sunflower'; font-weight: bold;"
                        >cancel</v-btn>
                    </v-stepper-content>
                    <v-stepper-content step="4">
                        <v-card
                        class="mb-12"
                        color="white"
                        >
                            <h2 style=" font-family: 'Sunflower';">My Account에서 올린 사진과 글을 보실수 있습니다</h2>  
                            <br>
                            <v-img :src="`http://i3b207.p.ssafy.io/res/chatbotcomplete.jpg`" max-height="500" max-width="500" class="mx-auto"></v-img>
                        </v-card>
                
                        <v-btn
                        color="teal"
                        @click="e1 = 1"
                        style=" font-family: 'Sunflower'; font-weight: bold;"
                        dark
                        >
                        Continue
                        </v-btn>
                
                        <v-btn
                            color="teal"
                            text
                            @click="dialog = false"
                            style=" font-family: 'Sunflower'; font-weight: bold;"
                        >cancel</v-btn>
                    </v-stepper-content>
                </v-stepper-items>
            </v-stepper>
        </v-dialog>
    </v-row>  
</template>

<script>
import http from "@/util/http-common";

export default {
    name: "ChatBotComp",
    components: {

    },
    props: {
        propChatBot: {type: Boolean},
        propChatList: {type: Object}
    },
    data: function() {
        return {
            chatbot: this.propChatBot,
            chatbotList: this.propChatList,
            dialog: false,
            e1: 1,
        }
    },
    methods: {
    deleteChatbotCard: function(num) {
      http.delete(`chatbot/${num}`)
      .then(()=>{
        this.$router.go(this.$router.currentRoute);
      })
    }
  },
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=East+Sea+Dokdo&family=Gaegu&family=Hi+Melody&family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&family=Poor+Story&family=Sunflower:wght@300&family=Yeon+Sung&display=swap");
</style>