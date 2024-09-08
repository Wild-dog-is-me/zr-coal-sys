package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.SupplierInfo;

/**
 * 供应商Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface SupplierInfoMapper extends BaseMapper<SupplierInfo>
{
    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    public SupplierInfo selectSupplierInfoById(Long id);

    /**
     * 查询供应商列表
     *
     * @param supplierInfo 供应商
     * @return 供应商集合
     */
    public List<SupplierInfo> selectSupplierInfoList(SupplierInfo supplierInfo);

    /**
     * 新增供应商
     *
     * @param supplierInfo 供应商
     * @return 结果
     */
    public int insertSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 修改供应商
     *
     * @param supplierInfo 供应商
     * @return 结果
     */
    public int updateSupplierInfo(SupplierInfo supplierInfo);

    /**
     * 删除供应商
     *
     * @param id 供应商主键
     * @return 结果
     */
    public int deleteSupplierInfoById(Long id);

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierInfoByIds(Long[] ids);
}
