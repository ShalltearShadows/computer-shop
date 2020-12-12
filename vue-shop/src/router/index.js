import Vue from 'vue'
import VueRouter from 'vue-router'


const Login = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Login');
const Home = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Home');
const Welcome = () => import(/* webpackChunkName: "Login_Home_Welcome" */ '../components/Welcome');


const Users = () => import(/* webpackChunkName: "Users" */ '../views/user/Users');


const Roles = () => import(/* webpackChunkName: "Roles_Rights" */ '../views/power/Roles');
const Rights = () => import(/* webpackChunkName: "Roles_Rights" */ '../views/power/Rights');


const Cate = () => import(/* webpackChunkName: "Cate_Params_GoodsList" */ '../views/goods/Cate');
const Params = () => import(/* webpackChunkName: "Cate_Params_GoodsList" */ '../views/goods/Params');
const GoodsList = () => import(/* webpackChunkName: "Cate_Params_GoodsList" */ '../views/goods/List');



const Add = () => import(/* webpackChunkName: "Add_Order_Report" */ '../views/goods/Add');
const Order = () => import(/* webpackChunkName: "Add_Order_Report" */ '../views/order/Order');
const Report = () => import(/* webpackChunkName: "Add_Order_Report" */ '../views/report/Report');

Vue.use(VueRouter);


const router = new VueRouter({
    mode: 'history',
    routes: [
        {path: '/', redirect: '/login'},
        {path: '/login', component: Login},
        {
            path: '/home',
            component: Home,
            redirect: '/welcome',
            children: [
                {path: '/welcome', component: Welcome},

                {path: '/user/list', component: Users},
                {path: '/perm/role', component: Roles},

                {path: '/perm/list', component: Rights},

                {path: '/categories', component: Cate},
                {path: '/params', component: Params},
                {path: '/goods', component: GoodsList},
                {path: '/goods/add', component: Add},
                {path: '/orders', component: Order},
                {path: '/reports', component: Report},
            ]
        }
    ]
});

//挂载路由守卫
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        return next();
    }

    //从sessionstorage中获取到保存的token值
    const tokenstr = window.sessionStorage.getItem('token');
    //没有token，强制跳转到登录页
    if (!tokenstr) {
        return next('/login');
    }
    next();
});

export default router;
