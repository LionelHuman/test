<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收件人姓名" prop="receiverName">
        <el-input
          v-model="queryParams.receiverName"
          placeholder="请输入收件人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收件人手机号" prop="receiverPhone">
        <el-input
          v-model="queryParams.receiverPhone"
          placeholder="请输入收件人手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学校/学院ID" prop="schoolId">
        <el-input
          v-model="queryParams.schoolId"
          placeholder="请输入学校/学院ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="详细地址" prop="addressDetail">
        <el-input
          v-model="queryParams.addressDetail"
          placeholder="请输入详细地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否默认地址" prop="isDefault">
        <el-input
          v-model="queryParams.isDefault"
          placeholder="请输入是否默认地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['biz:address:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:address:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:address:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:address:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="地址ID" align="center" prop="addressId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="收件人姓名" align="center" prop="receiverName" />
      <el-table-column label="收件人手机号" align="center" prop="receiverPhone" />
      <el-table-column label="学校/学院ID" align="center" prop="schoolId" />
      <el-table-column label="详细地址" align="center" prop="addressDetail" />
      <el-table-column label="是否默认地址" align="center" prop="isDefault" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:address:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:address:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改收货地址对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="收件人姓名" prop="receiverName">
          <el-input v-model="form.receiverName" placeholder="请输入收件人姓名" />
        </el-form-item>
        <el-form-item label="收件人手机号" prop="receiverPhone">
          <el-input v-model="form.receiverPhone" placeholder="请输入收件人手机号" />
        </el-form-item>
        <el-form-item label="学校/学院ID" prop="schoolId">
          <el-input v-model="form.schoolId" placeholder="请输入学校/学院ID" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addressDetail">
          <el-input v-model="form.addressDetail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="是否默认地址" prop="isDefault">
          <el-input v-model="form.isDefault" placeholder="请输入是否默认地址" />
        </el-form-item>
        <el-form-item label="删除标记" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标记" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAddress, getAddress, delAddress, addAddress, updateAddress } from "@/api/biz/address"

export default {
  name: "Address",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 收货地址表格数据
      addressList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        receiverName: null,
        receiverPhone: null,
        schoolId: null,
        addressDetail: null,
        isDefault: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        receiverName: [
          { required: true, message: "收件人姓名不能为空", trigger: "blur" }
        ],
        receiverPhone: [
          { required: true, message: "收件人手机号不能为空", trigger: "blur" }
        ],
        addressDetail: [
          { required: true, message: "详细地址不能为空", trigger: "blur" }
        ],
        isDefault: [
          { required: true, message: "是否默认地址不能为空", trigger: "blur" }
        ],
        delFlag: [
          { required: true, message: "删除标记不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询收货地址列表 */
    getList() {
      this.loading = true
      listAddress(this.queryParams).then(response => {
        this.addressList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        addressId: null,
        userId: null,
        receiverName: null,
        receiverPhone: null,
        schoolId: null,
        addressDetail: null,
        isDefault: null,
        createTime: null,
        updateTime: null,
        delFlag: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.addressId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加收货地址"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const addressId = row.addressId || this.ids
      getAddress(addressId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改收货地址"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.addressId != null) {
            updateAddress(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAddress(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const addressIds = row.addressId || this.ids
      this.$modal.confirm('是否确认删除收货地址编号为"' + addressIds + '"的数据项？').then(function() {
        return delAddress(addressIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/address/export', {
        ...this.queryParams
      }, `address_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
