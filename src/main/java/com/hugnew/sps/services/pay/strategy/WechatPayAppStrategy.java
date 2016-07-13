package com.hugnew.sps.services.pay.strategy;

import com.alibaba.fastjson.JSONObject;
import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.services.pay.util.app.wechat.util.MD5;
import com.hugnew.sps.services.pay.util.app.wechat.util.Util;
import com.hugnew.sps.enums.PayType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 微信app支付
 * Created by Martin on 2016/7/01.
 */
public class WechatPayAppStrategy implements PayStrategy {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(WechatPayAppStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {

        String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
        String entity = genProductArgs(params);
        byte[] buf = Util.httpPost(url, entity);
        String content = new String(buf);
        if(logger.isDebugEnabled()){
            logger.debug("wechat content:{}", content);
        }
        Map<String, String> xml = decodeXml(content);
        if(logger.isDebugEnabled()){
            logger.debug("wechat xml:{}", xml.toString());
        }
        List<NameValuePair> toRet = genPayReq(xml.get("prepay_id"));
        Map<String, Object> map = new HashMap<>();
        for (NameValuePair var : toRet) {
            if ("package".equals(var.getName())) {

                map.put("_package", var.getValue());
            } else {
                map.put(var.getName(), var.getValue());
            }
        }
        return JSONObject.toJSONString(map);
    }

    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            logger.error("decode xml error,ex:{}", e.toString());
        }
        return null;
    }

    private String genProductArgs(Map<String, Object> params) {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", PropertiesUtil.getValue("pay.request.wechart.appid")));
            String orderCode = (String) params.get("orderCode");
            if (StringUtils.isNotBlank(orderCode)) {
                packageParams.add(new BasicNameValuePair("body", "hugnew-订单编号：" + orderCode));
            } else {
                packageParams.add(new BasicNameValuePair("body", "hugnew订单支付"));
            }
            packageParams.add(new BasicNameValuePair("mch_id", PropertiesUtil.getValue("pay.request.wechart.mch_id")));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", PropertiesUtil.getValue("wechat.pay.notify.url")));
            packageParams.add(new BasicNameValuePair("out_trade_no", (String) params.get("payCode")));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", PropertiesUtil.getValue("pay.request.wechart.spbill_create_ip")));
            packageParams.add(new BasicNameValuePair("total_fee", String.valueOf(((BigDecimal)params.get("toPay")).multiply(BigDecimal.valueOf(100)).longValue())));
            packageParams.add(new BasicNameValuePair("trade_type", PropertiesUtil.getValue("pay.request.wechart.trade_type")));
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            return new String(xmlstring.getBytes("UTF-8"), "ISO-8859-1");
        } catch (Exception e) {
            logger.error("genProductArgs error, ex:{} ", e.getMessage());
        }
        return null;
    }

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(PropertiesUtil.getValue("pay.request.wechart.API_KEY"));
        String packageSign = null;
        try {
            packageSign = MD5.getMessageDigest(sb.toString().getBytes("utf-8")).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            logger.error("packageSign md5 error, ex:{} ", e.getMessage());
        }
        if(logger.isDebugEnabled()){
            logger.debug("wechat packageSign:{}", packageSign);
        }
        return packageSign;
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(PropertiesUtil.getValue("pay.request.wechart.API_KEY"));
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        if(logger.isDebugEnabled()){
            logger.debug("wechat appSign:{}", appSign);
        }
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        if(logger.isDebugEnabled()){
            logger.debug("packageParams toxml:{}", sb.toString());
        }
        return sb.toString();
    }

    private List<NameValuePair> genPayReq(String prepayId) {
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", PropertiesUtil.getValue("pay.request.wechart.appid")));
        signParams.add(new BasicNameValuePair("noncestr", genNonceStr()));
        signParams.add(new BasicNameValuePair("package", PropertiesUtil.getValue("pay.request.wechart.package")));
        signParams.add(new BasicNameValuePair("partnerid", PropertiesUtil.getValue("pay.request.wechart.mch_id")));
        signParams.add(new BasicNameValuePair("prepayid", prepayId));
        signParams.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis() / 1000)));
        String sign = genAppSign(signParams);
        signParams.add(new BasicNameValuePair("sign", sign));
        if(logger.isDebugEnabled()){
            logger.debug("signParams:{}", signParams.toString());
        }
        return signParams;
    }
}
