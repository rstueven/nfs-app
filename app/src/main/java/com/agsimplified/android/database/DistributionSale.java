package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.agsimplified.android.util.NetworkRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistributionSale {
    public DistributionSale() {}

    static String TABLE_NAME = "distribution_sales";
    
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
    
    public DistributionSale(JSONObject obj) {
        try {
            id = obj.optInt("id");
            jobPlanId = obj.optInt("job_plan_id");
            year = obj.optInt("year");
            fromId = obj.optInt("from_id");
            toId = obj.optInt("to_id");
            mileage = obj.optDouble("mileage");
            amount = obj.optDouble("amount");
            notes = obj.getString("notes");
            fromType = obj.getString("from_type");
            fromFieldId = obj.optInt("from_field_id");
            fromStorageInventoryId = obj.optInt("from_storage_inventory_id");
            toType = obj.getString("to_type");
            toFieldId = obj.optInt("to_field_id");
            toStorageInventoryId = obj.optInt("to_storage_inventory_id");
            productId = obj.optInt("product_id");
            productCost = obj.optDouble("product_cost");
            directions = obj.getString("directions");
            salePrice = obj.optDouble("sale_price");
            spreadingCost = obj.optDouble("spreading_cost");
            truckingCost = obj.optDouble("trucking_cost");
            loadingCost = obj.optDouble("loading_cost");
            previousCropId = obj.optInt("previous_crop_id");
            plannedCropId = obj.optInt("planned_crop_id");
            croppingRotation = obj.getString("cropping_rotation");
            tillagePractices = obj.getString("tillage_practices");
            plannedAcres = obj.optDouble("planned_acres");
        } catch (JSONException e) {
            Log.e("nfs", "DistributionSale(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }
    
    public DistributionSale(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        jobPlanId = c.getInt(c.getColumnIndex("job_plan_id"));
        year = c.getInt(c.getColumnIndex("year"));
        fromId = c.getInt(c.getColumnIndex("from_id"));
        toId = c.getInt(c.getColumnIndex("to_id"));
        mileage = c.getDouble(c.getColumnIndex("mileage"));
        amount = c.getDouble(c.getColumnIndex("amount"));
        notes = c.getString(c.getColumnIndex("notes"));
        fromType = c.getString(c.getColumnIndex("from_type"));
        fromFieldId = c.getInt(c.getColumnIndex("from_field_id"));
        fromStorageInventoryId = c.getInt(c.getColumnIndex("from_storage_inventory_id"));
        toType = c.getString(c.getColumnIndex("to_type"));
        toFieldId = c.getInt(c.getColumnIndex("to_field_id"));
        toStorageInventoryId = c.getInt(c.getColumnIndex("to_storage_inventory_id"));
        productId = c.getInt(c.getColumnIndex("product_id"));
        productCost = c.getDouble(c.getColumnIndex("product_cost"));
        directions = c.getString(c.getColumnIndex("directions"));
        salePrice = c.getDouble(c.getColumnIndex("sale_price"));
        spreadingCost = c.getDouble(c.getColumnIndex("spreading_cost"));
        truckingCost = c.getDouble(c.getColumnIndex("trucking_cost"));
        loadingCost = c.getDouble(c.getColumnIndex("loading_cost"));
        previousCropId = c.getInt(c.getColumnIndex("previous_crop_id"));
        plannedCropId = c.getInt(c.getColumnIndex("planned_crop_id"));
        croppingRotation = c.getString(c.getColumnIndex("cropping_rotation"));
        tillagePractices = c.getString(c.getColumnIndex("tillage_practices"));
        plannedAcres = c.getDouble(c.getColumnIndex("planned_acres"));
    }

    public static DistributionSale[] createDistributionSales(JSONArray distributionSales) {
        List<DistributionSale> distributionSaleList = new ArrayList<>();

        try {
            for (int i = 0; i < distributionSales.length(); i++) {
                distributionSaleList.add(new DistributionSale(distributionSales.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "DistributionSale.createDistributionSales(): " + e.getLocalizedMessage());
            Log.e("nfs", distributionSales.toString());
        }

        DistributionSale[] distributionSaleArray = new DistributionSale[distributionSaleList.size()];
        return distributionSaleList.toArray(distributionSaleArray);
    }

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

    @Override
    public String toString() {
        return "DistributionSale{" +
                "id=" + id +
                ", jobPlanId=" + jobPlanId +
                ", year=" + year +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", mileage=" + mileage +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                ", fromType='" + fromType + '\'' +
                ", fromFieldId=" + fromFieldId +
                ", fromStorageInventoryId=" + fromStorageInventoryId +
                ", toType='" + toType + '\'' +
                ", toFieldId=" + toFieldId +
                ", toStorageInventoryId=" + toStorageInventoryId +
                ", productId=" + productId +
                ", productCost=" + productCost +
                ", directions='" + directions + '\'' +
                ", salePrice=" + salePrice +
                ", spreadingCost=" + spreadingCost +
                ", truckingCost=" + truckingCost +
                ", loadingCost=" + loadingCost +
                ", previousCropId=" + previousCropId +
                ", plannedCropId=" + plannedCropId +
                ", croppingRotation='" + croppingRotation + '\'' +
                ", tillagePractices='" + tillagePractices + '\'' +
                ", plannedAcres=" + plannedAcres +
                '}';
    }

    protected static class LoadAsync extends LoadTableAsync {
        LoadAsync(SQLiteDatabase db) {
            super(db);
            Log.d("nfs", "DistributionSale.LoadAsync()");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("nfs", "DistributionSale.LoadAsync.doInBackground()");
            final String url = setUrl(TABLE_NAME);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("nfs", "DistributionSale.LoadAsync.onResponse(" + url + ")");
                            try {
//                                Log.d("nfs", response.toString(2));
                                DistributionSale[] distributionSales = DistributionSale.createDistributionSales(response.getJSONArray(TABLE_NAME));
                                new DistributionSale.LoadAsync.PopulateAsync(mDb).execute(distributionSales);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("nfs", "PopulateDbAsync.onErrorResponse()");
                            Log.e("nfs", error.toString());
                        }
                    }
            );

            NetworkRequestQueue.addToRequestQueue(request);

            return null;
        }

        private static class PopulateAsync extends AsyncTask<DistributionSale, Void, Void> {
            private SQLiteDatabase mDb;

            PopulateAsync(SQLiteDatabase db) {
                super();
                Log.d("nfs", "DistributionSale.PopulateAsync()");
                this.mDb = db;
            }

            @Override
            protected Void doInBackground(DistributionSale... distributionSales) {
                Log.d("nfs", "DistributionSale.PopulateAsync.doInBackground()");
                Log.d("nfs", "LOADING " + distributionSales.length + " DISTRIBUTIONSALES");
                mDb.execSQL("DELETE FROM " + TABLE_NAME);

                for (DistributionSale distributionSale : distributionSales) {
//                    Log.d("nfs", distributionSale.toString());
                    if (mDb.insertOrThrow(TABLE_NAME, null, distributionSale.getContentValues()) == -1) {
                        Log.e("nfs", "FAILED TO INSERT <" + distributionSale.id + ">");
                    }
                }

                Log.d("nfs", "DistributionSale.PopulateAsync() DONE");

                return null;
            }
        }
    }
}