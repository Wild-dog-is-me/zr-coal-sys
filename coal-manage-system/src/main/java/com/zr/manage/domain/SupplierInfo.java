package com.zr.manage.domain;

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
 * 供应商对象 zr_supplier_info
 *
 * @author zr
 * @date 2024-09-08
 */

@Accessors(chain = true)
@Builder
@TableName("zr_supplier_info")
@AllArgsConstructor
@NoArgsConstructor
public class SupplierInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 供应商简介 */
    @Excel(name = "供应商简介")
    private String supplierIntro;

    /** 供应商联系人 */
    @Excel(name = "供应商联系人")
    private String supplierPerson;

    /** 供应商联系电话 */
    @Excel(name = "供应商联系电话")
    private String supplierPhone;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName()
    {
        return supplierName;
    }
    public void setSupplierIntro(String supplierIntro)
    {
        this.supplierIntro = supplierIntro;
    }

    public String getSupplierIntro()
    {
        return supplierIntro;
    }
    public void setSupplierPerson(String supplierPerson)
    {
        this.supplierPerson = supplierPerson;
    }

    public String getSupplierPerson()
    {
        return supplierPerson;
    }
    public void setSupplierPhone(String supplierPhone)
    {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierPhone()
    {
        return supplierPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("supplierName", getSupplierName())
            .append("supplierIntro", getSupplierIntro())
            .append("supplierPerson", getSupplierPerson())
            .append("supplierPhone", getSupplierPhone())
            .toString();
    }
}
