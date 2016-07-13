package com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称：支付应答类
 * 功能：servlet方式，建议前台通知和后台通知用两个类实现，后台通知进行商户的数据库等处理,前台通知实现客户浏览器跳转
 * 类属性：
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 */

public class QuickPayResSampleServLet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = -6247574514957274823L;

    public void init() {

    }

    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(QuickPayConf.charset);
        } catch (UnsupportedEncodingException e) {
        }

        String[] resArr = new String[QuickPayConf.notifyVo.length];
        for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
            resArr[i] = request.getParameter(QuickPayConf.notifyVo[i]);
        }
        String signature = request.getParameter(QuickPayConf.signature);
        String signMethod = request.getParameter(QuickPayConf.signMethod);

        response.setContentType("text/html;charset=" + QuickPayConf.charset);
        response.setCharacterEncoding(QuickPayConf.charset);

        try {
            Boolean signatureCheck = new QuickPayUtils().checkSign(resArr, signMethod, signature);
            response.getWriter()
                    .append("建议前台通知和后台通知用两个类实现，后台通知进行商户的数据库等处理,前台通知实现客户浏览器跳转<br>")
                    .append("签名是否正确：" + signatureCheck)
                    .append("<br>交易是否成功：" + "00".equals(resArr[10]));
            if (!"00".equals(resArr[10])) {
                response.getWriter().append("<br>失败原因:" + resArr[11]);
            }
        } catch (IOException e) {

        }

        response.setStatus(HttpServletResponse.SC_OK);
    }


}
