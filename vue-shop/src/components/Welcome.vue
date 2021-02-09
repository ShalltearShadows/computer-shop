<template>
    <div>
        <h3>Welcome</h3>

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
