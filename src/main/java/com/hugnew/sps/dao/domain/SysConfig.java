package com.hugnew.sps.dao.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 字典实体
 * Created by Martin on 2016/7/01.
 */
@Table(name = "sps_sys_config")
public class SysConfig {

    /**
     * 参数名
     */
    @Id
    @Column(name = "sysKey")
    private String sysKey;

    /**
     * 修改日期
     */
    @Column(name = "modifyTime")
    private Long modifyTime;

    /**
     * 修改人
     */
    @Column(name = "accountId")
    private Long accountId;

    /**
     * 参数值
     */
    @Column(name = "sysValue")
    private String sysValue;

    /**
     * 参数描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 字典对应的操作
     */
    @Column(name = "operator")
    private String operator;

    /**
     * 是否是json字典，true为是，false不是，默认false
     */
    @Column(name = "isCache")
    private Boolean isCache;

    /**
     * 获取参数名
     *
     * @return sysKey - 参数名
     */
    public String getSysKey() {
        return sysKey;
    }

    /**
     * 设置参数名
     *
     * @param sysKey 参数名
     */
    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    /**
     * 获取修改日期
     *
     * @return modifyTime - 修改日期
     */
    public Long getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改日期
     *
     * @param modifyTime 修改日期
     */
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取修改人
     *
     * @return accountId - 修改人
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置修改人
     *
     * @param accountId 修改人
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取参数值
     *
     * @return sysValue - 参数值
     */
    public String getSysValue() {
        return sysValue;
    }

    /**
     * 设置参数值
     *
     * @param sysValue 参数值
     */
    public void setSysValue(String sysValue) {
        this.sysValue = sysValue;
    }

    /**
     * 获取 参数描述
     *
     * @return 参数描述 description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 参数描述
     *
     * @param description 参数描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 is cache.
     *
     * @return the is cache
     */
    public Boolean getIsCache() {
        return isCache;
    }

    /**
     * 设置 is cache.
     *
     * @param isCache the is cache
     */
    public void setIsCache(Boolean isCache) {
        this.isCache = isCache;
    }

    /**
     * 获取 operator.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置 operator.
     *
     * @param operator the operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}