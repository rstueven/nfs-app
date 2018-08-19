package com.agsimplified.android.model;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.agsimplified.android.database.DbOpenHelper;
import com.agsimplified.android.database.DistributionSale;
import com.agsimplified.android.database.Field;
import com.agsimplified.android.database.JobPlan;
import com.agsimplified.android.database.LoadSheet;
import com.agsimplified.android.database.Product;
import com.agsimplified.android.database.Site;
import com.agsimplified.android.database.Storage;
import com.agsimplified.android.database.StorageInventory;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadSheetDetail implements Serializable {
    private DistributionSale distributionSale;
    private JobPlan jobPlan;
    private Product product;
    private Site fromSite;
    private Site toSite;
    private StorageInventory fromStorageInventory;
    private StorageInventory toStorageInventory;
    private Field fromField;
    private Field toField;
    private List<LoadSheet> loadSheets = new ArrayList<>();

    public LoadSheetDetail(int dsId) {
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();

        Cursor cursor = db.query(DistributionSale.TABLE_NAME, null, "_id = " + dsId,
                null, null, null, null, "1");
        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            distributionSale = new DistributionSale(cursor);
            cursor.close();
        }

        if (distributionSale != null) {
            cursor = db.query(JobPlan.TABLE_NAME, null, "_id = " + distributionSale.getJobPlanId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                jobPlan = new JobPlan(cursor);
                cursor.close();
            }

            cursor = db.query(Product.TABLE_NAME, null, "_id = " + distributionSale.getProductId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                product = new Product(cursor);
                cursor.close();
            }

            cursor = db.query(Site.TABLE_NAME, null, "_id = " + distributionSale.getFromId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                fromSite = new Site(cursor);
                cursor.close();
            }

            cursor = db.query(Site.TABLE_NAME, null, "_id = " + distributionSale.getToId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                toSite = new Site(cursor);
                cursor.close();
            }

            cursor = db.query(StorageInventory.TABLE_NAME, null, "_id = " + distributionSale.getFromStorageInventoryId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                fromStorageInventory = new StorageInventory(cursor);
                cursor.close();
            }

            cursor = db.query(StorageInventory.TABLE_NAME, null, "_id = " + distributionSale.getToStorageInventoryId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                toStorageInventory = new StorageInventory(cursor);
                cursor.close();
            }

            cursor = db.query(Field.TABLE_NAME, null, "_id = " + distributionSale.getFromFieldId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                fromField = new Field(cursor);
                cursor.close();
            }

            cursor = db.query(Field.TABLE_NAME, null, "_id = " + distributionSale.getToFieldId(),
                    null, null, null, null, "1");
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                toField = new Field(cursor);
                cursor.close();
            }

            cursor = db.query(LoadSheet.TABLE_NAME, null, "distribution_sale_id = " + distributionSale.getId(),
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    loadSheets.add(new LoadSheet(cursor));
                }
                cursor.close();
            }
        }
    }

    public static List<String> allYears() {
        String sql = "SELECT DISTINCT year FROM " + DistributionSale.TABLE_NAME + " ORDER BY year DESC";
        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        list.add("");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("year"))));
            }

            cursor.close();
        }

        return list;
    }

    public LatLng toLatLng() {
        if (toField != null) {
            return toField.getLatLng();
        } else if (toStorageInventory != null) {
            return toField.getLatLng();
        } else {
            return null;
        }
    }

    public int getJobCode() {
        return (jobPlan != null) ? jobPlan.getJobCode() : -1;
    }

    public String getJobCodeString() {
        int jobCode = getJobCode();
        return (jobCode > 0) ? Integer.toString(jobCode) : null;
    }

    public int getClientJobCode() {
        return (jobPlan != null) ? jobPlan.getClientJobCode() : -1;
    }

    public String getClientJobCodeString() {
        int jobCode = getClientJobCode();
        return (jobCode > 0) ? Integer.toString(jobCode) : null;
    }

    public int getYear() {
        return (distributionSale != null) ? distributionSale.getYear() : -1;
    }

    public String getYearString() {
        int year = getYear();
        return (year > 0) ? Integer.toString(year) : null;
    }

    public String getProductName() {
        return (product != null) ? product.getName() : null;
    }

    public Site getFromSite() {
//        return (fromSite != null) ? fromSite.getName() : null;

        return fromSite;
    }

    public Site getToSite() {
        return toSite;
    }

    public double getAmount() {
        return (distributionSale != null) ? distributionSale.getAmount() : -1;
    }

    public DistributionSale getDistributionSale() {
        return distributionSale;
    }

    public void setDistributionSale(DistributionSale distributionSale) {
        this.distributionSale = distributionSale;
    }

    public JobPlan getJobPlan() {
        return jobPlan;
    }

    public void setJobPlan(JobPlan jobPlan) {
        this.jobPlan = jobPlan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setFromSite(Site fromSite) {
        this.fromSite = fromSite;
    }

    public void setToSite(Site toSite) {
        this.toSite = toSite;
    }

    public StorageInventory getFromStorageInventory() {
        return fromStorageInventory;
    }

    public void setFromStorageInventory(StorageInventory fromStorageInventory) {
        this.fromStorageInventory = fromStorageInventory;
    }

    public StorageInventory getToStorageInventory() {
        return toStorageInventory;
    }

    public void setToStorageInventory(StorageInventory toStorageInventory) {
        this.toStorageInventory = toStorageInventory;
    }

    public Field getFromField() {
        return fromField;
    }

    public void setFromField(Field fromField) {
        this.fromField = fromField;
    }

    public Field getToField() {
        return toField;
    }

    public void setToField(Field toField) {
        this.toField = toField;
    }

    public List<LoadSheet> getLoadSheets() {
        return loadSheets;
    }

    public void setLoadSheets(List<LoadSheet> loadSheets) {
        this.loadSheets = loadSheets;
    }

    public GeoJsonable getDestination() {
        return toField != null ? toField : Storage.find(toStorageInventory.getStorageableId());
    }

    @Override
    public String toString() {
        return "LoadSheetDetail{" +
                "distributionSale=" + distributionSale +
                ", jobPlan=" + jobPlan +
                ", product=" + product +
                ", fromSite=" + fromSite +
                ", toSite=" + toSite +
                ", fromStorageInventory=" + fromStorageInventory +
                ", toStorageInventory=" + toStorageInventory +
                ", fromField=" + fromField +
                ", toField=" + toField +
                ", loadSheets=" + loadSheets +
                '}';
    }

    @SuppressLint("DefaultLocale")
    public static List<LoadSheetDetail> search(Integer searchClientId, Integer searchYear, Integer searchJobCode, Integer searchClientJobCode, Integer searchFromId, Integer searchToId, Integer searchProductId) {
        List<LoadSheetDetail> result = new ArrayList<>();
        List<String> selection = new ArrayList<>();
        List<String> selectionArgs = new ArrayList<>();
        String searchSql = "SELECT distribution_sales._id FROM distribution_sales LEFT JOIN job_plans ON distribution_sales.job_plan_id = job_plans._id";

        if (searchClientId != null) {
            selection.add("job_plans.client_id = ?");
            selectionArgs.add(String.format("%d", searchClientId));
        }

        if (searchYear != null) {
            selection.add("distribution_sales.year = ?");
            selectionArgs.add(String.format("%d", searchYear));
        }

        if (searchJobCode != null) {
            selection.add("job_plans.job_code = ?");
            selectionArgs.add(String.format("%d", searchJobCode));
        }

        if (searchClientJobCode != null) {
            selection.add("job_plans.client_job_code = ?");
            selectionArgs.add(String.format("%d", searchClientJobCode));
        }

        if (searchFromId != null) {
            selection.add("distribution_sales.from_id = ?");
            selectionArgs.add(String.format("%d", searchFromId));
        }

        if (searchToId != null) {
            selection.add("distribution_sales.to_id = ?");
            selectionArgs.add(String.format("%d", searchToId));
        }

        if (searchProductId != null) {
            selection.add("distribution_sales.product_id = ?");
            selectionArgs.add(String.format("%d", searchProductId));
        }

        if (selection.size() > 0) {
            searchSql += " WHERE ";
            searchSql += TextUtils.join(" AND ", selection);
        }

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery(searchSql, selectionArgs.toArray(new String[selectionArgs.size()]));

        if (cursor != null) {
            while (cursor.moveToNext()) {
                result.add(new LoadSheetDetail(cursor.getInt(0)));
            }

            cursor.close();
        }

        return result;
    }
}