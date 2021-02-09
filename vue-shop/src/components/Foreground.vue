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
        <el-table :data="fmlData" stripe style="width: 1000px;margin: auto">
          <el-table-column width="120">
            <template slot-scope="scope">
              <el-image style="width: 100px; height: 100px" :src="scope.row.url" fit="fill"></el-image>
            </template>
          </el-table-column>
          <el-table-column width="180">
            <template slot-scope="scope">
              品牌：{{scope.row.brand}}<br>
              CPU：{{scope.row.cpu}}<br>
              GPU：{{scope.row.gpu}}
            </template>
          </el-table-column>
          <el-table-column prop="address"></el-table-column>
        </el-table>
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
      fmlData:[
        {url:'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',brand:'小鸟',cpu:'i7-10870',gpu:'RTX2070'}
      ]
    }
  },
  created() {
    this.getFFL()
  },
  methods:{
    async getFFL(){
      const {data: res} = await this.$http.get('good/fml');
      if (res.code !== 200) {
        return this.$message.error('获取列表失败！')
      }

      this.fmlData = res.data;
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
  background-color: #898585;
  color: #fff;
  font-family: Arial;
  font-size: 14px;
  letter-spacing: 1px;
}
</style>
