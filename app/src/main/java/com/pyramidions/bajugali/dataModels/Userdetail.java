
package com.pyramidions.bajugali.dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Userdetail {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("ContactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("owneruserid")
    @Expose
    private String owneruserid;
    @SerializedName("ApId")
    @Expose
    private String apId;
    @SerializedName("accesstoken")
    @Expose
    private String accesstoken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwneruserid() {
        return owneruserid;
    }

    public void setOwneruserid(String owneruserid) {
        this.owneruserid = owneruserid;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

}
