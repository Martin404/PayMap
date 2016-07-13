package com.hugnew.sps.services.pay.strategy;

import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.core.web.system.listener.InitListener;
import com.hugnew.sps.enums.PayType;
import com.hugnew.sps.services.ICheckSellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝web支付（对接即时到账支付，包含国内、国际）
 * Created by Martin on 2016/7/01.
 */
public class AlipayWebStrategy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(AlipayWebStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }
        String sellerCode = (String) params.get("sellerCode");
        ICheckSellerService checkSellerService = (ICheckSellerService) InitListener.context.getBean("checkSellerService");
        if(checkSellerService.isUseGlobalPay(sellerCode)){
            //国际支付
            return makeGlobalParam(params, retUrl);
        }else{
            //国内支付
            return makeMainParam(params, retUrl);
        }
    }

    private String makeGlobalParam(Map<String, Object> params, String retUrl) {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", PropertiesUtil.getValue("pay.request.alipayWebGlobal.service"));
        sParaTemp.put("partner", PropertiesUtil.getValue("pay.request.alipayWebGlobal.partner"));
        sParaTemp.put("seller_email", PropertiesUtil.getValue("pay.request.alipayWebGlobal.seller_email"));
        sParaTemp.put("_input_charset", PropertiesUtil.getValue("pay.request.alipayWebGlobal.input_charset"));
        sParaTemp.put("payment_type", PropertiesUtil.getValue("pay.request.alipayWebGlobal.payment_type"));
        sParaTemp.put("notify_url", PropertiesUtil.getValue("pay.notify.alipay.global.url"));
        sParaTemp.put("return_url", null != retUrl ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"));
        sParaTemp.put("out_trade_no", (String) params.get("payCode"));
        sParaTemp.put("subject", PropertiesUtil.getValue("pay.request.alipayWebGlobal.subject"));
        //支付宝国际如果传total_fee则以外币计算
        sParaTemp.put("rmb_fee", String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        sParaTemp.put("body", PropertiesUtil.getValue("pay.request.alipayWebGlobal.body"));
        //支付宝国际此处要求填写签约时所选的币种，此参数为必需参数，文档坑人
        sParaTemp.put("currency", PropertiesUtil.getValue("pay.request.alipayWebGlobal.currency"));
        sParaTemp.put("show_url", PropertiesUtil.getValue("pay.request.alipayWebGlobal.show_url"));
        //建立请求
        String sHtmlText = com.hugnew.sps.services.pay.util.web.ali.global.util.AlipaySubmit.buildRequestParams(sParaTemp);
        if(logger.isDebugEnabled()){
            logger.debug("alipay参数信息:{}", sHtmlText);
        }
        return sHtmlText;
    }

    private String makeMainParam(Map<String, Object> params, String retUrl) {
        Map<String, String> sParaTemp = new HashMap<>();
        sParaTemp.put("service", PropertiesUtil.getValue("pay.request.alipayWebMain.service"));
        sParaTemp.put("partner", PropertiesUtil.getValue("pay.request.alipayWebMain.partner"));
        sParaTemp.put("seller_email", PropertiesUtil.getValue("pay.request.alipayWebMain.seller_email"));
        sParaTemp.put("_input_charset", PropertiesUtil.getValue("pay.request.alipayWebMain.input_charset"));
        sParaTemp.put("payment_type", PropertiesUtil.getValue("pay.request.alipayWebMain.payment_type"));
        sParaTemp.put("notify_url", PropertiesUtil.getValue("pay.notify.alipay.main.url"));
        sParaTemp.put("return_url", null != retUrl ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"));
        sParaTemp.put("out_trade_no", (String) params.get("payCode"));
        sParaTemp.put("subject", PropertiesUtil.getValue("pay.request.alipayWebMain.subject"));
        sParaTemp.put("total_fee", String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        sParaTemp.put("body", PropertiesUtil.getValue("pay.request.alipayWebMain.body"));
        //订单或者商品 展示url；
        sParaTemp.put("show_url", PropertiesUtil.getValue("pay.request.alipayWebMain.show_url"));
        //建立请求
        String sHtmlText = com.hugnew.sps.services.pay.util.web.ali.main.util.AlipaySubmit.buildRequestParams(sParaTemp);
        if(logger.isDebugEnabled()){
            logger.debug("alipay参数信息:{}", sHtmlText);
        }
        return sHtmlText;
    }

}
