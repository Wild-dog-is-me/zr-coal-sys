package com.zr.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zr.manage.domain.BidInfo;

/**
 * 竞价采购Service接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface IBidInfoService extends IService<BidInfo>
{
    /**
     * 查询竞价采购
     *
     * @param id 竞价采购主键
     * @return 竞价采购
     */
    public BidInfo selectBidInfoById(Long id);

    /**
     * 查询竞价采购列表
     *
     * @param bidInfo 竞价采购
     * @return 竞价采购集合
     */
    public List<BidInfo> selectBidInfoList(BidInfo bidInfo);

    /**
     * 新增竞价采购
     *
     * @param bidInfo 竞价采购
     * @return 结果
     */
    public int insertBidInfo(BidInfo bidInfo);

    /**
     * 修改竞价采购
     *
     * @param bidInfo 竞价采购
     * @return 结果
     */
    public int updateBidInfo(BidInfo bidInfo);

    /**
     * 批量删除竞价采购
     *
     * @param ids 需要删除的竞价采购主键集合
     * @return 结果
     */
    public int deleteBidInfoByIds(Long[] ids);

    /**
     * 删除竞价采购信息
     *
     * @param id 竞价采购主键
     * @return 结果
     */
    public int deleteBidInfoById(Long id);
}
