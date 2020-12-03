import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './assets/css/global.css'
import Axios from "axios";
import store from './store'

Vue.config.productionTip = false;


//导入请求进度条
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';


// Axios错误的引用方式
// Vue.use(Axios);
// Axios正确的引用方式
Vue.prototype.$http = Axios;



Axios.defaults.baseURL="http://localhost:8080/";
// 前置拦截===发送请求前
Axios.interceptors.request.use (config =>{
    //开启进度条
    NProgress.start();


    //为请求头对象，添加Token验证的Authorization字段
    config.headers.Authorization = window.sessionStorage.getItem ('token');
    return config
});
// 后置拦截=== 获取相应后
Axios.interceptors.response.use (response => {
    //关闭进度条
    NProgress.done();

    console.log("====================================================")
    console.log(response.data)
    console.log("====================================================")

    if (response.data.code===200){
        return response
    }

    this.$message.error(response.data.msg)

    return Promise.reject(response.data.msg)

},error => {
    // 处理的是全局异常类返回的错误
    console.log("error"+error)

    if (error.response.status === 401){
        store.commit("REMOVE_INFO")
        router.push("/login")
    }

    this.$message.error(error.response.data)

    return Promise.reject(error)
});

// 导入字体图标
import './assets/fonts/iconfont.css'


// 导入Element
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
Vue.prototype.$message = ElementUI.Message;// 消息提示
Vue.prototype.$confirm = ElementUI.MessageBox;// 弹窗提示

// 导入树形网格
import TreeTable from 'vue-table-with-tree-grid'
Vue.component('tree-table',TreeTable);



// 导入富文本
// import VueQuillEditor from 'vue-quill-editor'
// import 'quill/dist/quill.core.css' // import styles
// import 'quill/dist/quill.snow.css' // for snow theme
// import 'quill/dist/quill.bubble.css' // for bubble theme
// Vue.use(VueQuillEditor, /* { default global options } */);



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