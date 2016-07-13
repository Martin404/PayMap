package com.hugnew.sps.dto;

import java.io.Serializable;

/**
 * APP端请求头所带的信息
 * Created by Martin on 2016/7/01.
 */
public class MobileInfo implements Serializable {

    /**
     * app版本
     */
    private String appVersion;

    /**
     * app系统版本
     */
    private String appSystemVersion;

    /**
     * app设备ID
     */
    private String appDeviceId;

    /**
     * app设备宽
     */
    private Integer appDeviceWidth;

    /**
     * app设备高
     */
    private Integer appDeviceHeight;

    /**
     * 是否开启夜间模式
     */
    private Boolean nightMode;

    /**
     * Instantiates a new Mobile info.
     *
     * @param appVersion the app version
     * @param appSystemVersion the app system version
     * @param appDeviceId the app device id
     * @param appDeviceWidth the app device width
     * @param appDeviceHeight the app device height
     * @param nightMode the night mode
     */
    public MobileInfo(String appVersion, String appSystemVersion, String appDeviceId, Integer appDeviceWidth, Integer appDeviceHeight, Boolean nightMode) {
        this.appVersion = appVersion;
        this.appSystemVersion = appSystemVersion;
        this.appDeviceId = appDeviceId;
        this.appDeviceWidth = appDeviceWidth;
        this.appDeviceHeight = appDeviceHeight;
        this.nightMode = nightMode;
    }

    /**
     * Instantiates a new Mobile info.
     */
    public MobileInfo() {
    }

    /**
     * 获取 app version.
     *
     * @return the app version
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置 app version.
     *
     * @param appVersion the app version
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取 app system version.
     *
     * @return the app system version
     */
    public String getAppSystemVersion() {
        return appSystemVersion;
    }

    /**
     * 设置 app system version.
     *
     * @param appSystemVersion the app system version
     */
    public void setAppSystemVersion(String appSystemVersion) {
        this.appSystemVersion = appSystemVersion;
    }

    /**
     * 获取 app device id.
     *
     * @return the app device id
     */
    public String getAppDeviceId() {
        return appDeviceId;
    }

    /**
     * 设置 app device id.
     *
     * @param appDeviceId the app device id
     */
    public void setAppDeviceId(String appDeviceId) {
        this.appDeviceId = appDeviceId;
    }

    /**
     * 获取 app device width.
     *
     * @return the app device width
     */
    public Integer getAppDeviceWidth() {
        return appDeviceWidth;
    }

    /**
     * 设置 app device width.
     *
     * @param appDeviceWidth the app device width
     */
    public void setAppDeviceWidth(Integer appDeviceWidth) {
        this.appDeviceWidth = appDeviceWidth;
    }

    /**
     * 获取 app device height.
     *
     * @return the app device height
     */
    public Integer getAppDeviceHeight() {
        return appDeviceHeight;
    }

    /**
     * 设置 app device height.
     *
     * @param appDeviceHeight the app device height
     */
    public void setAppDeviceHeight(Integer appDeviceHeight) {
        this.appDeviceHeight = appDeviceHeight;
    }

    /**
     * 获取 night mode.
     *
     * @return the night mode
     */
    public Boolean getNightMode() {
        return nightMode;
    }

    /**
     * 设置 night mode.
     *
     * @param nightMode the night mode
     */
    public void setNightMode(Boolean nightMode) {
        this.nightMode = nightMode;
    }
}
