<template>
  <div>
    <v-row style="display: flex;
justify-content:center;">
      <comment-sort-list-item-comp
        v-for="(item,index) in items"
        :key="`${index}_items`"
        :num="item.num"
        :user_num="item.user_num"
        :title="item.title"
        :thumbnail="item.thumbnail"
        :rank="index"
        :commentNum="comment[index]"
      />
    </v-row>
  </div>
</template>

<script>
import http from "@/util/http-common";
import CommentSortListItemComp from "@/components/Home/CommentSortListItemComp.vue";

export default {
  name: "CommentSortListComp",
  components: {
    CommentSortListItemComp
  },
  data: function() {
    return {
      items: [],
      comment: [],
    };
  },
  created() {
    http.get(`/article/commentsort`).then(({ data }) => {
      
      for(var i=0; i < 6; i+=2) {
        this.items.push(data[i]);
        this.comment.push(data[i+1]);
      }
      
    });
  }
};
</script>

<style>
</style>