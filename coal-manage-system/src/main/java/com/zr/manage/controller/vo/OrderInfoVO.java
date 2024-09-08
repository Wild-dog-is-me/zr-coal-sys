package com.zr.manage.controller.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zr.common.annotation.Excel;
import com.zr.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Odin
 * @Date: 2024/9/8 23:30
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVO extends BaseEntity {

    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private String orderNo;

    private String orderCoalId;

    private String coalKind;

    private String coalSize;

    private String orderStatus;

    private String orderPayStatus;

    private String orderPrice;

    private String orderRemark;

    private String orderBuyerName;

    private String orderBuyerPhone;

    private String orderBuyerAddress;

    private String orderHolderUserId;

    private String orderHolderUserName;
}
