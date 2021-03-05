<template>
  <div>
    <v-row style="display: flex;
justify-content:center;">
      <recent-sort-list-item-comp
        v-for="(item,index) in items"
        :key="`${index}_items`"
        :num="item.num"
        :user_num="item.user_num"
        :title="item.title"
        :thumbnail="item.thumbnail"
      />
    </v-row>
  </div>
</template>

<script>
import http from "@/util/http-common";
import RecentSortListItemComp from "@/components/Home/RecentSortListItemComp.vue";

export default {
  name: "RecentSortListComp",
  components: {
    RecentSortListItemComp
  },
  data: function() {
    return {
      items: [],
      
    };
  },
  created() {
    http.get(`/article/recentSort`).then(({ data }) => {
      this.items = data;
    });
    
  }
};
</script>

<style>
</style>