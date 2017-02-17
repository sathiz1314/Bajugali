package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 2/8/2017.
 */

public class DealDetailModel {
    private String DealId;
    private String DealName;
    private String DealImage;
    private String NormalPrice;
    private String OfferPrice;
    private String Description;
    private String DealStartDate;
    private String amount;
    private String DealEndDate;
    private String DealLocation;
    private String NoOfRedemption;

    public DealDetailModel(){

    }

    public String getDealId() {
        return DealId;
    }

    public void setDealId(String dealId) {
        DealId = dealId;
    }

    public String getDealName() {
        return DealName;
    }

    public void setDealName(String dealName) {
        DealName = dealName;
    }

    public String getDealImage() {
        return DealImage;
    }

    public void setDealImage(String dealImage) {
        DealImage = dealImage;
    }

    public String getNormalPrice() {
        return NormalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        NormalPrice = normalPrice;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDealStartDate() {
        return DealStartDate;
    }

    public void setDealStartDate(String dealStartDate) {
        DealStartDate = dealStartDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDealEndDate() {
        return DealEndDate;
    }

    public void setDealEndDate(String dealEndDate) {
        DealEndDate = dealEndDate;
    }

    public String getDealLocation() {
        return DealLocation;
    }

    public void setDealLocation(String dealLocation) {
        DealLocation = dealLocation;
    }

    public String getNoOfRedemption() {
        return NoOfRedemption;
    }

    public void setNoOfRedemption(String noOfRedemption) {
        NoOfRedemption = noOfRedemption;
    }





}
