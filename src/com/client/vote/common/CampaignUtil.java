package com.client.vote.common;

import java.util.Date;

public class CampaignUtil {

    public static boolean isCampaignStopped(String status, Date endDate) {
        return status.contains("STOPPED") || isCampaignExpired(endDate);
    }

    public static String campaignStatusMessage(String status, Date endDate) {
        if (status.contains("STOPPED")) {
            return "Stopped";
        } else if (isCampaignExpired(endDate)) {
            return "Expired";
        }
        return "";
    }

    public static boolean isCampaignExpired(Date endDate) {
        return endDate.before(new Date());
    }

    public static boolean isCampaignEditable(String status, Date endDate) {
        return (!isCampaignExpired(endDate) && !isCampaignStopped(status, endDate));
    }
}