package com.priti.model;

public class Hospital {
    private String id;
    private String name;
    private String contact;
    private String district;
    private String address;
    private double latitude;
    private double longitude;

    public Hospital() {}

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getDistrict() { return district; }
    public String getAddress() { return address; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setDistrict(String district) { this.district = district; }
    public void setAddress(String address) { this.address = address; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}