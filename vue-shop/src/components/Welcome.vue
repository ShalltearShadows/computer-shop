<template>
    <div>
        <h3>Welcome</h3>
      <el-button type="primary" @click="send1">测试1</el-button>
      <el-button type="primary" @click="send2">测试2</el-button>
      <el-button type="primary" @click="send3">测试3</el-button>
      <el-button type="primary" @click="send4">测试4</el-button>

    </div>
</template>

<script>
    export default {
        name: "Welcome",
      methods:{
        send1(){
          this.$http.get('/user/home')
        },
        send2(){
          this.$http.get('/user/perm')
        },
        send3(){
          this.$http.get('/user/perm')
        },
        send4(){
          this.$http.get('/user/perm')
        }
      }
    }
</script>
