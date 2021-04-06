<template>
  <el-container>
    <el-header class="header">
      <div style="margin-top: 15px; width:800px;display: inline-block;margin: auto">
        <el-input class="input-with-select" placeholder="请输入内容" v-model="input" clearable @click="queryInfo" @clear="clearInput">
          <el-button slot="append" icon="el-icon-search" @click="queryInfo"></el-button>
        </el-input>
      </div>
      <div style="padding-top: 16px">
        <div v-if="avatar===null">
          <el-link :underline="false" href="/login">登录</el-link>
        </div>
        <el-row v-else>
          <!-- 购物车 -->
          <el-col :span="12">
            <el-dropdown>
              <span class="el-dropdown-link">
                <el-badge style="margin-right: 20px; height: 16px" :hidden="count===0?true:false" :value="count" class="item">
                  <el-link class="el-link-icon" icon="el-icon-shopping-cart-1" :underline="false" href="/order/list"></el-link>
                </el-badge>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="item in cart">
                  <el-row>
                    <el-col>
                      <el-image style="width: 50px; height: 50px" fit="fill" :src="item.url">
                        <div slot="error" class="image-slot">
                          <i class="el-icon-picture-outline"></i>
                        </div>
                      </el-image>
                      X <div style="display: inline-block;">{{item.count}}</div>
                    </el-col>
                    <el-col>

                    </el-col>
                  </el-row>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
          <!-- 头像区 -->
          <el-col :span="12">
            <el-dropdown>
            <span class="el-dropdown-link">
              <el-avatar fit="fill" :src="avatar"></el-avatar>
            </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><el-link href="/welcome">后台</el-link></el-dropdown-item>
                <el-dropdown-item divided><el-link href="/indi/info">个人信息</el-link></el-dropdown-item>
                <el-dropdown-item divided><el-link @click="quit">退出</el-link></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </div>

    </el-header>

    <el-main>
      <!--轮播图-->
      <div class="carousel-css">
        <el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="(item,key) in fmlData" :key="key">
            <el-image :src="item.url" @click="showDialog(item.id)" fit="fill"></el-image>
          </el-carousel-item>
        </el-carousel>
      </div>

      <div>
        <ul class="infinite-list" style="overflow:auto" v-infinite-scroll="getFML" >
          <li v-for="item in fmlData" class="infinite-list-item">
            <el-image style="width: 100px; height: 100px;margin-left: 10px" :src="item.url" @click="showDialog(item.id)" fit="fill"></el-image>
            <div style="margin-left: 20px">
              品牌：{{ item.brand }}<br>
              CPU：{{ item.cpu }}<br>
              GPU：{{ item.gpu }}
            </div>
            <el-link></el-link>
          </li>
        </ul>
      </div>

      <!--商品信息框-->
      <el-dialog title="详细信息" :visible.sync="infoDialogVisible" width="50%" @close="infoDialogClosed">
        <div style="display: flex">
          <el-image style="width: 500px; height: auto;margin-left: 10px" :src="editForm.url" fit="fill"></el-image>

          <div class="dialog-main">
            品牌：{{editForm.brand}} <br>
            CPU： {{editForm.cpu}} <br>
            GPU： {{editForm.gpu}} <br>
            分辨率： {{editForm.screen}} <br>
            内存： {{editForm.memory}} <br>
            硬盘： {{editForm.hardDisk}} <br>
            库存： {{editForm.stock}} <br>
            价格： {{'￥'+editForm.price}} <br>

            <el-row style="margin-top: 30px">
              <el-col :span="8">数量:</el-col>
              <el-col :span="16" ><el-input-number size="small" v-model="inputCount" :min="1" :max="editForm.stock"></el-input-number></el-col>
            </el-row>
          </div>

        </div>

        <!-- 按钮区 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="infoDialogVisible = false">取 消</el-button>
          <el-button type="success" @click="addCart">加入购物车</el-button>
          <el-button type="warning" @click="toPay()">购 买</el-button>
        </span>
      </el-dialog>
    </el-main>

    <div v-html="alipayWap" ref="alipayWap"></div>

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
      num: 1,
      fmlData: [],
      infoDialogVisible: false,
      editForm: {
        goodId:'',
        brand: '',
        cpu: '',
        gpu: '',
        url: '',
        screen: '',
        memory: '',
        hardDisk: '',
        price: '',
        stock: '',
      },
      count:0,
      avatar:null,
      cart:[],
      inputCount:1,
      alipayWap:''
    }
  },
  created() {
    this.avatar = window.localStorage.getItem('avatar')
    this.getCart()
  },
  methods: {
    getFML() {
      this.$http.get('good/fml', {params: {num: this.num}}).then(res => {
        if (res.data.code !== 200) {
          return this.$message.error('获取列表失败！')
        }
        this.fmlData.push(...res.data.data)
        this.num++
      });
    },
    async getAllOrder(){
      const {data: res} = await this.$http.get("/order/all")
    },
    async showDialog(id) {
      const {data: res} = await this.$http.get('good/' + id)
      this.editForm.goodId = id
      this.editForm.brand = res.data.brand
      this.editForm.cpu = res.data.cpu
      this.editForm.gpu = res.data.gpu
      this.editForm.url = res.data.url
      this.editForm.screen = res.data.screen
      this.editForm.memory = res.data.memory
      this.editForm.hardDisk = res.data.hardDisk
      this.editForm.price = res.data.price
      this.editForm.stock = res.data.stock

      this.infoDialogVisible = true
    },
    infoDialogClosed() {
      this.editForm = []
    },
    clearInput(){
      this.num = 1
      this.getFML()
    },
    async addCart() {
      var good = this.editForm
      var total = this.inputCount * good.price
      var order = {goodId:good.goodId,count:this.inputCount,time: new Date(),total:total,url: good.url}

      this.$http.post("/order/add",order)

      this.cart.push(order)
      this.count = this.cart.length
      this.infoDialogVisible = false
      this.inputCount = 1
    },
    async queryInfo(){
      const {data:res} = await this.$http.get('good/query',{params:{info:this.input}})
      this.fmlData = res.data
    },
    quit() {
      //删除token
      window.localStorage.removeItem('token');
      window.localStorage.removeItem('avatar');
      this.$store.commit("REMOVE_INFO")
      this.avatar = null
    },
    addCount(stock){
      if (stock>this.inputCount){
        this.inputCount++
      }else {
        this.$message.error("库存不足")
      }
    },
    reduceCount(){
      if (this.inputCount>1){
        this.inputCount--
      }
    },
    async toPay(){
      var good = this.editForm
      var total = this.inputCount * good.price
      var order = {goodId:good.goodId,count:this.inputCount,time: new Date(),total:total}

      const {data:res} = await this.$http.post("/order/add",order)

      const pay = {id:res.data,total:total,count:this.inputCount,goodId:good.goodId}
      const {data: html} = await this.$http.post("/order/pay",pay)

      this.alipayWap = html.data;
      this.$nextTick(() => {
        console.log(this.$refs.alipayWap.children[0].submit())
      })
    },
    async getCart(){
      const {data:res} = await this.$http.get("/order/getCart");
      this.cart = res.data;
      console.log(res.data.length)
      this.count = res.data.length
    }
  },

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

.el-footer {
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
  color: #25272a;
}

.dialog-main{
  margin-left: 70px;
  line-height: 35px;
  color: cadetblue;
}
.el-link-icon{
  font-size: 24px;
}
</style>