package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 12/9/2016.
 */

public class BillModel {
    private String month;
    private String dueAmount;
    private String dueDate;
    private String StatusOfPayment;

    public BillModel() {
    }

    public String getMonth() {
        return month;
    }

    public String getStatusOfPayment() {
        return StatusOfPayment;
    }

    public void setStatusOfPayment(String statusOfPayment) {
        StatusOfPayment = statusOfPayment;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
