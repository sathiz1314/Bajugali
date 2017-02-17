package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 1/24/2017.
 */

public class AddModel {

    private String status;
    private String adtitle;
    private String description;
    private String category;
    private String displaycontactnumber;
    private String price;
    private String AdStartDate;
    private String AdEndDate;
    private String adimage;

    public AddModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDisplaycontactnumber() {
        return displaycontactnumber;
    }

    public void setDisplaycontactnumber(String displaycontactnumber) {
        this.displaycontactnumber = displaycontactnumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdStartDate() {
        return AdStartDate;
    }

    public void setAdStartDate(String adStartDate) {
        AdStartDate = adStartDate;
    }

    public String getAdEndDate() {
        return AdEndDate;
    }

    public void setAdEndDate(String adEndDate) {
        AdEndDate = adEndDate;
    }

    public String getAdimage() {
        return adimage;
    }

    public void setAdimage(String adimage) {
        this.adimage = adimage;
    }
}
