package com.zr.manage.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Odin
 * @Date: 2024/9/8 22:35
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoalOrderDto {

    /**
     * 煤炭物料ID
     */
    private Long coalId;

    /**
     * 下单吨数
     */
    private String orderTon;

    /**
     * 下单人
     */
    private String orderBuyName;

    /**
     * 下单人手机号
     */
    private String orderBuyerPhone;

    /**
     * 收货地址
     */
    private String orderRevAddress;

}
