package com.zr.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.SupplierInfoMapper;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.service.ISupplierInfoService;

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

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSupplierInfoByIds(Long[] ids) {
        return supplierInfoMapper.deleteSupplierInfoByIds(ids);
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
