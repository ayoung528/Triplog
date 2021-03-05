<template>
    <div>
        <v-container v-if="items.length == 0" style="text-align: center;">
            <br>
            <h1 style="color: gray; font-family: 'Nanum Gothic'"> (*≧∀≦*) </h1>
            <br>
            <h5 style="color: gray; font-family: 'Nanum Gothic'">해당하는 검색 결과가 없습니다.</h5>
        </v-container>
        <v-sheet v-else class="mx-auto" max-width="900">
            <h5 style="color: gray; font-family: 'Nanum Gothic'">{{items.length}} 개의 검색결과가 있습니다.</h5>
            <v-slide-group class="pa-4">
                <v-slide-item v-for="item in items" :key="item.num">
                    <article-search-comp
                    :propTitle="item.title"
                    :propUserNickname="item.userNickname"
                    :propThumbnail="item.thumbnail"
                    :propCreated_at="item.created_at"
                    :propNum="item.num"
                    />
                </v-slide-item>
            </v-slide-group>
        </v-sheet>
    </div>
</template>

<script>
import http from "@/util/http-common";
import ArticleSearchComp from "@/components/search/ArticleSearchComp.vue";

export default {
    name:"ArticleSearchTableComp",
    components: {
        ArticleSearchComp,
    },
    props: {
        propKeyword: {type: String},
        propOption: {type: String},
    },
    data () {
        return {
            items: [],
            keyword: this.propKeyword,
            option: this.propOption,
        }
    } ,
   methods: {
       fetchData: function() {
           if (this.option == 'title') {
                http.get(`/search/searchArticle/${this.keyword}`)
                .then((response) => {
                    this.items = response.data
                })
                .catch((e) => {
                    console.log(e)
                });

           } else {
               http.get(`/search/searchUserArticle/${this.keyword}`)
                .then((response)=>{
                    this.items = response.data
                }).catch((e)=>{
                    console.log(e)
                })
           }
      
     },
   },
    
   created() {
      this.fetchData()
   },
}
</script> 