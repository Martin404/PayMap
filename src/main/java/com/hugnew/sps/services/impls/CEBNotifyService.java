package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.csii.payment.client.core.CebMerchantSignVerify;
import com.hugnew.core.common.exception.SystemException;
import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PayPlatform;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.ICEBNotifyService;
import com.hugnew.sps.services.IPayMapService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 光大网页通知业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class CEBNotifyService implements ICEBNotifyService {

    private static Logger logger = LoggerFactory.getLogger(CEBNotifyService.class);
    @Autowired
    private IPayMapService payMapService;
    @Resource
    private RabbitTemplate amqpTemplate;

    @Override
    public void cebNotify(HttpServletResponse response, String plain, String signature) {
        if (StringUtils.isNotBlank(plain) && StringUtils.isNotBlank(signature)) {
            Boolean isValid = CebMerchantSignVerify.merchantVerifyPayGate_ABA(signature, plain);
            if (isValid) {
                Map<String, String> temp = genMapCEB(plain);
                if (!temp.isEmpty()) {
                    String code = temp.get("respCode");
                    if ("AAAAAAA".equals(code)) {
                        //AAAAAAA表示交易成功
                        String tempPayCode = temp.get("orderId");
                        if (StringUtils.isNotBlank(tempPayCode)) {
                            StringBuffer toRetBuffer = new StringBuffer();
                            toRetBuffer.append("merchantId")
                                    .append("=")
                                    .append(temp.get("merchantId"))
                                    .append("~|~")
                                    .append("orderId")
                                    .append("=")
                                    .append(temp.get("orderId"))
                                    .append("~|~")
                                    .append("transDateTime")
                                    .append("=")
                                    .append(temp.get("transDateTime"))
                                    .append("~|~")
                                    .append("procStatus")
                                    .append("=")
                                    .append("1")
                                    .append("~|~")
                                    .append("MerchantUrl2")
                                    .append("=")
                                    .append(PropertiesUtil.getValue("pay.request.retUrl"));
                            String retPlain = toRetBuffer.toString();
                            String retSign = CebMerchantSignVerify.merchantSignData_ABA(retPlain);
                            PayMap payMap = payMapService.updatePayMapByPayCode(tempPayCode, plain, signature, PlatformType.CEB, temp.get("transSeqNo"), PayPlatform.CEB.getCode());
                            amqpTemplate.convertAndSend("payNotify." + payMap.getRequestBiz() + payMap.getOrderCode(), JSON.toJSONString(payMap));
                            try {
                                //向银行回写本地处理成功消息，让银行不再继续发送通知消息
                                response.getOutputStream().print("Plain=" + retPlain);
                                response.getOutputStream().print(System.getProperty("line.separator"));
                                response.getOutputStream().print("Signature=" + retSign);
                            } catch (IOException e) {
                                logger.error("ceb response io error,ex:{}", e.getMessage());
                                throw new SystemException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    private Map<String, String> genMapCEB(String plain) {
        Pattern pattern = Pattern.compile("([\\w]+)=(.*)");
        Map<String, String> toRet = new HashMap<>();
        if (StringUtils.isNotBlank(plain)) {
            String[] temp = plain.split("~\\|~");
            if (temp.length > 0) {
                for (int i = 0; i < temp.length; i++) {
                    Matcher matcher = pattern.matcher(temp[i]);
                    while (matcher.find()) {
                        toRet.put(matcher.group(1), matcher.group(2));
                    }
                }
            }
        }
        return toRet;
    }
}
