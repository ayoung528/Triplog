<template>
  <div>
    <v-row style="display: flex;
justify-content:center;">
      <like-sort-list-item-comp
        v-for="(item, index) in items"
        :key="`${index}_items`"
        :num="item.num"
        :user_num="item.user_num"
        :title="item.title"
        :thumbnail="item.thumbnail"
        :rank="index"
        :likeCount="item.likeCount"
      />
    </v-row>
  </div>
</template>

<script>
import http from "@/util/http-common";
import LikeSortListItemComp from "@/components/Home/LikeSortListItemComp.vue";

export default {
  name: "LikeSortListComp",
  components: {
    LikeSortListItemComp
  },
  data: function() {
    return {
      items: []
    };
  },
  created() {
    http.get(`/article/likesort`).then(({ data }) => {
      this.items = data;
      
    });
  }
};
</script>