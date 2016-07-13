package com.hugnew.sps.dto;

import java.math.BigDecimal;

/**
 * Created by Martin on 2016/6/16.
 */
public class PayRequestParam {

    /**
     * 通知回调跳转页面
     */
    private String retUrl;

    /**
     * 订单ID
     */
    private Long orderID;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 支付编号
     */
    private String payCode;

    /**
     * 支付平台
     */
    private String onLineStyle;

    /**
     * 支付入口
     */
    private String browseType;

    /**
     * 支付金额
     */
    private BigDecimal toPay;

    /**
     * 商家
     */
    private String sellerName;

    /**
     * 商家编号
     */
    private String sellerCode;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 有效时间
     */
    private Long invalidTime;

    /**
     * 请求业务来源
     */
    private String requestBiz;

    /**
     * 获取 ret url.
     *
     * @return the ret url
     */
    public String getRetUrl() {
        return retUrl;
    }

    /**
     * 设置 ret url.
     *
     * @param retUrl the ret url
     */
    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    /**
     * 获取 order iD.
     *
     * @return the order iD
     */
    public Long getOrderID() {
        return orderID;
    }

    /**
     * 设置 order iD.
     *
     * @param orderID the order iD
     */
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    /**
     * 获取 order code.
     *
     * @return the order code
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置 order code.
     *
     * @param orderCode the order code
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取 on line style.
     *
     * @return the on line style
     */
    public String getOnLineStyle() {
        return onLineStyle;
    }

    /**
     * 设置 on line style.
     *
     * @param onLineStyle the on line style
     */
    public void setOnLineStyle(String onLineStyle) {
        this.onLineStyle = onLineStyle;
    }

    /**
     * 获取 browse type.
     *
     * @return the browse type
     */
    public String getBrowseType() {
        return browseType;
    }

    /**
     * 设置 browse type.
     *
     * @param browseType the browse type
     */
    public void setBrowseType(String browseType) {
        this.browseType = browseType;
    }

    /**
     * 获取 to pay.
     *
     * @return the to pay
     */
    public BigDecimal getToPay() {
        return toPay;
    }

    /**
     * 设置 to pay.
     *
     * @param toPay the to pay
     */
    public void setToPay(BigDecimal toPay) {
        this.toPay = toPay;
    }

    /**
     * 获取 seller name.
     *
     * @return the seller name
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * 设置 seller name.
     *
     * @param sellerName the seller name
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * 获取 seller code.
     *
     * @return the seller code
     */
    public String getSellerCode() {
        return sellerCode;
    }

    /**
     * 设置 seller code.
     *
     * @param sellerCode the seller code
     */
    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    /**
     * 获取 create time.
     *
     * @return the create time
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置 create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 invalid time.
     *
     * @return the invalid time
     */
    public Long getInvalidTime() {
        return invalidTime;
    }

    /**
     * 设置 invalid time.
     *
     * @param invalidTime the invalid time
     */
    public void setInvalidTime(Long invalidTime) {
        this.invalidTime = invalidTime;
    }

    /**
     * 获取 request biz.
     *
     * @return the request biz
     */
    public String getRequestBiz() {
        return requestBiz;
    }

    /**
     * 设置 request biz.
     *
     * @param requestBiz the request biz
     */
    public void setRequestBiz(String requestBiz) {
        this.requestBiz = requestBiz;
    }

    /**
     * 获取 pay code.
     *
     * @return the pay code
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * 设置 pay code.
     *
     * @param payCode the pay code
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
