package com.zr.manage.convert;

import cn.hutool.core.bean.BeanUtil;
import com.zr.manage.controller.vo.CheckInfoVO;
import com.zr.manage.controller.vo.OrderInfoVO;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.domain.OrderInfo;
import com.zr.manage.mapper.CoalInfoMapper;
import com.zr.manage.mapper.OrderInfoMapper;
import com.zr.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Odin
 * @Date: 2024/9/11 23:20
 * @Description:
 */

@Component
public class CheckInfoConvert {

    @Autowired
    private CoalInfoMapper cm;
    @Autowired
    private OrderInfoMapper om;
    @Autowired
    private SysUserMapper sm;

    private static CoalInfoMapper coalInfoMapper;
    private static OrderInfoMapper orderInfoMapper;
    private static SysUserMapper sysUserMapper;

    @PostConstruct
    public void init() {
        sysUserMapper = sm;
        orderInfoMapper = om;
        coalInfoMapper = cm;
    }

    public static CheckInfoVO convertVO(CheckInfo checkInfo) {
        CheckInfoVO checkInfoVO = BeanUtil.copyProperties(checkInfo, CheckInfoVO.class);
        Long checkOrderId = checkInfoVO.getCheckOrderId();
        OrderInfo orderInfo = orderInfoMapper.selectById(checkOrderId);
        checkInfoVO.setCoalKind(String.valueOf(orderInfo.getOrderCoalId()));
        return checkInfoVO;
    }

    public static List<CheckInfoVO> convertVO(List<CheckInfo> checkInfoList) {
        List<CheckInfoVO> list = new ArrayList<>();
        checkInfoList.forEach(checkInfo -> {
            list.add(convertVO(checkInfo));
        });
        return list;
    }
}
