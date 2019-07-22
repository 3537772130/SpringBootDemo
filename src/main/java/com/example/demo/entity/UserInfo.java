package com.example.demo.entity;

import com.example.demo.util.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private Integer id;

    private String userName;

    private String userPass;

    private String encryptionStr;

    private String nickName;

    private Boolean sex;

    private String headPortrait;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = Constants.DATE_TIME_JDK)
    private Date createTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = Constants.DATE_TIME_JDK)
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    public String getEncryptionStr() {
        return encryptionStr;
    }

    public void setEncryptionStr(String encryptionStr) {
        this.encryptionStr = encryptionStr == null ? null : encryptionStr.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UserInfo getUserInfo(UserInfo info) {
        info.setUserPass(null);
        info.setEncryptionStr(null);
        return info;
    }

    public UserInfo getUserInfoToUpdate(UserInfo info) {
        info.setUserName(null);
        info.setUserPass(null);
        info.setEncryptionStr(null);
        return info;
    }

    public UserInfo getUserInfoToPassword(UserInfo info) {
        UserInfo user = new UserInfo();
        user.setId(info.getId());
        user.setUserPass(info.userPass);
        return user;
    }
}
