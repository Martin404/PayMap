package com.hugnew.sps.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 银联通知业务接口
 * Created by Martin on 2016/7/01.
 */
public interface IUnionPayNotifyService {

    /**
     * acp通知
     * @param request
     */
    void unionPayNotifyApp(HttpServletRequest request);

    /**
     * upop通知
     * @param request
     * @param response
     */
    void unionPayNotifyWeb(HttpServletRequest request, HttpServletResponse response);
}
