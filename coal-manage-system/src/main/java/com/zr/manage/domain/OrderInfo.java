package com.zr.manage.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zr.common.annotation.Excel;
import com.zr.common.core.domain.BaseEntity;

/**
 * 订单信息对象 zr_order_info
 *
 * @author zr
 * @date 2024-09-08
 */

@Accessors(chain = true)
@Builder
@TableName("zr_order_info")
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 下单物料 */
    @Excel(name = "下单物料")
    private Long orderCoalId;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private String orderPayStatus;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal orderPrice;

    /** 订单备注 */
    @Excel(name = "订单备注")
    private String orderRemark;

    /** 下单人 */
    @Excel(name = "下单人")
    private String orderBuyerName;

    /** 下单人手机号 */
    @Excel(name = "下单人手机号")
    private String orderBuyerPhone;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String orderBuyerAddress;

    /** 订单添加人ID */
    @Excel(name = "订单添加人ID")
    private Long orderHolderUserId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }
    public void setOrderCoalId(Long orderCoalId)
    {
        this.orderCoalId = orderCoalId;
    }

    public Long getOrderCoalId()
    {
        return orderCoalId;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    public void setOrderPayStatus(String orderPayStatus)
    {
        this.orderPayStatus = orderPayStatus;
    }

    public String getOrderPayStatus()
    {
        return orderPayStatus;
    }
    public void setOrderPrice(BigDecimal orderPrice)
    {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderPrice()
    {
        return orderPrice;
    }
    public void setOrderRemark(String orderRemark)
    {
        this.orderRemark = orderRemark;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }
    public void setOrderBuyerName(String orderBuyerName)
    {
        this.orderBuyerName = orderBuyerName;
    }

    public String getOrderBuyerName()
    {
        return orderBuyerName;
    }
    public void setOrderBuyerPhone(String orderBuyerPhone)
    {
        this.orderBuyerPhone = orderBuyerPhone;
    }

    public String getOrderBuyerPhone()
    {
        return orderBuyerPhone;
    }
    public void setOrderBuyerAddress(String orderBuyerAddress)
    {
        this.orderBuyerAddress = orderBuyerAddress;
    }

    public String getOrderBuyerAddress()
    {
        return orderBuyerAddress;
    }
    public void setOrderHolderUserId(Long orderHolderUserId)
    {
        this.orderHolderUserId = orderHolderUserId;
    }

    public Long getOrderHolderUserId()
    {
        return orderHolderUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("orderNo", getOrderNo())
            .append("orderCoalId", getOrderCoalId())
            .append("orderStatus", getOrderStatus())
            .append("orderPayStatus", getOrderPayStatus())
            .append("orderPrice", getOrderPrice())
            .append("orderRemark", getOrderRemark())
            .append("orderBuyerName", getOrderBuyerName())
            .append("orderBuyerPhone", getOrderBuyerPhone())
            .append("orderBuyerAddress", getOrderBuyerAddress())
            .append("orderHolderUserId", getOrderHolderUserId())
            .toString();
    }
}
