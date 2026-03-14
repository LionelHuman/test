<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发送人ID" prop="sendUserId">
        <el-input
          v-model="queryParams.sendUserId"
          placeholder="请输入发送人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收人ID" prop="receiveUserId">
        <el-input
          v-model="queryParams.receiveUserId"
          placeholder="请输入接收人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联商品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入关联商品ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已读" prop="isRead">
        <el-input
          v-model="queryParams.isRead"
          placeholder="请输入是否已读"
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
          v-hasPermi="['biz:message:add']"
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
          v-hasPermi="['biz:message:edit']"
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
          v-hasPermi="['biz:message:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:message:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="messageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="消息ID" align="center" prop="msgId" />
      <el-table-column label="发送人ID" align="center" prop="sendUserId" />
      <el-table-column label="接收人ID" align="center" prop="receiveUserId" />
      <el-table-column label="关联商品ID" align="center" prop="productId" />
      <el-table-column label="消息内容" align="center" prop="msgContent" />
      <el-table-column label="是否已读" align="center" prop="isRead" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:message:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:message:remove']"
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

    <!-- 添加或修改消息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发送人ID" prop="sendUserId">
          <el-input v-model="form.sendUserId" placeholder="请输入发送人ID" />
        </el-form-item>
        <el-form-item label="接收人ID" prop="receiveUserId">
          <el-input v-model="form.receiveUserId" placeholder="请输入接收人ID" />
        </el-form-item>
        <el-form-item label="关联商品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入关联商品ID" />
        </el-form-item>
        <el-form-item label="消息内容">
          <editor v-model="form.msgContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="是否已读" prop="isRead">
          <el-input v-model="form.isRead" placeholder="请输入是否已读" />
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
import { listMessage, getMessage, delMessage, addMessage, updateMessage } from "@/api/biz/message"

export default {
  name: "Message",
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
      // 消息表格数据
      messageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sendUserId: null,
        receiveUserId: null,
        productId: null,
        msgContent: null,
        isRead: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sendUserId: [
          { required: true, message: "发送人ID不能为空", trigger: "blur" }
        ],
        receiveUserId: [
          { required: true, message: "接收人ID不能为空", trigger: "blur" }
        ],
        msgContent: [
          { required: true, message: "消息内容不能为空", trigger: "blur" }
        ],
        isRead: [
          { required: true, message: "是否已读不能为空", trigger: "blur" }
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
    /** 查询消息列表 */
    getList() {
      this.loading = true
      listMessage(this.queryParams).then(response => {
        this.messageList = response.rows
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
        msgId: null,
        sendUserId: null,
        receiveUserId: null,
        productId: null,
        msgContent: null,
        isRead: null,
        createTime: null,
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
      this.ids = selection.map(item => item.msgId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加消息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const msgId = row.msgId || this.ids
      getMessage(msgId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改消息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.msgId != null) {
            updateMessage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addMessage(this.form).then(response => {
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
      const msgIds = row.msgId || this.ids
      this.$modal.confirm('是否确认删除消息编号为"' + msgIds + '"的数据项？').then(function() {
        return delMessage(msgIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/message/export', {
        ...this.queryParams
      }, `message_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
