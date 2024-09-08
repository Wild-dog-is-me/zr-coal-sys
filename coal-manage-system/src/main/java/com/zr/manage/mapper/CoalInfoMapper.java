package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.CoalInfo;

/**
 * 煤矿信息Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface CoalInfoMapper extends BaseMapper<CoalInfo>
{
    /**
     * 查询煤矿信息
     *
     * @param id 煤矿信息主键
     * @return 煤矿信息
     */
    public CoalInfo selectCoalInfoById(Long id);

    /**
     * 查询煤矿信息列表
     *
     * @param coalInfo 煤矿信息
     * @return 煤矿信息集合
     */
    public List<CoalInfo> selectCoalInfoList(CoalInfo coalInfo);

    /**
     * 新增煤矿信息
     *
     * @param coalInfo 煤矿信息
     * @return 结果
     */
    public int insertCoalInfo(CoalInfo coalInfo);

    /**
     * 修改煤矿信息
     *
     * @param coalInfo 煤矿信息
     * @return 结果
     */
    public int updateCoalInfo(CoalInfo coalInfo);

    /**
     * 删除煤矿信息
     *
     * @param id 煤矿信息主键
     * @return 结果
     */
    public int deleteCoalInfoById(Long id);

    /**
     * 批量删除煤矿信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCoalInfoByIds(Long[] ids);
}
