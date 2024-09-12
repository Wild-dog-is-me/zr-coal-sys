package com.zr.manage.convert;

import cn.hutool.core.bean.BeanUtil;
import com.zr.manage.controller.vo.BidInfoVO;
import com.zr.manage.domain.BidInfo;
import com.zr.manage.mapper.SupplierInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Odin
 * @Date: 2024/9/12 23:35
 * @Description:
 */

@Component
public class BidInfoConvert {

    @Autowired
    private SupplierInfoMapper sm;
    private static SupplierInfoMapper supplierInfoMapper;

    @PostConstruct
    public void init() {
        supplierInfoMapper = this.sm;
    }

    public static BidInfoVO convertVO(BidInfo bidInfo) {
        BidInfoVO bidInfoVO = BeanUtil.copyProperties(bidInfo, BidInfoVO.class);
        String supplierName = supplierInfoMapper.selectById(bidInfo.getBidSupplierId()).getSupplierName();
        bidInfoVO.setBidSupplierName(supplierName);
        return bidInfoVO;
    }

    public static List<BidInfoVO> convertVO(List<BidInfo> bidInfoList) {
        List<BidInfoVO> bidInfoVOList = new ArrayList<>();
        bidInfoList.forEach(bidInfo -> {
            bidInfoVOList.add(convertVO(bidInfo));
        });
        return bidInfoVOList;
    }
}
