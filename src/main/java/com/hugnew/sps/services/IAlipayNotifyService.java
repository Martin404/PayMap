package com.hugnew.sps.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 支付宝通知业务接口
 * Created by Martin on 2016/7/01.
 */
public interface IAlipayNotifyService {

    /**
     * 国内支付宝app通知
     * @param request
     * @param response
     */
    void alipayNotifyMainApp(HttpServletRequest request, HttpServletResponse response);

    /**
     * 国内支付宝pc通知
     * @param request
     * @param response
     */
    void alipayNotifyMain(HttpServletRequest request, HttpServletResponse response);

    /**
     * 国际支付宝pc通知
     * @param request
     * @param response
     */
    void alipayNotifyGlobal(HttpServletRequest request, HttpServletResponse response);

    /**
     * 国际支付宝app通知
     * @param request
     * @param response
     */
    void alipayNotifyGlobalApp(HttpServletRequest request, HttpServletResponse response);
}
