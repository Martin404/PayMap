package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.sps.services.pay.util.app.wechat.util.HttpUtils;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PayPlatform;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.IPayMapService;
import com.hugnew.sps.services.IWechatNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信通知业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class WechatNotifyService implements IWechatNotifyService {

    private static Logger logger = LoggerFactory.getLogger(WechatNotifyService.class);
    @Autowired
    private IPayMapService payMapService;
    @Resource
    private RabbitTemplate amqpTemplate;

    @Override
    public void wechatNotify(HttpServletRequest request, HttpServletResponse response) {
        String xml = null;
        try {
            xml = HttpUtils.readInstream(request.getInputStream(), null);
        } catch (IOException e) {
            logger.error("xml read io error,ex:{}",e.getMessage());
            throw new SystemException(e);
        }
        if (null != xml) {
            if(logger.isDebugEnabled()){
                logger.debug("wechat notify xml:{}",xml);
            }
            Map<String, String> retParam = decodeXml(xml);
            if (null != retParam && retParam.size() > 0) {
                if(logger.isDebugEnabled()){
                    logger.debug("wechat xml to map,retParam:{}",retParam.toString());
                }
                if ("SUCCESS".equals(retParam.get("return_code"))) {
                    PayMap payMap = payMapService.updatePayMapByPayCode(retParam.get("out_trade_no"), retParam.toString(), null, PlatformType.WECHAT, retParam.get("transaction_id"), PayPlatform.WECHAT_APP.getCode());
                    amqpTemplate.convertAndSend("payNotify." + payMap.getRequestBiz() + payMap.getOrderCode(), JSON.toJSONString(payMap));
                    String toRet = "<xml>\n" +
                            "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                            "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                            "</xml>";
                    try {
                        response.getWriter().write(toRet);
                    } catch (IOException e) {
                        logger.error("response io error,ex:{}",e.getMessage());
                        throw new SystemException(e);
                    }
                } else {
                    logger.error("微信交易失败，订单号：{}",retParam.get("out_trade_no"));
                    String toRet = "<xml>\n" +
                            "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                            "  <return_msg><![CDATA[not success]]></return_msg>\n" +
                            "</xml>";
                    try {
                        response.getWriter().write(toRet);
                    } catch (IOException e) {
                        logger.error("response io error,ex:{}", e.getMessage());
                        throw new SystemException(e);
                    }
                }
            }

        }
    }

    private Map<String, String> decodeXml(String content) {
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
            logger.error("wechat decode xml error,ex:{}", e.getMessage());
            throw new SystemException(e);
        }
    }
}
