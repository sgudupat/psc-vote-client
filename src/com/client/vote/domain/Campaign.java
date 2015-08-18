package com.client.vote.domain;

import java.util.Date;

public class Campaign {

    String campaignId;
    String anchorName;
    String question;
    Date startDate;
    Date endDate;
    String status;

    public Campaign(String campaignId, String anchorName, String question, Date startDate, Date endDate, String status) {
        this.campaignId = campaignId;
        this.anchorName = anchorName;
        this.question = question;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "campaignId='" + campaignId + '\'' +
                ", anchorName='" + anchorName + '\'' +
                ", question='" + question + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}