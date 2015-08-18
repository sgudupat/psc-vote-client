package com.client.vote.domain;

import java.util.Date;
import java.util.List;

public class Campaign {

    String campaignId;
    String anchorName;
    String question;
    Date startDate;
    Date endDate;
    String status;
    String rewardInfo;
    String regionCountry;
    List<Option> options;

    public Campaign() {
    }

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

    public String getRewardInfo() {
        return rewardInfo;
    }

    public void setRewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public String getRegionCountry() {
        return regionCountry;
    }

    public void setRegionCountry(String regionCountry) {
        this.regionCountry = regionCountry;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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
                ", rewardInfo='" + rewardInfo + '\'' +
                ", regionCountry='" + regionCountry + '\'' +
                '}';
    }
}