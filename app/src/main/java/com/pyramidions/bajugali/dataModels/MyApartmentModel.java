package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 1/10/2017.
 */

public class MyApartmentModel {

    private String Memberimage;
    private String Name;
    private String designation;

    public MyApartmentModel() {
    }

    public String getMemberimage() {
        return Memberimage;
    }

    public void setMemberimage(String memberimage) {
        Memberimage = memberimage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
