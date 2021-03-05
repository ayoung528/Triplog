<template>
    <div>
        <v-toolbar color="teal lighten-2" dark rounded dense flat width="1000px">
            <v-toolbar-title style="font-family: 'Nanum Gothic'">"{{this.keyword}}" 의 검색결과 입니다.</v-toolbar-title>
        </v-toolbar>
        <v-card elevation="3">
        <v-card-subtitle outlined style="font-family: 'Nanum Gothic'" single-line>제목 검색결과
        </v-card-subtitle>
            <article-search-table-comp
            :propKeyword="this.keyword"
            :propOption="title"
            />
        <v-divider></v-divider>
        <v-card-subtitle outlined style="font-family: 'Nanum Gothic'" single-line>작성자 검색결과
        </v-card-subtitle>
            <article-search-table-comp
            :propKeyword="this.keyword"
            :propOption="writer"
            />
            </v-card>
    </div>
</template>

<script>
import ArticleSearchTableComp from "@/components/search/ArticleSearchTableComp.vue";

export default {
    name: 'ArticleSearch',
    components: {
        ArticleSearchTableComp,
    },
    data: function () {
        return {
            keyword: "",
            title: "title",
            writer: "writer"
        };
    },

    created() {
      this.keyword = this.$route.query.keyword;
   },
}
</script>
