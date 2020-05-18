package com.bp.repository;

import com.bp.entity.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单
 * @author DH
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
