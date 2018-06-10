package com.edu.erp.model;

public class CoopOrgRel extends BaseObject {
    private Long coopOrgId;

    private Double percentage;

    private String start_date;

    private String end_date;

    public Long getCoopOrgId() {
        return coopOrgId;
    }

    public void setCoopOrgId(Long coopOrgId) {
        this.coopOrgId = coopOrgId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
