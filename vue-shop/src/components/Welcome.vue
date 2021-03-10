<template>
  <el-card style="height: 850px;text-align: center">
    <img src="../assets/Welcome.png"></img>
    <el-divider></el-divider>
    <el-row :gutter="100">
      <el-col :span="6"><el-link type="primary" style="font-size: 20px" href="/foreground">去往前台</el-link></el-col>
      <el-col :span="6"><el-link type="primary" style="font-size: 20px" href="/indi/info">个人信息</el-link></el-col>
    </el-row>


  </el-card>
</template>

<script>
export default {
  name: "Welcome",
  data() {
    return {

    }
  },
  methods: {
    send1() {
      this.$http.get('/user/home')
    },
    send2() {
      this.$http.get('/user/perm')
    },
    send3() {
      this.$http.get('/user/perm')
    },
    send4() {
      this.$http.get('/user/perm')
    }
  }
}
</script>

<style>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>