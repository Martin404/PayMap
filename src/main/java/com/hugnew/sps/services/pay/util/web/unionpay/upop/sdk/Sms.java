package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;

public class Sms {

    /**
     * 商户无跳转发送短信请求
     *
     * @param args
     */
    public static void main(String[] args) {
        new Sms().send();
    }

    public void send() {
        //普通商户
        /*String[] valueVo = new String[]{
				QuickPayConf.version,//协议版本
				QuickPayConf.charset,//字符编码
				"",//收单机构代码
				QuickPayConf.merCode,//商户代码
				"测试商户简称",//商户简称
				"2011060512345678",//订单号
				"2012",//金额
				"156",//币种
				"13888888888",//手机号
				""//保留域  说明：如果是收单机构保留域需传收单代码如：{acqCode=00215800}，商户直接接入upop不传收单代码
		};*/
        //收单机构
		/*String[] valueVo = new String[]{
				QuickPayConf.version,//协议版本
				QuickPayConf.charset,//字符编码
				"00215800",//收单机构代码
				QuickPayConf.merCode,//商户代码
				"测试商户简称",//商户简称
				"2011060512345678",//订单号
				"2012",//金额
				"156",//币种
				"13888888887",//手机号
				""//保留域  说明：如果是收单机构保留域需传收单代码如：{acqCode=00215800}，商户直接接入upop不传收单代码
		};*/
        //二级商户
		/*String[] valueVo = new String[]{
				QuickPayConf.version,//协议版本
				QuickPayConf.charset,//字符编码
				"",//收单机构代码
				QuickPayConf.merCode,//商户代码
				"测试商户简称",//商户简称
				"2011060512345678",//订单号
				"2012",//金额
				"156",//币种
				"13888888886",//手机号
				"{merPlatformType=1&secondaryMerId=10000011110022&secondaryMerFullName=二级商户全称&secondaryMerAbbr=二级商户简称}"//保留域  说明：如果是收单机构保留域需传收单代码如：{acqCode=00215800}，商户直接接入upop不传收单代码
		};*/
        //特殊配置商户
        String[] valueVo = new String[]{
                QuickPayConf.version,//协议版本
                QuickPayConf.charset,//字符编码
                "",//收单机构代码
                "100000000000069",//商户代码
                "特殊配置商户",//商户简称
                "2011060512345678",//订单号
                "2012",//金额
                "156",//币种
                "{phoneNumber=13888888888&cardNumber=6212341111111111111}"//保留域  说明：如果是收单机构保留域需传收单代码如：{acqCode=00215800}，商户直接接入upop不传收单代码
        };
        QuickPayUtils quickPayUtils = new QuickPayUtils();
        String res = quickPayUtils.doPostQueryCmd(QuickPayConf.smsUrl, new QuickPayUtils().createBackStrForBackTrans(valueVo, QuickPayConf.smsVo));

        if (res != null && !"".equals(res)) {

            String[] arr = QuickPayUtils.getResArr(res);

            if (new QuickPayQuery().checkSecurity(arr)) {//验证签名
                merBusiness(arr);//商户业务逻辑
            }
        } else {
            System.out.println("报文格式为空");
        }
    }

    //商户的业务逻辑
    public void merBusiness(String[] arr) {

        //以下是商户业务处理
        String smsResult = "";
        for (int i = 0; i < arr.length; i++) {
            String[] smsResultArr = arr[i].split("=");
            // 处理商户业务逻辑
            if (smsResultArr.length >= 2 && "respMsg".equals(smsResultArr[0])) {
                smsResult = arr[i].substring(smsResultArr[0].length() + 1);

                break;
            }
        }
        if (smsResult != "") {
            System.out.println(smsResult);
        }
    }

}
