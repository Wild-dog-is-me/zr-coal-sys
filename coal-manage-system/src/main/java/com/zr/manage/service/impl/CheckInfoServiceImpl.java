package com.zr.manage.service.impl;

import java.util.List;
import com.zr.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.CheckInfoMapper;
import com.zr.manage.domain.CheckInfo;
import com.zr.manage.service.ICheckInfoService;

/**
 * 账单信息Service业务层处理
 * 
 * @author zr
 * @date 2024-09-08
 */
@Service
public class CheckInfoServiceImpl implements ICheckInfoService 
{
    @Autowired
    private CheckInfoMapper checkInfoMapper;

    /**
     * 查询账单信息
     * 
     * @param id 账单信息主键
     * @return 账单信息
     */
    @Override
    public CheckInfo selectCheckInfoById(Long id)
    {
        return checkInfoMapper.selectCheckInfoById(id);
    }

    /**
     * 查询账单信息列表
     * 
     * @param checkInfo 账单信息
     * @return 账单信息
     */
    @Override
    public List<CheckInfo> selectCheckInfoList(CheckInfo checkInfo)
    {
        return checkInfoMapper.selectCheckInfoList(checkInfo);
    }

    /**
     * 新增账单信息
     * 
     * @param checkInfo 账单信息
     * @return 结果
     */
    @Override
    public int insertCheckInfo(CheckInfo checkInfo)
    {
        checkInfo.setCreateTime(DateUtils.getNowDate());
        return checkInfoMapper.insertCheckInfo(checkInfo);
    }

    /**
     * 修改账单信息
     * 
     * @param checkInfo 账单信息
     * @return 结果
     */
    @Override
    public int updateCheckInfo(CheckInfo checkInfo)
    {
        checkInfo.setUpdateTime(DateUtils.getNowDate());
        return checkInfoMapper.updateCheckInfo(checkInfo);
    }

    /**
     * 批量删除账单信息
     * 
     * @param ids 需要删除的账单信息主键
     * @return 结果
     */
    @Override
    public int deleteCheckInfoByIds(Long[] ids)
    {
        return checkInfoMapper.deleteCheckInfoByIds(ids);
    }

    /**
     * 删除账单信息信息
     * 
     * @param id 账单信息主键
     * @return 结果
     */
    @Override
    public int deleteCheckInfoById(Long id)
    {
        return checkInfoMapper.deleteCheckInfoById(id);
    }
}
