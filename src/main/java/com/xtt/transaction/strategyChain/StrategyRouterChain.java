/*
 * Copyright (C) 1997-2020 康成投资（中国）有限公司
 *
 * http://www.rt-mart.com
 *
 * 版权归本公司所有，不得私自使用、拷贝、修改、删除，否则视为侵权
 */

package com.xtt.transaction.strategyChain;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * StrategyRouterChain
 *
 * @author dexu.tian
 * @date 2021/1/26
 */
public class StrategyRouterChain<T,R> {

    /**
     * strategyRouterChain
     *
     */
    private List<AbstractStrategyRouter> strategyRouterChain = new ArrayList<>();

    /**
     * the strategyRouterChain execute index
     *
     */
    private AtomicInteger index = new AtomicInteger(0);

    /**
     * the StrategyRouterChain entrance
     *
     * @param param
     * @return
     */
    public R doChain(T param) {
        if (index.get() >= strategyRouterChain.size()) {
            return null;
        }
        R res = null;
        AbstractStrategyRouter<T,R> abstractStrategyRouter = strategyRouterChain.get(index.get());
        index.incrementAndGet();
        if (null != abstractStrategyRouter) {
            res = abstractStrategyRouter.execute(param,this);
        }
        return res;
    }


    /**
     * 添加strategyRouter 到chain 中
     *
     * @param strategyRouter
     * @return
     */
    public boolean registerStrategyRouterChain(AbstractStrategyRouter strategyRouter) {
        strategyRouterChain.add(strategyRouter);
        return true;
    }
}
