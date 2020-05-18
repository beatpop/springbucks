package com.bp.service;

import com.bp.entity.Coffee;
import com.bp.entity.cache.CoffeeCache;
import com.bp.repository.CoffeeRepository;
import com.bp.repository.cache.CoffeeCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author DH
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "coffees")
public class CoffeeService {
    @Resource
    private CoffeeRepository coffeeRepository;

    @Resource
    private CoffeeCacheRepository cacheRepository;

    @CacheEvict
    public void reloadCoffee() {
    }

    /**
     * 查询所有coffee
     * @return
     */
    @Cacheable("coffees")
    public List<Coffee> findAllCoffees() {
        return coffeeRepository.findAll();
    }

    /**
     * 从缓存中读取（不存在则保存到缓存中）
     * @param name
     * @return
     */
    public Optional<Coffee> findCoffeeByNameFromCache(String name) {
        Optional<CoffeeCache> optionalCoffeeCache = cacheRepository.findOneByName(name);
        if (optionalCoffeeCache.isPresent()) {
            CoffeeCache coffeeCache = optionalCoffeeCache.get();
            Coffee coffee = Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            log.info("Coffee {} found in cache.", coffeeCache);
            return Optional.of(coffee);
        } else {
            Optional<Coffee> optionalCoffee = this.findOneByName(name);
            optionalCoffee.ifPresent(coffee -> {
                CoffeeCache coffeeCache = CoffeeCache.builder()
                        .id(coffee.getId())
                        .name(coffee.getName())
                        .price(coffee.getPrice())
                        .build();
                log.info("Save Coffee {} to cache.", coffeeCache);
                cacheRepository.save(coffeeCache);
            });
            return optionalCoffee;
        }
    }

    /**
     * 根据名称获取coffee
     *
     * @param name coffee名称
     * @return
     */
    public Optional<Coffee> findOneByName(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",
                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.EXACT, true));
        Optional<Coffee> coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    /**
     * 新增 coffee
     * @param coffee 需要新增的coffee
     * @return Coffee
     */
    public Coffee addCoffee(Coffee coffee) {
        if (coffee == null) {
            return null;
        }
        return coffeeRepository.save(coffee);
    }
}
