package com.hugnew.sps.services.pay.strategy;

import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.services.pay.util.web.unionpay.util.DemoBase;
import com.hugnew.sps.enums.PayType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 银联wap支付（对接acp）
 * Created by Martin on 2016/7/01.
 */
public class UnionWapStrategy extends DemoBase implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(UnionWapStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }
        //组装请求报文
        Map<String, String> data = new HashMap<String, String>();
        // 版本号
        data.put("version", PropertiesUtil.getValue("pay.request.unionpayApp.version"));
        // 字符集编码 默认"UTF-8"
        data.put("encoding", PropertiesUtil.getValue("pay.request.unionpayApp.encoding"));
        // 签名方法 01 RSA
        data.put("signMethod", PropertiesUtil.getValue("pay.request.unionpayApp.signMethod"));
        // 交易类型 01-消费
        data.put("txnType", PropertiesUtil.getValue("pay.request.unionpayApp.txnType"));
        // 交易子类型 01:自助消费 02:订购 03:分期付款
        data.put("txnSubType", PropertiesUtil.getValue("pay.request.unionpayApp.txnSubType"));
        // 业务类型
        data.put("bizType", PropertiesUtil.getValue("pay.request.unionpayApp.bizType"));
        // 渠道类型，07-PC，08-手机
        data.put("channelType", PropertiesUtil.getValue("pay.request.unionpayApp.channelType"));
        // 前台通知地址 ，控件接入方式无作用
        data.put("frontUrl", null != retUrl ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"));
        // 后台通知地址
        data.put("backUrl", PropertiesUtil.getValue("pay.notify.unionpay.url"));
        // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
        data.put("accessType", PropertiesUtil.getValue("pay.request.unionpayApp.accessType"));
        // 商户号码，请改成自己的商户号
        data.put("merId", PropertiesUtil.getValue("pay.request.unionpayApp.merId"));
        // 商户订单号，8-40位数字字母
        data.put("orderId", (String) params.get("payCode"));
        // 订单发送时间，取系统时间
        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        data.put("txnAmt", String.valueOf(((BigDecimal)params.get("toPay")).multiply(BigDecimal.valueOf(100)).longValue()));
        // 交易币种
        data.put("currencyCode", PropertiesUtil.getValue("pay.request.unionpayApp.currencyCode"));
        Map<String, String> submitFromData = signData(data);
        //待请求参数数组
        StringBuffer toRet = new StringBuffer();
        for (Map.Entry<String, String> entry : submitFromData.entrySet()) {
            toRet.append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"").append("&");
        }
        if(logger.isDebugEnabled()){
            logger.debug("union参数信息:{}", toRet.toString());
        }
        return toRet.substring(0, toRet.length() - 1);
    }

}
