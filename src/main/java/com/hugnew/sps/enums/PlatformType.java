package com.hugnew.sps.enums;

/**
 * Created by Martin on 2016/7/01.
 */
public enum PlatformType {

    DD("DD", "当当"),
    TB("TB", "淘宝"),
    JD("JD", "京东"),
    ICBC("ICBC", "工行"),
    UNIONPAY("UNIONPAY", "银联支付"),
    CEB("CEB", "光大银行"),
    PSBC("PSBC", "邮政储蓄"),
    CCB("CCB", "建行"),
    HZBSQ("HZBSQ", "杭州保税区"),
    NBBSQ("NBBSQ", "宁波保税区"),
    WECHAT("WECHAT", "微信支付"),
    YHD("YHD", "一号店");
    private String value;
    private String desc;

    PlatformType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static PlatformType getPlatform(Integer value) {
        if (1 == value || 2 == value || 6 == value) {
            return PlatformType.TB;
        } else if (3 == value || 4 == value || 5 == value) {
            return PlatformType.UNIONPAY;
        } else if (7 == value || 8 == value) {
            return PlatformType.PSBC;
        } else if (9 == value || 10 == value || 12 == value) {
            return PlatformType.CEB;
        } else if (11 == value) {
            return PlatformType.WECHAT;
        }
        return null;
    }

    public String value() {
        return value;
    }

    public String desc() {
        return desc;
    }
}
