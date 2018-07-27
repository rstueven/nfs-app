package com.agsimplified.android.model.distributionsale;

import java.io.Serializable;

/**
 * Created by rstueven on 2/28/18.
 *
 * A DistributionSale object.
 */

public class DistributionSale implements Serializable {
    private int id;
    private Integer jobCode;
    private Integer clientJobCode;
    private int year;
    private String product;
    private String fromOperation;
    private String toOperation;
    private Double plannedAmount;
    private Double hauledAmount;

    public DistributionSale(int id, Integer jobCode, Integer clientJobCode, int year, String product, String fromOperation, String toOperation, Double plannedAmount, Double hauledAmount) {
        this.id = id;
        this.jobCode = jobCode;
        this.clientJobCode = clientJobCode;
        this.year = year;
        this.product = product;
        this.fromOperation = fromOperation;
        this.toOperation = toOperation;
        this.plannedAmount = plannedAmount;
        this.hauledAmount = hauledAmount;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFromOperation() {
        return fromOperation;
    }

    public void setFromOperation(String fromOperation) {
        this.fromOperation = fromOperation;
    }

    public String getToOperation() {
        return toOperation;
    }

    public void setToOperation(String toOperation) {
        this.toOperation = toOperation;
    }

    public Double getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(Double plannedAmount) {
        this.plannedAmount = plannedAmount;
    }

    public Double getHauledAmount() {
        return hauledAmount;
    }

    public void setHauledAmount(Double hauledAmount) {
        this.hauledAmount = hauledAmount;
    }
}