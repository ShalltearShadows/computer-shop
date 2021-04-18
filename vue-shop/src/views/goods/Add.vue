<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>添加商品</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图 -->
    <el-card>
      <!-- 提示 -->
      <el-alert title="添加商品信息" type="info" center show-icon :closable="false"></el-alert>
      <!-- 步骤条 -->
      <el-steps :space="200" :active="+activeIndex" finish-status="success" align-center>
        <el-step title="基本信息"></el-step>
        <el-step title="商品参数"></el-step>
        <el-step title="商品图片"></el-step>
        <el-step title="完成"></el-step>
      </el-steps>

      <!-- Tab栏 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px" label-position="top">
        <el-tabs v-model="activeIndex" :tab-position="'left'" :before-leave="beforeTabLeave">

          <el-tab-pane label="基本信息" name="0">
            <el-form-item label="电脑品牌" prop="brand">
              <el-input v-model="addForm.brand"></el-input>
            </el-form-item>

            <el-form-item label="价格" prop="price">
              <el-input v-model="addForm.price"></el-input>
            </el-form-item>

            <el-form-item label="库存" prop="stock">
              <el-input v-model="addForm.stock"></el-input>
            </el-form-item>

          </el-tab-pane>

          <el-tab-pane label="商品参数" name="1">
            <el-form-item label="CPU" prop="cpu">
              <el-input v-model="addForm.cpu"></el-input>
            </el-form-item>
            <el-form-item label="GPU" prop="gpu">
              <el-input v-model="addForm.gpu"></el-input>
            </el-form-item>
            <el-form-item label="内存" prop="memory">
              <el-input v-model="addForm.memory"></el-input>
            </el-form-item>
            <el-form-item label="硬盘" prop="hardDisk">
              <el-input v-model="addForm.hardDisk"></el-input>
            </el-form-item>
            <el-form-item label="分辨率" prop="screen">
              <el-input v-model="addForm.screen"></el-input>
            </el-form-item>
          </el-tab-pane>

          <el-tab-pane label="电脑图片" name="2">
            <!-- action: 图片上传的API接口地址 -->
            <!--                        <el-upload :action="uploadURL" :on-preview="handlePreview" :on-remove="handleRemove" :headers="headerObj"-->
            <!--                                   list-type="picture" :on-success="handleSuccess">-->
            <!--                            <el-button size="small" type="primary">点击上传</el-button>-->
            <!--                        </el-upload>-->

            <el-row>
              <el-upload class="avatar-uploader el-upload" :action="uploadURL" :show-file-list="false"
                         :on-success="handleSuccess" :headers="headerObj">
                <el-image v-if="imageUrl" :src="imageUrl" class="el-upload"></el-image>
                <i v-else class="el-icon-plus avatar-uploader-icon el-upload"></i>
              </el-upload>
            </el-row>
            <el-row>
              <el-button type="primary" class="btnAdd" @click="addGoods">添加商品</el-button>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-form>
    </el-card>
    <el-dialog title="图片预览" :visible.sync="previewDialogVisible" width="50%">
      <img :src="picPreviewPath" alt="" class="previewImg">
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 步骤条默认激活 与左侧Tab联动
      activeIndex: '0',
      // 添加商品的表单对象
      addForm: {
        brand: '',
        price: '',
        stock: '',
        cpu: '',
        gpu: '',
        memory: '',
        hardDisk: '',
        screen: '',
        url: ''
      },
      addFormRules: {
        brand: [
          {required: true, message: '请输入电脑品牌', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'}
        ],
        stock: [
          {required: true, message: '请输入库存', trigger: 'blur'}
        ]
      },

      // 图片上传地址
      uploadURL: 'http://localhost:8080/good/upload',
      // 图片上传组件的请求对象
      headerObj: {
        Authorization: window.sessionStorage.getItem('token')
      },
      //上传成功后的地址
      imageUrl: null,
      picPreviewPath: '',
      // 图片预览对话框
      previewDialogVisible: false
    }
  },

  methods: {
    beforeTabLeave(activeName, odlActiveName) {
      // 第一页未填完，禁止跳到其他页
      if (odlActiveName === '0' && (this.addForm.brand === '' || this.addForm.price === '' || this.addForm.stock === '')) {
        this.$message.error('请先填写基本信息');
        return false
      }
    },
    // 处理图片预览
    handlePreview(file) {
      this.picPreviewPath = file.response.data.url;
      this.previewDialogVisible = true
    },
    // 处理移除图片的操作
    handleRemove(file) {
      // 1.获取将要删除图片的临时路径
      const filePath = file.response.data.tmp_path;
      // 2.从pics数组中，找到图片对应的索引值
      const i = this.addForm.pics.findIndex(x => x.pic === filePath);
      // 3.调用splice方法，移除图片信息
      this.addForm.splice(i, 1)
    },
    // 监听图片上传成功事件
    handleSuccess(response) {
      if (response.code !== 200) {
        this.$message.error(response.data)
      }
      // 2.将图片信息对象，add到addForm中
      this.addForm.url = response.data
      this.imageUrl = response.data
    },
    // 添加商品
    addGoods() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return this.$message.error('请填写必要的表单项！');

        this.$http.post('good/add', this.addForm).then(response => {
          if (response.data.code !== 200) {
            return this.$message.error('添加商品失败!');
          }

          this.$message.success('添加商品成功!');

          this.$router.push('/good/list')
        }).catch(reason => {
          this.$message.error('请求出错!');
        });
      })
    }
  }
}
</script>

<style lang='less' scoped>
.el-checkbox {
  margin: 0 8px 0 0 !important;
}

.previewImg {
  width: 100%;
}

.btnAdd {
  margin-top: 15px
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
