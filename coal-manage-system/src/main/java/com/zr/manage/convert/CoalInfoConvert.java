package com.zr.manage.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zr.common.utils.DictUtils;
import com.zr.common.utils.spring.SpringUtils;
import com.zr.manage.controller.vo.CoalInfoVO;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.domain.FileInfo;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.mapper.FileInfoMapper;
import com.zr.manage.mapper.SupplierInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Odin
 * @Date: 2024/9/8 15:40
 * @Description: 煤炭信息转换
 */

@Component
public class CoalInfoConvert {

    @Autowired
    private SupplierInfoMapper sm;
    @Autowired
    private FileInfoMapper fm;

    public static FileInfoMapper fileInfoMapper;
    public static SupplierInfoMapper supplierInfoMapper;

    @PostConstruct
    public void init() {
        supplierInfoMapper = sm;
        fileInfoMapper = fm;
    }

    public static CoalInfoVO convertVO(CoalInfo coalInfo) {
        CoalInfoVO coalInfoVO = BeanUtil.copyProperties(coalInfo, CoalInfoVO.class);
        SupplierInfo supplierInfo = supplierInfoMapper.selectById(coalInfo.getCoalSupplierId());
        LambdaQueryWrapper<FileInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FileInfo::getObjectId, coalInfo.getCoalKind());
        List<FileInfo> fileInfos = fileInfoMapper.selectList(lqw);
        if (ObjectUtil.isNotNull(supplierInfo)) {
            if (!CollectionUtil.isEmpty(fileInfos)) {
                coalInfoVO.setFileUrl(fileInfos.get(0).getFileUrl());
                coalInfoVO.setFileUrls(fileInfos.stream().map(FileInfo::getFileUrl).collect(Collectors.toList()));
            }
            coalInfoVO.setCoalKindDesc(DictUtils.getDictLabel("coal_kind",coalInfo.getCoalKind()));
            coalInfoVO.setCoalSizeDesc(DictUtils.getDictLabel("coal_size",coalInfo.getCoalSize()));
            coalInfoVO.setSupplierName(supplierInfo.getSupplierName());
            coalInfoVO.setSupplierPhone(supplierInfo.getSupplierPhone());
            coalInfoVO.setSupplierPerson(supplierInfo.getSupplierPerson());
        }
        return coalInfoVO;
    }

    public static List<CoalInfoVO> convertVO(List<CoalInfo> coalInfoList) {
        List<CoalInfoVO> coalInfoVOList = new ArrayList<>();
        coalInfoList.forEach(coalInfo -> {
            coalInfoVOList.add(convertVO(coalInfo));
        });
        return coalInfoVOList;
    }

    public static CoalInfo convertEntity(CoalInfoVO coalInfoVO) {
        return BeanUtil.copyProperties(coalInfoVO, CoalInfo.class);
    }

    public static List<CoalInfo> convertEntity(List<CoalInfoVO> coalInfoVOList) {
        List<CoalInfo> coalInfoList = new ArrayList<>();
        coalInfoVOList.forEach(coalInfoVO -> {
            coalInfoList.add(convertEntity(coalInfoVO));
        });
        return coalInfoList;
    }


}
