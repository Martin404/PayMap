package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 名称：商户后续类
 * 功能：
 * 类属性：方法调用
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 */

public class QuickPayBack {

    public static void main(String[] aa) {
        new QuickPayBack().doBackTrans();
    }

    /**
     * 后续交易入口
     *
     * @param transType   交易类型
     * @param orderNumber 商户订单号
     * @param orderTime   商户订单时间
     */
    public void doBackTrans() {
        //商户需要组装如下对象的数据
        String[] valueVo = new String[]{
                QuickPayConf.version,//协议版本
                QuickPayConf.charset,//字符编码
                "04",//交易类型
                "",//原始交易流水号
                QuickPayConf.merCode,//商户代码
                QuickPayConf.merName,//商户简称
                "",//收单机构代码（仅收单机构接入需要填写）
                "",//商户类别（收单机构接入需要填写）
                "",//商品URL
                "",//商品名称
                "",//商品单价 单位：分
                "",//商品数量
                "",//折扣 单位：分
                "",//运费 单位：分
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),//订单号（需要商户自己生成）
                "3100",//交易金额 单位：分
                "156",//交易币种
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),//交易时间
                "127.0.0.1",//用户IP
                "",//用户真实姓名
                "",//默认支付方式
                "",//默认银行编号
                "300000",//交易超时时间
                QuickPayConf.merFrontEndUrl,// 前台回调商户URL
                QuickPayConf.merBackEndUrl,// 后台回调商户URL
                ""//商户保留域
        };

        String res = getUnionPaySyncRes(valueVo);
        if (res != null && !"".equals(res)) {

            String[] arr = QuickPayUtils.getResArr(res);

            if (checkSecurity(arr)) {//验证签名
                merBusiness(arr);//商户业务逻辑
            }
        } else {
            System.out.println("报文格式为空");
        }
    }


    /**
     * 向银联发送后续交易请求
     *
     * @param transType
     * @param orderNumber
     * @param orderTime
     * @return
     */
    public String getUnionPaySyncRes(String[] valueVo) {
        QuickPayUtils quickPayUtils = new QuickPayUtils();
        return quickPayUtils.doPostQueryCmd(QuickPayConf.backStagegateWay, new QuickPayUtils().createBackStrForBackTrans(valueVo, QuickPayConf.reqVo));

    }

    //验证签名
    public boolean checkSecurity(String[] arr) {
        //验证签名
        int checkedRes = new QuickPayUtils().checkSecurity(arr);
        if (checkedRes == 1) {
            return true;
        } else if (checkedRes == 0) {
            System.out.println("验证签名失败");
            return false;
        } else if (checkedRes == 2) {
            System.out.println("报文格式错误");
            return false;
        }
        return false;
    }

    // 商户的业务逻辑
    public void merBusiness(String[] arr) {

        // 以下是商户业务处理
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            String[] resultArr = arr[i].split("=");
            // 处理商户业务逻辑
            if (resultArr.length >= 2 && "respCode".equals(resultArr[0])) {
                result = arr[i].substring(resultArr[0].length() + 1);

                break;
            }
        }
        if ("00".equals(result)) {
            System.out.println("银联开始受理请求，但是并不表示处理成功。交易类型为（01，31，04等）等成功后有后台通知发到报文的后台通知地址。交易类型71，商户自己发查询确定状态");
        } else if ("30".equals(result)) {
            System.out.println(result + ":报文格式错误");
        } else if ("94".equals(result)) {
            System.out.println(result + ":重复交易");
        } else if ("25".equals(result)) {
            System.out.println(result + ":查询原交易失败");
        } else if ("36".equals(result)) {
            System.out.println(result + ":交易金额超限");
        } else {
            System.out.println(result + ":其他错误");
        }

    }
}
