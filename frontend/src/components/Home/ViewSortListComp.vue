<template>
  <div>
    <v-row style="display: flex;
justify-content:center;">
      <view-sort-list-item-comp
        v-for="(item,index) in items"
        :key="`${index}_items`"
        :num="item.num"
        :user_num="item.user_num"
        :title="item.title"
        :thumbnail="item.thumbnail"
        :rank="index"
        :views="item.views"
      />
    </v-row>
  </div>
</template>

<script>
import http from "@/util/http-common";
import ViewSortListItemComp from "@/components/Home/ViewSortListItemComp.vue";
export default {
  name: "ViewSortListComp",
  components: {
    ViewSortListItemComp
  },
  data: function() {
    return {
      items: []
    };
  },
  created() {
    http.get(`/article/viewsort`).then(({ data }) => {
      this.items = data;
    });
  }
};
</script>

<style>
</style>