package com.client.vote.domain;

import java.util.Date;

public class Anchor {

    String clientId;
    String anchorName;
    Date creationDate;
    String price;
    String status;

    public Anchor(String clientId, String anchorName, Date creationDate, String price, String status) {
        this.clientId = clientId;
        this.anchorName = anchorName;
        this.creationDate = creationDate;
        this.price = price;
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Anchor{" +
                "clientId='" + clientId + '\'' +
                ", anchorName='" + anchorName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}