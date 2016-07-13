package com.hugnew.sps.services;

/**
 * 邮政通知业务接口
 * Created by Martin on 2016/7/01.
 */
public interface IPSBCNotifyService {

    /**
     * 邮政通知
     * @param plain
     * @param signature
     */
    void psbcNotify(String plain, String signature);

}
