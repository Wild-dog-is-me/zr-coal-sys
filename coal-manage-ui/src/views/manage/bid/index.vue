<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="bidName">
        <el-input
          v-model="queryParams.bidName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格" prop="bidCoalSize">
        <el-input
          v-model="queryParams.bidCoalSize"
          placeholder="请输入规格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞价价格" prop="bidCoalPrice">
        <el-input
          v-model="queryParams.bidCoalPrice"
          placeholder="请输入竞价价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞价公司ID" prop="bidSupplierId">
        <el-input
          v-model="queryParams.bidSupplierId"
          placeholder="请输入竞价公司ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞价备注" prop="bidRemark">
        <el-input
          v-model="queryParams.bidRemark"
          placeholder="请输入竞价备注"
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
          v-hasPermi="['manage:bid:add']"
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
          v-hasPermi="['manage:bid:edit']"
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
          v-hasPermi="['manage:bid:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:bid:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bidList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="产品名称" align="center" prop="bidName" />
      <el-table-column label="类型" align="center" prop="bidCoalType" />
      <el-table-column label="规格" align="center" prop="bidCoalSize" />
      <el-table-column label="竞价价格" align="center" prop="bidCoalPrice" />
      <el-table-column label="竞价公司ID" align="center" prop="bidSupplierId" />
      <el-table-column label="竞价备注" align="center" prop="bidRemark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:bid:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:bid:remove']"
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

    <!-- 添加或修改竞价采购对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品名称" prop="bidName">
          <el-input v-model="form.bidName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="规格" prop="bidCoalSize">
          <el-input v-model="form.bidCoalSize" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="竞价价格" prop="bidCoalPrice">
          <el-input v-model="form.bidCoalPrice" placeholder="请输入竞价价格" />
        </el-form-item>
        <el-form-item label="竞价公司ID" prop="bidSupplierId">
          <el-input v-model="form.bidSupplierId" placeholder="请输入竞价公司ID" />
        </el-form-item>
        <el-form-item label="竞价备注" prop="bidRemark">
          <el-input v-model="form.bidRemark" placeholder="请输入竞价备注" />
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
import { listBid, getBid, delBid, addBid, updateBid } from "@/api/manage/bid";

export default {
  name: "Bid",
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
      // 竞价采购表格数据
      bidList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bidName: null,
        bidCoalType: null,
        bidCoalSize: null,
        bidCoalPrice: null,
        bidSupplierId: null,
        bidRemark: null
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
    /** 查询竞价采购列表 */
    getList() {
      this.loading = true;
      listBid(this.queryParams).then(response => {
        this.bidList = response.rows;
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
        bidName: null,
        bidCoalType: null,
        bidCoalSize: null,
        bidCoalPrice: null,
        bidSupplierId: null,
        bidRemark: null
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
      this.title = "添加竞价采购";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBid(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改竞价采购";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBid(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBid(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除竞价采购编号为"' + ids + '"的数据项？').then(function() {
        return delBid(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/bid/export', {
        ...this.queryParams
      }, `bid_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
