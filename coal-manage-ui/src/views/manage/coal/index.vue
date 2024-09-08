<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="煤矿类型" prop="coalKind">
        <el-input
          v-model="queryParams.coalKind"
          placeholder="请输入煤矿类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格" prop="coalSize">
        <el-input
          v-model="queryParams.coalSize"
          placeholder="请输入规格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="coalPrice">
        <el-input
          v-model="queryParams.coalPrice"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产地" prop="coalOriginPlace">
        <el-input
          v-model="queryParams.coalOriginPlace"
          placeholder="请输入产地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品描述" prop="coalDecs">
        <el-input
          v-model="queryParams.coalDecs"
          placeholder="请输入产品描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单库存" prop="coalInventory">
        <el-input
          v-model="queryParams.coalInventory"
          placeholder="请输入订单库存"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商ID" prop="coalSupplierId">
        <el-input
          v-model="queryParams.coalSupplierId"
          placeholder="请输入供应商ID"
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
          v-hasPermi="['manage:coal:add']"
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
          v-hasPermi="['manage:coal:edit']"
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
          v-hasPermi="['manage:coal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:coal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="coalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="煤矿类型" align="center" prop="coalKind" />
      <el-table-column label="规格" align="center" prop="coalSize" />
      <el-table-column label="价格" align="center" prop="coalPrice" />
      <el-table-column label="产地" align="center" prop="coalOriginPlace" />
      <el-table-column label="产品描述" align="center" prop="coalDecs" />
      <el-table-column label="订单库存" align="center" prop="coalInventory" />
      <el-table-column label="供应商ID" align="center" prop="coalSupplierId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:coal:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:coal:remove']"
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

    <!-- 添加或修改煤矿信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="煤矿类型" prop="coalKind">
          <el-input v-model="form.coalKind" placeholder="请输入煤矿类型" />
        </el-form-item>
        <el-form-item label="规格" prop="coalSize">
          <el-input v-model="form.coalSize" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="价格" prop="coalPrice">
          <el-input v-model="form.coalPrice" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="产地" prop="coalOriginPlace">
          <el-input v-model="form.coalOriginPlace" placeholder="请输入产地" />
        </el-form-item>
        <el-form-item label="产品描述" prop="coalDecs">
          <el-input v-model="form.coalDecs" placeholder="请输入产品描述" />
        </el-form-item>
        <el-form-item label="订单库存" prop="coalInventory">
          <el-input v-model="form.coalInventory" placeholder="请输入订单库存" />
        </el-form-item>
        <el-form-item label="供应商ID" prop="coalSupplierId">
          <el-input v-model="form.coalSupplierId" placeholder="请输入供应商ID" />
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
import { listCoal, getCoal, delCoal, addCoal, updateCoal } from "@/api/manage/coal";

export default {
  name: "Coal",
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
      // 煤矿信息表格数据
      coalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        coalKind: null,
        coalSize: null,
        coalPrice: null,
        coalOriginPlace: null,
        coalDecs: null,
        coalInventory: null,
        coalSupplierId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询煤矿信息列表 */
    getList() {
      this.loading = true;
      listCoal(this.queryParams).then(response => {
        this.coalList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createBy: null,
        updateBy: null,
        createTime: null,
        updateTime: null,
        coalKind: null,
        coalSize: null,
        coalPrice: null,
        coalOriginPlace: null,
        coalDecs: null,
        coalInventory: null,
        coalSupplierId: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加煤矿信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCoal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改煤矿信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCoal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCoal(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除煤矿信息编号为"' + ids + '"的数据项？').then(function() {
        return delCoal(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/coal/export', {
        ...this.queryParams
      }, `coal_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
