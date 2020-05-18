package com.bp.springbucks;

import com.bp.common.convert.BytesToMoneyConverter;
import com.bp.common.convert.MoneyToBytesConverter;
import com.bp.common.utils.MoneyTransferUtil;
import com.bp.entity.Coffee;
import com.bp.entity.CoffeeOrder;
import com.bp.repository.CoffeeRepository;
import com.bp.repository.cache.CoffeeCacheRepository;
import com.bp.service.CoffeeOrderService;
import com.bp.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

/**
 * @author DH
 */

@ComponentScan(basePackages = "com.bp")
@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EntityScan(basePackages = "com.bp.entity")
@EnableJpaRepositories(basePackages = "com.bp.repository")
@EnableCaching(proxyTargetClass = true)
@EnableRedisRepositories(basePackages = "com.bp.repository.cache")
public class SpringBucksApplication implements CommandLineRunner {

    @Bean("lettuceCustomizer")
    public LettuceClientConfigurationBuilderCustomizer lettuceCustomizer() {
        return clientConfigurationBuilder -> clientConfigurationBuilder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Bean("redisCustomConversions")
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(MoneyToBytesConverter.class, BytesToMoneyConverter.class));
    }

    @Resource
    private CoffeeService coffeeService;

    @Resource
    private CoffeeRepository coffeeRepository;

    @Resource
    private CoffeeOrderService orderService;

    @Resource
    private CoffeeCacheRepository coffeeCacheRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBucksApplication.class, args);
        System.out.println("Server started: http://localhost:8888");
    }

    @Override
    public void run(String... args) throws Exception {
        // initAddCoffee();
        initFindCoffee();
        // 测试缓存
        log.info("Count: {}", coffeeService.findAllCoffees().size());
        for (int i = 0; i < 5; i++) {
            log.info("Reading from cache.");
            coffeeService.findAllCoffees();
        }
//        coffeeService.reloadCoffee();
        Thread.sleep(5000);
        log.info("Reading after refresh.");
        coffeeService.findAllCoffees().forEach(c -> log.info("Coffee {}", c.getName()));

        // Order
        //initAddOrder();
    }

    private void initAddCoffee() {
        Coffee coffee = coffeeService.addCoffee(new Coffee("KabQnuo", Money.of(CurrencyUnit.of("CNY"), 3022)));
        log.info("Add Coffee: {}" + coffee.toString());
    }

    private void initFindCoffee() {
        log.info("Find By Name: {}", coffeeService.findOneByName("KabQnuo").toString());
    }

    private void initAddOrder() {
        Coffee coffee = coffeeRepository.findById(6L).orElse(null);
        if (coffee != null) {
//            CoffeeOrder savedOrder = CoffeeOrder.builder()
//                    .customer("张三丰")
//                    .items(Collections.singletonList(coffee))
//                    .price(MoneyTransferUtil.jodaMoneyChange("CNY", 3022L))
//                    .build();

            CoffeeOrder savedOrder = orderService.createCoffeeOrder("张三丰", coffee);
            log.info("Add CoffeeOrder: {}", savedOrder);
        }
    }
}
