package com.agsimplified.android.models;

import java.io.Serializable;

/**
 * Created by rstueven on 3/1/18.
 *
 * A FieldActivity object.
 */

public class FieldActivity implements Serializable {
    private int id;
    private Integer jobCode;
    private Integer clientJobCode;
    private int year;
    private String activityType;
    private String operation;
    private String farm;

    public FieldActivity(int id, Integer jobCode, Integer clientJobCode, int year, String activityType, String operation, String farm) {
        this.id = id;
        this.jobCode = jobCode;
        this.clientJobCode = clientJobCode;
        this.year = year;
        this.activityType = activityType;
        this.operation = operation;
        this.farm = farm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getJobCode() {
        return jobCode;
    }

    public String getJobCodeString() {
        return jobCode == null ? "" : jobCode.toString();
    }

    public void setJobCode(Integer jobCode) {
        this.jobCode = jobCode;
    }

    public Integer getClientJobCode() {
        return clientJobCode;
    }

    public String getClientJobCodeString() {
        return clientJobCode == null ? "" : clientJobCode.toString();
    }

    public void setClientJobCode(Integer clientJobCode) {
        this.clientJobCode = clientJobCode;
    }

    public int getYear() {
        return year;
    }

    public String getYearString() {
        return Integer.toString(year);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }
}