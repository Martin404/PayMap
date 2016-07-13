package com.hugnew.sps.realm.token;


import com.hugnew.core.common.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Map;

/**
 * Created by Liujishuai on 2015/5/15.
 */

/**
 * 消息摘要生成器
 */
public class HmacSHA256Utils {
    private static Logger logger = LoggerFactory.getLogger(HmacSHA256Utils.class);
    private HmacSHA256Utils(){}
    public static String digest(String key, String content) {
        try {
            logger.info("key:{};content:{}",key,content);
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HmacSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
           // byte[] hexB = new Hex().encode(doFinal);
            String oss=Base64.encodeBase64String(mac.doFinal(dataBytes));
            logger.info("加密后的字符串：{}" , oss);
            return oss;

           // return new String(hexB, "utf-8");
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public static String digest(String key, Map<String, ?> map) {
        StringBuilder s = new StringBuilder();
        for(Object values : map.values()) {
            if(values instanceof String[]) {
                for(String value : (String[])values) {
                    s.append(value);
                }
            } else if(values instanceof List) {
                for(String value : (List<String>)values) {
                    s.append(value);
                }
            } else {
                s.append(values);
            }
        }
        return digest(key, s.toString());
    }
}
