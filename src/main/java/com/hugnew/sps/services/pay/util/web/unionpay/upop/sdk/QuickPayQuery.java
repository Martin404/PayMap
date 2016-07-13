package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;


/**
 * 名称：商户查询类
 * 功能：
 * 类属性：方法调用
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 */

public class QuickPayQuery {

    public static void main(String[] aa) {
        new QuickPayQuery().queryTrans("01", "201511241949571", "20151124113058");
    }


    /**
     * 查询交易入口
     *
     * @param transType   交易类型
     * @param orderNumber 商户订单号
     * @param orderTime   商户订单时间
     */
    public String queryTrans(String transType, String orderNumber, String orderTime) {
        String res = query(transType, orderNumber, orderTime);
        if (res != null && !"".equals(res)) {

            String[] arr = QuickPayUtils.getResArr(res);

            if (checkSecurity(arr)) {//验证签名
                return merBusiness(arr);//商户业务逻辑
            } else {
                return "5";
            }
        } else {
            System.out.println("报文格式为空");
            return "5";
        }
    }


    /**
     * 向银联发送查询请求
     *
     * @param transType
     * @param orderNumber
     * @param orderTime
     * @return
     */
    public String query(String transType, String orderNumber, String orderTime) {
        String[] valueVo = new String[]{
                QuickPayConf.version,//协议版本
                QuickPayConf.charset,//字符编码
                transType,//交易类型
                QuickPayConf.merCode,//商户代码
                orderNumber,//订单号
                orderTime,//交易时间
                ""//保留域  说明：如果是收单机构保留域需传收单代码如：{acqCode=00215800}，商户直接接入upop不传收单代码
        };
        QuickPayUtils quickPayUtils = new QuickPayUtils();
        return quickPayUtils.doPostQueryCmd(QuickPayConf.queryUrl, new QuickPayUtils().createBackStr(valueVo, QuickPayConf.queryVo));

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

    //商户的业务逻辑
    public String merBusiness(String[] arr) {
        /**
         * queryResult=0或者2时 respCode为00，其余情况下respCode为非全零的两位错误码
         * queryResult为空时报文格式错误
         * queryResult：
         * 0：成功（响应码respCode为00）
         * 1：失败（响应码respCode非00）
         * 2：处理中（响应码respCode为00）
         * 3：无此交易（响应码respCode非00）
         */

        //以下是商户业务处理
        String queryResult = "";
        for (int i = 0; i < arr.length; i++) {
            String[] queryResultArr = arr[i].split("=");
            // 处理商户业务逻辑
            if (queryResultArr.length >= 2 && "queryResult".equals(queryResultArr[0])) {
                queryResult = arr[i].substring(queryResultArr[0].length() + 1);

                break;
            }
        }
        if (queryResult != "") {
            return queryResult;
            /*if ("0".equals(queryResult)) {
				System.out.println("交易成功");
			}
			if ("1".equals(queryResult)) {
				System.out.println("交易失败");
			}
			if ("2".equals(queryResult)) {
				System.out.println("交易处理中");
			}
			if ("3".equals(queryResult)) {
				System.out.println("无此交易");
			}*/
        } else {
            return "5";
            //System.out.println("报文格式错误");
        }

    }
}
	