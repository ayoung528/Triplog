import Vue from "vue";
import Vuex from "vuex";
import user from "./modules/user";
import auth from "./modules/auth";
import {VueMasonryPlugin} from 'vue-masonry';
Vue.use(Vuex);
Vue.use(VueMasonryPlugin)

const debug = process.env.NODE_ENV !== "production";

export default new Vuex.Store({
  modules: {
    user,
    auth
  },
  strict: debug
});
