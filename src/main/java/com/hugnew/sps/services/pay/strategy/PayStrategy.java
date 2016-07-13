package com.hugnew.sps.services.pay.strategy;

import com.hugnew.sps.enums.PayType;

import java.util.Map;

/**
 * 支付策略路由
 * Created by Martin on 2016/7/01.
 */
public interface PayStrategy {

    /**
     * 调用对应支付平台组装支付请求报文
     * @param payType 传入需要的支付方式
     * @param params  其他额外需要的参数
     * @return 生成的支付请求
     */
    String generatePayParams(PayType payType, Map<String, Object> params);

}
