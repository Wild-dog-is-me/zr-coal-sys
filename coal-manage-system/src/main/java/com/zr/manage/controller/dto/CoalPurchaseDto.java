package com.zr.manage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Odin
 * @Date: 2024/9/8 23:02
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoalPurchaseDto {

    /**
     * 煤炭ID
     */
    private Long coalId;

    /**
     * 下单吨数
     */
    private String orderTon;

    /**
     * 供应商
     */
    private Long supplierId;
}
