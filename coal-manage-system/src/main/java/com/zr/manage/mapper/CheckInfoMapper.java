package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.CheckInfo;

/**
 * 账单信息Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface CheckInfoMapper extends BaseMapper<CheckInfo>
{
    /**
     * 查询账单信息
     *
     * @param id 账单信息主键
     * @return 账单信息
     */
    public CheckInfo selectCheckInfoById(Long id);

    /**
     * 查询账单信息列表
     *
     * @param checkInfo 账单信息
     * @return 账单信息集合
     */
    public List<CheckInfo> selectCheckInfoList(CheckInfo checkInfo);

    /**
     * 新增账单信息
     *
     * @param checkInfo 账单信息
     * @return 结果
     */
    public int insertCheckInfo(CheckInfo checkInfo);

    /**
     * 修改账单信息
     *
     * @param checkInfo 账单信息
     * @return 结果
     */
    public int updateCheckInfo(CheckInfo checkInfo);

    /**
     * 删除账单信息
     *
     * @param id 账单信息主键
     * @return 结果
     */
    public int deleteCheckInfoById(Long id);

    /**
     * 批量删除账单信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckInfoByIds(Long[] ids);
}
