package com.bp.common.utils;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * joda Money转换
 * @author DH
 */
public class MoneyTransferUtil {
    /**
     * 金额数据转为joda.Money类型
     * @param currencyUnit 转换单位（如 CNY）
     * @param amount 金额（两位小数*100得到的Long）
     * @return Money
     */
    public static Money jodaMoneyChange(String currencyUnit, Long amount) {
        if (!"".equals(currencyUnit) && amount != null) {
            return Money.of(CurrencyUnit.of(currencyUnit), amount);
        }
        return null;
    }
}
