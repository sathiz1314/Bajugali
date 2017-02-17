package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 1/10/2017.
 */

public class SubmitRequestModel {

    private String TicketNum;
    private String subject;
    private String submitdate;
    private String status;
    private String assignedto;
    private String Resolutioncomment;
    private String ResolvedOnDate;
    private String description;

    public SubmitRequestModel() {
    }

    public String getTicketNum() {
        return TicketNum;
    }

    public void setTicketNum(String ticketNum) {
        TicketNum = ticketNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(String submitdate) {
        this.submitdate = submitdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedto() {
        return assignedto;
    }

    public void setAssignedto(String assignedto) {
        this.assignedto = assignedto;
    }

    public String getResolutioncomment() {
        return Resolutioncomment;
    }

    public void setResolutioncomment(String resolutioncomment) {
        Resolutioncomment = resolutioncomment;
    }

    public String getResolvedOnDate() {
        return ResolvedOnDate;
    }

    public void setResolvedOnDate(String resolvedOnDate) {
        ResolvedOnDate = resolvedOnDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
