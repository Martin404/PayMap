package com.hugnew.sps.services.pay.util.web.unionpay.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;


/**
 * 名称：商户后台通知类
 * 功能：
 * 类属性：
 * 方法调用 版本：5.0
 * 日期：2014-07
 * 作者：中国银联ACP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 */

public class BackRcvResponse extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 3414800502432002480L;

    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                //System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    @Override
    public void init() throws ServletException {
        /**
         * 参数初始化
         * 在java main 方式运行时必须每次都执行加载
         * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
         */
        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        req.setCharacterEncoding("ISO-8859-1");
        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取请求参数中所有的信息
        Map<String, String> reqParam = getAllRequestParam(req);
        // 打印请求报文
        LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Entry<String, String> e = it.next();
                String key = e.getKey();
                String value = e.getValue();
                value = new String(value.getBytes("ISO-8859-1"), encoding);
                valideData.put(key, value);
            }
        }

        // 验证签名
        if (!SDKUtil.validate(valideData, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
            LogUtil.writeLog("验证签名结果[成功].");
        }

        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        this.doPost(req, resp);
    }

}
