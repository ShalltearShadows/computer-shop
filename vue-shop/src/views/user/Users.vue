<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片试图区 -->
    <el-card class="box-card">
      <!-- gutter 表示格与戈之间的间距，span：每个格子的长度 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 搜索与添加区域 -->
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">
            添加用户
          </el-button>
        </el-col>
      </el-row>

      <!-- 用户列表区 border:表格的边框线；stripe：隔行变色 -->
      <el-table :data="userList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="账号" prop="id"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="电话" prop="mobile"></el-table-column>
        <el-table-column label="角色" prop="role">
          <template v-slot="scope">
            <p v-if="scope.row.role===1">管理员</p>
            <p v-else-if="scope.row.role===4">VIP用户</p>
            <p v-else>普通用户</p>
          </template>
        </el-table-column>
        <el-table-column label="地址" prop="address"></el-table-column>
        <el-table-column label="状态" prop="status">
          <template v-slot="scope">
            <el-switch v-model="scope.row.status" @change="userStateChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <!-- 修改按钮 -->
            <el-tooltip class="item" effect="dark" content="修改信息" placement="top-start" :enterable="false">
              <el-button type="primary" icon="el-icon-edit" circle @click="showEditDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <!-- 删除按钮 -->
            <el-tooltip class="item" effect="dark" content="删除用户" placement="top-start" :enterable="false">
              <el-button type="danger" icon="el-icon-delete" @click="removeUserById(scope.row.id)" circle></el-button>
            </el-tooltip>
            <!-- 分配角色 -->
            <el-tooltip class="item" effect="dark" content="分配角色" placement="top-start" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" @click="showRoleDialog(scope.row)" circle></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum" :page-sizes="[4, 8, 12, 16]"
                     :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper"
                     :total="total"></el-pagination>
    </el-card>

    <!--添加用户对话框-->
    <!-- visible：是否显示对话框；width: 宽度为屏幕的50% -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!--输入区-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input prop="password" v-model="addForm.password" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="addForm.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="addForm.address" clearable></el-input>
        </el-form-item>
      </el-form>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </span>
    </el-dialog>

    <!--修改用户对话框-->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <!--输入区-->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="ID" prop="id">
          <el-input v-model="editForm.id" clearable disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="editForm.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="email">
          <el-input v-model="editForm.address" clearable></el-input>
        </el-form-item>
      </el-form>

      <!-- 按钮区 -->
      <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editUser">确 定</el-button>
            </span>
    </el-dialog>

    <!--分配角色对话框-->
    <el-dialog title="分配角色" :visible.sync="setRoleDialogVisible" width="50%" @close="setRoleDialogClosed">

      <el-form :model="userInfo" ref="editFormRef" label-width="70px" class="demo-ruleForm">
        <el-form-item label="用户名：" prop="username">
          <el-input v-model="userInfo.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色名：" prop="role">
          <el-input v-model="role" disabled></el-input>
        </el-form-item>
        <el-form-item label="请选择：" prop="selectedRoleId">
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <!-- value 进入到v-model里的值 -->
            <el-option v-for="item in rolesList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="setRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRoleInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Users",
  created() {
    this.getUserList();
  },
  data() {

    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 8
      },
      userList: [],
      total: 0,
      //控制添加用户对话框的显示与隐藏
      addDialogVisible: false,
      editDialogVisible: false,
      setRoleDialogVisible: false,
      //添加用户的表单数据
      addForm: {
        username: '',
        password: '',
        mobile: '',
        address: ''
      },
      editForm: {
        id: '',
        username: '',
        mobile: '',
        address: ''
      },
      // 添加功能的验证规则
      addFormRules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
        ],
        // email: [
        //     { required: true, message: "请输入邮箱", trigger: "blur"},
        //     { validator:this.checkEmail, trigger: "blur"}
        // ],
        mobile: [
          {required: true, message: "请输入手机", trigger: "blur"},
          {validator: this.checkMobile, trigger: "blur"}
        ],
        address: [
          {required: true, message: "请输入地址", trigger: "blur"}
        ]
      },
      editFormRules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: "请输入手机", trigger: "blur"},
          {validator: this.checkMobile, trigger: "blur"}
        ],
        address: [
          {required: true, message: "请输入地址", trigger: "blur"}
        ]
      },
      //需要分配角色的用户信息
      userInfo: {},
      //所有角色的列表
      rolesList: [],
      //选中的角色Id
      selectedRoleId: '',
      role:null

    }
  },
  methods: {
    async getUserList() {

      const {data: res} = await this.$http.get('user/list', {params: this.queryInfo});

      if (res.code !== 200) {
        return this.$message.error("获取用户列表失败");
      }

      this.userList = res.data.users;
      this.total = res.data.total;
    },
    //监听page size改变的事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getUserList();
    },
    //当前第几页处理函数
    handleCurrentChange(newPage) {
      this.queryInfo.pagenum = newPage;
      this.getUserList();
    },
    // 监听状态开关的改变
    async userStateChanged(userInfo) {
      const {data: res} = await this.$http.post('user/ban',{id:userInfo.id,status:userInfo.status})
      if (res.code !== 200) {
          userInfo.status = !userInfo.status;
          return this.$message.error(res.msg);
      }

    },
    //验证邮箱规则
    checkEmail(rule, value, cb) {
      //验证邮箱的正则
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;

      if (regEmail.test(value)) {
        //合法的邮箱
        return cb()
      }
      cb(new Error('请输入合法的邮箱'));
    },
    //验证手机号规则
    checkMobile(rule, value, cb) {
      //验证手机号的正则
      const regMobile = /^(1[3|5-9])\d{9}$/;

      if (regMobile.test(value)) {
        //合法的邮箱
        return cb();
      }
      cb(new Error('请输入合法的手机号'));
    },
    //添加窗口关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    addUser() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) {
          return;
        }
        const {data: res} = await this.$http.post('user/add', this.addForm)

        this.$message.success('添加用户成功！');

        //关闭窗口
        this.addDialogVisible = false;

        //刷新用户列表
        this.getUserList();
      })
    },
    async showEditDialog(id) {
      const {data: res} = await this.$http.get('user/edit/' + id)

      if (res.code !== 200) {
        return this.$message.error('获取用户信息失败！')
      }

      this.editForm = res.data;

      this.editDialogVisible = true;

    },
    editUser() {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) {
          return;
        }

        const {data: res} = await this.$http.post('user/edit', this.editForm);

        if (res.code !== 200) {
          return this.$message.error('修改用户失败！');
        }

        //关闭窗口
        this.editDialogVisible = false;

        this.$message.success('修改用户成功！');

        //刷新用户列表
        this.getUserList();
      })
    },
    removeUserById(id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 删除操作
        this.$http.post('user/delete/' + id).then(response => {
          if (response.data.code === 200) {
            this.$message.success("删除成功");
            this.getUserList();
          } else {
            this.$message.error("删除失败")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //展示分配角色的对话框
    showRoleDialog(userInfo) {
      this.userInfo = userInfo;
      if (userInfo.role === 1){
        this.role = "管理员"
      }else if (userInfo.role === 4){
        this.role = "普通用户"
      }

      //再展示对话框之前，获取所有角色的列表
      this.$http.get('user/roles').then(response => {
        if (response.data.code !== 200) {
          return this.$message.error("获取角色列表失败");
        }
        this.rolesList = response.data.data;
      });

      this.setRoleDialogVisible = true;
    },
    // 为用户添加角色
    addRoleInfo() {
      if (!this.selectedRoleId) {
        return this.$message.error("请选择要分配的角色");
      }
      this.$http.post(`user/role/${this.userInfo.id}`, {id: this.selectedRoleId}).then(response => {
        if (response.data.code !== 200) {
          return this.$message.error("分配角色失败");
        }

        this.$message.success("分配角色成功");
        this.getUserList();
        this.setRoleDialogVisible = false;
      });
    },
    setRoleDialogClosed() {
      this.selectedRoleId = '';
      this.userInfo = '';
    }
  },

}
</script>
