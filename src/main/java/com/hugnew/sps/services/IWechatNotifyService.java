package com.hugnew.sps.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信通知业务接口
 * Created by Martin on 2016/7/01.
 */
public interface IWechatNotifyService {

    /**
     * 微信通知
     * @param request
     * @param response
     */
    void wechatNotify(HttpServletRequest request, HttpServletResponse response);

}
