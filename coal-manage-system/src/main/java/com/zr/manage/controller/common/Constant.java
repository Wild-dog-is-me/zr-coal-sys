package com.zr.manage.controller.common;

/**
 * @Author: Odin
 * @Date: 2024/9/8 22:46
 * @Description:
 */
public interface Constant {

    // ================= 订单流转状态常量 =================
    // 备货
    public static String ORDER_STATUS_CREATE = "0";

    // 发货
    public static String ORDER_STATUS_SEND = "1";

    // 已收货
    public static String ORDER_STATUS_RCV = "2";


    // ================= 订单支付状态常量 =================

    // 未支付订单
    public static String UN_PAY = "0";

    // 未支付完成
    public static String UN_PAY_FINISH = "1";

    // 已支付订单
    public static String PAY_FINISH = "2";

    // ================= 订单类型常量 =================
    // 采购订单
    public static String PURCHASE_ORDER = "0";

    // 销售订单
    public static String SALE_ORDER = "1";

    // ================= 采购订单信息常量 =================
    public static String PURCHASE_ORDER_PHONE = "0512-6876543";
    public static String PURCHASE_ORDER_ADDRESS = "江苏省南京市浦口区煤炭工业园默认仓库";

}
