package com.hugnew.sps.services.pay.strategy;

import com.hugnew.sps.enums.PayType;

import java.util.Map;

/**
 * 支付策略上下文
 * Created by Martin on 2016/7/01.
 */
public class StrategyContext {

    private PayStrategy payStrategy;

    /**
     * 调用对应支付平台组装支付请求报文
     * @param payType
     * @param params
     * @return
     */
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        payStrategy = StrategyFactory.getInstance().creator(payType.value());
        return payStrategy.generatePayParams(payType, params);
    }

}
