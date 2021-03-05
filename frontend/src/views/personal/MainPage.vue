<template>
  <div>
    <PersonalMainComp
      :hostNum="item.num"
      :hostNickName="item.nickname"
      :hostEmail="item.email"
      :hostIntro="item.intro"
      :isMyBlog="isMyBlog"
      @update-profile="updateProfile"
    />
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import PersonalMainComp from "@/components/personal/PersonalMainComp.vue";
import http from "@/util/http-common";

export default {
  name: "PersonalMain",
  components: {
    PersonalMainComp
  },
  data: function() {
    return {
      item: {},
      isMyBlog: false,
      mediaItem:[],
    };
  },
  methods: {
    updateProfile: function() {
      this.$emit("update-profile");
    }
  },
  created() {
    http.get(`/users/get/${this.$route.params.hostNum}`).then(({ data }) => {
      this.item = data;
      if (this.getUserNum == this.item.num) {
        this.isMyBlog = true;
      }
    });
  },
  computed: {
    ...mapGetters(["getUserNum"]),
    ...mapState({
      userNum: state => `${state.user.getUserNum}`
    })
  }
};
</script>
