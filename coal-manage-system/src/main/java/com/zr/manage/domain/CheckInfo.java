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
 * 账单信息对象 zr_check_info
 *
 * @author zr
 * @date 2024-09-08
 */

@Accessors(chain = true)
@Builder
@TableName("zr_check_info")
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long checkOrderId;

    /** 收款金额 */
    @Excel(name = "收款金额")
    private BigDecimal checkRevAmt;

    /** 支付人 */
    @Excel(name = "支付人")
    private String checkPayName;

    /** 支付人联系方式 */
    @Excel(name = "支付人联系方式")
    private String checkPayPhone;

    /** 支付备注 */
    @Excel(name = "支付备注")
    private String checkPayRemark;

    /** 收款人 */
    @Excel(name = "收款人")
    private Long checkHolderUserId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCheckOrderId(Long checkOrderId)
    {
        this.checkOrderId = checkOrderId;
    }

    public Long getCheckOrderId()
    {
        return checkOrderId;
    }
    public void setCheckRevAmt(BigDecimal checkRevAmt)
    {
        this.checkRevAmt = checkRevAmt;
    }

    public BigDecimal getCheckRevAmt()
    {
        return checkRevAmt;
    }
    public void setCheckPayName(String checkPayName)
    {
        this.checkPayName = checkPayName;
    }

    public String getCheckPayName()
    {
        return checkPayName;
    }
    public void setCheckPayPhone(String checkPayPhone)
    {
        this.checkPayPhone = checkPayPhone;
    }

    public String getCheckPayPhone()
    {
        return checkPayPhone;
    }
    public void setCheckPayRemark(String checkPayRemark)
    {
        this.checkPayRemark = checkPayRemark;
    }

    public String getCheckPayRemark()
    {
        return checkPayRemark;
    }
    public void setCheckHolderUserId(Long checkHolderUserId)
    {
        this.checkHolderUserId = checkHolderUserId;
    }

    public Long getCheckHolderUserId()
    {
        return checkHolderUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("checkOrderId", getCheckOrderId())
            .append("checkRevAmt", getCheckRevAmt())
            .append("checkPayName", getCheckPayName())
            .append("checkPayPhone", getCheckPayPhone())
            .append("checkPayRemark", getCheckPayRemark())
            .append("checkHolderUserId", getCheckHolderUserId())
            .toString();
    }
}
