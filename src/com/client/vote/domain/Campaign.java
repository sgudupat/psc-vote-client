package com.client.vote.domain;

import java.util.Date;

public class Campaign {

    String campaignId;
    String question;
    Date startDate;
    Date endDate;

    public Campaign(String campaignId, String question, Date startDate, Date endDate) {
        this.campaignId = campaignId;
        this.question = question;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "Campaign{" +
                "campaignId='" + campaignId + '\'' +
                ", question='" + question + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}