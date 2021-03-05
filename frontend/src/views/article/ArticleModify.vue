<template>
    <div>
        <article-modify-comp
            :articleNum = "item.num"
            :articleUserNum = "item.user_num"
            :articleTripPackageNum = "item.trippackage_num"
            :articleThumbnail = "item.thumbnail"
            :articleTemp = "item.temp"
            :articleTitle = "item.title"
            :articlePlace = "item.place"
            :articleDateStart = "item.date_start"
            :articleDateEnd = "item.date_end"
            :articleCreatedAt = "item.created_at"
            :articleContent = "item.content"
        />
    </div>
</template>

<script>
import ArticleModifyComp from "@/components/article/ArticleModifyComp.vue";
import http from "@/util/http-common";

export default {
  name: "ArticleModify",
  components: {
    ArticleModifyComp
  },
  data: function() {
    return {
      item: {},
      blogMaster: "유성"
    };
  },
  created() {
    http
      .get(`/article/${this.$route.params.articleNum}`)
      .then(({ data }) => {
        this.item = data;
      });
  }
};
</script>
