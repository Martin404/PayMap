package com.hugnew.sps.services.pay.util.web.unionpay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.unionpay.acp.sdk.SDKConfig;

/**
 * 名称： 第一卷 商户卷 第1\5\6部分 跳转网关支付产品\手机控件支付产品\手机网页支付产品<br>
 * 功能： 6.3　消费撤销类交易 <br>
 * 版本： 5.0<br>
 * 日期： 2014-07<br>
 * 作者： 中国银联ACP团队<br>
 * 版权： 中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考。<br>
 */
public class Form_6_3_ConsumeUndo extends DemoBase {

    public static void main(String[] args) {

        /**
         * 参数初始化
         * 在java main 方式运行时必须每次都执行加载
         * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
         */
        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

        /**
         * 组装请求报文
         */
        Map<String, String> data = new HashMap<String, String>();
        // 版本号
        data.put("version", "5.0.0");
        // 字符集编码 默认"UTF-8"
        data.put("encoding", "UTF-8");
        // 签名方法 01 RSA
        data.put("signMethod", "01");
        // 交易类型
        data.put("txnType", "31");
        // 交易子类型
        data.put("txnSubType", "00");
        // 业务类型
        data.put("bizType", "000201");
        // 渠道类型，07-PC，08-手机
        data.put("channelType", "08");
        // 前台通知地址 ，控件接入方式无作用
        data.put("frontUrl", "http://localhost:8080/ACPTest/acp_front_url.do");
        // 后台通知地址
        data.put("backUrl", "http://222.222.222.222:8080/ACPTest/acp_back_url.do");
        // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
        data.put("accessType", "0");
        // 商户号码，请改成自己的商户号
        data.put("merId", "888888888888888");
        //原消费的queryId，可以从查询接口或者通知接口中获取
        data.put("origQryId", "201502112158205063088");
        // 商户订单号，8-40位数字字母，重新产生，不同于原消费
        data.put("orderId", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        // 订单发送时间，取系统时间
        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        // 交易金额，消费撤销时需和原消费一致
        data.put("txnAmt", "1");
        // 交易币种
        data.put("currencyCode", "156");
        // 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
        // data.put("reqReserved", "透传信息");

        data = signData(data);

        // 交易请求url 从配置文件读取
        String url = SDKConfig.getConfig().getBackRequestUrl();

        Map<String, String> resmap = submitUrl(data, url);

        System.out.println("请求报文=[" + data.toString() + "]");
        System.out.println("应答报文=[" + resmap.toString() + "]");
    }
}
