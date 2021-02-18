/*
 * Copyright (C) 1997-2020 康成投资（中国）有限公司
 *
 * http://www.rt-mart.com
 *
 * 版权归本公司所有，不得私自使用、拷贝、修改、删除，否则视为侵权
 */

package com.xtt.transaction.strategyChain;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * AbstractStrategyRouter
 *
 * @author dexu.tian
 * @date 2021/1/26
 */
public abstract class AbstractStrategyRouter<T,R> {

    /**
     * default StrategyHandler
     */
    private StrategyHandler <T,R> defaultStrategyHandler = StrategyHandler.DEFAULT_STRATEGY_HANDLER;


    /**
     * 策略映射器
     */
    private StrategyMapper<T,R> strategyMapper;

    /**
     * 子类需要实现的方法，根据不同的参数注册不通的策略处理器
     *
     * @return
     */
    protected abstract StrategyMapper <T,R> registerStrategyMapper();

    /**
     * 根据指定的入参返回对应的策略映射器,并调用处理器执行
     *
     * @param param
     * @return
     */
    public R applyStrategy (T param) {
        StrategyHandler <T,R>  strategyHandler = strategyMapper.get(param);
        if (null == strategyHandler) {
            strategyHandler = defaultStrategyHandler;
        }
        return strategyHandler.apply(param);
    }

    /**
     * chain do execute
     *
     * @param param
     * @param strategyRouterChain
     */
    public R execute(T param,StrategyRouterChain strategyRouterChain) {
        R res = this.applyStrategy(param);
        strategyRouterChain.doChain(param);
        return res;
    }

    @PostConstruct
    private void initStrategyMapper() {
        strategyMapper = registerStrategyMapper();
        Objects.requireNonNull(strategyMapper, "strategyMapper cannot be null");
    }

    /**
     * 策略映射器，根据指定的入参返回对应的策略映射器
     *
     * @param <T> 策略的入参类型
     * @param <R> 策略的返回类型
     */
    public interface StrategyMapper<T,R> {

        /**
         * 根据入参类型 获取对应的策略处理器
         *
         * @param param
         * @return
         */
        StrategyHandler <T,R> get(T param);
    }
}
