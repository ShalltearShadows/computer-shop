<template>
  <el-container>
    <el-header class="header">
      <div style="margin-top: 15px; width:800px;display: inline-block;margin: auto">
        <el-input placeholder="请输入内容" v-model="input" class="input-with-select">
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </div>
      <el-link type="primary" :underline="false" href="/login">登录</el-link>
    </el-header>

    <el-main>
      <!--轮播图-->
      <div class="carousel-css">
        <el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="(item,key) in fmlData" :key="key">
            <el-image :src="item.url" fit="fill"></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>

      <div>
        <ul class="infinite-list" style="overflow:auto" v-infinite-scroll="getFML">
          <li v-for="item in fmlData" class="infinite-list-item">
            <el-image style="width: 100px; height: 100px;margin-left: 10px" :src="item.url" fit="fill"></el-image>
            <div style="margin-left: 20px">
              品牌：{{item.brand}}<br>
              CPU：{{item.cpu}}<br>
              GPU：{{item.gpu}}
            </div>
          </li>
        </ul>

      </div>
    </el-main>

    <el-footer>
      <p>Copyright© 2021 李群</p>
    </el-footer>
  </el-container>

</template>

<script>
export default {
  name: "Foreground",
  data() {
    return {
      input: '',
      fmlData: [],
      num:1
    }
  },
  created() {
    // this.getFML()
  },

  methods:{
     getFML(){
       console.log(333)
       this.$http.get('good/fml',{params:{num:this.num}}).then(res=>{
         if (res.data.code !== 200) {
           return this.$message.error('获取列表失败！')
         }
         this.fmlData.push(...res.data.data)
         this.num++
       });

    }
  }
}
</script>

<style scoped>
.el-carousel-item {
  width: 1000px;
}

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

.carousel-css {
  width: 1000px;
  margin: auto;
}

.header {
  display: flex;
  justify-content: flex-end;
}

.el-footer{
  height: 30px;
  line-height: 35px;
  bottom: 0;
  width: 100%;
  text-align: center;
  /*background-color: #898585;*/
  /*color: #fff;*/
  font-family: Arial;
  font-size: 14px;
  letter-spacing: 1px;
}

.infinite-list {
  height: 1000px;
  width: 1000px;
  padding: 0;
  margin: auto;
  list-style: none;
}
.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: start;
  height: 120px;
  background: #e8f3fe;
  margin: 10px;
  color: #7dbcfc;
}
</style>
