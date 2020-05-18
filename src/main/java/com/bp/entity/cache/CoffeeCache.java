package com.bp.entity.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * 咖啡缓存
 * @author DH
 */
@RedisHash(value = "springbucks-coffees", timeToLive = 5000)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeCache {
    @Id
    private Long id;

    /**
     * 名称
     */
    @Indexed
    private String name;

    /**
     * 价格
     */
    private Money price;
}
