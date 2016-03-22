package com.xm.demo.retrofit.entity;

import java.util.List;

/**
 * Created by lvxia on 2016-03-22.
 */
public class User {

    private int type;
    private String username;
    private String sex;
    private String mobile;
    private String email;
    private String tel;
    private String avatarid;

    private List<DeptBean> dept;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatarid() {
        return avatarid;
    }

    public void setAvatarid(String avatarid) {
        this.avatarid = avatarid;
    }

    public List<DeptBean> getDept() {
        return dept;
    }

    public void setDept(List<DeptBean> dept) {
        this.dept = dept;
    }

    public static class DeptBean {
        private String no;
        private String name;
        private String title;
        private int isdefault;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(int isdefault) {
            this.isdefault = isdefault;
        }
    }

}
