package com.example.student.bluetoothattendanceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {

    private int id;
    private String name;
    private String address;

    public Meeting() {
    }

    protected Meeting(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel source) {
            return new Meeting(source);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return address;
    }

    public void setPhone(String phone) {
        this.address = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
    }

}