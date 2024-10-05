package com.zr.manage.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.exception.base.BaseException;
import com.zr.common.utils.DateUtils;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.mapper.CheckInfoMapper;
import com.zr.manage.mapper.CoalInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.SupplierInfoMapper;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.service.ISupplierInfoService;

import javax.annotation.Resource;

/**
 * 供应商Service业务层处理
 *
 * @author zr
 * @date 2024-09-08
 */
@Service
public class SupplierInfoServiceImpl extends ServiceImpl<SupplierInfoMapper, SupplierInfo> implements ISupplierInfoService {
    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

    @Resource

    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    @Override
    public SupplierInfo selectSupplierInfoById(Long id) {
        return supplierInfoMapper.selectSupplierInfoById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param supplierInfo 供应商
     * @return 供应商
     */
    @Override
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo) {
        return supplierInfoMapper.selectSupplierInfoList(supplierInfo);
    }

    /**
     * 新增供应商
     *
     * @param supplierInfo 供应商
     * @return 结果
     */
    @Override
    public int insertSupplierInfo(SupplierInfo supplierInfo) {
        supplierInfo.setCreateTime(DateUtils.getNowDate());
        return supplierInfoMapper.insertSupplierInfo(supplierInfo);
    }

    /**
     * 修改供应商
     *
     * @param supplierInfo 供应商
     * @return 结果
     */
    @Override
    public int updateSupplierInfo(SupplierInfo supplierInfo) {
        supplierInfo.setUpdateTime(DateUtils.getNowDate());
        return supplierInfoMapper.updateSupplierInfo(supplierInfo);
    }

    @Resource
    private CheckInfoMapper checkInfoMapper;
    @Resource
    private CoalInfoMapper coalInfoMapper;

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoByIds(Long[] ids) {
        Long id = ids[0];
        LambdaQueryWrapper<CoalInfo> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(CoalInfo::getCoalSupplierId, id);
        List<CoalInfo> coalInfos = coalInfoMapper.selectList(lqw1);
        LambdaQueryWrapper<CheckInfo> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(CheckInfo::getCheckHolderUserId, id);
        List<CheckInfo> checkInfos = checkInfoMapper.selectList(lqw2);
        if (ObjectUtil.isNull(checkInfos) && ObjectUtil.isNull(coalInfos)) {
            return supplierInfoMapper.deleteSupplierInfoByIds(ids);
        } else {
            throw new BaseException("当前供应商仍有商品或订单未处理");
        }
    }

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoById(Long id) {
        return supplierInfoMapper.deleteSupplierInfoById(id);
    }
}
