package com.hugnew.sps.controllers.front.open.pay;

import com.hugnew.core.common.exception.BusinessException;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.sps.controllers.base.BaseController;
import com.hugnew.sps.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支付通知入口
 * Created by Martin on 2016/7/01.
 */
@RequestMapping(value = "/open/payNotify")
public class PayNotifyController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PayNotifyController.class);
    @Autowired
    private IWechatNotifyService wechatNotifyService;
    @Autowired
    private IUnionPayNotifyService unionPayNotifyService;
    @Autowired
    private ICEBNotifyService cebNotifyService;
    @Autowired
    private ICEBGatewayNotifyService cebGatewayNotifyService;
    @Autowired
    private IPSBCNotifyService psbcNotifyService;
    @Autowired
    private IAlipayNotifyService alipayNotifyService;

    /**
     * 光大网页通知回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/cebNotify", method = RequestMethod.POST)
    public void cebNotify(HttpServletRequest request, HttpServletResponse response) {
        cebNotifyService.cebNotify(response, request.getParameter("Plain"), request.getParameter("Signature"));
    }

    /**
     * 光大网关通知回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/cebGatewayNotify", method = RequestMethod.POST)
    public void cebGatewayNotify(HttpServletRequest request, HttpServletResponse response) {
        cebGatewayNotifyService.cebGatewayNotify(response, request.getParameter("Plain"), request.getParameter("Signature"));
    }

    /**
     * 邮政通知回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "/psbcNotify", method = RequestMethod.POST)
    public void psbcNotify(HttpServletRequest request, HttpServletResponse response) {
        psbcNotifyService.psbcNotify(request.getParameter("Plain"), request.getParameter("Signature"));
    }

    /**
     * 银联app通知回调（acp）
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/unionPayNotify")
    public void unionPayNotifyApp(HttpServletRequest request, HttpServletResponse response) throws IOException, BusinessException {
        unionPayNotifyService.unionPayNotifyApp(request);
    }

    /**
     * 银联web通知回调(upop)
     * @param request
     * @param response
     */
    @RequestMapping(value = "/unionPayNotifyWeb")
    public void unionPayNotifyWeb(HttpServletRequest request, HttpServletResponse response) {
        unionPayNotifyService.unionPayNotifyWeb(request, response);
    }

    /**
     * 国内支付宝app通知回调
     * @param request
     * @param response
     * @throws SystemException
     * @throws BusinessException
     */
    @RequestMapping(value = "/alipayNotifyMainApp", method = RequestMethod.POST)
    public void alipayNotifyMainApp(HttpServletRequest request, HttpServletResponse response) throws SystemException, BusinessException {
        alipayNotifyService.alipayNotifyMainApp(request, response);
    }

    /**
     * 国内支付宝web通知回调
     * @param request
     * @param response
     * @throws SystemException
     * @throws BusinessException
     */
    @RequestMapping(value = "/alipayNotifyMain", method = RequestMethod.POST)
    public void alipayNotifyMain(HttpServletRequest request, HttpServletResponse response) throws SystemException, BusinessException {
        alipayNotifyService.alipayNotifyMain(request, response);
    }

    /**
     * 国际支付宝app通知回调
     * @param request
     * @param response
     * @throws SystemException
     * @throws BusinessException
     */
    @RequestMapping(value = "alipayNotifyGlobalApp", method = RequestMethod.POST)
    public void alipayNotifyGlobalApp(HttpServletRequest request, HttpServletResponse response) throws SystemException, BusinessException {
        alipayNotifyService.alipayNotifyGlobalApp(request, response);
    }

    /**
     * 国际支付宝web通知回调
     * @param request
     * @param response
     * @throws SystemException
     * @throws BusinessException
     */
    @RequestMapping(value = "alipayNotifyGlobal", method = RequestMethod.POST)
    public void alipayNotifyGlobal(HttpServletRequest request, HttpServletResponse response) throws SystemException, BusinessException {
        alipayNotifyService.alipayNotifyGlobal(request, response);
    }

    /**
     * 微信通知回调
     * @param request
     * @param response
     */
    @RequestMapping(value = "wechatNotify", method = RequestMethod.POST)
    public void wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        wechatNotifyService.wechatNotify(request, response);
    }

}
