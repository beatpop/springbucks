package com.bp.repository;

import com.bp.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 咖啡（菜单）
 * @author DH
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
