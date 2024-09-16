<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="煤炭类型" prop="coalKind">
        <el-select v-model="queryParams.coalKind" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.coal_kind"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="规格" prop="coalSize">
        <el-select v-model="queryParams.coalSize" placeholder="请选择规格" clearable>
          <el-option
            v-for="dict in dict.type.coal_size"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="coalSize">
        <el-select v-model="queryParams.coalSupplierId" placeholder="请选择供应商" clearable>
          <el-option
            v-for="item in supplierList"
            :key="item.id"
            :label="item.label"
            :value="item.supplierName"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="coalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="煤炭类型" align="center" prop="coalKind">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coal_kind" :value="scope.row.coalKind"/>
        </template>
      </el-table-column>
      <el-table-column label="规格" align="center" prop="coalSize">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.coal_size" :value="scope.row.coalSize"/>
        </template>
      </el-table-column>
      <el-table-column label="价格(元/吨)" align="center" prop="coalPrice">
        <template slot-scope="scope">
          <el-tag type="default">{{ scope.row.coalPrice }}.00 ¥</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="产品描述" align="center" prop="coalDecs"/>
      <el-table-column label="产品库存(吨)" align="center" prop="coalInventory"/>
      <el-table-column label="预览图" align="center" prop="fileUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.fileUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="supplierName"/>
      <el-table-column label="供应商联系人" align="center" prop="supplierPerson">
        <template slot-scope="scope">
          <el-tag type="default">{{ scope.row.supplierPerson }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="供应商电话" align="center" prop="supplierPhone"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:coal:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="addOrder(scope.row)"
          >下单
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

    <el-dialog :title="addCoalOrderTitle" :visible.sync="openCoalOrder" width="500px" append-to-body>
      <el-form ref="addOrderForm" :model="addOrderForm" label-width="80px">
        <el-form-item label="下单吨数" prop="orderTon">
          <el-input-number v-model="addOrderForm.orderTon" :precision="2"
                           :step="0.5" :max="100" placeholder="下单吨数"/>
        </el-form-item>
        <el-form-item label="下单人" prop="orderBuyName">
          <el-input v-model="addOrderForm.orderBuyName" placeholder="请输入下单人"/>
        </el-form-item>
        <el-form-item label="联系方式" prop="orderBuyerPhone">
          <el-input v-model="addOrderForm.orderBuyerPhone" placeholder="请输入下单人联系方式"/>
        </el-form-item>
        <el-form-item label="收货地址" prop="orderRevAddress">
          <el-input v-model="addOrderForm.orderRevAddress" placeholder="请输入收货地址"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCoalOrder">下 单</el-button>
        <el-button @click="cancelOrder">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="purchaseTitle" :visible.sync="openPurchase" width="500px" append-to-body>
      <el-form ref="purchaseForm" :model="purchaseForm" label-width="80px">
        <el-form-item label="下单物料" prop="coalId">
          <el-select v-model="purchaseForm.coalId" placeholder="请选择类型" clearable>
            <el-option
              v-for="dict in dict.type.coal_kind"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="采购吨数" prop="orderTon">
          <el-input-number v-model="purchaseForm.orderTon" :precision="2"
                           :step="0.5" :max="100" placeholder="采购吨数"/>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="purchaseForm.supplierId" placeholder="请选择供应商" clearable>
            <el-option
              v-for="item in supplierList"
              :key="item.id"
              :label="item.supplierName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPurchase">下 单</el-button>
        <el-button @click="cancelPurchase">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改煤炭信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="煤炭类型" prop="coalKind">
          <el-select v-model="form.coalKind" placeholder="请选择类型" clearable>
            <el-option
              v-for="dict in dict.type.coal_kind"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="规格" prop="coalSize">
          <el-select v-model="form.coalSize" placeholder="请选择规格" clearable>
            <el-option
              v-for="dict in dict.type.coal_size"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="coalPrice">
          <el-input v-model="form.coalPrice" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="产品描述" prop="coalDecs">
          <el-input v-model="form.coalDecs" placeholder="请输入产品描述"/>
        </el-form-item>
        <el-form-item label="供应商" prop="coalSupplierId">
          <el-select v-model="form.coalSupplierId" placeholder="请选择供应商" clearable value-key="value">
            <el-option
              v-for="item in supplierList"
              :key="item.id"
              :label="item.supplierName"
              :value="item.id"
            />
          </el-select>
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
  listCoal,
  getCoal,
  delCoal,
  addCoal,
  updateCoal,
  purchaseOrder,
  saleOrder
} from "@/api/manage/coal";
import {listSupplier} from "@/api/manage/supplier";

export default {
  name: "Coal",
  dicts: ['coal_size', 'coal_kind'],
  data() {
    return {
      supplierList: [],
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
      disableFlag: true,
      // 总条数
      total: 0,
      // 煤炭信息表格数据
      coalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openPurchase: false,
      openCoalOrder: false,
      purchaseTitle: "",
      addCoalOrderTitle: "",
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
      purchaseForm: {},
      addOrderForm: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getSupplierList();
    this.getList();
  },
  methods: {
    /** 查询煤炭信息列表 */
    getList() {
      this.loading = true;
      listCoal(this.queryParams).then(response => {
        this.coalList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getSupplierList() {
      listSupplier().then(res => {
        this.supplierList = res.rows;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    cancelPurchase() {
      this.openPurchase = false;
      this.resetPurchaseForm();
    },
    cancelOrder() {
      this.openCoalOrder = false;
      this.resetAddCoalOrder();
    },
    resetPurchaseForm() {
      this.purchaseForm = {
        coalId: null,
        orderTon: null,
        supplierId: null
      }
    },
    resetAddCoalOrder() {
      this.addOrderForm = {
        coalId: null,
        orderTon: null,
        orderBuyName: null,
        orderBuyerPhone: null,
        orderRevAddress: null
      }
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加煤炭信息";
    },
    purchaseAdd() {
      this.resetPurchaseForm();
      this.openPurchase = true;
      this.purchaseTitle = "添加采购信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCoal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改煤炭信息";
      });
    },
    addOrder(row) {
      this.resetAddCoalOrder();
      const id = row.id
      getCoal(id).then(res => {
        this.addCoalOrderTitle = "添加订单";
        this.addOrderForm.coalId = res.data.id
        this.openCoalOrder = true
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
    submitCoalOrder() {
      saleOrder(this.addOrderForm).then(res => {
        this.$modal.msgSuccess("下单成功");
        this.openCoalOrder = false;
        this.getList()
      })
    },
    submitPurchase() {
      purchaseOrder(this.purchaseForm).then(response => {
        this.$modal.msgSuccess("下单成功");
        this.openPurchase = false;
        this.getList();
      });
    }
  },
  /** 删除按钮操作 */
  handleDelete(row) {
    const ids = row.id || this.ids;
    this.$modal.confirm('是否确认删除煤炭信息编号为"' + ids + '"的数据项？').then(function () {
      return delCoal(ids);
    }).then(() => {
      this.getList();
      this.$modal.msgSuccess("删除成功");
    }).catch(() => {
    });
  }
  ,
  /** 导出按钮操作 */
  handleExport() {
    this.download('manage/coal/export', {
      ...this.queryParams
    }, `coal_${new Date().getTime()}.xlsx`)
  }
};
</script>
