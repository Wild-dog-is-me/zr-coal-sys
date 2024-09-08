package com.zr.manage.service;

import java.util.List;

import com.zr.manage.controller.vo.CoalInfoVO;
import com.zr.manage.domain.CoalInfo;

/**
 * 煤矿信息Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface ICoalInfoService
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
    public List<CoalInfoVO> selectCoalInfoList(CoalInfo coalInfo);

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
     * 批量删除煤矿信息
     *
     * @param ids 需要删除的煤矿信息主键集合
     * @return 结果
     */
    public int deleteCoalInfoByIds(Long[] ids);

    /**
     * 删除煤矿信息信息
     *
     * @param id 煤矿信息主键
     * @return 结果
     */
    public int deleteCoalInfoById(Long id);
}
