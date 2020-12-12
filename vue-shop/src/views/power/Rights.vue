<template>
    <div>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>权限列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 卡片试图区 -->
        <el-card class="box-card">
            <el-table :data="rightsList" stripe border>
                <el-table-column label="序号" type="index" width="200px"></el-table-column>
                <el-table-column label="权限名称" prop="name"></el-table-column>
                <el-table-column label="路径" prop="perm"></el-table-column>
                <el-table-column label="权限等级" prop="level">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.level===1">一级</el-tag>
                        <el-tag v-else-if="scope.row.level===2" type="success">二级</el-tag>
                        <el-tag v-else type="warning">三级</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
    </div>
</template>

<script>
    export default {
        created(){
          this.getRightsList();
        },
        data(){
            return {
                //所有的权限列表
                rightsList:[]
            }
        },
        methods:{
            async getRightsList(){
                const {data: res} = await this.$http.get('perm/allperms')
                if(res.code!==200){
                    return this.$message.error("获取用户权限列表失败");
                }

                this.rightsList = res.data;
            }
        }
    }
</script>
