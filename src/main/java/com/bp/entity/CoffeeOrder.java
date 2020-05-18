package com.bp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 订单
 * @author DH
 */
@Entity
@Table(name = "t_order")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder extends BaseEntity implements Serializable {

    /**
     * 顾客
     */
    @Column(name = "`customer`")
    private String customer;

    /**
     * 咖啡
     */
    @ManyToMany
    @JoinTable(name = "t_order_coffee")
    @OrderBy("id")
    private List<Coffee> items;

    /**
     * 订单状态
     */
    @Enumerated
    @Column(name = "`state`", nullable = false)
    private OrderState state;

    /**
     * 价格
     */
    @Column(name = "`price`")
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}
