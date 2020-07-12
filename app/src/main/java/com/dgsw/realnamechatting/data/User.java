package com.dgsw.realnamechatting.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

public class User implements Parcelable {
    private String uid;
    private String email;
    private String password;
    private String name;
    private String phone;
//    private LocalDateTime birthday;
//    private String profileImgUrl;
//    private LocalDateTime createDate;
    private String profileMessage;

    protected User(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        profileMessage = in.readString();
    }

    public User() { }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(profileMessage);
    }
}
