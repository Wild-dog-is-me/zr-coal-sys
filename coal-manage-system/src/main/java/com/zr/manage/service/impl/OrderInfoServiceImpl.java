package com.zr.manage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.exception.base.BaseException;
import com.zr.common.utils.DateUtils;
import com.zr.common.utils.SecurityUtils;
import com.zr.manage.controller.common.Constant;
import com.zr.manage.controller.dto.CoalOrderDto;
import com.zr.manage.controller.dto.CoalPurchaseDto;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.mapper.CheckInfoMapper;
import com.zr.manage.mapper.CoalInfoMapper;
import com.zr.manage.util.RandomNumberGenerator;
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
    @Autowired
    private CheckInfoMapper checkInfoMapper;

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
        if (coalInventoryNum.compareTo(orderTonNum) < 0) {
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
        orderInfo.setOrderNo(RandomNumberGenerator.gen());
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
        orderInfo.setOrderNo(RandomNumberGenerator.gen());
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

    @Override
    public void payDivide(String id, String payAmt) {
        OrderInfo orderInfo = this.getById(id);
        if (orderInfo.getOrderPayStatus().equals(Constant.PAY_FINISH)) {
            throw new BaseException("已完成支付，请勿重复支付");
        }
        LambdaQueryWrapper<CheckInfo> qw = new LambdaQueryWrapper<>();
        qw.eq(CheckInfo::getCheckOrderId, orderInfo.getId());
        List<CheckInfo> checkInfoList = checkInfoMapper.selectList(qw);
        double sum = checkInfoList.stream()
                .mapToDouble(checkInfo -> Double.parseDouble(String.valueOf(checkInfo.getCheckRevAmt())))
                .sum();
        BigDecimal sumBigDecimal = new BigDecimal(0);
        BigDecimal sub = new BigDecimal(0);
        if (!Double.isNaN(sum)) {
            sumBigDecimal = BigDecimal.valueOf(sum);
            boolean result = sumBigDecimal.compareTo(orderInfo.getOrderPrice()) < 0;
            if (!result) {
                throw new BaseException("已支付超出所需支付金额，请重新修改支付金额");
            }

            sub = orderInfo.getOrderPrice().subtract(sumBigDecimal).subtract(new BigDecimal(payAmt));
        } else {
            sub = orderInfo.getOrderPrice().subtract(new BigDecimal(payAmt));
        }
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckOrderId(orderInfo.getId());
        checkInfo.setCheckRevAmt(new BigDecimal(payAmt));
        checkInfo.setCheckPayName(orderInfo.getOrderBuyerName());
        checkInfo.setCheckPayPhone(orderInfo.getOrderBuyerPhone());

        StringBuffer sb = new StringBuffer();
        sb.append("订单编号:");
        sb.append(orderInfo.getOrderNo());
        sb.append(" 分期付款");
        sb.append("付款金额:" + payAmt);
        if ((sub.compareTo(BigDecimal.ZERO)) == 0) {
            sb.append(",已支付完成");
        } else {
            sb.append(",仍需支付:" + sub);
        }
        checkInfo.setCheckPayRemark(sb.toString());
        checkInfo.setCheckHolderUserId(SecurityUtils.getUserId());
        checkInfoMapper.insert(checkInfo);
        if (sub.compareTo(orderInfo.getOrderPrice()) == 0||sub.compareTo(BigDecimal.ZERO) == 0) {
            orderInfo.setOrderPayStatus(Constant.PAY_FINISH);
        } else {
            orderInfo.setOrderPayStatus(Constant.UN_PAY_FINISH);
        }
        this.updateById(orderInfo);
    }
}
