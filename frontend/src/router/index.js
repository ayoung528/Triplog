import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import APIError from "../components/error/APIError.vue";

import error from "../components/error/error.vue";
import EmailAuth from "../components/error/EmailAuth.vue";
import EmailAuthSuccess from "../components/error/EmailAuthSuccess.vue";
import ArticleWrite from "../views/article/ArticleWrite.vue"
import ArticleSearch from "../views/article/ArticleSearch.vue";
import ArticleInfo from "../views/article/ArticleInfo.vue"

import ArticleList from "../views/article/ArticleList.vue"
// import ArticleModify from "../views/article/ArticleModify.vue"
import Like from "../views/likey/Like.vue"


import AuthSearch from "../components/account/AuthSearch.vue";
import Login from "../components/account/Login.vue";
import SocialLogin from "../views/SocialLogin.vue";
import temp from "../views/temporaryArticle/temp.vue"
import UserSetting from "../views/personal/UserSetting.vue"
// import { component } from "vue/types/umd";
// import PersonalMain from "@/components/personal/PersonalMainComp.vue";
Vue.use(VueRouter);

const routes = [{
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/login/",
        name: "Login",
        component: Login
    },
    {
        path: "/login/social/:site",
        name: "SocialLogin",
        component: SocialLogin
    },
    {
        path: "/emailauth",
        name: "EmailAuth",
        component: EmailAuth
    },
    {
        path: "/emailauth/success",
        name: "EmailAuthSuccess",
        component: EmailAuthSuccess
    },
    {
        path: "/like",
        name: "Like",
        component: Like
    },
    {
        path: "/about",
        name: "About",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
            import ( /* webpackChunkName: "about" */ "../views/About.vue")
    },
    {
        path: "/article/ArticleSearch",
        name: "ArticleSearch",
        component: ArticleSearch
    },
    {
        path: "/apierror/:errorCode",
        name: "APIError",
        component: APIError
    },
    {
        path: "/article/write",
        name: "articleWrite",
        component: ArticleWrite
    },
    {
        path: "/article/detail/:articleNum",
        name: "articleDetail",
        component: ArticleInfo
    },
    {
        path: "/article/list/:hostNum",
        name: "articleList",
        component: ArticleList
    },
    {
        path: "/article/modify/:articleNum",
        name: "articleModify",
        component: ArticleWrite
    },
    {
        path: "/tempArticle",
        name: "tempArticle",
        component: temp
    },
    {
        path: "/userSetting/:hostNum",
        name: "UserSetting",
        component: UserSetting
    },
    {
        path: "/**",
        name: "error",
        component: error
    },
  {
    path: "/auth/search/",
    name: "AuthSearch",
    component: AuthSearch
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;