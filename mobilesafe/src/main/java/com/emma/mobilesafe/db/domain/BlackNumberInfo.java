package com.emma.mobilesafe.db.domain;

/**
 * Created by Administrator on 2017/4/9.
 */
public class BlackNumberInfo {
    public String phone;
    public String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "BlackNumberInfo{" +
                "mode='" + mode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
