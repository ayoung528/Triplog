<template>
    <v-card width="300" class="ma-3" elevation="2">
        <v-img v-if="thumbnail" :src="`http://i3b207.p.ssafy.io/res/articleImage/${thumbnail}`"
        height="300" >
            <v-card-title style="font-family: 'Nanum Gothic'" class="title white--text">
                <v-row class="fill-height flex-column" justify="space-between">
                    <p class="text-left ma-2 subheading" style="font-family: 'Nanum Gothic'; text-shadow: 3px 3px 4px #383838;">{{this.title}}</p>

                    <div>
                        <p class="ml-2 body-1 text-left" style="font-family: 'Nanum Gothic'; text-shadow: 3px 3px 4px #383838;">by {{this.userNickname}}</p>
                        <br>
                        <p class="ml-2 caption text-left" style="font-family: 'Nanum Gothic'; text-shadow: 3px 3px 4px #383838;">{{this.created_at | moment('YYYY-MM-DD')}}</p>
                    </div>
                </v-row>
            </v-card-title>
            <br>
            <br>
            <br>
            <div class="text-right ma-2">
                <v-btn color="teal" dark @click="clickTableRow()">READMORE</v-btn>
            </div>
        </v-img>
        <v-img v-else :src="`http://i3b207.p.ssafy.io/res/articleImage/noimage.jpg`" height="300"
        >
        
            <v-card-title style="font-family: 'Nanum Gothic'" class="title black--text">
                <v-row class="fill-height flex-column" justify="space-between">
                    <p class="text-left ma-2 subheading black--text" style="font-family: 'Nanum Gothic'; text-shadow: 3px 3px 4px #383838;">{{this.title}}</p>

                    <div>
                        <p class="ml-2 body-1 text-left black--text" style="font-family: 'Nanum Gothic'; text-shadow: 3px 3px 4px #383838;">by {{this.userNickname}}</p>
                        <v-layout column align-center justify-center class="white--text">
                            <h1 class="grey--text font-weight-bold text-center">No Image</h1>
                        </v-layout>
                        <p class="ml-2 caption text-left black--text text-shadow: 3px 3px 4px #383838;">{{this.created_at | moment('YYYY-MM-DD')}}</p>
                    </div>
                </v-row>
            </v-card-title>
            <br>
            <br>
            <div class="text-right ma-2">
                <v-btn color="teal" dark @click="clickTableRow()">READMORE</v-btn>
            </div>
            
        </v-img>
        <template v-slot:placeholder>
            <v-row
                class="fill-height ma-0"
                align="center"
                justify="center"
            >
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
            </v-row>
        </template>
    </v-card>
</template>

<script>
export default {
    name: "ArticleSearchComp",
    props: {
        propCreated_at: { type: String },
        propUserNickname: { type: String },
        propTitle: { type: String },
        propThumbnail: { type: String },
        propNum: {type: Number},
    },
    data() {
        return {
            created_at: this.propCreated_at,
            userNickname: this.propUserNickname,
            title: this.propTitle,
            thumbnail: this.propThumbnail,
            num: this.propNum,
        };
    },
    methods: {
        clickTableRow: function() {
            this.$router.push(`/article/detail/${this.num}`);
        },
    },
    created: function() {
        if (this.title.length >= 10) {
            this.title = this.title.substring(0,9)+"...";
        }
    }
}
</script>