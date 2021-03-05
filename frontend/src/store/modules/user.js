import { USER_REQUEST, USER_ERROR, USER_SUCCESS, USER_UPDATE } from "../actions/user";
import apiCall2 from "../../util/api2";
import Vue from "vue";
import { AUTH_LOGOUT } from "../actions/auth";

const state = {
  status: "",
  profilename: localStorage.getItem("profilename") || "",
  realname: localStorage.getItem("realname") || "",
  email: localStorage.getItem("email") || "",
  userNum: localStorage.getItem("userNum") || "",
  userImg: localStorage.getItem("userImg") || "",
  valid: localStorage.getItem("valid") || "",
};

const getters = {
  getProfile: state => state.profilename,
  isProfileLoaded: state => !!state.profilename,
  getRealName: state => state.realname,
  getEmail: state => state.email,
  getUserNum: state => state.userNum,
  getUserImg: state => state.userImg,
  getValid: state => state.valid,
};

const actions = {
  [USER_REQUEST]: ({ commit, dispatch }, args) => {
    apiCall2(args.email)
      .then(resp => {
        commit(USER_SUCCESS, resp);
      })
      .catch(() => {
        commit(USER_ERROR);
        dispatch(AUTH_LOGOUT);
      });
  },
  [USER_UPDATE]: ({ commit }, resp) => {
    apiCall2(resp)
      .then(resp => {
        commit(USER_SUCCESS, resp);
      })
      .catch(() => {
        commit(USER_ERROR);
      });
  }
};

const mutations = {
  [USER_REQUEST]: state => {
    state.status = "loading";
  },
  [USER_SUCCESS]: (state, resp) => {
    state.status = "success";
    localStorage.setItem("profilename", resp.nickname);
    localStorage.setItem("realname", resp.name);
    localStorage.setItem("email", resp.email);
    localStorage.setItem("userNum", resp.num);
    localStorage.setItem("userImg", resp.imagesrc);
    localStorage.setItem("valid", resp.valid);
    Vue.set(state, "profilename", resp.nickname);
    Vue.set(state, "realname", resp.name);
    Vue.set(state, "email", resp.email);
    Vue.set(state, "userNum", resp.num);
    Vue.set(state, "userImg", resp.imagesrc);
    Vue.set(state, "valid", resp.valid);

  },
  [USER_ERROR]: state => {
    state.status = "error";
  },
  [AUTH_LOGOUT]: state => {
    state.profilename = "";
    state.realname = "";
    state.email = "";
    state.userNum = "";
    state.userImg = "";

    state.valid = "";
  },
  modifyProfileName: function (state, after) {
    state.profilename = after;
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};
