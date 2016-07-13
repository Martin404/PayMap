package com.hugnew.sps.services.pay.strategy;

import com.psbc.payment.client.SignatureService;
import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.enums.PayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮政wap支付
 * Created by Martin on 2016/7/01.
 */
public class PSBCWapStragegy implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(PSBCWapStragegy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String, String> plainMap = new HashMap<>();
        //IPER个人客户支付 WPER手机支付 IPSR退货交易 IQSR单笔交易查询 BQSR多笔交易查询 IDFR对帐文件下载
        plainMap.put("TranAbbr", PropertiesUtil.getValue("pay.request.psbc.wap.TranAbbr"));
        plainMap.put("MercDtTm", format.format(new Date()));
        //最长为20位，保证唯一，不能重复
        plainMap.put("TermSsn", (String) params.get("payCode"));
        plainMap.put("OSttDate", "");//可以为空但是不可以不传；原交易商户日期，支付交易不用赋值
        plainMap.put("OAcqSsn", "");
        plainMap.put("MercCode", PropertiesUtil.getValue("pay.request.psbc.MercCode"));
        plainMap.put("TermCode", "");
        plainMap.put("TranAmt", String.valueOf(((BigDecimal) params.get("toPay")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        try {
            plainMap.put("MercUrl", URLEncoder.encode(PropertiesUtil.getValue("pay.notify.psbc.url"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("MercUrl encode error:{}", PropertiesUtil.getValue("pay.notify.psbc.url"));
        }
        plainMap.put("Remark1", "");
        plainMap.put("Remark2", "");
        String plain = concatMap(plainMap);
        String sign = SignatureService.sign(plain);
        Map<String, String> toRet = new HashMap<>();
        toRet.put("transName", PropertiesUtil.getValue("pay.request.psbc.wap.transName"));
        toRet.put("Plain", plain);
        toRet.put("Signature", sign);
        toRet.put("url", PropertiesUtil.getValue("pay.request.psbc.url"));
        if(logger.isDebugEnabled()){
            logger.debug("psbc参数信息:{}", toRet.toString());
        }
        return buildRequestParams(toRet);
    }

    private String concatMap(Map<String, String> plainMap) {
        StringBuffer toRetBuff = new StringBuffer();
        for (Map.Entry<String, String> entry : plainMap.entrySet()) {
            toRetBuff.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
        }
        return toRetBuff.substring(0, toRetBuff.length() - 1);
    }

    private String buildRequestParams(Map<String, String> sParaTemp) {
        StringBuffer toRet = new StringBuffer();
        for (Map.Entry<String, String> entry : sParaTemp.entrySet()) {
            toRet.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append("&");
        }
        return toRet.substring(0, toRet.length() - 1);
    }

}
