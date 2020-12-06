<template>
    <el-container class="home-container">
        <!-- 页面头部区 -->
        <el-header>
            <div>
                <img src="../assets/star.png">
                <span>电商后台管理系统</span>
            </div>
            <el-button type="info" @click="quit">退出</el-button>
        </el-header>
        <!-- 页面主体区 -->
        <el-container>
            <!-- 侧边栏 -->
            <el-aside :width="isCollapse ? '64px':'200px'">

                <div class="toggle-button" @click="toggleCollapse">|||</div>

                <!-- 菜单区 active-text-color为激活时按钮颜色,unique-opened是否只展开一项，collapse-transition折叠过程动画，router路由跳转,default-active:选择哪个按钮高亮 -->
                <el-menu
                        background-color="#333744"
                        text-color="#fff"
                        active-text-color="#409EFF"
                        :collapse="isCollapse"
                        :collapse-transition="false"
                        router
                        :default-active="this.$route.path"
                        unique-opened>
                    <!-- 一级菜单 -->
                    <!-- item.id+'' 之所以要加‘’空字符串是因为index只接受字符串不接受变量 -->
                    <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
                        <!-- 一级菜单文本区 -->
                        <template slot="title">
                            <!-- 一级菜单的图标区 -->
                            <i :class="icons[item.id]"></i>
                            <!-- 文字区 -->
                            <span>{{item.authName}}</span>
                        </template>

                        <!--                        <el-menu-item-group>-->
                        <!--                            <template slot="title">分组一</template>-->
                        <!--                            <el-menu-item index="1-1">选项1</el-menu-item>-->
                        <!--                            <el-menu-item index="1-2">选项2</el-menu-item>-->
                        <!--                        </el-menu-item-group>-->

                        <!-- 二级菜单 点击按钮，跳转的url就是index,开启功能时需要在el-menu设置router属性 -->
                        <el-menu-item :index="'/'+subItem.path" v-for="subItem in item.children" :key="subItem.id">
                            <template slot="title">
                                <i class="el-icon-menu"></i>
                                <span>{{subItem.authName}}</span>
                            </template>
                        </el-menu-item>
                        <!-- 三级菜单 -->
                        <!--                        <el-submenu index="1-4" v-for="">-->
                        <!--                            <template slot="title">选项4</template>-->
                        <!--                            <el-menu-item index="1-4-1">选项1</el-menu-item>-->
                        <!--                        </el-submenu>-->
                    </el-submenu>
                </el-menu>
            </el-aside>
            <!-- 内容区 -->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    export default {
        name: "Home",
        data() {
            return {
                //左侧菜单数据
                menuList: [],
                icons: {
                    125: 'iconfont icon-users',
                    103: 'iconfont icon-tijikongjian',
                    101: 'iconfont icon-shangpin',
                    102: 'iconfont icon-danju',
                    145: 'iconfont icon-baobiao'
                },
                isCollapse: false
            }
        },
        created() {
            // this.getListMenu();
        },
        methods: {
            quit() {
                //删除token
                window.sessionStorage.removeItem('token');
                this.$store.commit("REMOVE_INFO")
                //跳转到登录页
                this.$router.push('/login')
            },
            async getListMenu() {

                const {data: res} = await this.$http.get('user/menu');

                this.menuList = res.data;
            },
            //点击按钮
            toggleCollapse(){
                this.isCollapse = !this.isCollapse;
            }
        }
    }
</script>

<style lang="less" scoped>
    .home-container {
        height: 100%;
    }

    .el-header {
        background-color: #373D41;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color: #fff;
        font-size: 20px;

        > div {
            display: flex;
            align-items: center;

            span {
                margin-left: 15px;
            }
        }

        img {
            width: 60px;
            height: 60px;
        }
    }

    .el-aside {
        background-color: #333744;

        .el-menu{
            border-right: none;
        }
        .toggle-button{
            background-color: #4A5064;
            font-size: 10px;
            line-height: 24px;
            color: #fff;
            text-align: center;
            letter-spacing: 0.2em;
            cursor: pointer;
        }
    }

    .el-main {
        background-color: #EAEDF1;
    }

    .iconfont{
        margin-right: 10px;
    }
</style>
