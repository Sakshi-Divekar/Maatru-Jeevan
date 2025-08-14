package com.priti.model;

public class UserEmergency {
    private String uid;
    private String name;
    private String emergencyContact;
    private String district;
    private double latitude;
    private double longitude;

    public UserEmergency() {}

    public UserEmergency(String uid, String name, String emergencyContact, String district, double latitude, double longitude) {
        this.uid = uid;
        this.name = name;
        this.emergencyContact = emergencyContact;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmergencyContact() { return emergencyContact; }
    public String getDistrict() { return district; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void setUid(String uid) { this.uid = uid; }
    public void setName(String name) { this.name = name; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    public void setDistrict(String district) { this.district = district; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}