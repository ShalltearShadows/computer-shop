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
                    <p v-if="scope.row.role===1">超级管理员</p>
                    <p v-else-if="scope.row.role===2">管理员</p>
                    <p v-else-if="scope.row.role===3">VIP用户</p>
                    <p v-else>普通用户</p>
                  </template>
                </el-table-column>
                <el-table-column label="地址" prop="address"></el-table-column>
                <el-table-column label="状态" prop="status">
                    <template v-slot="scope">
                        <el-switch v-model="scope.row.status===1" @change="userStateChanged(scope.row)"></el-switch>
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
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pagenum"
                    :page-sizes="[4, 8, 12, 16]"
                    :page-size="queryInfo.pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </el-card>

        <!--添加用户对话框-->
        <!-- visible：是否显示对话框；width: 宽度为屏幕的50% -->
        <el-dialog
                title="添加用户"
                :visible.sync="addDialogVisible"
                width="50%"
                @close="addDialogClosed">
            <!--输入区-->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px" class="demo-ruleForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addForm.username" clearable></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input prop="password" v-model="addForm.password" clearable></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="addForm.email" clearable></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="addForm.mobile" clearable></el-input>
                </el-form-item>
            </el-form>

            <!-- 按钮区 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </span>
        </el-dialog>

        <!--修改用户对话框-->
        <el-dialog
                title="修改用户"
                :visible.sync="editDialogVisible"
                width="50%"
                @close="editDialogClosed">
            <!--输入区-->
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px" class="demo-ruleForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="editForm.username" clearable disabled></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email" clearable></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="editForm.mobile" clearable></el-input>
                </el-form-item>
            </el-form>

            <!-- 按钮区 -->
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editUser">确 定</el-button>
            </span>
        </el-dialog>

        <!--分配角色对话框-->
        <el-dialog
                title="分配角色"
                :visible.sync="setRoleDialogVisible"
                width="50%"
                @close="setRoleDialogClosed">

            <div>
                <p>{{userInfo.username}}</p>
                <p>{{userInfo.role_name}}</p>
                <p>
                    <el-select v-model="selectedRoleId" placeholder="请选择">
                        <!-- value 进入到v-model里的值 -->
                        <el-option
                                v-for="item in rolesList"
                                :key="item.id"
                                :label="item.roleName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </p>
            </div>

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
                    pagesize: 4
                },
                userList: [],
                total: 0,
                //控制添加用户对话框的显示与隐藏
                addDialogVisible: false,
                editDialogVisible: false,
                setRoleDialogVisible: false,
                //添加用户的表单数据
                addForm:{
                    username: '',
                    password: '',
                    email: '',
                    mobile: ''
                },
                editForm:{
                    username: '',
                    email: '',
                    mobile: ''
                },
                // 添加功能的验证规则
                addFormRules: {
                    username: [
                        { required: true, message: "请输入用户名", trigger: "blur"},
                        { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: "请输入密码", trigger: "blur"},
                        { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: "请输入邮箱", trigger: "blur"},
                        { validator:this.checkEmail, trigger: "blur"}
                    ],
                    mobile: [
                        { required: true, message: "请输入手机", trigger: "blur"},
                        { validator:this.checkMobile, trigger: "blur"}
                    ]
                },
                editFormRules: {
                    username: [
                        { required: true, message: "请输入用户名", trigger: "blur"},
                        { min: 5, max: 16, message: '长度在 5 到 16 个字符', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: "请输入邮箱", trigger: "blur"},
                        { validator:this.checkEmail, trigger: "blur"}
                    ],
                    mobile: [
                        { required: true, message: "请输入手机", trigger: "blur"},
                        { validator:this.checkMobile, trigger: "blur"}
                    ]
                },
                //需要分配角色的用户信息
                userInfo:{},
                //所有角色的列表
                rolesList:[],
                //选中的角色Id
                selectedRoleId: '',

            }
        },
        methods: {
            async getUserList() {

                const {data: res} = await this.$http.get('user/list');

                if (res.code !== 200) {
                    return this.$message.error("获取用户列表失败");
                }

                this.userList = res.data;
                // this.total = res.data.total;
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
                // const {data: res} = await this.$http.put(`users/${userInfo.id}/state/${userInfo.mg_state}`)
                // if (res.meta.status !== 200) {
                //     userInfo.mg_state = !userInfo.mg_state;
                //     return this.$message.error("更新用户失败");
                // }
                //
                // this.$message.success("更新用户状态成功");
            },
            //验证邮箱规则
            checkEmail(rule,value,cb) {
                //验证邮箱的正则
                const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;

                if (regEmail.test(value)){
                    //合法的邮箱
                    return cb()
                }
                cb(new Error('请输入合法的邮箱'));
            },
            //验证手机号规则
            checkMobile(rule,value,cb) {
                //验证手机号的正则
                const regMobile = /^(1[3|5-9])\d{9}$/;

                if (regMobile.test(value)){
                    //合法的邮箱
                    return cb();
                }
                cb(new Error('请输入合法的手机号'));
            },
            //添加窗口关闭事件
            addDialogClosed(){
                this.$refs.addFormRef.resetFields();
            },
            editDialogClosed(){
                this.$refs.editFormRef.resetFields();
            },
            addUser(){
                this.$refs.addFormRef.validate(async valid => {
                    if (!valid){
                        return;
                    }
                    const {data:res} = await this.$http.post('users',this.addForm)

                    if (res.meta.status!==201){
                        return this.$message.error('添加用户失败！');
                    }

                    this.$message.success('添加用户成功！');

                    //关闭窗口
                    this.dialogVisible = false;

                    //刷新用户列表
                    this.getUserList();
                })
            },
            async showEditDialog(id){
                const {data:res} = await this.$http.get('users/'+id)

                if (res.meta.status!==200){
                    return this.$message.error('查询用户失败！');
                }

                this.editForm = res.data;

                this.editDialogVisible = true;

            },
            editUser(){
                this.$refs.editFormRef.validate(async valid => {
                    if (!valid){
                        return;
                    }

                    const {data:res} = await this.$http.put('users/'+this.editForm.id,{
                        email: this.editForm.email,
                        mobile: this.editForm.mobile
                    });

                    if (res.meta.status!==200){
                        return this.$message.error('修改用户失败！');
                    }

                    //关闭窗口
                    this.editDialogVisible = false;

                    this.$message.success('修改用户成功！');

                    //刷新用户列表
                    this.getUserList();
                })
            },
            removeUserById(id){
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 删除操作
                    this.$http.delete('users/'+id).then(response =>{
                        if (response.data.meta.status===200){
                            this.$message.success("删除成功");
                            this.getUserList();
                        }else {
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
            showRoleDialog(userInfo){
                this.userInfo = userInfo;

                //再展示对话框之前，获取所有角色的列表
                this.$http.get('roles').then(response =>{
                    if (response.status!==200){
                        return this.$message.error("获取角色列表失败");
                    }
                    this.rolesList = response.data.data;

                });

                this.setRoleDialogVisible = true;
            },
            // 为用户添加角色
            addRoleInfo(){
                if (!this.selectedRoleId){
                    return this.$message.error("请选择要分配的角色");
                }
                this.$http.put(`users/${this.userInfo.id}/role`,{rids:this.selectedRoleId}).then(response =>{
                    if (response.status!==200){
                        return this.$message.error("分配角色失败");
                    }

                    this.$message.success("分配角色成功");
                });

                this.getUserList();
                this.setRoleDialogVisible = false;
            },
            setRoleDialogClosed(){
                this.selectedRoleId = '';
                this.userInfo = '';
            }
        },

    }
</script>
