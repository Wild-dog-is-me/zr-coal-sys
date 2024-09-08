package com.zr.manage.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zr.manage.domain.BidInfo;

/**
 * 竞价采购Mapper接口
 *
 * @author zr
 * @date 2024-09-08
 */
public interface BidInfoMapper extends BaseMapper<BidInfo>
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
     * 删除竞价采购
     *
     * @param id 竞价采购主键
     * @return 结果
     */
    public int deleteBidInfoById(Long id);

    /**
     * 批量删除竞价采购
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBidInfoByIds(Long[] ids);
}
