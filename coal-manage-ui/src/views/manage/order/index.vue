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
        <el-select v-model="form.coalKind" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.coal_kind"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="订单类型" prop="orderRemark">
        <el-select v-model="queryParams.orderRemark" placeholder="请选择订单类型" clearable>
          <el-option
            v-for="dict in dict.type.order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="流转状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择流转状态" clearable>
          <el-option
            v-for="dict in dict.type.order_transfer"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态" prop="orderPayStatus">
        <el-select v-model="queryParams.orderPayStatus" placeholder="请选择流转状态" clearable>
          <el-option
            v-for="dict in dict.type.order_pay"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column label="订单编号" align="center" prop="orderNo"/>
      <el-table-column label="下单物料" align="center" prop="coalKind">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coal_kind" :value="scope.row.coalKind"/>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_transfer" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="orderPayStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_pay" :value="scope.row.orderPayStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="订单金额" align="center" prop="orderPrice"/>
      <el-table-column label="订单类型" align="center" prop="orderRemark">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_type" :value="scope.row.orderRemark"/>
        </template>
      </el-table-column>
      <el-table-column label="订单创建时间" align="center" prop="createTime" />
      <el-table-column label="下单人" align="center" prop="orderBuyerName"/>
      <el-table-column label="下单人手机号" align="center" prop="orderBuyerPhone"/>
      <el-table-column label="收货地址" align="center" prop="orderBuyerAddress"/>
      <el-table-column label="订单添加人" align="center" prop="orderHolderUserName"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDeliver(scope.row)"
          >发货
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleRec(scope.row)"
          >确认收货
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handlePay(scope.row)"
          >全额付款
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handlePartPay(scope.row)"
          >分期支付
          </el-button>
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

    <el-dialog :title="partPayTitle" :visible.sync="partPayFlag" width="500px" append-to-body>
      <el-form ref="partPayForm" :model="partPayForm" label-width="80px">
        <el-form-item label="分期支付金额" prop="payAmt">
          <el-input v-model="partPayForm.payAmt" placeholder="请输入分期付款金额"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPartPay">确 定</el-button>
        <el-button @click="cancelPartPay">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号"/>
        </el-form-item>
        <el-form-item label="订单金额" prop="orderPrice">
          <el-input v-model="form.orderPrice" placeholder="请输入订单金额"/>
        </el-form-item>
        <el-form-item label="下单人" prop="orderBuyerName">
          <el-input v-model="form.orderBuyerName" placeholder="请输入下单人"/>
        </el-form-item>
        <el-form-item label="下单人手机号" prop="orderBuyerPhone">
          <el-input v-model="form.orderBuyerPhone" placeholder="请输入下单人手机号"/>
        </el-form-item>
        <el-form-item label="收货地址" prop="orderBuyerAddress">
          <el-input v-model="form.orderBuyerAddress" placeholder="请输入收货地址"/>
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
import {
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
  queryCoalList,
  deliverOrder,
  revOrder,
  payFinishOrder,
  partPay
} from "@/api/manage/order";

export default {
  name: "Order",
  dicts: ['order_transfer', 'order_pay', 'order_type', 'coal_kind'],
  data() {
    return {
      // 遮罩层
      loading: true,
      coalList: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      partPayFlag: false,
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
      partPayTitle: "",
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
      rules: {},
      partPayForm:{}
    };
  },
  created() {
    this.getCoalList();
    this.getList();
  },
  methods: {
    getCoalList() {
      queryCoalList().then(res => {
        this.coalList = res.data;
      })
    },
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
    cancelPartPay() {
      this.partPayFlag = false;
      this.resetPartPay();
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
    resetPartPay(){
      this.partPayForm = {
        id: null,
        payAmt: null
      }
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单信息";
    },
    handlePartPay(row) {
      this.resetPartPay()
      const id = row.id
      this.partPayForm.id = id
      this.partPayFlag = true
      this.partPayTitle = "分期支付"
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
    submitPartPay() {
      partPay(this.partPayForm).then(res => {
        this.$modal.msgSuccess("分期付款成功");
        this.resetPartPay()
        this.partPayFlag = false;
        this.getList()
      })
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
      this.$modal.confirm('是否确认删除订单信息编号为"' + ids + '"的数据项？').then(function () {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    handleDeliver(row) {
      const id = row.id;
      deliverOrder(id).then(res => {
        this.$modal.msgSuccess("发货成功");
        this.getList()
      })
    },
    handleRec(row) {
      const id = row.id;
      revOrder(id).then(res => {
        this.$modal.msgSuccess("确认收货成功");
        this.getList()
      })
    },
    handlePay(row) {
      const id = row.id;
      payFinishOrder(id).then(res => {
        this.$modal.msgSuccess("付款成功");
        this.getList()
      })
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
