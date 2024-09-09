package com.zr.manage.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zr.common.annotation.Excel;
import com.zr.common.core.domain.BaseEntity;

/**
 * 竞价采购对象 zr_bid_info
 *
 * @author zr
 * @date 2024-09-08
 */

@Accessors(chain = true)
@Builder
@TableName("zr_bid_info")
@AllArgsConstructor
@NoArgsConstructor
public class BidInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 产品名称 */
    /**
     * 1:焦煤、2:肥煤、3:无烟煤、4:瘦煤、5:弱粘结煤、6:气煤、7:长焰煤、8:贫煤
     */
    @Excel(name = "产品名称")
    private String bidName;

    /** 类型 */
    /**
     * 1:大块、2:中块、3:小块
     */
    @Excel(name = "类型")
    private String bidCoalType;

    /** 规格 */
    @Excel(name = "规格")
    private String bidCoalSize;

    /** 竞价价格 */
    @Excel(name = "竞价价格")
    private BigDecimal bidCoalPrice;

    /** 竞价公司ID */
    @Excel(name = "竞价公司ID")
    private String bidSupplierId;

    /** 竞价备注 */
    @Excel(name = "竞价备注")
    private String bidRemark;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBidName(String bidName)
    {
        this.bidName = bidName;
    }

    public String getBidName()
    {
        return bidName;
    }
    public void setBidCoalType(String bidCoalType)
    {
        this.bidCoalType = bidCoalType;
    }

    public String getBidCoalType()
    {
        return bidCoalType;
    }
    public void setBidCoalSize(String bidCoalSize)
    {
        this.bidCoalSize = bidCoalSize;
    }

    public String getBidCoalSize()
    {
        return bidCoalSize;
    }
    public void setBidCoalPrice(BigDecimal bidCoalPrice)
    {
        this.bidCoalPrice = bidCoalPrice;
    }

    public BigDecimal getBidCoalPrice()
    {
        return bidCoalPrice;
    }
    public void setBidSupplierId(String bidSupplierId)
    {
        this.bidSupplierId = bidSupplierId;
    }

    public String getBidSupplierId()
    {
        return bidSupplierId;
    }
    public void setBidRemark(String bidRemark)
    {
        this.bidRemark = bidRemark;
    }

    public String getBidRemark()
    {
        return bidRemark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("bidName", getBidName())
            .append("bidCoalType", getBidCoalType())
            .append("bidCoalSize", getBidCoalSize())
            .append("bidCoalPrice", getBidCoalPrice())
            .append("bidSupplierId", getBidSupplierId())
            .append("bidRemark", getBidRemark())
            .toString();
    }
}
