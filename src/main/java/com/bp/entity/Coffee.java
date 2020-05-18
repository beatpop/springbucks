package com.bp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Coffee 咖啡实体（菜单）
 * @author DH
 */
@Entity
@Table(name = "t_coffee")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee extends BaseEntity implements Serializable {
    /**
     * 咖啡名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 价格
     */
    @Column(name = "`price`")
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}
