import Vue from "vue";
import App from "./App.vue";
import router from "./router/index.js";
import store from "./store/index.js";
import Vuex from "vuex";
import vuetify from "./plugins/vuetify";
import vueMoment from 'vue-moment';
import VueGmaps from 'vue-gmaps';
import * as VueGoogleMaps from 'vue2-google-maps'

Vue.use(vueMoment)
Vue.use(Vuex);
Vue.use(VueGmaps, {
    key: 'AIzaSyC3JEsAuKanTHq2XVnX2uWx9y-0bFEp9iY',
    libraries: ['places'],
    version: '3'
});
Vue.use(VueGoogleMaps, {
    load: {
        key: 'AIzaSyC3JEsAuKanTHq2XVnX2uWx9y-0bFEp9iY',
        libraries: 'places',
    }
})

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount("#app");