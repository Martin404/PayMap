package com.hugnew.sps.services;

import com.hugnew.sps.dto.PayRequestParam;

import java.util.Map;

/**
 * Created by Martin on 2016/7/01.
 */
public interface IPayRouteService {

    /**
     * 组装支付请求报文（http入口）
     * @param payRequestParam
     * @return
     */
    public Map<String, Object> getPayRetMap(PayRequestParam payRequestParam);

    /**
     * 组装支付请求报文（MQ入口）
     * @param payRequestParam
     * @return
     */
    public Boolean getPayRetMap4MQ(PayRequestParam payRequestParam);
}
