<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下单物料" prop="orderCoalId">
        <el-input
          v-model="queryParams.orderCoalId"
          placeholder="请输入下单物料"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单金额" prop="orderPrice">
        <el-input
          v-model="queryParams.orderPrice"
          placeholder="请输入订单金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单备注" prop="orderRemark">
        <el-input
          v-model="queryParams.orderRemark"
          placeholder="请输入订单备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下单人" prop="orderBuyerName">
        <el-input
          v-model="queryParams.orderBuyerName"
          placeholder="请输入下单人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下单人手机号" prop="orderBuyerPhone">
        <el-input
          v-model="queryParams.orderBuyerPhone"
          placeholder="请输入下单人手机号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货地址" prop="orderBuyerAddress">
        <el-input
          v-model="queryParams.orderBuyerAddress"
          placeholder="请输入收货地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单添加人ID" prop="orderHolderUserId">
        <el-input
          v-model="queryParams.orderHolderUserId"
          placeholder="请输入订单添加人ID"
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
          v-hasPermi="['manage:order:add']"
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
          v-hasPermi="['manage:order:edit']"
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
          v-hasPermi="['manage:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="下单物料" align="center" prop="orderCoalId" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" />
      <el-table-column label="支付状态" align="center" prop="orderPayStatus" />
      <el-table-column label="订单金额" align="center" prop="orderPrice" />
      <el-table-column label="订单备注" align="center" prop="orderRemark" />
      <el-table-column label="下单人" align="center" prop="orderBuyerName" />
      <el-table-column label="下单人手机号" align="center" prop="orderBuyerPhone" />
      <el-table-column label="收货地址" align="center" prop="orderBuyerAddress" />
      <el-table-column label="订单添加人ID" align="center" prop="orderHolderUserId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:order:remove']"
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

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="下单物料" prop="orderCoalId">
          <el-input v-model="form.orderCoalId" placeholder="请输入下单物料" />
        </el-form-item>
        <el-form-item label="订单金额" prop="orderPrice">
          <el-input v-model="form.orderPrice" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="订单备注" prop="orderRemark">
          <el-input v-model="form.orderRemark" placeholder="请输入订单备注" />
        </el-form-item>
        <el-form-item label="下单人" prop="orderBuyerName">
          <el-input v-model="form.orderBuyerName" placeholder="请输入下单人" />
        </el-form-item>
        <el-form-item label="下单人手机号" prop="orderBuyerPhone">
          <el-input v-model="form.orderBuyerPhone" placeholder="请输入下单人手机号" />
        </el-form-item>
        <el-form-item label="收货地址" prop="orderBuyerAddress">
          <el-input v-model="form.orderBuyerAddress" placeholder="请输入收货地址" />
        </el-form-item>
        <el-form-item label="订单添加人ID" prop="orderHolderUserId">
          <el-input v-model="form.orderHolderUserId" placeholder="请输入订单添加人ID" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/manage/order";

export default {
  name: "Order",
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
      // 订单信息表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        orderCoalId: null,
        orderStatus: null,
        orderPayStatus: null,
        orderPrice: null,
        orderRemark: null,
        orderBuyerName: null,
        orderBuyerPhone: null,
        orderBuyerAddress: null,
        orderHolderUserId: null
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
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orderNo: null,
        orderCoalId: null,
        orderStatus: null,
        orderPayStatus: null,
        orderPrice: null,
        orderRemark: null,
        orderBuyerName: null,
        orderBuyerPhone: null,
        orderBuyerAddress: null,
        orderHolderUserId: null
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
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除订单信息编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
