package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PayPlatform;
import com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk.QuickPayConf;
import com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk.QuickPayQuery;
import com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk.QuickPayUtils;
import com.hugnew.sps.services.pay.util.web.unionpay.util.BackRcvResponse;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.IPayMapService;
import com.hugnew.sps.services.IUnionPayNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 银联通知业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class UnionPayNotifyService implements IUnionPayNotifyService {

    private static Logger logger = LoggerFactory.getLogger(UnionPayNotifyService.class);
    @Autowired
    private IPayMapService payMapService;
    @Resource
    private RabbitTemplate amqpTemplate;

    @Override
    public void unionPayNotifyApp(HttpServletRequest request) {
        Map<String, String> params = BackRcvResponse.getAllRequestParam(request);
        if(logger.isDebugEnabled()){
            logger.debug("订单orderCode：{}银联支付返回参数列表：{}", new Object[]{params.get("orderId"), params.toString()});
        }
        if ("00".equals(params.get("respCode"))) {
            PayMap payMap = payMapService.updatePayMapByPayCode(params.get("orderId"), params.toString(), null, PlatformType.UNIONPAY, params.get("queryId"), PayPlatform.UNION_APP.getCode());
            amqpTemplate.convertAndSend("payNotify." + payMap.getRequestBiz() + payMap.getOrderCode(), JSON.toJSONString(payMap));
        } else {
            logger.error("订单orderCode：{}银联支付支付失败", params.get("orderId"));
        }
    }

    @Override
    public void unionPayNotifyWeb(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = BackRcvResponse.getAllRequestParam(request);
        try {
            request.setCharacterEncoding(QuickPayConf.charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("request charset encode error,ex:{}",e.getMessage());
            throw new SystemException(e);
        }
        String[] resArr = new String[QuickPayConf.notifyVo.length];
        for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
            resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
        }
        response.setContentType("text/html;charset=" + QuickPayConf.charset);
        response.setCharacterEncoding(QuickPayConf.charset);
        String signature = request.getParameter(QuickPayConf.signature);
        String signMethod = request.getParameter(QuickPayConf.signMethod);
        Boolean signatureCheck = new QuickPayUtils().checkSign(resArr, signMethod, signature);
        if (signatureCheck) {
            if ("00".equals(resArr[10])) {
                String queryResult = new QuickPayQuery().queryTrans("01", resArr[8], new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                if ("0".equals(queryResult)) {
                    PayMap payMap = payMapService.updatePayMapByPayCode(resArr[8], params.toString(), null, PlatformType.UNIONPAY, params.get("qid"), PayPlatform.UNION_PC.getCode());
                    amqpTemplate.convertAndSend("payNotify." + payMap.getRequestBiz() + payMap.getOrderCode(), JSON.toJSONString(payMap));
                }else {
                    logger.error("订单号orderCode:{}银联交易失败",resArr[8]);
                }
            } else {
                logger.error("银联web支付接口验证签名成功，但是支付失败！！！失败原因：{}", resArr[11]);
            }
        } else {
            logger.error("银联web支付接口验证签名失败！疑似伪装数据！");
        }

    }

}
