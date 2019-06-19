package com.example.demo.entity;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private Integer id;

    private String userName;

    private String userPass;

    private String encryptionStr;

    private String nickName;

    private Boolean sex;

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
}