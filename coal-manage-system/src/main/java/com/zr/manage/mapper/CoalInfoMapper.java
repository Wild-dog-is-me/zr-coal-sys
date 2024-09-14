package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.CoalInfo;

/**
 * 煤炭信息Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface CoalInfoMapper extends BaseMapper<CoalInfo>
{
    /**
     * 查询煤炭信息
     *
     * @param id 煤炭信息主键
     * @return 煤炭信息
     */
    public CoalInfo selectCoalInfoById(Long id);

    /**
     * 查询煤炭信息列表
     *
     * @param coalInfo 煤炭信息
     * @return 煤炭信息集合
     */
    public List<CoalInfo> selectCoalInfoList(CoalInfo coalInfo);

    /**
     * 新增煤炭信息
     *
     * @param coalInfo 煤炭信息
     * @return 结果
     */
    public int insertCoalInfo(CoalInfo coalInfo);

    /**
     * 修改煤炭信息
     *
     * @param coalInfo 煤炭信息
     * @return 结果
     */
    public int updateCoalInfo(CoalInfo coalInfo);

    /**
     * 删除煤炭信息
     *
     * @param id 煤炭信息主键
     * @return 结果
     */
    public int deleteCoalInfoById(Long id);

    /**
     * 批量删除煤炭信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCoalInfoByIds(Long[] ids);
}
