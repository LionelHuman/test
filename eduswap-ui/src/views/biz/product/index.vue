<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入分类ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布者ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入发布者ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原价" prop="originalPrice">
        <el-input
          v-model="queryParams.originalPrice"
          placeholder="请输入原价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现价" prop="currentPrice">
        <el-input
          v-model="queryParams.currentPrice"
          placeholder="请输入现价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品成色" prop="productGrade">
        <el-select v-model="queryParams.productGrade" placeholder="请选择商品成色" clearable>
          <el-option
            v-for="dict in dict.type.product_grade"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否保修" prop="isWarranty">
        <el-input
          v-model="queryParams.isWarranty"
          placeholder="请输入是否保修"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保修期限" prop="warrantyPeriod">
        <el-input
          v-model="queryParams.warrantyPeriod"
          placeholder="请输入保修期限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择商品状态" clearable>
          <el-option
            v-for="dict in dict.type.product_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="浏览量" prop="viewCount">
        <el-input
          v-model="queryParams.viewCount"
          placeholder="请输入浏览量"
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
          v-hasPermi="['biz:product:add']"
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
          v-hasPermi="['biz:product:edit']"
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
          v-hasPermi="['biz:product:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:product:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品ID" align="center" prop="productId" />
      <el-table-column label="分类ID" align="center" prop="categoryId" />
      <el-table-column label="发布者ID" align="center" prop="userId" />
      <el-table-column label="商品名称" align="center" prop="productName" />
      <el-table-column label="商品描述" align="center" prop="productDesc" />
      <el-table-column label="原价" align="center" prop="originalPrice" />
      <el-table-column label="现价" align="center" prop="currentPrice" />
      <el-table-column label="商品成色" align="center" prop="productGrade">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_grade" :value="scope.row.productGrade"/>
        </template>
      </el-table-column>
      <el-table-column label="是否保修" align="center" prop="isWarranty" />
      <el-table-column label="保修期限" align="center" prop="warrantyPeriod" />
      <el-table-column label="商品主图" align="center" prop="productPics" />
      <el-table-column label="商品状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.product_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="浏览量" align="center" prop="viewCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:product:remove']"
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="发布者ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入发布者ID" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品描述" prop="productDesc">
          <el-input v-model="form.productDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="原价" prop="originalPrice">
          <el-input v-model="form.originalPrice" placeholder="请输入原价" />
        </el-form-item>
        <el-form-item label="现价" prop="currentPrice">
          <el-input v-model="form.currentPrice" placeholder="请输入现价" />
        </el-form-item>
        <el-form-item label="商品成色" prop="productGrade">
          <el-select v-model="form.productGrade" placeholder="请选择商品成色">
            <el-option
              v-for="dict in dict.type.product_grade"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否保修" prop="isWarranty">
          <el-input v-model="form.isWarranty" placeholder="请输入是否保修" />
        </el-form-item>
        <el-form-item label="保修期限" prop="warrantyPeriod">
          <el-input v-model="form.warrantyPeriod" placeholder="请输入保修期限" />
        </el-form-item>
        <el-form-item label="商品主图" prop="productPics">
          <el-input v-model="form.productPics" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="商品状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择商品状态">
            <el-option
              v-for="dict in dict.type.product_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="浏览量" prop="viewCount">
          <el-input v-model="form.viewCount" placeholder="请输入浏览量" />
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/biz/product"

export default {
  name: "Product",
  dicts: ['product_status', 'product_grade'],
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
      // 商品表格数据
      productList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        userId: null,
        productName: null,
        productDesc: null,
        originalPrice: null,
        currentPrice: null,
        productGrade: null,
        isWarranty: null,
        warrantyPeriod: null,
        productPics: null,
        status: null,
        viewCount: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          { required: true, message: "分类ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "发布者ID不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],
        currentPrice: [
          { required: true, message: "现价不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "商品状态不能为空", trigger: "change" }
        ],
        viewCount: [
          { required: true, message: "浏览量不能为空", trigger: "blur" }
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
    /** 查询商品列表 */
    getList() {
      this.loading = true
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows
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
        productId: null,
        categoryId: null,
        userId: null,
        productName: null,
        productDesc: null,
        originalPrice: null,
        currentPrice: null,
        productGrade: null,
        isWarranty: null,
        warrantyPeriod: null,
        productPics: null,
        status: null,
        viewCount: null,
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
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加商品"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const productId = row.productId || this.ids
      getProduct(productId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改商品"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addProduct(this.form).then(response => {
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
      const productIds = row.productId || this.ids
      this.$modal.confirm('是否确认删除商品编号为"' + productIds + '"的数据项？').then(function() {
        return delProduct(productIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
