package com.bp.service;

import com.bp.common.utils.MoneyTransferUtil;
import com.bp.entity.Coffee;
import com.bp.entity.CoffeeOrder;
import com.bp.entity.OrderState;
import com.bp.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 订单管理
 * @author DH
 */
@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
    @Resource
    private CoffeeOrderRepository coffeeOrderRepository;

    /**
     * 新建订单
     *
     * @param customer 顾客名称
     * @param coffees 下单的coffee列表
     * @return CoffeeOrder
     */
    public CoffeeOrder createCoffeeOrder(String customer, Coffee ...coffees) {
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .customer(customer)
                .state(OrderState.INIT)
                .items(new ArrayList<Coffee>(Arrays.asList(coffees)))
                .price(MoneyTransferUtil.jodaMoneyChange("CNY", (long) (coffees.length * 3200)))
                .build();
        CoffeeOrder orderSaved = coffeeOrderRepository.save(coffeeOrder);
        log.info("New CoffeeOrder: {}" + orderSaved.toString());
        return orderSaved;
    }

    /**
     * 更新订单状态
     * @param coffeeOrder 需要更新的订单
     * @param updateState 需要更新的订单状态
     * @return boolean
     */
    public boolean updateCoffeeOrder(CoffeeOrder coffeeOrder, OrderState updateState) {
        if (coffeeOrder.getId() == null || "".equals(coffeeOrder.getId().toString())) {
            return false;
        }
        if (updateState.compareTo(coffeeOrder.getState()) < 0) {
            return false;
        }

        CoffeeOrder selOrder = coffeeOrderRepository.findById(coffeeOrder.getId()).orElse(null);
        if (selOrder != null) {
            coffeeOrder.setState(updateState);
            coffeeOrderRepository.save(coffeeOrder);
            log.info("Update CoffeeOrder: {}" + coffeeOrder.toString());
            return true;
        }

        return false;
    }
}
