package com.edu.erp.model;

public class CoopOrg extends BaseObject {
    private String coopOrgName;

    private Double percentage;

    private String description;

    public String getCoopOrgName() {
        return coopOrgName;
    }

    public void setCoopOrgName(String coopOrgName) {
        this.coopOrgName = coopOrgName;
    }

    public Double getPercentage() { return percentage;  }

    public void setPercentage(Double percentage) { this.percentage = percentage;  }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
