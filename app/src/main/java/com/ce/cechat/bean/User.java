package com.ce.cechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author CE Chen
 *
 * User bean
 */
public class User implements Parcelable, Serializable {

    protected String hyphenateId;

    protected String name;

    protected String nickname;

    protected String head;

    public User() {
    }

    public User(String name) {
        this.hyphenateId = name;
        this.name = name;
        this.nickname = name;
    }

    public User(String hyphenateId, String name, String nickname, String head) {
        this.hyphenateId = hyphenateId;
        this.name = name;
        this.nickname = nickname;
        this.head = head;
    }

    protected User(Parcel in) {
        hyphenateId = in.readString();
        name = in.readString();
        nickname = in.readString();
        head = in.readString();
    }

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

    public String getHyphenateId() {
        return hyphenateId;
    }

    public void setHyphenateId(String hyphenateId) {
        this.hyphenateId = hyphenateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "User{ hyphenateId=" + hyphenateId +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head='" + head + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hyphenateId);
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeString(head);
    }
}
