package com.dgsw.realnamechatting.data;

import java.time.LocalDateTime;

public class User {
    private String uid;
    private String email;
    private String password;
    private String name;
    private String phone;
//    private LocalDateTime birthday;
//    private String profileImgUrl;
//    private LocalDateTime createDate;
    private String profileMessage;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public LocalDateTime getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(LocalDateTime birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getProfileImgUrl() {
//        return profileImgUrl;
//    }
//
//    public void setProfileImgUrl(String profileImgUrl) {
//        this.profileImgUrl = profileImgUrl;
//    }
//
//    public LocalDateTime getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }

    public String getProfileMessage() {
        return profileMessage;
    }

    public void setProfileMessage(String profileMessage) {
        this.profileMessage = profileMessage;
    }
}
