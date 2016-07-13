package com.hugnew.sps.services.pay.strategy;

import com.alibaba.fastjson.JSON;
import com.csii.payment.client.core.CebMerchantSignVerify;
import com.hugnew.core.util.DateUtils;
import com.hugnew.sps.services.pay.util.web.ceb.PayUtils;
import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.enums.PayType;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 光大网关web支付
 * Created by Martin on 2016/7/01.
 */
public class CEBGatewayPayStrategy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(CEBGatewayPayStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }
        Map<String, String> plainMap = new HashMap<>();
        plainMap.put("transId", PropertiesUtil.getValue("pay.request.cebGateway.transId"));
        plainMap.put("merchantId", PropertiesUtil.getValue("pay.request.cebGateway.merchantId"));
        plainMap.put("orderId", (String) params.get("payCode"));
        plainMap.put("transAmt", String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        plainMap.put("transDateTime", format.format(new Date()));
        plainMap.put("currencyType", PropertiesUtil.getValue("pay.request.cebGateway.currencyType"));
        plainMap.put("customerName", "");//可空字段
        plainMap.put("productInfo", PropertiesUtil.getValue("pay.request.cebGateway.productInfo"));
        plainMap.put("merSecName", PropertiesUtil.getValue("pay.request.cebGateway.merSecName"));
        plainMap.put("customerEMail", "");
        plainMap.put("merURL", PropertiesUtil.getValue("pay.notify.ceb.gateway.url"));//用于后台通知商户
        plainMap.put("merURL1", StringUtils.isNotBlank(retUrl) ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"));//用于后台通知商户失败或者默认情况下，引导客户回商户页面
        plainMap.put("payIp", "");//可空字段
        plainMap.put("msgExt", PropertiesUtil.getValue("pay.request.cebGateway.msgExt"));//扩展字段
        String plain = concatMap(plainMap);
        String sign = CebMerchantSignVerify.merchantSignData_ABA(plain);
        Map<String, String> toRet = new HashMap<>();
        toRet.put("TransName", PropertiesUtil.getValue("pay.request.cebGateway.transName"));
        toRet.put("Plain", plain);
        toRet.put("Signature", sign);
        toRet.put("url", PropertiesUtil.getValue("pay.request.cebGateway.web.url"));
        if(logger.isDebugEnabled()){
            logger.debug("cebGateway参数信息:{}", toRet.toString());
        }
        return buildRequestParams(toRet);
    }

    private String concatMap(Map<String, String> plainMap) {
        StringBuffer toRetBuff = new StringBuffer();
        for (Map.Entry<String, String> entry : plainMap.entrySet()) {
            toRetBuff.append(entry.getKey()).append("=").append(entry.getValue()).append("~|~");
        }
        return toRetBuff.substring(0, toRetBuff.length() - 3);
    }

    private String buildRequestParams(Map<String, String> sParaTemp) {

        StringBuffer toRet = new StringBuffer();
        for (Map.Entry<String, String> entry : sParaTemp.entrySet()) {
            toRet.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append("&");
        }
        return toRet.substring(0, toRet.length() - 1);
    }

}
