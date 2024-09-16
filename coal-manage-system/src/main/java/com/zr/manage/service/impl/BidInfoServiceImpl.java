package com.zr.manage.service.impl;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.common.utils.DateUtils;
import com.zr.common.utils.DictUtils;
import com.zr.manage.domain.CoalInfo;
import com.zr.manage.domain.NoticeInfo;
import com.zr.manage.domain.SupplierInfo;
import com.zr.manage.mapper.SupplierInfoMapper;
import com.zr.manage.service.ICoalInfoService;
import com.zr.manage.service.INoticeInfoService;
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
    @Autowired
    private ICoalInfoService coalInfoService;
    @Autowired
    private INoticeInfoService noticeInfoService;
    @Autowired
    private SupplierInfoMapper supplierInfoMapper;

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
        if (!ObjectUtil.isNull(bidInfo.getBidSupplierId())) {
            LambdaQueryWrapper<SupplierInfo> lqw = new LambdaQueryWrapper<>();
            lqw.eq(SupplierInfo::getSupplierName, bidInfo.getBidSupplierId());
            SupplierInfo supplierInfo = supplierInfoMapper.selectOne(lqw);
            if (!ObjectUtil.isNull(supplierInfo)) {
                bidInfo.setBidSupplierId(String.valueOf(supplierInfo.getId()));
            }
        }
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

    @Override
    public void purchase(Long id) {
        BidInfo bidInfo = bidInfoMapper.selectById(id);
        LambdaQueryWrapper<CoalInfo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CoalInfo::getCoalKind, bidInfo.getBidCoalType());
        CoalInfo one = coalInfoService.getOne(lqw);
        if (!ObjectUtil.isNull(one)) {
            return;
        }
        CoalInfo coalInfo = new CoalInfo();
        coalInfo.setCoalKind(bidInfo.getBidCoalType());
        coalInfo.setCoalSize(bidInfo.getBidCoalSize());
        coalInfo.setCoalPrice(bidInfo.getBidCoalPrice());
        coalInfo.setCoalDecs(bidInfo.getBidRemark());
        coalInfo.setCoalInventory("0");
        coalInfo.setCoalSupplierId(Long.valueOf(bidInfo.getBidSupplierId()));
        coalInfoService.save(coalInfo);

        NoticeInfo noticeInfo = new NoticeInfo();
        noticeInfo.setNoticeType("2");
        noticeInfo.setNoticeTitle("供应商竞价结果公示");
        String coalKind = DictUtils.getDictLabel("coal_kind", bidInfo.getBidCoalType());
        String coalSize = DictUtils.getDictLabel("coal_size", bidInfo.getBidCoalSize());
        String supplierName = supplierInfoMapper.selectById(bidInfo.getBidSupplierId()).getSupplierName();
        String price = String.valueOf(bidInfo.getBidCoalPrice());
        StringBuffer sb = new StringBuffer();
        sb.append("恭喜");
        sb.append(supplierName + "公司对煤炭类型为[");
        sb.append(coalKind + "]-[" + coalSize + "],所提出的竞价" + price);
        sb.append("(元/吨)被批准采购,特此公示,望周知");
        noticeInfo.setNoticeContent(sb.toString());
        noticeInfoService.save(noticeInfo);
    }
}
