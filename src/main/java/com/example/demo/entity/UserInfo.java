package com.example.demo.entity;

import com.example.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private Integer id;

    private String mobile;

    private String password;

    private String encrypted;

    private String nickName;

    private String avatarUrl;

    private Boolean gender;

    private String birthday;

    private String email;

    private Boolean isDealer;

    private Double balance;

    private Double freeBalance;

    private Integer integral;

    private Integer goodsCount;

    private String extensionCode;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = Constants.DATE_TIME_YMD)
    private Date createDate;

    private Boolean status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted == null ? null : encrypted.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getIsDealer() {
        return isDealer;
    }

    public void setIsDealer(Boolean isDealer) {
        this.isDealer = isDealer;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFreeBalance() {
        return freeBalance;
    }

    public void setFreeBalance(Double freeBalance) {
        this.freeBalance = freeBalance;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode == null ? null : extensionCode.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserInfo getUserInfo(UserInfo info) {
        info.setPassword(null);
        info.setEncrypted(null);
        return info;
    }

    public UserInfo getUserInfoToUpdate(UserInfo info) {
        info.setMobile(null);
        info.setPassword(null);
        info.setEncrypted(null);
        info.setExtensionCode(null);
        return info;
    }

    public UserInfo getUserInfoToPassword(UserInfo info) {
        UserInfo user = new UserInfo();
        user.setId(info.getId());
        user.setPassword(info.getPassword());
        return user;
    }
}
