<template>
  <div class="publish-container">
    <!-- 发布表单 -->
    <div class="form-container">
      <el-card>
        <h2 slot="header">发布闲置物品</h2>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="form.productName" placeholder="请输入商品名称" maxlength="50" show-word-limit />
          </el-form-item>

          <el-form-item label="商品分类" prop="categoryId">
            <el-select v-model="form.categoryId" placeholder="请选择商品分类" style="width: 100%">
              <el-option
                v-for="dict in productClassDict"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="商品描述" prop="productDesc">
            <el-input
              v-model="form.productDesc"
              type="textarea"
              :rows="4"
              placeholder="请详细描述商品信息，包括新旧程度、购买时间等"
              maxlength="500"
              show-word-limit
            />
          </el-form-item>

          <el-form-item label="原价" prop="originalPrice">
            <el-input-number v-model="form.originalPrice" :min="0" :precision="2" placeholder="请输入原价" style="width: 100%" />
          </el-form-item>

          <el-form-item label="现价" prop="currentPrice">
            <el-input-number v-model="form.currentPrice" :min="0" :precision="2" placeholder="请输入现价" style="width: 100%" />
          </el-form-item>

          <el-form-item label="商品成色" prop="productGrade">
            <el-select v-model="form.productGrade" placeholder="请选择商品成色" style="width: 100%">
              <el-option label="99 新" value="0" />
              <el-option label="95 新" value="1" />
              <el-option label="9 新" value="2" />
              <el-option label="8 新" value="3" />
              <el-option label="破损" value="4" />
            </el-select>
          </el-form-item>

          <el-form-item label="是否保修" prop="isWarranty">
            <el-radio-group v-model="form.isWarranty">
              <el-radio label="0">否</el-radio>
              <el-radio label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="保修期限" prop="warrantyPeriod" v-if="form.isWarranty === '1'">
            <el-date-picker
              v-model="form.warrantyPeriod"
              type="date"
              placeholder="选择保修到期日期"
              style="width: 100%"
              :disabled-date="disabledDate"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
            />
          </el-form-item>

          <el-form-item label="商品图片">
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
              :on-change="handleFileChange"
              :on-remove="handleRemove"
              :file-list="fileList"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" :loading="submitLoading" @click="submitForm">立即发布</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import { addProduct } from '@/api/biz/product'

export default {
  name: 'Publish',
  dicts: ['product_class'],
  data() {
    return {
      submitLoading: false,
      form: {
        productName: '',
        categoryId: '',
        productDesc: '',
        originalPrice: 0,
        currentPrice: 0,
        productGrade: '',
        isWarranty: '0',
        warrantyPeriod: '',
        productPics: '',
        status: '0' // 审核中
      },
      rules: {
        productName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择商品分类', trigger: 'change' }
        ],
        currentPrice: [
          { required: true, message: '请输入现价', trigger: 'blur' }
        ],
        productGrade: [
          { required: true, message: '请选择商品成色', trigger: 'change' }
        ]
      },
      fileList: [],
      imageUrls: []
    }
  },
  computed: {
    productClassDict() {
      return this.dict.type.product_class || []
    }
  },
  methods: {
    // 禁用过去的日期（只能选择今天及以后的日期）
    disabledDate(time) {
      return time.getTime() < Date.now() - 86400000 // 86400000 = 1 天的毫秒数
    },
    // 处理文件变化
    handleFileChange(file, fileList) {
      this.fileList = fileList
      // 模拟上传，实际应该调用上传接口
      this.imageUrls = fileList.map(f => URL.createObjectURL(f.raw))
    },
    // 删除文件
    handleRemove(file, fileList) {
      this.fileList = fileList
      this.imageUrls = fileList.map(f => URL.createObjectURL(f.raw))
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.submitLoading = true
          try {
            // 获取当前登录用户 ID
            const userId = this.$store.state.user.id
            if (!userId) {
              this.$message.error('请先登录')
              this.$router.push('/login')
              return
            }
            
            // 将图片 URL 转为逗号分隔的字符串
            const formData = {
              ...this.form,
              userId: userId,
              productPics: this.imageUrls.join(',')
            }
            
            await addProduct(formData)
            this.$message.success('发布成功，等待审核')
            // 跳转到个人中心查看已发布的商品
            this.$router.push('/user/center')
          } catch (error) {
            this.$message.error('发布失败：' + (error.message || '未知错误'))
          } finally {
            this.submitLoading = false
          }
        }
      })
    },
    // 重置表单
    resetForm() {
      this.$refs.form.resetFields()
      this.fileList = []
      this.imageUrls = []
    }
  }
}
</script>

<style scoped lang="scss">
.publish-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.form-container {
  max-width: 800px;
  margin: 30px auto;
  padding: 0 20px;

  .el-card {
    h2 {
      margin: 0;
      font-size: 20px;
      color: #333;
    }
  }
}
</style>
