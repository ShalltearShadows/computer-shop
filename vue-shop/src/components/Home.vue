<template>
  <el-container class="home-container">
    <!-- 页面头部区 -->
    <el-header>
      <div>
        <img src="../assets/star.png">
        <span>后台管理系统</span>
      </div>

      <!-- 头像区 -->
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="50" :src="avatar"></el-avatar>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item><el-link href="/foreground">前台</el-link></el-dropdown-item>
          <el-dropdown-item divided><el-link href="/indi/info">个人信息</el-link></el-dropdown-item>
          <el-dropdown-item divided><el-link @click="quit">退出</el-link></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </el-header>
    <!-- 页面主体区 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px':'200px'">

        <div class="toggle-button" @click="toggleCollapse">|||</div>

        <!-- 菜单区 active-text-color为激活时按钮颜色,unique-opened是否只展开一项，collapse-transition折叠过程动画，router路由跳转,default-active:选择哪个按钮高亮 -->
        <el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" :collapse="isCollapse"
                 :collapse-transition="false" router :default-active="this.$route.path">
          <!-- 一级菜单 -->
          <!-- item.id+'' 之所以要加‘’空字符串是因为index只接受字符串不接受变量 -->
          <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
            <!-- 一级菜单文本区 -->
            <template slot="title">
              <!-- 一级菜单的图标区 -->
              <i :class="icons[item.id]"></i>
              <!-- 文字区 -->
              <span>{{ item.name }}</span>
            </template>
            <!-- 二级菜单 点击按钮，跳转的url就是index,开启功能时需要在el-menu设置router属性 -->
            <el-menu-item :index="'/'+subItem.path" v-for="subItem in item.children" :key="subItem.id">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{ subItem.name }}</span>
              </template>
            </el-menu-item>
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
import Bus from "../router/bus.js"
export default {
  name: "Home",
  data() {
    return {
      //左侧菜单数据
      menuList: [],
      icons: {
        1: 'iconfont icon-users',
        2: 'iconfont icon-tijikongjian',
        3: 'iconfont icon-shangpin',
        4: 'iconfont icon-danju',
        5: 'iconfont icon-baobiao'
      },
      isCollapse: false,
      avatar: null
    }
  },
  created() {
    this.getListMenu()
    this.getAvatar()
  },
  methods: {
    quit() {
      //删除token
      window.sessionStorage.removeItem('token');
      window.sessionStorage.removeItem('avatar');
      this.$store.commit("REMOVE_INFO")
      //跳转到登录页
      this.$router.push('/foreground')
    },
    async getListMenu() {
      const {data: res} = await this.$http.get('user/menu');

      this.menuList = res.data;
    },
    //点击按钮
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    getAvatar() {
      this.$http.get('/user/avatar').then(res => {
        var avatar = "data:image/png;base64," + res.data.data
        window.sessionStorage.setItem("avatar", avatar)
        this.avatar = avatar
      })
    },

  },
  mounted() {
    Bus.$on("getAvatar",()=>{
      this.getAvatar()
    })
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
  padding-right: 47px;
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

  .el-menu {
    border-right: none;
  }

  .toggle-button {
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

.iconfont {
  margin-right: 10px;
}
</style>
