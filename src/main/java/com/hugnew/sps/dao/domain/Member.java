package com.hugnew.sps.dao.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户信息实体
 * Created by Martin on 2016/7/01.
 */
@Table(name = "sps_member")
public class Member extends BaseDomain {

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户系统编号
     */
    @Column(name = "userCode")
    private String userCode;

    /**
     * 用户标签
     */
    @Column(name = "userLabel")
    private Integer userLabel;

    /**
     * 用户密码
     */
    @Column(name = "password")
    @NotNull(message = "{member.password.notnull}")
    private String password;

    /**
     * 密码加密的盐
     */
    @Column(name = "salt")
    private String salt;

    /**
     * 用户状态：0 正常 / 1 禁用
     */
    @Column(name = "status")
    @Max(value = 1, message = "{member.status.max}")
    private Integer status;

    /**
     * 会员级别
     */
    @Column(name = "grade")
    private Integer grade;

    /**
     * 真实姓名
     */
    @Column(name = "realName")
    private String realName;

    /**
     * 昵称
     */
    @Column(name = "nickName")
    private String nickName;

    /**
     * 用户头像
     */
    @Column(name = "showImage")
    private String showImage;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Long birthday;

    /**
     * 生日字符
     */
    @Column(name = "birthdayStr")
    private String birthdayStr;

    /**
     * 性别:0 男 / 1 女 / 2 保密
     */
    @Column(name = "sex")
    @Max(value = 2, message = "{member.sex.max}")
    private Integer sex;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 邮箱校验状态：0 未校验 / 1 校验通过
     */
    @Max(value = 1, message = "{member.emailStatus.max}")
    @Column(name = "emailStatus")
    private Integer emailStatus;

    /**
     * 手机号
     */
    @Column(name = "cellphone")
    private String cellphone;

    /**
     * 手机号校验状态：0 未校验 / 1 校验通过
     */
    @Column(name = "phoneStatus")
    @Max(value = 1, message = "{member.phoneStatus.max}")
    private Integer phoneStatus;

    /**
     * 电话号码
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 所在省份
     */
    @Column(name = "province")
    private String province;

    /**
     * 所在城市
     */
    @Column(name = "city")
    private String city;

    /**
     * 所在区县
     */
    @Column(name = "country")
    private String country;

    /**
     * 具体详细地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 添加日期:Unix时间戳
     */
    @Column(name = "createTime")
    private Long createTime;

    /**
     * 最后更新日期：Unix时间戳
     */
    @Column(name = "modifyTime")
    private Long modifyTime;

    /**
     * 更多其他信息
     */
    @Column(name = "otherInfo")
    private String otherInfo;

    /**
     * 证件号码
     */
    @Column(name = "IdCardNo")
    private String IdCardNo;

    /**
     * 推荐人ID
     */
    @Column(name = "referee")
    private Long referee;

    /**
     * 现金账户余额
     */
    @Column(name = "amount")
    private BigDecimal amount;

    /**
     * 剩余积分
     */
    @Column(name = "points")
    private Integer points;

    /**
     * 交易次数
     */
    @Column(name = "orderCount")
    private Integer orderCount;

    /**
     * Odoo对应id
     */
    @Column(name = "odooId")
    private Integer odooId;

    /**
     * 所购订单总金额
     */
    @Column(name = "orderAmount")
    private BigDecimal orderAmount;
    /**
     * 会员标记 0普通会员 1特殊关注会员
     */
    @Column(name = "memberMark")
    private Integer memberMark;
    /**
     * 会员标签
     */
    @Column(name = "memberLabel")
    private String memberLabel;
    /**
     * 标记原因
     */
    @Column(name = "labelReason")
    private String labelReason;
    /**
     * 标记删除
     */
    @Column(name = "del")
    private Boolean del;
    /**
     * 当前等级下会员花费的金额
     */
    @Column(name = "gradeAmount")
    private BigDecimal gradeAmount;
    /**
     * 会员手机改变时间
     */
    @Column(name = "phoneUpdateTime")
    private Long phoneUpdateTime;
    /**
     * 会员邮箱改变时间
     */
    @Column(name = "emailUpdateTime")
    private Long emailUpdateTime;
    /**
     * 会员等级改变时间
     */
    @Column(name = "gradeChangeTime")
    private Long gradeChangeTime;

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户系统编号
     *
     * @return userCode - 用户系统编号
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * 设置用户系统编号
     *
     * @param userCode 用户系统编号
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取 用户标签
     *
     * @return the 用户标签
     */
    public Integer getUserLabel() {
        return userLabel;
    }

    /**
     * 设置 用户标签
     *
     * @param userLabel 用户标签
     */
    public void setUserLabel(Integer userLabel) {
        this.userLabel = userLabel;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取密码加密的盐
     *
     * @return salt - 密码加密的盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码加密的盐
     *
     * @param salt 密码加密的盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取用户状态：0 正常 / 1 禁用
     *
     * @return status - 用户状态：0 正常 / 1 禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置用户状态：0 正常 / 1 禁用
     *
     * @param status 用户状态：0 正常 / 1 禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取会员级别
     *
     * @return grade - 会员级别
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * 设置会员级别
     *
     * @param grade 会员级别
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取真实姓名
     *
     * @return realName - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取昵称
     *
     * @return nickName - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取用户头像
     *
     * @return showImage - 用户头像
     */
    public String getShowImage() {
        return showImage;
    }

    /**
     * 设置用户头像
     *
     * @param showImage 用户头像
     */
    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Long getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取 生日字符
     *
     * @return 生日字符 birthday str
     */
    public String getBirthdayStr() {
        return birthdayStr;
    }

    /**
     * 设置  生日字符
     *
     * @param birthdayStr 生日字符
     */
    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    /**
     * 获取性别:0 男 / 1 女 / 2 保密
     *
     * @return sex - 性别:0 男 / 1 女 / 2 保密
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别:0 男 / 1 女 / 2 保密
     *
     * @param sex 性别:0 男 / 1 女 / 2 保密
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取邮箱校验状态：0 未校验 / 1 校验通过
     *
     * @return emailStatus - 邮箱校验状态：0 未校验 / 1 校验通过
     */
    public Integer getEmailStatus() {
        return emailStatus;
    }

    /**
     * 设置邮箱校验状态：0 未校验 / 1 校验通过
     *
     * @param emailStatus 邮箱校验状态：0 未校验 / 1 校验通过
     */
    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    /**
     * 获取手机号
     *
     * @return cellphone - 手机号
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * 设置手机号
     *
     * @param cellphone 手机号
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * 获取手机号校验状态：0 未校验 / 1 校验通过
     *
     * @return phoneStatus - 手机号校验状态：0 未校验 / 1 校验通过
     */
    public Integer getPhoneStatus() {
        return phoneStatus;
    }

    /**
     * 设置手机号校验状态：0 未校验 / 1 校验通过
     *
     * @param phoneStatus 手机号校验状态：0 未校验 / 1 校验通过
     */
    public void setPhoneStatus(Integer phoneStatus) {
        this.phoneStatus = phoneStatus;
    }

    /**
     * 获取电话号码
     *
     * @return telephone - 电话号码
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置电话号码
     *
     * @param telephone 电话号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取所在省份
     *
     * @return province - 所在省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置所在省份
     *
     * @param province 所在省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取所在城市
     *
     * @return city - 所在城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所在城市
     *
     * @param city 所在城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取所在区县
     *
     * @return country - 所在区县
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置所在区县
     *
     * @param country 所在区县
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取具体详细地址
     *
     * @return address - 具体详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置具体详细地址
     *
     * @param address 具体详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取添加日期:Unix时间戳
     *
     * @return createTime - 添加日期:Unix时间戳
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加日期:Unix时间戳
     *
     * @param createTime 添加日期:Unix时间戳
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后更新日期：Unix时间戳
     *
     * @return modifyTime - 最后更新日期：Unix时间戳
     */
    public Long getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置最后更新日期：Unix时间戳
     *
     * @param modifyTime 最后更新日期：Unix时间戳
     */
    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取更多其他信息
     *
     * @return otherInfo - 更多其他信息
     */
    public String getOtherInfo() {
        return otherInfo;
    }

    /**
     * 设置更多其他信息
     *
     * @param otherInfo 更多其他信息
     */
    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    /**
     * 获取证件号码
     *
     * @return 证件号码 id card no
     */
    public String getIdCardNo() {
        return IdCardNo;
    }

    /**
     * 设置证件号码
     *
     * @param idCardNo 证件号码
     */
    public void setIdCardNo(String idCardNo) {
        IdCardNo = idCardNo;
    }

    /**
     * 获取 推荐人ID
     *
     * @return referee - 推荐人ID
     */
    public Long getReferee() {
        return referee;
    }

    /**
     * 设置 推荐人ID
     *
     * @param referee 推荐人ID
     */
    public void setReferee(Long referee) {
        this.referee = referee;
    }

    /**
     * 获取 现金账户余额
     *
     * @return amount - 现金账户余额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置 现金账户余额
     *
     * @param amount 现金账户余额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取 剩余积分
     *
     * @return pionts - 剩余积分
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 设置 剩余积分
     *
     * @param points 剩余积分
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * 获取交易次数.
     *
     * @return 交易次数 order count
     */
    public Integer getOrderCount() {
        return orderCount;
    }

    /**
     * 设置交易次数.
     *
     * @param orderCount 交易次数
     */
    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    /**
     * 获取 odoo id.
     *
     * @return odooId odoo id
     */
    public Integer getOdooId() {
        return odooId;
    }

    /**
     * 设置 odooid.
     *
     * @param odooId odooId
     */
    public void setOdooId(Integer odooId) {
        this.odooId = odooId;
    }

    /**
     * 获取 所购订单总金额
     *
     * @return 所购订单总金额 order amount
     */
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置 所购订单总金额
     *
     * @param orderAmount 所购订单总金额
     */
    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    /**
     * 获取 会员标记,0-普通会员,1-特殊关注会员
     *
     * @return 会员标记, 0 -普通会员,1-特殊关注会员
     */
    public Integer getMemberMark() {
        return memberMark;
    }

    /**
     * 设置 会员标记,0-普通会员,1-特殊关注会员
     *
     * @param memberMark 会员标记,0-普通会员,1-特殊关注会员
     */
    public void setMemberMark(Integer memberMark) {
        this.memberMark = memberMark;
    }

    /**
     * 获取 会员标签
     *
     * @return 会员标签 member label
     */
    public String getMemberLabel() {
        return memberLabel;
    }

    /**
     * 设置 会员标签
     *
     * @param memberLabel 会员标签
     */
    public void setMemberLabel(String memberLabel) {
        this.memberLabel = memberLabel;
    }

    /**
     * 获取 标记原因
     *
     * @return 标记原因 label reason
     */
    public String getLabelReason() {
        return labelReason;
    }

    /**
     * 设置 标记原因
     *
     * @param labelReason 标记原因
     */
    public void setLabelReason(String labelReason) {
        this.labelReason = labelReason;
    }

    /**
     * 获取 删除标记
     *
     * @return the del 删除标记
     */
    public Boolean getDel() {
        return del;
    }

    /**
     * 设置 删除标记
     *
     * @param del 删除标记
     */
    public void setDel(Boolean del) {
        this.del = del;
    }

    /**
     * 获取 当前等级下花费总金额
     *
     * @return 当前等级下花费总金额 grade amount
     */
    public BigDecimal getGradeAmount() {
        return gradeAmount;
    }

    /**
     * 设置 当前等级下花费总金额
     *
     * @param gradeAmount 当前等级下花费总金额
     */
    public void setGradeAmount(BigDecimal gradeAmount) {
        this.gradeAmount = gradeAmount;
    }

    /**
     * 获取 等级改变时间
     *
     * @return 等级改变时间 grade change time
     */
    public Long getGradeChangeTime() {
        return gradeChangeTime;
    }

    /**
     * 设置 等级改变时间
     *
     * @param gradeChangeTime 等级改变时间
     */
    public void setGradeChangeTime(Long gradeChangeTime) {
        this.gradeChangeTime = gradeChangeTime;
    }

    /**
     * 获取 phone update time.
     *
     * @return the phone update time
     */
    public Long getPhoneUpdateTime() {
        return phoneUpdateTime;
    }

    /**
     * 设置 phone update time.
     *
     * @param phoneUpdateTime the phone update time
     */
    public void setPhoneUpdateTime(Long phoneUpdateTime) {
        this.phoneUpdateTime = phoneUpdateTime;
    }

    /**
     * 获取 email update time.
     *
     * @return the email update time
     */
    public Long getEmailUpdateTime() {
        return emailUpdateTime;
    }

    /**
     * 设置 email update time.
     *
     * @param emailUpdateTime the email update time
     */
    public void setEmailUpdateTime(Long emailUpdateTime) {
        this.emailUpdateTime = emailUpdateTime;
    }
}
