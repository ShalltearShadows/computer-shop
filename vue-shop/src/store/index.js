import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: '',
        userInfo: JSON.parse(sessionStorage.getItem("userInfo")),
        avatar: ''
    },
    mutations: {
        //set
        SET_TOKEN: (state, token) => {
            state.token = token
            sessionStorage.setItem("token", token)
        },
        SET_USERINFO: (state, userInfo) => {
            state.userInfo = userInfo
            sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
        },
        SET_AVATAR: (state, avatar) => {
            state.avatar = avatar
            console.log("store"+state.avatar)
        },
        REMOVE_INFO: (state) => {
            state.token = ''
            sessionStorage.setItem("token", '')
            state.userInfo = ''
            sessionStorage.setItem("userInfo", JSON.stringify(''))
        }
    },
    getters: {
        //get
        getUser: state => {
            return state.userInfo
        },
        getAvatar: state => {
            return state.avatar
        }
    },
    actions: {},
    modules: {}
})
