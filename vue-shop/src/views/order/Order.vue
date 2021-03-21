<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <el-row>
        <el-col :span="6">
          <el-input placeholder="请输入内容" v-model="queryInfo.query">
            <el-button slot="append" icon="el-icon-search" @click="getOrderList"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 订单列表 -->
      <el-table :data="orderList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="订单编号" prop="id"></el-table-column>
        <el-table-column label="用户账号" prop="userId"></el-table-column>
        <el-table-column label="商品编号" prop="goodId"></el-table-column>
        <el-table-column label="商品数量" prop="count"></el-table-column>
        <el-table-column label="下单时间">
          <template slot-scope="scope">
            {{scope.row.time | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column label="是否付款">
          <template slot-scope="scope">
            <el-tag type="danger" size="mini" v-if="scope.row.pay===0">未付款</el-tag>
            <el-tag type="success" size="mini" v-else>已付款</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="总价">
          <template slot-scope="scope">
            {{scope.row.total|moneyFormat}}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="toPay(scope.row.goodId)">付款</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum" :page-sizes="[5, 10, 15, 20]" :page-size="queryInfo.pagesize"
                     layout="total, sizes, prev, pager, next, jumper" :total="pageTotal" background></el-pagination>
    </el-card>

    <!--商品信息框-->
    <el-dialog title="详细信息" :visible.sync="infoDialogVisible" width="50%" @close="infoDialogClosed">
      <div style="display: flex">
        <el-image style="width: 500px; height: auto;margin-left: 10px" :src="infoForm.url" fit="fill"></el-image>

        <div class="dialog-main">
          品牌：{{infoForm.brand}} <br>
          CPU： {{infoForm.cpu}} <br>
          GPU： {{infoForm.gpu}} <br>
          分辨率： {{infoForm.screen}} <br>
          内存： {{infoForm.memory}} <br>
          硬盘： {{infoForm.hardDisk}} <br>
          库存： {{infoForm.stock}} <br>
          价格： {{infoForm.price|moneyFormat}} <br>
        </div>

      </div>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
          <el-button @click="infoDialogVisible = false">取 消</el-button>
          <el-button type="success" @click="pay">支 付</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>

export default {
  data() {
    return {
      // 订单列表查询参数
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5
      },
      pageTotal: 0,
      // 订单列表
      orderList: [],
      infoDialogVisible: false,
      infoForm:{},
    }
  },
  created() {
    this.getOrderList()
  },
  methods: {
    async getOrderList() {
      const {data: res} = await this.$http.get('/order/all',{params: this.queryInfo});
      if (res.code !== 200) {
        return this.$message.error('获取订单列表失败！')
      }

      this.orderList = res.data.orders
      this.pageTotal = res.data.total
    },
    // 分页
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getOrderList()
    },
    handleCurrentChange(newSize) {
      this.queryInfo.pagenum = newSize;
      this.getOrderList()
    },
    infoDialogClosed(){
      this.infoForm = {}
    },

    // 通过Id删除
    async removeById(id) {
      const confirmResult = await this.$confirm(
          '此操作将永久删除, 是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).catch(err => err);
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除！')
      }
      const {data: res} = await this.$http.post('order/delete'+id);
      if (res.code !== 200) {
        return this.$message.error('删除失败！')
      }
      this.$message.success('删除成功！');

    },
    async toPay(id){
      const {data: res} = await this.$http.get('good/' + id)
      this.infoForm.goodId = id
      this.infoForm.brand = res.data.brand
      this.infoForm.cpu = res.data.cpu
      this.infoForm.gpu = res.data.gpu
      // this.editForm.url = res.data.url
      this.infoForm.url = "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
      this.infoForm.screen = res.data.screen
      this.infoForm.memory = res.data.memory
      this.infoForm.hardDisk = res.data.hardDisk
      this.infoForm.price = res.data.price
      this.infoForm.stock = res.data.stock

      this.infoDialogVisible = true
    },
    pay() {
      //TODO 支付
    }
  }
}
</script>

<style lang="less" scoped>
.el-cascader {
  width: 100%;
}
</style>
