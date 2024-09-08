package com.zr.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import com.zr.manage.controller.vo.CoalInfoVO;
import com.zr.manage.convert.CoalInfoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.CoalInfoMapper;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.service.ICoalInfoService;

/**
 * 煤矿信息Service业务层处理
 *
 * @author zr
 * @date 2024-09-08
 */
@Service
public class CoalInfoServiceImpl extends ServiceImpl<CoalInfoMapper,CoalInfo> implements ICoalInfoService
{
    @Autowired
    private CoalInfoMapper coalInfoMapper;

    /**
     * 查询煤矿信息
     *
     * @param id 煤矿信息主键
     * @return 煤矿信息
     */
    @Override
    public CoalInfo selectCoalInfoById(Long id)
    {
        return coalInfoMapper.selectCoalInfoById(id);
    }

    /**
     * 查询煤矿信息列表
     *
     * @param coalInfo 煤矿信息
     * @return 煤矿信息
     */
    @Override
    public List<CoalInfoVO> selectCoalInfoList(CoalInfo coalInfo)
    {
        return CoalInfoConvert.convertVO(coalInfoMapper.selectCoalInfoList(coalInfo));
    }

    /**
     * 新增煤矿信息
     *
     * @param coalInfo 煤矿信息
     * @return 结果
     */
    @Override
    public int insertCoalInfo(CoalInfo coalInfo)
    {
        coalInfo.setCoalInventory("0");
        coalInfo.setCreateTime(DateUtils.getNowDate());
        return coalInfoMapper.insertCoalInfo(coalInfo);
    }

    /**
     * 修改煤矿信息
     *
     * @param coalInfo 煤矿信息
     * @return 结果
     */
    @Override
    public int updateCoalInfo(CoalInfo coalInfo)
    {
        coalInfo.setUpdateTime(DateUtils.getNowDate());
        return coalInfoMapper.updateCoalInfo(coalInfo);
    }

    /**
     * 批量删除煤矿信息
     *
     * @param ids 需要删除的煤矿信息主键
     * @return 结果
     */
    @Override
    public int deleteCoalInfoByIds(Long[] ids)
    {
        return coalInfoMapper.deleteCoalInfoByIds(ids);
    }

    /**
     * 删除煤矿信息信息
     *
     * @param id 煤矿信息主键
     * @return 结果
     */
    @Override
    public int deleteCoalInfoById(Long id)
    {
        return coalInfoMapper.deleteCoalInfoById(id);
    }
}
