package com.bp.common.convert;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.nio.charset.StandardCharsets;

/**
 * Money类型转换为bytes类型
 * @author DH
 */
@WritingConverter
public class MoneyToBytesConverter implements Converter<Money, byte[]> {
    @Override
    public byte[] convert(Money money) {
        String amountStr = Long.toString(money.getAmountMinorLong());
        return amountStr.getBytes(StandardCharsets.UTF_8);
    }
}
