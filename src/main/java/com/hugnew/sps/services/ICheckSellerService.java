package com.hugnew.sps.services;

/**
 * 商家校验业务接口
 * Created by Martin on 2016/7/01.
 */
public interface ICheckSellerService {

    /**
     * 是否使用国际支付宝
     * @param sellerCode
     * @return
     */
    boolean isUseGlobalPay(String sellerCode);

}
