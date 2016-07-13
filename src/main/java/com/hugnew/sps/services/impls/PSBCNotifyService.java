package com.hugnew.sps.services.impls;

import com.alibaba.fastjson.JSON;
import com.psbc.payment.client.SignatureService;
import com.hugnew.sps.dao.domain.PayMap;
import com.hugnew.sps.enums.PayPlatform;
import com.hugnew.sps.enums.PlatformType;
import com.hugnew.sps.services.IPSBCNotifyService;
import com.hugnew.sps.services.IPayMapService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮政通知业务
 * Created by Martin on 2016/7/01.
 */
@Service
public class PSBCNotifyService implements IPSBCNotifyService {

    private static Logger logger = LoggerFactory.getLogger(PSBCNotifyService.class);
    @Autowired
    private IPayMapService payMapService;
    @Resource
    private RabbitTemplate amqpTemplate;

    @Override
    public void psbcNotify(String plain, String signature) {
        if (StringUtils.isNotBlank(plain) && StringUtils.isNotBlank(signature)) {
            Boolean isValid = SignatureService.verify(plain, signature);
            if (isValid) {
                Map<String, String> temp = genMapPSBC(plain);
                if (!temp.isEmpty()) {
                    String code = temp.get("RespCode");
                    if ("00000000".equals(code)) { //00000000表示交易成功
                        PayMap payMap = payMapService.updatePayMapByPayCode(temp.get("TermSsn"), plain, signature, PlatformType.PSBC, temp.get("TermSsn"), PayPlatform.PSBC.getCode());
                        amqpTemplate.convertAndSend("payNotify." + payMap.getRequestBiz() + payMap.getOrderCode(), JSON.toJSONString(payMap));
                    }
                }
            } else {
                logger.warn("邮局支付出现验证签名不通过信息！返回明文如下：【{}】,签名如下：【{}】", new Object[]{plain, signature});
            }
        }
    }

    private Map<String, String> genMapPSBC(String plain) {
        Pattern pattern = Pattern.compile("([\\w]+)=([\\w]*)");
        Map<String, String> toRet = new HashMap<>();
        if (StringUtils.isNotBlank(plain)) {
            String[] temp = plain.split("\\|");
            if (temp.length > 0) {
                for (int i = 0; i < temp.length; i++) {
                    if (StringUtils.isNotBlank(temp[i])) {
                        Matcher matcher = pattern.matcher(temp[i]);
                        while (matcher.find()) {
                            toRet.put(matcher.group(1), matcher.group(2));

                        }
                    }

                }
            }
        }
        return toRet;
    }
}
