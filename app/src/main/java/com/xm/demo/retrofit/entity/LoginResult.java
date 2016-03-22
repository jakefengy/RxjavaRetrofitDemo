package com.xm.demo.retrofit.entity;

/**
 * Created by lvxia on 2016-03-22.
 */
public class LoginResult {

    private String AccessToken;
    private String expires_in;
    private String uid;
    private String name;
    private int logintimes;
    private String avatar;
    private String orgname;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogintimes() {
        return logintimes;
    }

    public void setLogintimes(int logintimes) {
        this.logintimes = logintimes;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

}
