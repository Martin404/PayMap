package com.hugnew.sps.dao.domain;

import javax.persistence.*;

/**
 * 三方支付实体
 * Created by Martin on 2016/7/01.
 */
@Table(name = "sps_payment")
public class Payment extends BaseDomain {

    /**
     * 支付名称
     */
    @Column(name = "payName")
    private String payName;

    /**
     * 支付关键字
     */
    @Column(name = "payKey")
    private String payKey;

    /**
     * 支付加密数据
     */
    @Column(name = "payData")
    private String payData;

    /**
     * 回调URI
     */
    @Column(name = "payURI")
    private String payURI;

    /**
     * 三方支付LOGO地址
     */
    @Column(name = "payLogoPath")
    private String payLogoPath;

    /**
     * 三方支付LOGO地址
     */
    @Column(name = "pcPayLogoPath")
    private String pcPayLogoPath;

    /**
     * 支付三方LOGO地址
     */
    @Column(name = "shiftPayLogoPath")
    private String shiftPayLogoPath;

    /**
     * 是否支付平台，为了方便pc端显示
     * 1：平台；0：银行；
     */
    @Column(name = "isPlat")
    private Integer isPlat;

    /**
     * 是否web可用：0否，1是
     */
    @Column(name = "isWebOn")
    private Integer isWebOn;

    /**
     * 是否app可用：0否，1是
     */
    @Column(name = "isAppOn")
    private Integer isAppOn;


    /**
     * 获取 shift pay logo path.
     *
     * @return the shift pay logo path
     */
    public String getShiftPayLogoPath() {
        return shiftPayLogoPath;
    }

    /**
     * 设置 shift pay logo path.
     *
     * @param shiftPayLogoPath the shift pay logo path
     */
    public void setShiftPayLogoPath(String shiftPayLogoPath) {
        this.shiftPayLogoPath = shiftPayLogoPath;
    }

    /**
     * 获取 pc pay logo path.
     *
     * @return the pc pay logo path
     */
    public String getPcPayLogoPath() {
        return pcPayLogoPath;
    }

    /**
     * 设置 pc pay logo path.
     *
     * @param pcPayLogoPath the pc pay logo path
     */
    public void setPcPayLogoPath(String pcPayLogoPath) {
        this.pcPayLogoPath = pcPayLogoPath;
    }

    /**
     * 获取 is web on.
     *
     * @return the is web on
     */
    public Integer getIsWebOn() {
        return isWebOn;
    }

    /**
     * 设置 is web on.
     *
     * @param isWebOn the is web on
     */
    public void setIsWebOn(Integer isWebOn) {
        this.isWebOn = isWebOn;
    }

    /**
     * 获取 is app on.
     *
     * @return the is app on
     */
    public Integer getIsAppOn() {
        return isAppOn;
    }

    /**
     * 设置 is app on.
     *
     * @param isAppOn the is app on
     */
    public void setIsAppOn(Integer isAppOn) {
        this.isAppOn = isAppOn;
    }

    /**
     * 获取 is plat.
     *
     * @return the is plat
     */
    public Integer getIsPlat() {
        return isPlat;
    }

    /**
     * 设置 is plat.
     *
     * @param isPlat the is plat
     */
    public void setIsPlat(Integer isPlat) {
        this.isPlat = isPlat;
    }

    /**
     * 获取 pay logo path.
     *
     * @return the pay logo path
     */
    public String getPayLogoPath() {
        return payLogoPath;
    }

    /**
     * 设置 pay logo path.
     *
     * @param payLogoPath the pay logo path
     */
    public void setPayLogoPath(String payLogoPath) {
        this.payLogoPath = payLogoPath;
    }

    /**
     * 获取支付名称
     *
     * @return payName - 支付名称
     */
    public String getPayName() {
        return payName;
    }

    /**
     * 设置支付名称
     *
     * @param payName 支付名称
     */
    public void setPayName(String payName) {
        this.payName = payName;
    }

    /**
     * 获取支付关键字
     *
     * @return payKey - 支付关键字
     */
    public String getPayKey() {
        return payKey;
    }

    /**
     * 设置支付关键字
     *
     * @param payKey 支付关键字
     */
    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    /**
     * 获取支付加密数据
     *
     * @return payData - 支付加密数据
     */
    public String getPayData() {
        return payData;
    }

    /**
     * 设置支付加密数据
     *
     * @param payData 支付加密数据
     */
    public void setPayData(String payData) {
        this.payData = payData;
    }

    /**
     * 获取回调URI
     *
     * @return payURI - 回调URI
     */
    public String getPayURI() {
        return payURI;
    }

    /**
     * 设置回调URI
     *
     * @param payURI 回调URI
     */
    public void setPayURI(String payURI) {
        this.payURI = payURI;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (this.payKey != null ? !this.payKey.equals(payment.payKey) : payment.payKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}