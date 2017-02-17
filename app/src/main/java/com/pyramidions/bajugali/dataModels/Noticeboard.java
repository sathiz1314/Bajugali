
package com.pyramidions.bajugali.dataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Noticeboard {

    @SerializedName("NoticeID")
    @Expose
    private String noticeID;
    @SerializedName("ApId")
    @Expose
    private String apId;
    @SerializedName("NoticeName")
    @Expose
    private String noticeName;
    @SerializedName("contactpersonname")
    @Expose
    private String contactpersonname;
    @SerializedName("NoticeSummary")
    @Expose
    private String noticeSummary;
    @SerializedName("NoticeDescription")
    @Expose
    private String noticeDescription;
    @SerializedName("NoticeContactUserId")
    @Expose
    private String noticeContactUserId;
    @SerializedName("NoticeCreateDate")
    @Expose
    private String noticeCreateDate;
    @SerializedName("NoticeExpiryDate")
    @Expose
    private String noticeExpiryDate;

    public String getNoticeID() {
        return noticeID;
    }

    public void setNoticeID(String noticeID) {
        this.noticeID = noticeID;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getNoticeSummary() {
        return noticeSummary;
    }

    public void setNoticeSummary(String noticeSummary) {
        this.noticeSummary = noticeSummary;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public String getNoticeContactUserId() {
        return noticeContactUserId;
    }

    public void setNoticeContactUserId(String noticeContactUserId) {
        this.noticeContactUserId = noticeContactUserId;
    }

    public String getNoticeCreateDate() {
        return noticeCreateDate;
    }

    public void setNoticeCreateDate(String noticeCreateDate) {
        this.noticeCreateDate = noticeCreateDate;
    }

    public String getNoticeExpiryDate() {
        return noticeExpiryDate;
    }

    public void setNoticeExpiryDate(String noticeExpiryDate) {
        this.noticeExpiryDate = noticeExpiryDate;
    }

}
