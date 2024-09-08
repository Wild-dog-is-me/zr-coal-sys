package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.domain.CheckInfo;

/**
 * 账单信息Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface ICheckInfoService extends IService<CheckInfo>
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
     * 批量删除账单信息
     *
     * @param ids 需要删除的账单信息主键集合
     * @return 结果
     */
    public int deleteCheckInfoByIds(Long[] ids);

    /**
     * 删除账单信息信息
     *
     * @param id 账单信息主键
     * @return 结果
     */
    public int deleteCheckInfoById(Long id);
}
