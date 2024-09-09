package com.zr.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zr.manage.controller.vo.OrderInfoVO;
import com.zr.manage.convert.OrderInfoConvert;
import com.zr.manage.service.ICoalInfoService;
import com.zr.manage.service.impl.CoalInfoServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * 查询订单信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OrderInfo orderInfo) {
        startPage();
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
}
