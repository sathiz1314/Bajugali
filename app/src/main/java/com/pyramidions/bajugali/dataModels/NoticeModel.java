package com.pyramidions.bajugali.dataModels;

/**
 * Created by User on 12/17/2016.
 */

public class NoticeModel {
    private String NoticeName = "";
    private String NoticeSummary = "";
    private String NoticeDescription = "";
    private String NoticeContactUserId = "";
    private String NoticeCreateDate = "";
    private String NoticeExpiryDate = "";
    private String contactpersonname = "";
    private String contactpersonnumber = "";
    private String contactpersonemail = "";
    private String NoticeID = "";

    public String getNoticeID() {
        return NoticeID;
    }

    public void setNoticeID(String noticeID) {
        NoticeID = noticeID;
    }

    public NoticeModel() {
    }

    public String getNoticeName() {
        return NoticeName;
    }

    public void setNoticeName(String noticeName) {
        NoticeName = noticeName;
    }

    public String getNoticeSummary() {
        return NoticeSummary;
    }

    public void setNoticeSummary(String noticeSummary) {
        NoticeSummary = noticeSummary;
    }

    public String getNoticeDescription() {
        return NoticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        NoticeDescription = noticeDescription;
    }

    public String getNoticeContactUserId() {
        return NoticeContactUserId;
    }

    public void setNoticeContactUserId(String noticeContactUserId) {
        NoticeContactUserId = noticeContactUserId;
    }

    public String getNoticeCreateDate() {
        return NoticeCreateDate;
    }

    public void setNoticeCreateDate(String noticeCreateDate) {
        NoticeCreateDate = noticeCreateDate;
    }

    public String getNoticeExpiryDate() {
        return NoticeExpiryDate;
    }

    public void setNoticeExpiryDate(String noticeExpiryDate) {
        NoticeExpiryDate = noticeExpiryDate;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getContactpersonnumber() {
        return contactpersonnumber;
    }

    public void setContactpersonnumber(String contactpersonnumber) {
        this.contactpersonnumber = contactpersonnumber;
    }

    public String getContactpersonemail() {
        return contactpersonemail;
    }

    public void setContactpersonemail(String contactpersonemail) {
        this.contactpersonemail = contactpersonemail;
    }
}
