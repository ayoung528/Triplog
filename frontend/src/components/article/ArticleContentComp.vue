<template>
  <div>
    <p v-html="content">
    </p>
  </div>
</template>

<script>
export default {
  name: "ArticleContentComp",
  props: {
    content: { type: String },
  },
};
</script>