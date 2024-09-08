package com.zr.manage.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zr.common.utils.spring.SpringUtils;
import com.zr.manage.controller.vo.CoalInfoVO;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.mapper.SupplierInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Odin
 * @Date: 2024/9/8 15:40
 * @Description:
 */

@Component
public class CoalInfoConvert {

    @Autowired
    private SupplierInfoMapper sm;

    public static SupplierInfoMapper supplierInfoMapper;

    @PostConstruct
    public void init() {
        supplierInfoMapper = sm;
    }

    public static CoalInfoVO convertVO(CoalInfo coalInfo) {
        CoalInfoVO coalInfoVO = BeanUtil.copyProperties(coalInfo, CoalInfoVO.class);
        SupplierInfo supplierInfo = supplierInfoMapper.selectById(coalInfo.getCoalSupplierId());
        if (ObjectUtil.isNotNull(supplierInfo)) {
            coalInfoVO.setSupplierName(supplierInfo.getSupplierName());
            coalInfoVO.setSupplierPhone(supplierInfo.getSupplierPhone());
            coalInfoVO.setSupplierPerson(supplierInfo.getSupplierPerson());
        }
        return coalInfoVO;
    }

    public static List<CoalInfoVO> convertVO(List<CoalInfo> coalInfoList){
        List<CoalInfoVO> coalInfoVOList = new ArrayList<>();
        coalInfoList.forEach(coalInfo -> {
            CoalInfoVO coalInfoVO = convertVO(coalInfo);
            coalInfoVOList.add(coalInfoVO);
        });
        return coalInfoVOList;
    }

    public static CoalInfo convertEntity(CoalInfoVO coalInfoVO) {
        return BeanUtil.copyProperties(coalInfoVO, CoalInfo.class);
    }

    public static List<CoalInfo> convertEntity(List<CoalInfoVO> coalInfoVOList){
        List<CoalInfo> coalInfoList = new ArrayList<>();
        coalInfoVOList.forEach(coalInfoVO -> {
            CoalInfo coalInfo = convertEntity(coalInfoVO);
            coalInfoList.add(coalInfo);
        });
        return coalInfoList;
    }


}
