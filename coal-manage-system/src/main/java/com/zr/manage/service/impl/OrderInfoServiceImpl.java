package com.zr.manage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.exception.base.BaseException;
import com.zr.common.utils.DateUtils;
import com.zr.common.utils.SecurityUtils;
import com.zr.manage.controller.common.Constant;
import com.zr.manage.controller.dto.CoalOrderDto;
import com.zr.manage.controller.dto.CoalPurchaseDto;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.mapper.CoalInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.OrderInfoMapper;
import com.zr.manage.domain.OrderInfo;
import com.zr.manage.service.IOrderInfoService;

/**
 * 订单信息Service业务层处理
 *
 * @author zr
 * @date 2024-09-08
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private CoalInfoMapper coalInfoMapper;

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public OrderInfo selectOrderInfoById(Long id) {
        return orderInfoMapper.selectOrderInfoById(id);
    }

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo) {
        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int insertOrderInfo(OrderInfo orderInfo) {
        orderInfo.setCreateTime(DateUtils.getNowDate());
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        orderInfo.setUpdateTime(DateUtils.getNowDate());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByIds(Long[] ids) {
        return orderInfoMapper.deleteOrderInfoByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoById(Long id) {
        return orderInfoMapper.deleteOrderInfoById(id);
    }

    @Override
    public void order(CoalOrderDto dto) {
        Long coalId = dto.getCoalId();
        String orderTon = dto.getOrderTon();
        CoalInfo coalInfo = coalInfoMapper.selectById(coalId);
        String coalInventory = coalInfo.getCoalInventory();
        //  =============下单流程=============
        // 1. 判断库存是否充足
        BigDecimal coalInventoryNum = new BigDecimal(coalInventory);
        BigDecimal orderTonNum = new BigDecimal(orderTon);
        if (orderTonNum.compareTo(coalInventoryNum) < 0) {
            throw new BaseException("库存不足");
        }
        // 2.扣减库存
        BigDecimal subtract = coalInventoryNum.subtract(orderTonNum);
        coalInfo.setCoalInventory(subtract.toString());
        coalInfoMapper.updateById(coalInfo);
        // 3. 生成订单
        // 3.1 计算订单价格
        BigDecimal coalPrice = coalInfo.getCoalPrice();
        BigDecimal orderPrice = orderTonNum.multiply(coalPrice);
        // 3.2 初始化订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IdUtil.simpleUUID());
        orderInfo.setOrderCoalId(coalId);
        orderInfo.setOrderStatus(Constant.ORDER_STATUS_CREATE);
        orderInfo.setOrderPayStatus(Constant.UN_PAY);
        orderInfo.setOrderPrice(orderPrice);
        orderInfo.setOrderRemark(Constant.SALE_ORDER);
        orderInfo.setOrderBuyerName(dto.getOrderBuyName());
        orderInfo.setOrderBuyerAddress(dto.getOrderRevAddress());
        orderInfo.setOrderBuyerPhone(dto.getOrderBuyerPhone());
        orderInfo.setOrderHolderUserId(SecurityUtils.getUserId());
        this.save(orderInfo);
    }

    @Override
    public void purchase(CoalPurchaseDto dto) {
        // ===========采购流程===========
        // 1.默认直接创建订单，订单支付完成关闭后添加库存
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IdUtil.simpleUUID());
        // 2.计算订单价格
        Long coalId = dto.getCoalId();
        CoalInfo coalInfo = coalInfoMapper.selectById(coalId);
        BigDecimal coalPrice = coalInfo.getCoalPrice();
        BigDecimal orderTonNum = new BigDecimal(dto.getOrderTon());
        BigDecimal orderPrice = coalPrice.multiply(orderTonNum);
        orderInfo.setOrderPrice(orderPrice);
        // 3.设置订单信息
        orderInfo.setOrderCoalId(coalId);
        orderInfo.setOrderStatus(Constant.ORDER_STATUS_CREATE);
        orderInfo.setOrderPayStatus(Constant.UN_PAY);
        orderInfo.setOrderRemark(Constant.PURCHASE_ORDER);
        orderInfo.setOrderBuyerName(SecurityUtils.getUsername());
        orderInfo.setOrderBuyerPhone(Constant.PURCHASE_ORDER_PHONE);
        orderInfo.setOrderBuyerAddress(Constant.PURCHASE_ORDER_ADDRESS);
        orderInfo.setOrderHolderUserId(dto.getSupplierId());
        this.save(orderInfo);
    }
}
