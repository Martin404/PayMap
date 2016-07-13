package com.hugnew.sps.dao.domain;


import javax.persistence.*;

/**
 * 交易记录实体
 * Created by Martin on 2016/7/01.
 */
@Table(name = "sps_pay_map")
public class PayMap extends BaseDomain {

    /**
     * 所属订单ID
     */
    @Column(name = "orderId")
    private Long orderId;

    /**
     * 所属订单code
     */
    @Column(name = "orderCode")
    private String orderCode;

    /**
     * 临时支付号ID
     */
    @Column(name = "tempPayCode")
    private String tempPayCode;

    /**
     * 所属平台
     */
    @Column(name = "platform")
    private String platform;

    /**
     * 支付所生成的请求信息
     */
    @Column(name = "payParams")
    private String payParams;

    /**
     * 支付后回调时的详细信息
     */
    @Column(name = "retMsg")
    private String retMsg;

    /**
     * 备用消息
     */
    @Column(name = "retMsg2")
    private String retMsg2;

    /**
     * 是否已支付0否；1是
     */
    @Column(name = "isPaid")
    private String isPaid;

    /**
     * 流水号
     */
    @Column(name = "swiftNumber")
    private String swiftNumber;

    /**
     * 通知回调时间
     */
    @Column(name = "notify_time")
    private Long notifyTime;

    /**
     * 支付请求业务来源
     */
    @Column(name = "requestBiz")
    private String requestBiz;

    /**
     * 冗余字段1
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 冗余字段2
     */
    @Column(name = "remark2")
    private String remark2;

    /**
     * 获取 swift number.
     *
     * @return the swift number
     */
    public String getSwiftNumber() {
        return swiftNumber;
    }

    /**
     * 设置 swift number.
     *
     * @param swiftNumber the swift number
     */
    public void setSwiftNumber(String swiftNumber) {
        this.swiftNumber = swiftNumber;
    }

    /**
     * 获取所属订单ID
     *
     * @return orderId - 所属订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置所属订单ID
     *
     * @param orderId 所属订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取所属订单code
     *
     * @return orderCode - 所属订单code
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置所属订单code
     *
     * @param orderCode 所属订单code
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 获取临时支付号ID
     *
     * @return tempPayCode - 临时支付号ID
     */
    public String getTempPayCode() {
        return tempPayCode;
    }

    /**
     * 设置临时支付号ID
     *
     * @param tempPayCode 临时支付号ID
     */
    public void setTempPayCode(String tempPayCode) {
        this.tempPayCode = tempPayCode;
    }

    /**
     * 获取所属平台
     *
     * @return platform - 所属平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置所属平台
     *
     * @param platform 所属平台
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取支付所生成的请求信息
     *
     * @return payParams - 支付所生成的请求信息
     */
    public String getPayParams() {
        return payParams;
    }

    /**
     * 设置支付所生成的请求信息
     *
     * @param payParams 支付所生成的请求信息
     */
    public void setPayParams(String payParams) {
        this.payParams = payParams;
    }

    /**
     * 获取支付后回调时的详细信息
     *
     * @return retMsg - 支付后回调时的详细信息
     */
    public String getRetMsg() {
        return retMsg;
    }

    /**
     * 设置支付后回调时的详细信息
     *
     * @param retMsg 支付后回调时的详细信息
     */
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
     * 获取备用消息
     *
     * @return retMsg2 - 备用消息
     */
    public String getRetMsg2() {
        return retMsg2;
    }

    /**
     * 设置备用消息
     *
     * @param retMsg2 备用消息
     */
    public void setRetMsg2(String retMsg2) {
        this.retMsg2 = retMsg2;
    }

    /**
     * 获取是否已支付0否；1是
     *
     * @return isPaid - 是否已支付0否；1是
     */
    public String getIsPaid() {
        return isPaid;
    }

    /**
     * 设置是否已支付0否；1是
     *
     * @param isPaid 是否已支付0否；1是
     */
    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * 获取 remark.
     *
     * @return remark remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 remark.
     *
     * @param remark the remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 remark 2.
     *
     * @return remark2 remark 2
     */
    public String getRemark2() {
        return remark2;
    }

    /**
     * 设置 remark 2.
     *
     * @param remark2 the remark 2
     */
    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    /**
     * 获取 notify time.
     *
     * @return the notify time
     */
    public Long getNotifyTime() {
        return notifyTime;
    }

    /**
     * 设置 notify time.
     *
     * @param notifyTime the notify time
     */
    public void setNotifyTime(Long notifyTime) {
        this.notifyTime = notifyTime;
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
}