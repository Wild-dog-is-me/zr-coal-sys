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
 * 煤炭信息对象 zr_coal_info
 *
 * @author zr
 * @date 2024-09-08
 */

@Accessors(chain = true)
@Builder
@TableName("zr_coal_info")
@AllArgsConstructor
@NoArgsConstructor
public class CoalInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 煤炭类型 */
    @Excel(name = "煤炭类型")
    private String coalKind;

    /** 规格 */
    @Excel(name = "规格")
    private String coalSize;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal coalPrice;

    /** 产地 */
    @Excel(name = "产地")
    private String coalOriginPlace;

    /** 产品描述 */
    @Excel(name = "产品描述")
    private String coalDecs;

    /** 订单库存 */
    @Excel(name = "订单库存")
    private String coalInventory;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long coalSupplierId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCoalKind(String coalKind)
    {
        this.coalKind = coalKind;
    }

    public String getCoalKind()
    {
        return coalKind;
    }
    public void setCoalSize(String coalSize)
    {
        this.coalSize = coalSize;
    }

    public String getCoalSize()
    {
        return coalSize;
    }
    public void setCoalPrice(BigDecimal coalPrice)
    {
        this.coalPrice = coalPrice;
    }

    public BigDecimal getCoalPrice()
    {
        return coalPrice;
    }
    public void setCoalOriginPlace(String coalOriginPlace)
    {
        this.coalOriginPlace = coalOriginPlace;
    }

    public String getCoalOriginPlace()
    {
        return coalOriginPlace;
    }
    public void setCoalDecs(String coalDecs)
    {
        this.coalDecs = coalDecs;
    }

    public String getCoalDecs()
    {
        return coalDecs;
    }
    public void setCoalInventory(String coalInventory)
    {
        this.coalInventory = coalInventory;
    }

    public String getCoalInventory()
    {
        return coalInventory;
    }
    public void setCoalSupplierId(Long coalSupplierId)
    {
        this.coalSupplierId = coalSupplierId;
    }

    public Long getCoalSupplierId()
    {
        return coalSupplierId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("coalKind", getCoalKind())
                .append("coalSize", getCoalSize())
                .append("coalPrice", getCoalPrice())
                .append("coalOriginPlace", getCoalOriginPlace())
                .append("coalDecs", getCoalDecs())
                .append("coalInventory", getCoalInventory())
                .append("coalSupplierId", getCoalSupplierId())
                .toString();
    }
}
