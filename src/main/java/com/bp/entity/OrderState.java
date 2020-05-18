package com.bp.entity;

/**
 * 订单状态枚举类型
 * @author DH
 */
public enum  OrderState {
    /**
     * 初始
     */
    INIT,
    /**
     * 已支付
     */
    PAID,
    /**
     * 制造中
     */
    BREWING,
    /**
     * 制造完成
     */
    BREWED,
    /**
     * 已取
     */
    TAKEN,
    /**
     * 已取消
     */
    CANCELLED
}
