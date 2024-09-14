package com.zr.manage.controller.vo;

import com.zr.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Odin
 * @Date: 2024/9/12 23:33
 * @Description:
 */

@Data
public class BidInfoVO extends BaseEntity {

    private Long id;

    private String bidName;

    private String bidCoalType;

    private String bidCoalSize;

    private BigDecimal bidCoalPrice;

    private String bidSupplierId;

    private String bidRemark;

    private String bidSupplierName;
}
