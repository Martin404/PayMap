package com.hugnew.sps.services.pay.strategy;

import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.core.web.system.listener.InitListener;
import com.hugnew.sps.enums.PayType;
import com.hugnew.sps.services.ICheckSellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 支付宝app支付（对接移动支付，包含国内、国际）
 * Created by Martin on 2016/7/01.
 */
public class AlipayAppStrategy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(AlipayAppStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }
        String sellerCode = (String) params.get("sellerCode");
        ICheckSellerService checkSellerService = (ICheckSellerService) InitListener.context.getBean("checkSellerService");
        if (checkSellerService.isUseGlobalPay(sellerCode)) {
            //国际支付
            return makeGlobalParam(params);
        } else {
            //国内支付
            return makeMainParams(params, retUrl);
        }
    }

    private String makeGlobalParam(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        // 签约合作者身份ID
        sb.append("partner=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.partner")).append("\"");
        // 签约卖家支付宝账号
        sb.append("&seller_id=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.seller_email")).append("\"");
        // 商户网站唯一订单号
        sb.append("&out_trade_no=\"").append((String) params.get("payCode")).append("\"");
        // 商品名称
        sb.append("&subject=\"").append(params.get("sellerName")).append(params.get("orderCode")).append("\"");
        // 商品详情
        sb.append("&body=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.body")).append("\"");
        // 商品金额
        sb.append("&rmb_fee=\"").append(String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())).append("\"");
        sb.append("&currency=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.currency")).append("\"");
        sb.append("&forex_biz=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.forex_biz")).append("\"");
        // 服务器异步通知页面路径
        sb.append("&notify_url=\"").append(PropertiesUtil.getValue("pay.notify.alipay.globalApp.url")).append("\"");
        // 服务接口名称， 固定值
        sb.append("&service=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.service")).append("\"");
        // 支付类型， 固定值
        sb.append("&payment_type=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.payment_type")).append("\"");
        // 参数编码， 固定值
        sb.append("&_input_charset=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.input_charset")).append("\"");
        sb.append("&appenv=").append("\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.appenv")).append("\"");
        String sign = com.hugnew.sps.services.pay.util.app.ali.global.sign.RSA.sign(sb.toString(), PropertiesUtil.getValue("pay.request.alipayAppGlobal.private_key"), PropertiesUtil.getValue("pay.request.alipayAppGlobal.input_charset"));
        try {
            sign = URLEncoder.encode(sign, PropertiesUtil.getValue("pay.request.alipayAppGlobal.input_charset"));
        } catch (UnsupportedEncodingException e) {
            logger.error("alipayGlobal app sign encode error,ex:{}",e.getMessage());
        }
        String sHtmlText = sb.append("&sign=\"").append(sign).append("\"&sign_type=\"").append(PropertiesUtil.getValue("pay.request.alipayAppGlobal.sign_type")).append("\"").toString();
        if(logger.isDebugEnabled()){
            logger.debug("alipay参数信息:{}", sHtmlText);
        }
        return sHtmlText;
    }

    private String makeMainParams(Map<String, Object> params, String retUrl) {
        StringBuffer sb = new StringBuffer();
        // 签约合作者身份ID
        sb.append("partner=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.partner")).append("\"");
        // 签约卖家支付宝账号
        sb.append("&seller_id=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.seller_email")).append("\"");
        // 商户网站唯一订单号
        sb.append("&out_trade_no=\"").append((String) params.get("payCode")).append("\"");
        // 商品名称
        sb.append("&subject=\"").append(params.get("sellerName")).append(params.get("orderCode")).append("\"");
        // 商品详情
        sb.append("&body=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.body")).append("\"");
        // 商品金额
        sb.append("&total_fee=\"").append(String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())).append("\"");
        // 服务器异步通知页面路径
        sb.append("&notify_url=\"").append(PropertiesUtil.getValue("pay.notify.alipay.mainApp.url")).append("\"");
        // 服务接口名称， 固定值
        sb.append("&service=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.service")).append("\"");
        // 支付类型， 固定值
        sb.append("&payment_type=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.payment_type")).append("\"");
        // 参数编码， 固定值
        sb.append("&_input_charset=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.input_charset")).append("\"");
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        sb.append("&return_url=\"").append(null != retUrl ? retUrl : PropertiesUtil.getValue("pay.request.alipayAppMain.retUrl")).append("\"");
        String sign = com.hugnew.sps.services.pay.util.app.ali.main.sign.RSA.sign(sb.toString(), PropertiesUtil.getValue("pay.request.alipayAppMain.private_key"), PropertiesUtil.getValue("pay.request.alipayAppMain.input_charset"));
        try {
            sign = URLEncoder.encode(sign, PropertiesUtil.getValue("pay.request.alipayAppMain.input_charset"));
        } catch (UnsupportedEncodingException e) {
            logger.error("alipayMain app sign encode error,ex:{}", e.getMessage());
        }
        String sHtmlText = sb.append("&sign=\"").append(sign).append("\"&sign_type=\"").append(PropertiesUtil.getValue("pay.request.alipayAppMain.sign_type")).append("\"").toString();
        if(logger.isDebugEnabled()){
            logger.debug("alipay参数信息:{}", sHtmlText);
        }
        return sHtmlText;
    }

}
