<template >
  <v-container>
    <v-subheader v-if="item.header" :key="item.header" v-text="item.header"></v-subheader>

    <v-divider v-else-if="item.divider" :key="index" :inset="item.inset"></v-divider>
  </v-container>
</template>

<script>
export default {
  name: "CommentAsistUnitComp",
  components: {},
  data() {
    return {};
  },
  props: {
    item: Object,
    index: Number
  },
  methods: {},
  computed: {},
  created() {}
};
</script>

<style>
</style>