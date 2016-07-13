package com.hugnew.sps.services;

import javax.servlet.http.HttpServletResponse;

/**
 * 光大网关通知业务接口
 * Created by Martin on 2016/7/01.
 */
public interface ICEBGatewayNotifyService {

    /**
     * 光大网关通知
     * @param response
     * @param plain
     * @param signature
     */
    void cebGatewayNotify(HttpServletResponse response, String plain, String signature);

}
