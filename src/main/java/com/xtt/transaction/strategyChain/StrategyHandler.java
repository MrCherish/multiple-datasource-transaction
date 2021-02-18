/*
 * Copyright (C) 1997-2020 康成投资（中国）有限公司
 *
 * http://www.rt-mart.com
 *
 * 版权归本公司所有，不得私自使用、拷贝、修改、删除，否则视为侵权
 */

package com.xtt.transaction.strategyChain;

/**
 * 策略处理器
 *
 * @author dexu.tian
 * @date 2021/1/26
 */
public interface StrategyHandler<T,R> {

    public StrategyHandler DEFAULT_STRATEGY_HANDLER = t -> null;

    /**
     * apply strategy
     *
     * @param param
     * @return
     */
    R apply(T param);
}
