package com.zr.manage.controller.vo;

import com.zr.common.annotation.Excel;
import com.zr.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Odin
 * @Date: 2024/9/8 15:31
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoalInfoVO extends BaseEntity {

    private Long id;

    /** 煤炭类型 */
    private String coalKind;

    /** 规格 */
    private String coalSize;

    /** 价格 */
    private BigDecimal coalPrice;

    /** 产地 */
    private String coalOriginPlace;

    /** 产品描述 */
    private String coalDecs;

    /** 订单库存 */
    private String coalInventory;

    /** 供应商ID */
    private Long coalSupplierId;

    /** 供应商名称 */
    private String supplierName;
    /** 供应商联系人 */
    private String supplierPerson;
    /** 供应商联系方式 */
    private String supplierPhone;

    private List<String> fileUrls;

    private String fileUrl;

}
