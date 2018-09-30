package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class DistributionSale extends AbstractTable {
    public static final String TABLE_NAME = "distribution_sales";

    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "job_plan_id INTEGER",
            "year INTEGER",
            "from_id INTEGER",
            "to_id INTEGER",
            "mileage REAL",
            "amount REAL",
            "notes TEXT",
            "from_type TEXT",
            "from_field_id INTEGER",
            "from_storage_inventory_id INTEGER",
            "to_type TEXT",
            "to_field_id INTEGER",
            "to_storage_inventory_id INTEGER",
            "product_id INTEGER",
            "product_cost REAL",
            "directions TEXT",
            "sale_price REAL",
            "spreading_cost REAL",
            "trucking_cost REAL",
            "loading_cost REAL",
            "previous_crop_id INTEGER",
            "planned_crop_id INTEGER",
            "cropping_rotation TEXT",
            "tillage_practices TEXT",
            "planned_acres REAL",
            "PRIMARY KEY (_id)"
    };

    private int id;
    private int jobPlanId;
    private int year;
    private int fromId;
    private int toId;
    private double mileage;
    private double amount;
    private String notes;
    private String fromType;
    private int fromFieldId;
    private int fromStorageInventoryId;
    private String toType;
    private int toFieldId;
    private int toStorageInventoryId;
    private int productId;
    private double productCost;
    private String directions;
    private double salePrice;
    private double spreadingCost;
    private double truckingCost;
    private double loadingCost;
    private int previousCropId;
    private int plannedCropId;
    private String croppingRotation;
    private String tillagePractices;
    private double plannedAcres;

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("job_plan_id", jobPlanId);
        cv.put("year", year);
        cv.put("from_id", fromId);
        cv.put("to_id", toId);
        cv.put("mileage", mileage);
        cv.put("amount", amount);
        cv.put("notes", notes);
        cv.put("from_type", fromType);
        cv.put("from_field_id", fromFieldId);
        cv.put("from_storage_inventory_id", fromStorageInventoryId);
        cv.put("to_type", toType);
        cv.put("to_field_id", toFieldId);
        cv.put("to_storage_inventory_id", toStorageInventoryId);
        cv.put("product_id", productId);
        cv.put("product_cost", productCost);
        cv.put("directions", directions);
        cv.put("sale_price", salePrice);
        cv.put("spreading_cost", spreadingCost);
        cv.put("trucking_cost", truckingCost);
        cv.put("loading_cost", loadingCost);
        cv.put("previous_crop_id", previousCropId);
        cv.put("planned_crop_id", plannedCropId);
        cv.put("cropping_rotation", croppingRotation);
        cv.put("tillage_practices", tillagePractices);
        cv.put("planned_acres", plannedAcres);
        return cv;
    }

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        jobPlanId = cursor.getInt(cursor.getColumnIndex("job_plan_id"));
        year = cursor.getInt(cursor.getColumnIndex("year"));
        fromId = cursor.getInt(cursor.getColumnIndex("from_id"));
        toId = cursor.getInt(cursor.getColumnIndex("to_id"));
        mileage = cursor.getDouble(cursor.getColumnIndex("mileage"));
        amount = cursor.getDouble(cursor.getColumnIndex("amount"));
        notes = cursor.getString(cursor.getColumnIndex("notes"));
        fromType = cursor.getString(cursor.getColumnIndex("from_type"));
        fromFieldId = cursor.getInt(cursor.getColumnIndex("from_field_id"));
        fromStorageInventoryId = cursor.getInt(cursor.getColumnIndex("from_storage_inventory_id"));
        toType = cursor.getString(cursor.getColumnIndex("to_type"));
        toFieldId = cursor.getInt(cursor.getColumnIndex("to_field_id"));
        toStorageInventoryId = cursor.getInt(cursor.getColumnIndex("to_storage_inventory_id"));
        productId = cursor.getInt(cursor.getColumnIndex("product_id"));
        directions = cursor.getString(cursor.getColumnIndex("directions"));
        salePrice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
        spreadingCost = cursor.getDouble(cursor.getColumnIndex("spreading_cost"));
        truckingCost = cursor.getDouble(cursor.getColumnIndex("trucking_cost"));
        loadingCost = cursor.getDouble(cursor.getColumnIndex("loading_cost"));
        previousCropId = cursor.getInt(cursor.getColumnIndex("previous_crop_id"));
        plannedCropId = cursor.getInt(cursor.getColumnIndex("planned_crop_id"));
        croppingRotation = cursor.getString(cursor.getColumnIndex("cropping_rotation"));
        tillagePractices = cursor.getString(cursor.getColumnIndex("tillage_practices"));
        plannedAcres = cursor.getDouble(cursor.getColumnIndex("planned_acres"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobPlanId() {
        return jobPlanId;
    }

    public void setJobPlanId(int jobPlanId) {
        this.jobPlanId = jobPlanId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public int getFromFieldId() {
        return fromFieldId;
    }

    public void setFromFieldId(int fromFieldId) {
        this.fromFieldId = fromFieldId;
    }

    public int getFromStorageInventoryId() {
        return fromStorageInventoryId;
    }

    public void setFromStorageInventoryId(int fromStorageInventoryId) {
        this.fromStorageInventoryId = fromStorageInventoryId;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public int getToFieldId() {
        return toFieldId;
    }

    public void setToFieldId(int toFieldId) {
        this.toFieldId = toFieldId;
    }

    public int getToStorageInventoryId() {
        return toStorageInventoryId;
    }

    public void setToStorageInventoryId(int toStorageInventoryId) {
        this.toStorageInventoryId = toStorageInventoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getSpreadingCost() {
        return spreadingCost;
    }

    public void setSpreadingCost(double spreadingCost) {
        this.spreadingCost = spreadingCost;
    }

    public double getTruckingCost() {
        return truckingCost;
    }

    public void setTruckingCost(double truckingCost) {
        this.truckingCost = truckingCost;
    }

    public double getLoadingCost() {
        return loadingCost;
    }

    public void setLoadingCost(double loadingCost) {
        this.loadingCost = loadingCost;
    }

    public int getPreviousCropId() {
        return previousCropId;
    }

    public void setPreviousCropId(int previousCropId) {
        this.previousCropId = previousCropId;
    }

    public int getPlannedCropId() {
        return plannedCropId;
    }

    public void setPlannedCropId(int plannedCropId) {
        this.plannedCropId = plannedCropId;
    }

    public String getCroppingRotation() {
        return croppingRotation;
    }

    public void setCroppingRotation(String croppingRotation) {
        this.croppingRotation = croppingRotation;
    }

    public String getTillagePractices() {
        return tillagePractices;
    }

    public void setTillagePractices(String tillagePractices) {
        this.tillagePractices = tillagePractices;
    }

    public double getPlannedAcres() {
        return plannedAcres;
    }

    public void setPlannedAcres(double plannedAcres) {
        this.plannedAcres = plannedAcres;
    }
}