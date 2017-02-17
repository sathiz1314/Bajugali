package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 1/3/2017.
 */

public class AmenitiesModel {

    private String AmID;
    private String ApId;
    private String Amenityname;
    private String Description;
    private String firstshift;
    private String seconfdshift;
    private String WorkingDays;
    private String rulesandregulations;
    private String price;
    private String booking;

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public AmenitiesModel() {
    }

    public String getAmID() {
        return AmID;
    }

    public void setAmID(String amID) {
        AmID = amID;
    }

    public String getApId() {
        return ApId;
    }

    public void setApId(String apId) {
        ApId = apId;
    }

    public String getAmenityname() {
        return Amenityname;
    }

    public void setAmenityname(String amenityname) {
        Amenityname = amenityname;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFirstshift() {
        return firstshift;
    }

    public void setFirstshift(String firstshift) {
        this.firstshift = firstshift;
    }

    public String getSeconfdshift() {
        return seconfdshift;
    }

    public void setSeconfdshift(String seconfdshift) {
        this.seconfdshift = seconfdshift;
    }

    public String getWorkingDays() {
        return WorkingDays;
    }

    public void setWorkingDays(String workingDays) {
        WorkingDays = workingDays;
    }

    public String getRulesandregulations() {
        return rulesandregulations;
    }

    public void setRulesandregulations(String rulesandregulations) {
        this.rulesandregulations = rulesandregulations;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
