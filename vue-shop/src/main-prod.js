import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './assets/css/global.css'
import Axios from "axios";

Vue.config.productionTip = false;

//导入请求进度条
import NProgress from 'nprogress';


// Axios错误的引用方式
// Vue.use(Axios);
// Axios正确的引用方式
Vue.prototype.$http = Axios;

Axios.defaults.baseURL="http://localhost:8080/";
Axios.interceptors.request.use (config =>{
    //开启进度条
    NProgress.start();


    //为请求头对象，添加Token验证的Authorization字段
    config.headers.Authorization = window.sessionStorage.getItem ('token');
    return config
});
Axios.interceptors.response.use (config => {
    //关闭进度条
    NProgress.done();

    return config;
});

// 导入字体图标
import './assets/fonts/iconfont.css'


// 导入树形网格
import TreeTable from 'vue-table-with-tree-grid'
Vue.component('tree-table',TreeTable);


// 导入富文本
import VueQuillEditor from 'vue-quill-editor'
Vue.use(VueQuillEditor);



Vue.filter('dateFormat',function (originVal) {
    const dt = new Date(originVal);

    const y = dt.getFullYear();
    const m = (dt.getMonth()+1+'').padStart(2,'0');
    const d = (dt.getDate()+1+'').padStart(2,'0');

    const hh = (dt.getHours() +'').padStart(2,'0');
    const mm = (dt.getMinutes() + '').padStart(2,'0');
    const ss = (dt.getSeconds() + '').padStart(2,'0');

    return `${y}-${m}-${d} ${hh}:${mm}:${ss}`
});


new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
