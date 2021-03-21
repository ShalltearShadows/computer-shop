<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 搜索域和添加按钮 -->
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getGoodsList">
            <el-button slot="append" icon="el-icon-search" @click="getGoodsList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="goAddPage">添加商品</el-button>
        </el-col>
      </el-row>


      <!-- 表格数据 -->
      <el-table :data="goodsList" border stripe>
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column label="编号" prop="id" width="170px"></el-table-column>
        <el-table-column label="品牌" prop="brand" width="370px"></el-table-column>
        <el-table-column label="价格" prop="price" width="120px"></el-table-column>
        <el-table-column label="CPU" prop="cpu" width="120px"></el-table-column>
        <el-table-column label="GPU" prop="gpu" width="120px"></el-table-column>
        <el-table-column label="内存" prop="memory" width="120px"></el-table-column>
        <el-table-column label="硬盘" prop="hardDisk" width="120px"></el-table-column>
        <el-table-column label="分辨率" prop="screen" width="120px"></el-table-column>
        <el-table-column label="库存" prop="stock" width="120px"></el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="updateById(scope.row.id)">修改</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum" :page-sizes="[5, 10, 15, 20]" :page-size="queryInfo.pagesize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total" background></el-pagination>

      <!--修改商品对话框-->
      <el-dialog title="修改用户" :visible.sync="editGoodVisible" width="40%" @close="editGoodDialogClosed">
        <!--输入区-->
        <el-form :model="editGoodForm" :rules="editFormRules" ref="editGoodFormRef" label-width="70px"
                 class="demo-ruleForm">
          <el-form-item label="编号">
            <el-input v-model="editGoodForm.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="品牌" prop="brand">
            <el-input v-model="editGoodForm.brand" clearable></el-input>
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input v-model="editGoodForm.price" clearable></el-input>
          </el-form-item>
          <el-form-item label="CPU">
            <el-input v-model="editGoodForm.cpu" clearable></el-input>
          </el-form-item>
          <el-form-item label="GPU">
            <el-input v-model="editGoodForm.gpu" clearable></el-input>
          </el-form-item>
          <el-form-item label="内存">
            <el-input v-model="editGoodForm.memory" clearable></el-input>
          </el-form-item>
          <el-form-item label="硬盘">
            <el-input v-model="editGoodForm.hardDisk" clearable></el-input>
          </el-form-item>
          <el-form-item label="分辨率">
            <el-input v-model="editGoodForm.screen" clearable></el-input>
          </el-form-item>
          <el-form-item label="库存" prop="stock">
            <el-input v-model="editGoodForm.stock" clearable></el-input>
          </el-form-item>
        </el-form>

        <!-- 按钮区 -->
        <span slot="footer" class="dialog-footer">
                <el-button @click="editGoodVisible = false">取 消</el-button>
                <el-button type="primary" @click="editGood">确 定</el-button>
            </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      // 商品列表
      goodsList: [],
      // 商品总数
      total: 0,
      editGoodVisible: false,
      editGoodForm: {
        id: '',
        brand: '',
        price: '',
        cpu: '',
        gpu: '',
        memory: '',
        hardDisk: '',
        screen: '',
        stock: ''

      },
      editFormRules: {
        brand: [
          {required: true, message: "请输入品牌", trigger: "blur"}
        ],
        price: [
          {required: true, message: "请输入价格", trigger: "blur"}
        ],
        stock: [
          {required: true, message: "请输入库存", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.getGoodsList()
  },
  methods: {
    // 根据分页获取对应的商品列表
    async getGoodsList() {
      const {data: res} = await this.$http.get('good/list', {
        params: this.queryInfo
      });
      if (res.code !== 200) {
        return this.$message.error('获取商品列表失败！')
      }

      this.goodsList = res.data.goods;
      this.total = res.data.total
    },
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getGoodsList()
    },
    handleCurrentChange(newSize) {
      this.queryInfo.pagenum = newSize;
      this.getGoodsList()
    },
    // 通过Id删除商品
    async removeById(gid) {
      const confirmResult = await this.$confirm(
          '此操作将永久删除该商品, 是否继续?',
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
      const {data: res} = await this.$http.post('good/delete', {id: gid});
      if (res.code !== 200) {
        return this.$message.error('删除商品失败！')
      }
      this.$message.success('删除商品成功！');
      this.getGoodsList()
    },
    updateById(id) {
      this.editGoodForm.id = id;
      this.editGoodVisible = true;
    },
    goAddPage() {
      this.$router.push('/goods/add')
    },
    editGoodDialogClosed() {
      this.editGoodForm.brand = ''
      this.editGoodForm.price = ''
      this.editGoodForm.cpu = ''
      this.editGoodForm.gpu = ''
      this.editGoodForm.memory = ''
      this.editGoodForm.hardDisk = ''
      this.editGoodForm.screen = ''
      this.editGoodForm.stock = ''
    },
    editGood() {
      this.$refs.editGoodFormRef.validate(async valid => {
        if (!valid) {
          return;
        }
        const {data: res} = await this.$http.post('good/update', this.editGoodForm)

        this.$message.success('修改成功！');

        this.editGoodVisible = false;

        this.getGoodsList()

      })
    }
  }
}
</script>

<style lang="less" scoped>
</style>
