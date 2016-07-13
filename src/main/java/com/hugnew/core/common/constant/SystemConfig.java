package com.hugnew.core.common.constant;


/**
 * 从SpringApplicationContext中设置的系统参数
 * Created by Martin on 2016/7/01.
 */
public class SystemConfig {

    //系统默认游客的用户名
    private static String guestUsername = "";

    private SystemConfig() {
    }

    public static String getGuestUsername() {
        return guestUsername;
    }

    public static void setGuestUsername(String guestUsername) {
        SystemConfig.guestUsername = guestUsername;
    }
}
