<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人管理</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card>
      <el-row :gutter="20">
        <el-col :span="4"><el-avatar shape="square" :size="120" fit="fill" :src="imageUrl"></el-avatar></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><el-tag effect="plain">ID</el-tag></el-col>
        <el-col :span="4"><el-tag effect="plain">{{ infoForm.id }}</el-tag></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><el-tag effect="plain">用户名</el-tag></el-col>
        <el-col :span="4"><el-tag effect="plain">{{ infoForm.username }}</el-tag></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><el-tag effect="plain">手机</el-tag></el-col>
        <el-col :span="4"><el-tag effect="plain">{{ infoForm.mobile }}</el-tag></el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><el-tag effect="plain">地址</el-tag></el-col>
        <el-col :span="4"><el-tag effect="plain">{{ infoForm.address }}</el-tag></el-col>
      </el-row>

    </el-card>
    <el-card>
      <el-button type="primary" @click="infoDialogVisible = true">修改信息</el-button>
      <el-button type="primary" @click="passwordDialogVisible = true">修改密码</el-button>
      <el-button type="primary" @click="avatarDialogVisible = true">修改头像</el-button>
    </el-card>

    <!--修改信息对话框-->
    <el-dialog title="修改信息" :visible.sync="infoDialogVisible" width="50%" @close="infoDialogClosed">
      <!--输入区-->
      <el-form ref="infoFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="用户名">
          <el-input v-model="infoForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="infoForm.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="infoForm.address" clearable></el-input>
        </el-form-item>
      </el-form>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="infoDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="alterInfo">确 定</el-button>
            </span>
    </el-dialog>

    <!--修改password对话框-->
    <el-dialog title="修改密码" :visible.sync="passwordDialogVisible" width="50%" @close="passwordDialogClosed">
      <!--输入区-->
      <el-form ref="infoFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="新密码">
          <el-input v-model="passwordForm.p1" clearable></el-input>
        </el-form-item>
        <el-form-item label="再次输入">
          <el-input v-model="passwordForm.p2" clearable></el-input>
        </el-form-item>
      </el-form>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="passwordDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="alterPassword">确 定</el-button>
            </span>
    </el-dialog>

    <el-dialog title="修改头像" :visible.sync="avatarDialogVisible" width="20%" @close="avatarDialogClosed">
      <el-upload
          class="avatar-uploader el-upload"
          :action="uploadURL"
          :show-file-list="false"
          :on-success="handleSuccess"
          :headers="headerObj">
        <el-tooltip class="item" effect="dark" content="点击图片进行修改" placement="top">
          <el-image v-if="imageUrl" :src="imageUrl" class="avatar el-upload"></el-image>
          <i v-else class="el-icon-plus avatar-uploader-icon el-upload"></i>
        </el-tooltip>

      </el-upload>
    </el-dialog>



  </div>
</template>

<script>
import Bus from "../../router/bus"
export default {
  name: "info",
  data() {
    return {
      infoForm:{
        id:'',
        username:'',
        mobile:'',
        address:'',
      },
      passwordForm:{
        id:'',
        p1:'',
        p2:''
      },
      infoDialogVisible:false,
      passwordDialogVisible:false,
      avatarDialogVisible:false,
      avatar:'',
      uploadURL: 'http://localhost:8080/user/upload',
      imageUrl: '',
      headerObj: {
        Authorization: window.sessionStorage.getItem('token')
      },
    }
  },
  created() {
    this.getInfo()
    this.passwordForm.id = this.infoForm.id

    this.imageUrl = window.sessionStorage.getItem('avatar')
  },
  methods:{
    async getInfo(){
      const {data: res} = await this.$http.get('/user/info')
      console.log(res)
      this.infoForm.id = res.data.id
      this.infoForm.username = res.data.username
      this.infoForm.mobile = res.data.mobile
      this.infoForm.address = res.data.address
    },
    infoDialogClosed(){
      this.infoDialogVisible = false
    },
    async alterInfo(){
      const {data: res} = await this.$http.post('/user/alter',this.infoForm)

      this.infoDialogClosed()
    },
    passwordDialogClosed(){
      this.passwordForm.p1 = ''
      this.passwordForm.p2 = ''
      this.passwordDialogVisible = false
    },
    avatarDialogClosed(){
      this.avatarDialogVisible = false
    },
    async alterPassword(){

      if (this.passwordForm.p1!==this.passwordForm.p2){
        return this.$message.error("两次密码不一致")
      }

      const {data: res} = await this.$http.post('/user/password',{id:this.passwordForm.id,password:this.passwordForm.p2})

      this.passwordDialogClosed()
    },
    handleSuccess(response){
      if (response.code!==200){
        this.$message.error("上传失败")
      }

      var avatar = "data:image/png;base64," + response.data
      window.sessionStorage.setItem("avatar", avatar)
      this.imageUrl = avatar

      this.avatarDialogVisible = false

      Bus.$emit("getAvatar");
    },

  }
}
</script>

<style scoped>
.el-row{
  margin: 20px;
}
.el-card{
  margin-top: 20px;
}
</style>
