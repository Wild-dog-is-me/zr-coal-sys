package com.zr.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zr.manage.mapper.BidInfoMapper;
import com.zr.manage.domain.BidInfo;
import com.zr.manage.service.IBidInfoService;

/**
 * 竞价采购Service业务层处理
 *
 * @author zr
 * @date 2024-09-08
 */
@Service
public class BidInfoServiceImpl extends ServiceImpl<BidInfoMapper,BidInfo> implements IBidInfoService
{
    @Autowired
    private BidInfoMapper bidInfoMapper;

    /**
     * 查询竞价采购
     *
     * @param id 竞价采购主键
     * @return 竞价采购
     */
    @Override
    public BidInfo selectBidInfoById(Long id)
    {
        return bidInfoMapper.selectBidInfoById(id);
    }

    /**
     * 查询竞价采购列表
     *
     * @param bidInfo 竞价采购
     * @return 竞价采购
     */
    @Override
    public List<BidInfo> selectBidInfoList(BidInfo bidInfo)
    {
        return bidInfoMapper.selectBidInfoList(bidInfo);
    }

    /**
     * 新增竞价采购
     *
     * @param bidInfo 竞价采购
     * @return 结果
     */
    @Override
    public int insertBidInfo(BidInfo bidInfo)
    {
        bidInfo.setCreateTime(DateUtils.getNowDate());
        return bidInfoMapper.insertBidInfo(bidInfo);
    }

    /**
     * 修改竞价采购
     *
     * @param bidInfo 竞价采购
     * @return 结果
     */
    @Override
    public int updateBidInfo(BidInfo bidInfo)
    {
        bidInfo.setUpdateTime(DateUtils.getNowDate());
        return bidInfoMapper.updateBidInfo(bidInfo);
    }

    /**
     * 批量删除竞价采购
     *
     * @param ids 需要删除的竞价采购主键
     * @return 结果
     */
    @Override
    public int deleteBidInfoByIds(Long[] ids)
    {
        return bidInfoMapper.deleteBidInfoByIds(ids);
    }

    /**
     * 删除竞价采购信息
     *
     * @param id 竞价采购主键
     * @return 结果
     */
    @Override
    public int deleteBidInfoById(Long id)
    {
        return bidInfoMapper.deleteBidInfoById(id);
    }
}
