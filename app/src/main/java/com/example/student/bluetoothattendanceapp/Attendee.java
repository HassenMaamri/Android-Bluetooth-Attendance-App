package com.example.student.bluetoothattendanceapp;

public class Attendee {

    private String fName;
    private String lName;


    public Attendee(String fName, String lName){
        this.fName = fName;
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
