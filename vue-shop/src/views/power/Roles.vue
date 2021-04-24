<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片试图区 -->
    <el-card class="box-card">
      <!-- 按钮区 -->
      <el-row>
        <el-col>
          <el-button type="primary" @click="setAddRoleDialogVisible=true">添加角色</el-button>
        </el-col>
      </el-row>


      <!-- 角色列表区 -->
      <el-table :data="rolesList" stripe border>
        <!-- 展开列 -->
        <el-table-column type="expand">
          <template v-slot="scope">
            <!--  -->
            <el-row :class="['bdbottom', i1 === 0 ? 'bdtop':'','vcenter']" v-for="(item1,i1) in scope.row.permission" :key="item1.permId">
              <!-- 一行分为24格， span=5表示该列占5格， -->
              <el-col :span="6">
                <el-tag closable @close="removeRightById(scope.row,item1.permId)">{{ item1.name }}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 渲染二级+三级 -->
              <el-col :span="18">
                <el-row :class="[i2 === 0 ? '':'bdtop','vcenter']" v-for="(item2,i2) in item1.children" :key="item2.permId">
                  <!-- 渲染二级权限 -->
                  <el-col :span="6">
                    <el-tag closable type="success" @close="removeRightById(scope.row,item2.permId)">{{ item2.name }}
                    </el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <!-- 渲染三级 -->
                  <el-col :span="18">
                    <!-- 渲染三级权限 -->
                    <el-col>
                      <el-tag closable v-for="item3 in item2.children" :key="item3.permId" type="warning"
                              @close="removeRightById(scope.row,item3.permId)">{{ item3.name }}
                      </el-tag>
                    </el-col>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>

        <!-- 索引列 -->
        <el-table-column label="#" type="index"></el-table-column>
        <el-table-column label="角色名称" prop="name"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditNameForm(scope.row)">编辑
            </el-button>
            <el-button size="mini" type="danger" icon="el-icon-delete" @click="deleteRole(scope.row.id)">删除</el-button>
            <el-button size="mini" type="warning" icon="el-icon-setting" @click="showSetRightDialog(scope.row)">分配权限
            </el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>


    <!--添加角色对话框-->
    <el-dialog title="添加新角色" :visible.sync="setAddRoleDialogVisible" width="50%" @close="addRoleDialogClosed">
      <!--输入区-->
      <el-form :model="addRoleForm" :rules="addRoleFormRules" ref="addRoleFormRef" label-width="80px"
               class="demo-ruleForm">
        <el-form-item label="新角色名" prop="roleName">
          <el-input v-model="addRoleForm.roleName" clearable></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
              <el-button @click="setAddRoleDialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="addRole">确 定</el-button>
            </span>
    </el-dialog>


    <!--编辑角色名对话框-->
    <el-dialog title="编辑角色名" :visible.sync="setEditNameDialogVisible" width="50%" @close="editNameDialogClosed">
      <!--输入区-->
      <el-form :model="editNameForm" :rules="editNameFormRules" ref="editNameFormRef" label-width="80px"
               class="demo-ruleForm">
        <el-form-item label="ID">
          <el-input v-model="editNameForm.roleId" clearable disabled></el-input>
        </el-form-item>
        <el-form-item label="旧角色名">
          <el-input v-model="editNameForm.old" disabled></el-input>
        </el-form-item>
        <el-form-item label="新角色名" prop="roleName">
          <el-input v-model="editNameForm.roleName" clearable></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
            <el-button @click="setEditNameDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="alterName">确 定</el-button>
          </span>
    </el-dialog>


    <!--分配权限对话框-->
    <el-dialog title="分配权限" :visible.sync="setRightDialogVisible" width="50%">
      <!-- rightsList:要渲染的数据; show-check：展示勾选框; default-expand-all:展开所有项;
       node-key:每个节点的唯一索引值，为权限的id; default-checked-keys:默认要勾选的节点 -->
      <el-tree :data="rightsList" show-checkbox default-expand-all node-key="permId"
               :default-checked-keys="defKeys" :props="this.treeProps" ref="treeRef"></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addtRights">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  data() {
    return {
      //所有角色列表数据
      rolesList: [],
      //所有权限的数据
      rightsList: [],
      //控制分配权限对话框显示
      setRightDialogVisible: false,
      treeProps: {
        //树形控件的一级标题，其值应于rightsList的属性名对应
        label: 'name',
        //子标题
        children: 'children'
      },
      //树形控件默认选中的节点id值
      defKeys: [],
      //当前即将分配权限的角色id
      roleId: '',
      editNameForm: {
        roleId: '',
        old: '',
        roleName: ''
      },
      setEditNameDialogVisible: false,
      editNameFormRules: {
        roleName: [
          {required: true, message: "请输入角色名", trigger: "blur"}
        ]
      },
      setAddRoleDialogVisible: false,
      addRoleForm: {
        roleName: ''
      },
      addRoleFormRules: {
        roleName: [
          {required: true, message: "请输入角色名", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.getRolesList();
  },
  methods: {
    //获取所有角色的列表
    async getRolesList() {
      const {data: res} = await this.$http.get('perm/role');
      this.rolesList = res.data;
    },
    // 根据ID删除对应的权限
    async removeRightById(role, rightId) {
      console.log(rightId)
      // 弹框提示 删除
      const confirmResult = await this.$confirm(
          '此操作将永久删除该权限, 是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).catch(err => err);
      // 点击确定 返回值为：confirm
      // 点击取消 返回值为： cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消权限删除')
      }

      const {data: res} = await this.$http.post('perm/delperm/', {roleId: role.id, permId: rightId});
      if (res.code!==200){
        return this.$message.error(res.msg)
      }
      role.permission = res.data
    },
    showSetRightDialog(role) {
      this.roleId = role.id;

      //获取所有权限的数据
      this.$http.get('perm/list').then(response => {
        if (response.status !== 200) {
          return this.$message.error("获取数据失败")
        }
        this.rightsList = response.data.data;
      });

      //清空之前的权限数组
      this.defKeys = [];
      //递归获取三级节点的id
      role.children = role.permission
      this.getLeafKeys(role, this.defKeys);

      this.setRightDialogVisible = true;
    },
    getLeafKeys(node, arr) {
      //如果当前节点不包含children属性则是三级节点
      if (!node.children) {
        return arr.push(node.permId);
      }

      node.children.forEach(item => {
        this.getLeafKeys(item, arr)
      })
    },
    //添加权限
    addtRights() {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ];

      const idStr = keys.join(',');

      this.$http.post('perm/assign', {rid: this.roleId, ids: idStr}).then(response => {
        if (response.data.code !== 200) {
          this.setRightDialogVisible = false;
          return this.$message.error(response.data.msg);
        }

        this.$message.success("添加权限成功");

        this.getRolesList();

        this.setRightDialogVisible = false;
      })
    },
    showEditNameForm(role) {
      this.editNameForm.roleId = role.id
      this.editNameForm.old = role.name
      this.editNameForm.roleName = ''
      this.setEditNameDialogVisible = true
    },

    alterName() {
      this.$refs.editNameFormRef.validate(async valid => {
        if (!valid) {
          return;
        }
        const {data: res} = await this.$http.post('perm/alname',
            {roleId: this.editNameForm.roleId, roleName: this.editNameForm.roleName})
        if (res.code!==200){
          this.setEditNameDialogVisible = false
          return this.$message.error(res.msg)
        }

        this.setEditNameDialogVisible = false
        this.getRolesList()
      })
    },
    editNameDialogClosed() {
      this.$refs.editNameFormRef.resetFields()
    },
    addRoleDialogClosed() {
      this.$refs.addRoleFormRef.resetFields()
    },
    async deleteRole(rid) {
      this.$confirm('此操作将永久删除该角色，是否继续', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('perm/delrole', {id: rid}).then(response => {
          if (response.data.code === 200) {
            this.$message.success("删除成功")
            this.getRolesList()
          } else {
            this.$message.error(response.data.msg)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      })
    },
    addRole() {
      this.$refs.addRoleFormRef.validate(async valid => {
        if (!valid) {
          return;
        }
        const {data: res} = await this.$http.post('perm/add',
            {name: this.addRoleForm.roleName})
        if (res.code !== 200) {
          return this.$message.error('添加失败')
        }

        this.$message.success(res.data)
        this.setAddRoleDialogVisible = false

        this.getRolesList()
      })
    }
  }

}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}

.bdtop {
  border-top: 1px solid #eee;
}

.bdbottom {
  border-bottom: 1px solid #eee;
}

.vcenter {
  display: flex;
  align-items: center;
}
</style>
