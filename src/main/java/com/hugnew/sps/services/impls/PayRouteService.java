package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.hugnew.sps.dao.*;
import com.hugnew.sps.dao.domain.*;
import com.hugnew.sps.dto.PayRequestParam;
import com.hugnew.sps.services.pay.strategy.StrategyContext;
import com.hugnew.sps.enums.PayType;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 支付请求路由业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class PayRouteService implements IPayRouteService {

    private static Logger logger = LoggerFactory.getLogger(PayRouteService.class);
    @Resource
    private RabbitTemplate amqpTemplate;
    @Autowired
    private PayMapMapper payMapMapper;

    public PayType dealPayType(String onLineStyle, String browseType) {
        PayType payType = null;
        switch (onLineStyle) {
            case "alipay":
                switch (browseType) {
                    case "web":
                        payType = PayType.ALIPAY_WEB;
                        break;
                    case "wap":
                        payType = PayType.ALIPAY_WAP;
                        break;
                    case "app":
                        payType = PayType.ALIPAY_APP;
                        break;
                    default:
                        payType = null;

                }
                break;
            case "unionpay":
                switch (browseType) {
                    case "web":
                        payType = PayType.UNION_WEB;
                        break;
                    case "wap":
                        payType = PayType.UNION_WAP;
                        break;
                    case "app":
                        payType = PayType.UNION_APP;
                        break;
                    default:
                        payType = null;

                }
                break;
            case "psbc":
                switch (browseType) {
                    case "web":
                        payType = PayType.PSBC_WEB;
                        break;
                    case "wap":
                        payType = PayType.PSBC_WAP;
                        break;
                    default:
                        payType = null;

                }
                break;
            case "ceb":
                switch (browseType) {
                    case "web":
                        payType = PayType.CEB_WEB;
                        break;
                    case "wap":
                        payType = PayType.CEB_WAP;
                        break;
                    default:
                        payType = null;
                }
                break;
            case "wechat":
                switch (browseType) {
                    case "app":
                        payType = PayType.WECHAT_APP;
                        break;
                    default:
                        payType = null;
                }
                break;
            case "ceb_GW":
                switch (browseType) {
                    case "web":
                        payType = PayType.CEB_GATEWAY_WEB;
                        break;
                    case "wap":
                        payType = PayType.CEB_GATEWAY_WAP;
                        break;
                    default:
                        payType = null;
                }
                break;
            default:
                payType = null;
        }
        return payType;
    }

    private void savePayRecord(String payRequsetMsg, PayType payType, PayRequestParam payRequestParam) {
        PayMap payMap = new PayMap();
        payMap.setOrderId(payRequestParam.getOrderID());
        payMap.setOrderCode(payRequestParam.getOrderCode());
        PlatformType type = PlatformType.getPlatform(payType.value());
        payMap.setPlatform(type.value());
        payMap.setTempPayCode(payRequestParam.getPayCode());
        payMap.setPayParams(payRequsetMsg);
        payMap.setRequestBiz(payRequestParam.getRequestBiz());
        payMapMapper.insertSelective(payMap);
    }

    @Override
    public Map<String, Object> getPayRetMap(PayRequestParam payRequestParam) {
        Map<String, Object> retMap = assembleRetMap(payRequestParam);
        return retMap;
    }

    @Override
    public Boolean getPayRetMap4MQ(PayRequestParam payRequestParam) {
        Map<String, Object> retMap = assembleRetMap(payRequestParam);
        amqpTemplate.convertAndSend("payRequestCallback." + payRequestParam.getRequestBiz() + payRequestParam.getOrderCode(), JSON.toJSONString(retMap));
        return true;
    }

    private Map<String, Object> assembleRetMap(PayRequestParam payRequestParam) {
        Map<String, Object> paramsToPass = new HashMap<>();
        if (StringUtils.isNotBlank(payRequestParam.getRetUrl())) {
            paramsToPass.put("retUrl", payRequestParam.getRetUrl());
        }
        paramsToPass.put("toPay", payRequestParam.getToPay());
        paramsToPass.put("payCode", payRequestParam.getPayCode());
        paramsToPass.put("sellerCode", payRequestParam.getSellerCode());
        paramsToPass.put("sellerName", payRequestParam.getSellerName());
        paramsToPass.put("orderCode", payRequestParam.getOrderCode());
        paramsToPass.put("invalidTime", payRequestParam.getInvalidTime());
        paramsToPass.put("orderCreateTime", payRequestParam.getCreateTime());
        PayType payType = dealPayType(payRequestParam.getOnLineStyle(), payRequestParam.getBrowseType());
        StrategyContext context = new StrategyContext();
        String payRequsetMsg = context.generatePayParams(payType, paramsToPass);
        if(logger.isDebugEnabled()){
            logger.debug("订单code为{}的支付请求参数生成信息：{}", new Object[]{payRequestParam.getOrderCode(), payRequsetMsg});
        }
        savePayRecord(payRequsetMsg, payType, payRequestParam);
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("payData", payRequsetMsg);
        retMap.put("payment", payRequestParam.getOnLineStyle());
        retMap.put("orderID", payRequestParam.getOrderID());
        retMap.put("errorCode", "0710");
        retMap.put("message", "请去第三方平台支付~~");
        return retMap;
    }

}

