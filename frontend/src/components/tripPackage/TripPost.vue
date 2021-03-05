<template>
  <v-list-item>
    <v-list-item-avatar>
      <v-img v-if="thumbnail" :src="`../../res/articleImage/${thumbnail}`" />
      <v-img v-else :src="`../../res/articleImage/noimage.jpg`" />
    </v-list-item-avatar>
    <v-list-item-content>
      <v-list-item-title>{{title}}</v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
export default {
  name: "TripPost",
  props: {
    num: { type: Number },
    user_num: { type: Number },
    title: { type: String },
    thumbnail: { type: String },
    created_at: { type: String }
  }
};
</script>

<style>
</style>