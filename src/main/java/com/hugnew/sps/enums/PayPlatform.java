package com.hugnew.sps.enums;

/**
 * 三方支付
 * Created by Martin on 2016/7/01.
 */
public enum PayPlatform {

    UNION_PC("101", "银联(onlinePay)"),
    UNION_APP("102", "银联(手机)"),
    CEB_GATEWAY("201", "光大网关"),
    CEB("202", "光大网页"),
    ALIPAY_GLOBAL("301", "支付宝(国际)"),
    ALIPAY_COMMON("302", "支付宝(普通)"),
    WECHAT_APP("401", "微信支付(开放平台)"),
    WECHAT_WAP("402", "微信支付(公众平台)"),
    ACCOUNT_COMMON("501", "现金账户"),
    PSBC("601", "邮政银行");

    private String code;
    private String label;

    private PayPlatform(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static PayPlatform getByCode(String code) {
        for (PayPlatform o : PayPlatform.values()) {
            if (o.getCode().equals(code)) {
                return o;
            }
        }
        throw new IllegalArgumentException("Not exist "
                + PayPlatform.class.getName() + " for code " + code);
    }
}
