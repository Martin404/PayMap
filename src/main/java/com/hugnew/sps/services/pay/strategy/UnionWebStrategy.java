package com.hugnew.sps.services.pay.strategy;

import com.hugnew.core.util.PropertiesUtil;
import com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk.QuickPayConf;
import com.hugnew.sps.services.pay.util.web.unionpay.upop.sdk.QuickPayUtils;
import com.hugnew.sps.services.pay.util.web.unionpay.util.DemoBase;
import com.hugnew.sps.enums.PayType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 银联web支付（对接upop，仅用于示范，upop属于老平台，acp是新平台）
 * Created by Martin on 2016/7/01.
 */
public class UnionWebStrategy extends DemoBase implements PayStrategy {

    private static Logger logger = LoggerFactory.getLogger(UnionWebStrategy.class);

    @Override
    public String generatePayParams(PayType payType, Map<String, Object> params) {
        String retUrl = null;
        if (params.size() > 0 && null != params.get("retUrl")) {
            retUrl = (String) params.get("retUrl");
        }
        //商户需要组装如下对象的数据
        String[] valueVo = new String[]{
                QuickPayConf.version,//协议版本
                QuickPayConf.charset,//字符编码
                QuickPayConf.transType,//交易类型
                "",//原始交易流水号
                QuickPayConf.merCode,//商户代码
                QuickPayConf.merName,//商户简称
                "",//收单机构代码（仅收单机构接入需要填写）
                "",//商户类别（收单机构接入需要填写）
                "",//商品URL
                QuickPayConf.commodityName,//商品名称
                "",//商品单价 单位：分
                "",//商品数量
                QuickPayConf.commodityDiscount,//折扣 单位：分
                "",//运费 单位：分
                (String) params.get("payCode"),//订单号（需要商户自己生成）
                String.valueOf(((BigDecimal)params.get("toPay")).multiply(BigDecimal.valueOf(100)).longValue()),//交易金额 单位：分
                QuickPayConf.orderCurrency,//交易币种
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),//交易时间
                QuickPayConf.customerIp,//用户IP
                "",//用户真实姓名
                "",//默认支付方式
                "",//默认银行编号
                "",//交易超时时间
                StringUtils.isNotBlank(retUrl) ? retUrl : PropertiesUtil.getValue("pay.request.retUrl"),// 前台回调商户URL
                PropertiesUtil.getValue("pay.notify.unionpayWeb.url"),// 后台回调商户URL
                ""//商户保留域
        };
        String signType = QuickPayConf.signType;
        String rethtml = new QuickPayUtils().createPayHtml(valueVo, signType);//跳转到银联页面支付
        if(logger.isDebugEnabled()){
            logger.debug("union参数信息:{}", rethtml);
        }
        return rethtml;
    }

}
