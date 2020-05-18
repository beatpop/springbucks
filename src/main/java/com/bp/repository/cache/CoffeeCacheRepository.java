package com.bp.repository.cache;

import com.bp.entity.cache.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 咖啡缓存仓库
 *
 * @author DH
 */
public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long> {
    /**
     * 根据名称获取一条数据
     * @param name 名称
     * @return
     */
    Optional<CoffeeCache> findOneByName(String name);
}
