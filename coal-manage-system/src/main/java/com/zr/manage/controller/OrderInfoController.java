package com.zr.manage.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zr.common.core.domain.model.LoginUser;
import com.zr.common.exception.base.BaseException;
import com.zr.common.utils.SecurityUtils;
import com.zr.manage.controller.common.Constant;
import com.zr.manage.controller.dto.DividePayDto;
import com.zr.manage.controller.vo.OrderInfoVO;
import com.zr.manage.convert.OrderInfoConvert;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.service.ICheckInfoService;
import com.zr.manage.service.ICoalInfoService;
import com.zr.manage.service.impl.CoalInfoServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zr.common.annotation.Log;
import com.zr.common.core.controller.BaseController;
import com.zr.common.core.domain.AjaxResult;
import com.zr.common.enums.BusinessType;
import com.zr.manage.domain.OrderInfo;
import com.zr.manage.service.IOrderInfoService;
import com.zr.common.utils.poi.ExcelUtil;
import com.zr.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 *
 * @author zr
 * @date 2024-09-08
 */
@RestController
@RequestMapping("/manage/order")
public class OrderInfoController extends BaseController {
    @Autowired
    private IOrderInfoService orderInfoService;
    @Autowired
    private ICoalInfoService coalInfoService;
    @Autowired
    private ICheckInfoService checkInfoService;

    /**
     * 查询订单信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OrderInfo orderInfo) {
        startPage();
        boolean admin = SecurityUtils.getLoginUser().equals("admin");
        if (!admin) {
            orderInfo.setOrderHolderUserId(SecurityUtils.getUserId());
        }
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        return getDataTable(OrderInfoConvert.convertVO(list));
    }

    /**
     * 导出订单信息列表
     */
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderInfo orderInfo) {
        List<OrderInfo> list = orderInfoService.selectOrderInfoList(orderInfo);
        ExcelUtil<OrderInfo> util = new ExcelUtil<OrderInfo>(OrderInfo.class);
        util.exportExcel(response, list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderInfoService.selectOrderInfoById(id));
    }

    /**
     * 新增订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInfo orderInfo) {
        return toAjax(orderInfoService.insertOrderInfo(orderInfo));
    }

    /**
     * 修改订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderInfo orderInfo) {
        return toAjax(orderInfoService.updateOrderInfo(orderInfo));
    }

    /**
     * 删除订单信息
     */
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderInfoService.deleteOrderInfoByIds(ids));
    }

    /**
     * 查询物料
     */
    @GetMapping("/queryCoal")
    public AjaxResult queryCoal() {
        return AjaxResult.success(coalInfoService.list());
    }

    /**
     * 发货
     */
    @GetMapping("/deliver")
    public AjaxResult deliver(@RequestParam String id) {
        OrderInfo orderInfo = orderInfoService.getById(id);
        String orderPayStatus = orderInfo.getOrderPayStatus();
        if (orderPayStatus.equals(Constant.UN_PAY)) {
            throw new BaseException("未付款，无法发货");
        }
        orderInfo.setOrderStatus(Constant.ORDER_STATUS_SEND);
        orderInfoService.updateById(orderInfo);
        return AjaxResult.success();
    }

    /**
     * 收货
     */
    @GetMapping("/rev")
    public AjaxResult rev(@RequestParam String id) {
        OrderInfo orderInfo = orderInfoService.getById(id);
        if (!orderInfo.getOrderStatus().equals(Constant.ORDER_STATUS_SEND)) {
            throw new BaseException("未发货，正在加急备货中");
        }
        if (!orderInfo.getOrderPayStatus().equals(Constant.PAY_FINISH)) {
            throw new BaseException("未付清款项，无法收货");
        }
        if (!orderInfo.getOrderStatus().equals(Constant.ORDER_STATUS_RCV)) {
            throw new BaseException("请勿重复确认收货");
        }
        orderInfo.setOrderStatus(Constant.ORDER_STATUS_RCV);
        orderInfoService.updateById(orderInfo);
        return AjaxResult.success();
    }

    /**
     * 一次性付款清帐
     */
    @GetMapping("/payFinish")
    public AjaxResult payFinish(@RequestParam String id) {
        // 更新订单表
        OrderInfo orderInfo = orderInfoService.getById(id);
        if (orderInfo.getOrderPayStatus().equals(Constant.UN_PAY_FINISH)) {
            throw new BaseException("已选择分期付款方式，无法直接支付");
        }
        if (orderInfo.getOrderPayStatus().equals(Constant.PAY_FINISH)) {
            throw new BaseException("此订单已付款");
        }
        orderInfo.setOrderPayStatus(Constant.PAY_FINISH);
        orderInfoService.updateById(orderInfo);
        // 插入账单
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckOrderId(orderInfo.getId());
        checkInfo.setCheckRevAmt(orderInfo.getOrderPrice());
        checkInfo.setCheckPayName(orderInfo.getOrderBuyerName());
        checkInfo.setCheckPayPhone(orderInfo.getOrderBuyerPhone());
        StringBuffer sb = new StringBuffer();
        sb.append("订单编号:");
        sb.append(orderInfo.getOrderNo());
        sb.append(" 全额付款");
        sb.append("付款金额:" + orderInfo.getOrderPrice());
        checkInfo.setCheckPayRemark(sb.toString());
        checkInfo.setCheckHolderUserId(SecurityUtils.getUserId());
        checkInfoService.save(checkInfo);
        return AjaxResult.success();
    }

    /**
     * 未一次性清账单 分批付款
     * 校验是否超额支付，订单支付满额之后，自动支付完成
     */
    @PostMapping("/payDivide")
    public AjaxResult payDivide(@RequestBody DividePayDto dto) {
        orderInfoService.payDivide(dto.getId(), dto.getPayAmt());
        return AjaxResult.success();
    }
}
