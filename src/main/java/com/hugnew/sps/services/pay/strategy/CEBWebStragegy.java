package com.hugnew.sps.services.pay.strategy;

import com.csii.payment.client.core.CebMerchantSignVerify;
import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.enums.PayType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 光大网页web支付
 * Created by Martin on 2016/7/01.
 */
public class CEBWebStragegy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(CEBWebStragegy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }

        Map<String, String> plainMap = new HashMap<>();
        plainMap.put("MerchantId", PropertiesUtil.getValue("pay.request.ceb.merchantId"));
        plainMap.put("IdentityType", PropertiesUtil.getValue("pay.request.ceb.identityType"));
        plainMap.put("MerCifId", PropertiesUtil.getValue("pay.request.ceb.merCifId"));
        plainMap.put("PayType", PropertiesUtil.getValue("pay.request.ceb.payType"));
        plainMap.put("MerchantSeqNo", (String) params.get("payCode"));
        plainMap.put("MerchantDateTime", format.format(new Date()));
        plainMap.put("TransAmount", String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        plainMap.put("TerminalType", PropertiesUtil.getValue("pay.request.ceb.terminalType"));
        plainMap.put("TerminalId", PropertiesUtil.getValue("pay.request.ceb.terminalId"));
        plainMap.put("MerSecName", PropertiesUtil.getValue("pay.request.ceb.merSecName"));
        plainMap.put("ProductCataLog", PropertiesUtil.getValue("pay.request.ceb.productCataLog"));//57：综合服务（固定值）
        plainMap.put("MerProduct", PropertiesUtil.getValue("pay.request.ceb.merProduct"));//商品名称  ，竟然是必填 ？
        plainMap.put("MerchantUrl", PropertiesUtil.getValue("pay.notify.ceb.url"));
        plainMap.put("MerchantUrl1", StringUtils.isNotBlank(retUrl) ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"));
        plainMap.put("UserIp", "");//客户在商户网站上生成订单时的客户IP  我感觉没必要传
        plainMap.put("msgExt", PropertiesUtil.getValue("pay.request.ceb.msgExt"));//附加信息

        String plain = concatMap(plainMap);
        String sign = CebMerchantSignVerify.merchantSignData_ABA(plain);
        Map<String, String> toRet = new HashMap<>();
        toRet.put("transName", PropertiesUtil.getValue("pay.request.ceb.web.transName"));
        toRet.put("Plain", plain);
        toRet.put("Signature", sign);
        if(logger.isDebugEnabled()){
            logger.debug("ceb参数信息:{}", toRet.toString());
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
