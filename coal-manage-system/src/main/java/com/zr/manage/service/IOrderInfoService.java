package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.controller.dto.CoalOrderDto;
import com.zr.manage.controller.dto.CoalPurchaseDto;
import com.zr.manage.domain.OrderInfo;

/**
 * 订单信息Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface IOrderInfoService extends IService<OrderInfo>
{
    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);

    /**
     * 煤炭下单
     * @param dto
     */
    void order(CoalOrderDto dto);

    /**
     * 煤炭采购
     * @param dto
     */
    void purchase(CoalPurchaseDto dto);

    void payDivide(String id, String payAmt);
}
