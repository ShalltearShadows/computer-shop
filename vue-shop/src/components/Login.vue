<template>
  <div class="lo">
    <div class="login_box">
      <!-- 头像区 -->
      <div class="avatar_box">
        <img src="../assets/logo.png">
      </div>
      <!-- 登录区 -->
      <el-form ref="loginFormRef" label-width="0px" class="login_form" :model="loginForm" :rules="loginFormRules">
        <!--用户名-->
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" v-model="loginForm.id"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="loginForm.password" type="password"
                    @keyup.enter.native="login"></el-input>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button type="info" class="reg" @click="registerDialogVisible = true">注册</el-button>
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="loginFormReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog title="添加用户" :visible.sync="registerDialogVisible" width="30%" @close="registerDialogClosed">
      <!--输入区-->
      <el-form :model="registerForm" :rules="registerFormRules" ref="registerFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input prop="password" v-model="registerForm.password" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="registerForm.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="registerForm.address" clearable></el-input>
        </el-form-item>
      </el-form>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="registerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="register">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        id: '1700701',
        password: '123456'
      },
      //验证
      loginFormRules: {
        id: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
        ]
      },
      registerDialogVisible: false,
      registerForm: {},
      registerFormRules:{
        username: [
          { required: true, message: "请输入用户名", trigger: "blur"},
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur"},
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        // email: [
        //     { required: true, message: "请输入邮箱", trigger: "blur"},
        //     { validator:this.checkEmail, trigger: "blur"}
        // ],
        mobile: [
          { required: true, message: "请输入手机", trigger: "blur"},
          { validator:this.checkMobile, trigger: "blur"}
        ],
        address: [
          { required: true, message: "请输入地址", trigger: "blur"}
        ]
      }
    }
  },
  methods: {
    //重置
    loginFormReset() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      //点击登陆时，查看上面的rules是否验证成功
      this.$refs.loginFormRef.validate((valid) => {
        if (!valid) {
          return;
        }

        this.$http.post('/login', this.loginForm).then(res => {

          this.$message.success("登陆成功")
          //将服务器返回的token存储到sessionStorage
          window.localStorage.setItem("token", res.headers.authorization)

          this.$store.commit("SET_TOKEN", res.headers.authorization)
          this.$store.commit("SET_USERINFO", res.data.data)
          this.getAvatar()

        });
      });

    },
    async getAvatar() {
      const {data:res} = await this.$http.get('/user/avatar')

      var avatar = "data:image/png;base64," + res.data
      window.localStorage.setItem("avatar", avatar)

      // 通过编程式导航跳转到前台主页
      this.$router.push('/foreground')
    },
    async register(){
      this.$refs.registerFormRef.validate(async valid => {
        if (!valid){
          return;
        }
        const {data:res} = await this.$http.post('user/register',this.registerForm).then(res => {

          if (res.data.code!==200){
            this.$message.error("登录失败")
            return
          }
          this.$message.success("登陆成功")
          //将服务器返回的token存储到sessionStorage
          window.localStorage.setItem("token", res.headers.authorization)

          this.$store.commit("SET_TOKEN", res.headers.authorization)
          this.$store.commit("SET_USERINFO", res.data.data)
          this.getAvatar()

        });

      })
    },
    registerDialogClosed(){
      this.$refs.registerFormRef.resetFields();
    },
  }
}
</script>

<style lang="less" scoped>
.lo {
  background-color: gray;
  height: 100%;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 5px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border-radius: 50%;
    border: 1px solid #eee;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
}

.btns {
  display: flex;
  justify-content: flex-end;
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.reg {
  margin-right: 180px;
}
</style>
