package com.zr.manage.convert;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zr.common.core.domain.entity.SysUser;
import com.zr.manage.controller.common.Constant;
import com.zr.manage.controller.vo.OrderInfoVO;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.domain.OrderInfo;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.mapper.CoalInfoMapper;
import com.zr.manage.mapper.SupplierInfoMapper;
import com.zr.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Odin
 * @Date: 2024/9/8 23:30
 * @Description:
 */

@Component
public class OrderInfoConvert {

    @Autowired
    private SysUserMapper sum;
    private static SysUserMapper sysUserMapper;

    @Autowired
    private CoalInfoMapper cim;
    private static CoalInfoMapper coalInfoMapper;

    @Autowired
    private SupplierInfoMapper sim;
    private static SupplierInfoMapper supplierInfoMapper;

    @PostConstruct
    public void init() {
        sysUserMapper = this.sum;
        coalInfoMapper = this.cim;
        supplierInfoMapper = this.sim;
    }

    public static OrderInfoVO convertVO(OrderInfo orderInfo) {
        OrderInfoVO orderInfoVO = BeanUtil.copyProperties(orderInfo, OrderInfoVO.class);
        LambdaQueryWrapper<CoalInfo> lqw = new LambdaQueryWrapper();
        lqw.eq(CoalInfo::getCoalKind, orderInfo.getOrderCoalId());
        CoalInfo coalInfo = coalInfoMapper.selectList(lqw).get(0);
        orderInfoVO.setCoalKind(coalInfo.getCoalKind());
        orderInfoVO.setCoalSize(coalInfo.getCoalSize());
        String orderRemark = orderInfo.getOrderRemark();
        if (orderRemark.equals(Constant.PURCHASE_ORDER)) {
            SupplierInfo supplierInfo = supplierInfoMapper.selectById(orderInfo.getOrderHolderUserId());
            orderInfoVO.setOrderHolderUserName(supplierInfo.getSupplierName());
        } else if (orderRemark.equals(Constant.SALE_ORDER)) {
            SysUser sysUser = sysUserMapper.selectUserById(orderInfo.getOrderHolderUserId());
            orderInfoVO.setOrderHolderUserName(sysUser.getUserName());
        }
        return orderInfoVO;
    }

    public static List<OrderInfoVO> convertVO(List<OrderInfo> orderInfoList) {
        List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
        orderInfoList.forEach(orderInfo -> {
            orderInfoVOList.add(convertVO(orderInfo));
        });
        return orderInfoVOList;
    }

    public static OrderInfo convertEntity(OrderInfoVO orderInfoVO) {
        return BeanUtil.copyProperties(orderInfoVO, OrderInfo.class);
    }

    public static List<OrderInfo> convertEntity(List<OrderInfoVO> orderInfoVOList) {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        orderInfoVOList.forEach(orderInfoVO -> {
            orderInfoList.add(convertEntity(orderInfoVO));
        });
        return orderInfoList;
    }
}
