package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 1/2/2017.
 */

public class OnGoingDataModel {
    private String EventDescription;
    private String EventName;
    private String date;
    private String time;
    private String EventImage;
    private String Chiefguest;
    private String EventId;
    private String amount;
    private String paymentstatus;

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventImage() {
        return EventImage;
    }

    public void setEventImage(String eventImage) {
        EventImage = eventImage;
    }

    public String getChiefguest() {
        return Chiefguest;
    }

    public void setChiefguest(String chiefguest) {
        Chiefguest = chiefguest;
    }

    public OnGoingDataModel() {
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
