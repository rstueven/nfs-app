package com.agsimplified.android.model;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.agsimplified.android.database.Client;
import com.agsimplified.android.database.DbOpenHelper;
import com.agsimplified.android.database.DistributionSale;

import java.util.ArrayList;
import java.util.List;

public class LoadSheet {
    private static final String SQL = "SELECT clients.name AS client_name, job_plans.job_code, job_plans.client_job_code, distribution_sales.year, distribution_sales.amount, distribution_sales.planned_acres, products.name AS product_name, from_site.name AS from_site, to_site.name AS to_site FROM clients JOIN job_plans ON job_plans.client_id = clients._id JOIN distribution_sales ON distribution_sales.job_plan_id = job_plans._id JOIN products ON products._id = distribution_sales.product_id JOIN sites from_site ON from_site._id = distribution_sales.from_id JOIN sites to_site ON to_site._id = distribution_sales.from_id";

    private int clientName;
    private int jobCode;
    private int clientJobCode;
    private int year;
    private double amount;
    private double plannedAcres;
    private String productName;
    private String fromSite;
    private String toSite;

    public LoadSheet(Cursor cursor) {
        populateFromCursor(cursor);
    }

    public LoadSheet(int dsId) {
        String sql = SQL + " WHERE distribution_sales._id = ?";
        @SuppressLint("DefaultLocale") String[] selectionArgs = {String.format("%d", dsId)};
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, selectionArgs);

        if (cursor != null) {
            cursor.moveToFirst();
            populateFromCursor(cursor);
            cursor.close();
        }
    }

    private void populateFromCursor(Cursor cursor) {
        jobCode = cursor.getInt(cursor.getColumnIndex("job_code"));
        clientJobCode = cursor.getInt(cursor.getColumnIndex("client_job_code"));
        year = cursor.getInt(cursor.getColumnIndex("year"));
        amount = cursor.getDouble(cursor.getColumnIndex("amount"));
        plannedAcres = cursor.getDouble(cursor.getColumnIndex("planned_acres"));
        productName = cursor.getString(cursor.getColumnIndex("product_name"));
        fromSite = cursor.getString(cursor.getColumnIndex("from_site"));
        toSite = cursor.getString(cursor.getColumnIndex("to_site"));
    }

    public static List<String> allYears() {
        String sql = "SELECT DISTINCT year FROM " + DistributionSale.TABLE_NAME + " ORDER BY year DESC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("year"))));
            }

            cursor.close();
        }

        return list;
    }

    @SuppressLint("DefaultLocale")
    public static List<LoadSheet> search(Integer searchClientId, Integer searchYear, Integer searchJobCode, Integer searchClientJobCode, Integer searchFromId, Integer searchToId, Integer searchProductId) {
        List<LoadSheet> result = new ArrayList<>();
        List<String> searchTerms = new ArrayList<>();
        List<String> selectionArgs = new ArrayList<>();
        String sql = SQL;

        if (searchClientId != null) {
            searchTerms.add("clients._id = ?");
            selectionArgs.add(String.format("%d", searchClientId));
        }

        if (searchYear != null) {
            searchTerms.add("distribution_sales.year = ?");
            selectionArgs.add(String.format("%d", searchYear));
        }

        if (searchJobCode != null) {
            searchTerms.add("job_plans.job_code = ?");
            selectionArgs.add(String.format("%d", searchJobCode));
        }

        if (searchClientJobCode != null) {
            searchTerms.add("job_plans.client_job_code = ?");
            selectionArgs.add(String.format("%d", searchClientJobCode));
        }

        if (searchFromId != null) {
            searchTerms.add("distribution_sales.from_id = ?");
            selectionArgs.add(String.format("%d", searchFromId));
        }

        if (searchToId != null) {
            searchTerms.add("distribution_sales.to_id = ?");
            selectionArgs.add(String.format("%d", searchToId));
        }

        if (searchProductId != null) {
            searchTerms.add("products._id = ?");
            selectionArgs.add(String.format("%d", searchProductId));
        }

        if (searchTerms.size() > 0) {
            sql += " WHERE ";
            sql += TextUtils.join(" AND ", searchTerms);
        }

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, selectionArgs.toArray(new String[selectionArgs.size()]));

        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(new LoadSheet(cursor));
            }

            cursor.close();
        }

        return result;
    }

    public int getJobCode() {
        return jobCode;
    }

    public String getJobCodeString() {
        return Integer.toString(jobCode);
    }

    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    public int getClientJobCode() {
        return clientJobCode;
    }

    public String getClientJobCodeString() {
        return Integer.toString(clientJobCode);
    }

    public void setClientJobCode(int clientJobCode) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPlannedAcres() {
        return plannedAcres;
    }

    public void setPlannedAcres(double plannedAcres) {
        this.plannedAcres = plannedAcres;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFromSite() {
        return fromSite;
    }

    public void setFromSite(String fromSite) {
        this.fromSite = fromSite;
    }

    public String getToSite() {
        return toSite;
    }

    public void setToSite(String toSite) {
        this.toSite = toSite;
    }

    @Override
    public String toString() {
        return "LoadSheet{" +
                "jobCode=" + jobCode +
                ", clientJobCode=" + clientJobCode +
                ", year=" + year +
                ", amount=" + amount +
                ", plannedAcres=" + plannedAcres +
                ", productName='" + productName + '\'' +
                ", fromSite='" + fromSite + '\'' +
                ", toSite='" + toSite + '\'' +
                '}';
    }
}