<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单ID" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入商品ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评价人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入评价人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="被评价人ID" prop="sellerId">
        <el-input
          v-model="queryParams.sellerId"
          placeholder="请输入被评价人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="evalScore">
        <el-input
          v-model="queryParams.evalScore"
          placeholder="请输入评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回复时间" prop="replyTime">
        <el-date-picker clearable
          v-model="queryParams.replyTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择回复时间">
        </el-date-picker>
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
          v-hasPermi="['biz:evaluation:add']"
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
          v-hasPermi="['biz:evaluation:edit']"
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
          v-hasPermi="['biz:evaluation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:evaluation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="evaluationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评价ID" align="center" prop="evalId" />
      <el-table-column label="订单ID" align="center" prop="orderId" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="评价人ID" align="center" prop="userId" />
      <el-table-column label="被评价人ID" align="center" prop="sellerId" />
      <el-table-column label="评分" align="center" prop="evalScore" />
      <el-table-column label="评价内容" align="center" prop="evalContent" />
      <el-table-column label="评价图片" align="center" prop="evalPics" />
      <el-table-column label="卖家回复" align="center" prop="replyContent" />
      <el-table-column label="回复时间" align="center" prop="replyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.replyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:evaluation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:evaluation:remove']"
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

    <!-- 添加或修改评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单ID" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入订单ID" />
        </el-form-item>
        <el-form-item label="商品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入商品ID" />
        </el-form-item>
        <el-form-item label="评价人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入评价人ID" />
        </el-form-item>
        <el-form-item label="被评价人ID" prop="sellerId">
          <el-input v-model="form.sellerId" placeholder="请输入被评价人ID" />
        </el-form-item>
        <el-form-item label="评分" prop="evalScore">
          <el-input v-model="form.evalScore" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="评价内容">
          <editor v-model="form.evalContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="评价图片" prop="evalPics">
          <el-input v-model="form.evalPics" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="卖家回复">
          <editor v-model="form.replyContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="回复时间" prop="replyTime">
          <el-date-picker clearable
            v-model="form.replyTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择回复时间">
          </el-date-picker>
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
import { listEvaluation, getEvaluation, delEvaluation, addEvaluation, updateEvaluation } from "@/api/biz/evaluation"

export default {
  name: "Evaluation",
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
      // 评价表格数据
      evaluationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        productId: null,
        userId: null,
        sellerId: null,
        evalScore: null,
        evalContent: null,
        evalPics: null,
        replyContent: null,
        replyTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "订单ID不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "商品ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "评价人ID不能为空", trigger: "blur" }
        ],
        sellerId: [
          { required: true, message: "被评价人ID不能为空", trigger: "blur" }
        ],
        evalScore: [
          { required: true, message: "评分不能为空", trigger: "blur" }
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
    /** 查询评价列表 */
    getList() {
      this.loading = true
      listEvaluation(this.queryParams).then(response => {
        this.evaluationList = response.rows
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
        evalId: null,
        orderId: null,
        productId: null,
        userId: null,
        sellerId: null,
        evalScore: null,
        evalContent: null,
        evalPics: null,
        replyContent: null,
        replyTime: null,
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
      this.ids = selection.map(item => item.evalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加评价"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const evalId = row.evalId || this.ids
      getEvaluation(evalId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改评价"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.evalId != null) {
            updateEvaluation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addEvaluation(this.form).then(response => {
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
      const evalIds = row.evalId || this.ids
      this.$modal.confirm('是否确认删除评价编号为"' + evalIds + '"的数据项？').then(function() {
        return delEvaluation(evalIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/evaluation/export', {
        ...this.queryParams
      }, `evaluation_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
