package com.priti.model;

public class PreDeliveryUser {
    private String uid;
    private String fullName;
    private String email;
    private int age;
    private int pregnancyWeek;
    private String mobile;

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getuserEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getPregnancyWeek() { return pregnancyWeek; }
    public void setPregnancyWeek(int pregnancyWeek) { this.pregnancyWeek = pregnancyWeek; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

}
