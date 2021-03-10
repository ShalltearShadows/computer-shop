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
          <el-input placeholder="请输入内容">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 订单列表 -->
      <el-table :data="orderList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="订单编号" prop="id"></el-table-column>
        <el-table-column label="用户账号" prop="userId"></el-table-column>
        <el-table-column label="商品账号" prop="goodId"></el-table-column>
        <el-table-column label="商品数量" prop="count"></el-table-column>
        <el-table-column label="下单时间" prop="time"></el-table-column>
        <el-table-column label="是否付款">
          <template slot-scope="scope">
            <el-tag type="danger" size="mini" v-if="scope.row.pay">未付款</el-tag>
            <el-tag type="success" size="mini" v-else>已付款</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="总价" prop="total"></el-table-column>

        <el-table-column label="操作">
          <template>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="infoDialogVisible = true"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum" :page-sizes="[5, 10, 15, 20]"
                     :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper"
                     :total="pageTotal"></el-pagination>
    </el-card>

    <!-- 编辑对话框 -->
    <el-dialog title="修改地址" :visible.sync="infoDialogVisible" width="50%" @close="infoDialogClosed">
      <el-form :model="infoForm" :rules="infoFormRules" ref="infoFormRef" label-width="100px">

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click=" ">取 消</el-button>
        <el-button type="primary" @click="infoDialogVisible = false">确 定</el-button>
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
        pagesize: 10
      },
      pageTotal: 0,
      // 订单列表
      orderList: [],
      infoDialogVisible: false,
      infoForm:{

      },
      infoFormRules:[]
    }
  },
  created() {
    this.getOrderList()
  },
  methods: {
    async getOrderList() {
      // const {data: res} = await this.$http.get('/order/all', {
      //   params: this.queryInfo
      // });
      const {data: res} = await this.$http.get('/order/all');
      if (res.code !== 200) {
        return this.$message.error('获取订单列表失败！')
      }

      this.orderList = res.data
      this.pageTotal = this.orderList.length
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
  }
}
</script>

<style lang="less" scoped>
.el-cascader {
  width: 100%;
}
</style>
