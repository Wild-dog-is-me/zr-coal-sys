package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.controller.vo.CoalInfoVO;
import com.zr.manage.domain.CoalInfo;

/**
 * 煤炭信息Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface ICoalInfoService extends IService<CoalInfo>
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
    public List<CoalInfoVO> selectCoalInfoList(CoalInfo coalInfo);

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
     * 批量删除煤炭信息
     *
     * @param ids 需要删除的煤炭信息主键集合
     * @return 结果
     */
    public int deleteCoalInfoByIds(Long[] ids);

    /**
     * 删除煤炭信息信息
     *
     * @param id 煤炭信息主键
     * @return 结果
     */
    public int deleteCoalInfoById(Long id);
}
