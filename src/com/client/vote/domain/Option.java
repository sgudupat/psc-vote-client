package com.client.vote.domain;

public class Option {

    String optionId;
    String optionValue;

    public Option(String optionId, String optionValue) {
        this.optionId = optionId;
        this.optionValue = optionValue;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionId='" + optionId + '\'' +
                ", optionValue='" + optionValue + '\'' +
                '}';
    }
}