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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobPlan implements Serializable {
    public JobPlan() {
    }

    public static final String TABLE_NAME = "job_plans";
    static final String[] COLUMNS = {
            "_id INTEGER NOT NULL",
            "client_id INTEGER",
            "description TEXT",
            "status TEXT",
            "job_type TEXT",
            "job_code INTEGER",
            "job_status TEXT",
            "notes TEXT",
            "manager_id INTEGER",
            "wizard_complete TEXT",
            "year INTEGER",
            "manager_emailed INTEGER",
            "accounting_manager_id INTEGER",
            "accounting_manager_emailed INTEGER",
            "created_by_id INTEGER",
            "client_job_code INTEGER",
            "PRIMARY KEY (_id)"
    };

    private int id;
    private int clientId;
    private String description;
    private String status;
    private String jobType;
    private int jobCode;
    private String jobStatus;
    private String notes;
    private int managerId;
    private String wizardComplete;
    private int year;
    private boolean managerEmailed;
    private int accountingManagerId;
    private boolean accountingManagerEmailed;
    private int createdById;
    private int clientJobCode;

    public JobPlan(JSONObject obj) {
        try {
            id = obj.optInt("id");
            clientId = obj.optInt("client_id");
            description = obj.getString("description");
            status = obj.getString("status");
            jobType = obj.getString("job_type");
            jobCode = obj.optInt("job_code");
            jobStatus = obj.getString("job_status");
            notes = obj.getString("notes");
            managerId = obj.optInt("manager_id");
            wizardComplete = obj.getString("wizard_complete");
            year = obj.optInt("year");
            managerEmailed = obj.optInt("year") == 1;
            accountingManagerId = obj.optInt("accounting_manager_id");
            accountingManagerEmailed = obj.optInt("accounting_manager_emailed") == 1;
            createdById = obj.optInt("created_by_id");
            clientJobCode = obj.optInt("client_job_code");
        } catch (JSONException e) {
            Log.e("nfs", "JobPlan(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public JobPlan(Cursor c) {
        id = c.getInt(c.getColumnIndex("_id"));
        clientId = c.getInt(c.getColumnIndex("client_id"));
        description = c.getString(c.getColumnIndex("description"));
        status = c.getString(c.getColumnIndex("status"));
        jobType = c.getString(c.getColumnIndex("job_type"));
        jobCode = c.getInt(c.getColumnIndex("job_code"));
        jobStatus = c.getString(c.getColumnIndex("job_status"));
        notes = c.getString(c.getColumnIndex("notes"));
        managerId = c.getInt(c.getColumnIndex("manager_id"));
        wizardComplete = c.getString(c.getColumnIndex("wizard_complete"));
        year = c.getInt(c.getColumnIndex("year"));
        managerEmailed = c.getInt(c.getColumnIndex("year")) == 1;
        accountingManagerId = c.getInt(c.getColumnIndex("accounting_manager_id"));
        accountingManagerEmailed = c.getInt(c.getColumnIndex("accounting_manager_emailed")) == 1;
        createdById = c.getInt(c.getColumnIndex("created_by_id"));
        clientJobCode = c.getInt(c.getColumnIndex("client_job_code"));
    }

    public static JobPlan find(int id) {
        JobPlan item = null;

        SQLiteDatabase db = DbOpenHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            item = new JobPlan(cursor);
            cursor.close();
        } else {
            Log.w("nfs", "JOBPLAN(" + id + ") NOT FOUND");
        }

        return item;
    }

    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("client_id", clientId);
        cv.put("description", description);
        cv.put("status", status);
        cv.put("job_type", jobType);
        cv.put("job_code", jobCode);
        cv.put("job_status", jobStatus);
        cv.put("notes", notes);
        cv.put("manager_id", managerId);
        cv.put("wizard_complete", wizardComplete);
        cv.put("year", year);
        cv.put("manager_emailed", managerEmailed);
        cv.put("accounting_manager_id", accountingManagerId);
        cv.put("accounting_manager_emailed", accountingManagerEmailed);
        cv.put("created_by_id", createdById);
        cv.put("client_job_code", clientJobCode);
        return cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getJobCode() {
        return jobCode;
    }

    public void setJobCode(int jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getWizardComplete() {
        return wizardComplete;
    }

    public void setWizardComplete(String wizardComplete) {
        this.wizardComplete = wizardComplete;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isManagerEmailed() {
        return managerEmailed;
    }

    public void setManagerEmailed(boolean managerEmailed) {
        this.managerEmailed = managerEmailed;
    }

    public int getAccountingManagerId() {
        return accountingManagerId;
    }

    public void setAccountingManagerId(int accountingManagerId) {
        this.accountingManagerId = accountingManagerId;
    }

    public boolean isAccountingManagerEmailed() {
        return accountingManagerEmailed;
    }

    public void setAccountingManagerEmailed(boolean accountingManagerEmailed) {
        this.accountingManagerEmailed = accountingManagerEmailed;
    }

    public int getCreatedById() {
        return createdById;
    }

    public void setCreatedById(int createdById) {
        this.createdById = createdById;
    }

    public int getClientJobCode() {
        return clientJobCode;
    }

    public void setClientJobCode(int clientJobCode) {
        this.clientJobCode = clientJobCode;
    }

    @Override
    public String toString() {
        return "JobPlan{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", jobStatus='" + jobStatus + '\'' +
                ", notes='" + notes + '\'' +
                ", managerId=" + managerId +
                ", wizardComplete='" + wizardComplete + '\'' +
                ", year=" + year +
                ", managerEmailed=" + managerEmailed +
                ", accountingManagerId=" + accountingManagerId +
                ", accountingManagerEmailed=" + accountingManagerEmailed +
                ", createdById=" + createdById +
                ", clientJobCode=" + clientJobCode +
                '}';
    }

    public static JobPlan[] jsonToArray(JSONArray jsonArray) {
        List<JobPlan> list = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new JobPlan(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "JobPlan.jsonToArray(): " + e.getLocalizedMessage());
            Log.e("nfs", jsonArray.toString());
        }

        JobPlan[] array = new JobPlan[list.size()];
        return list.toArray(array);
    }

    protected static class PopulateAsync extends AsyncTask<JSONArray, Void, Void> {
        private DbOpenHelper dbHelper;
        private SQLiteDatabase mDb;

        PopulateAsync(DbOpenHelper dbHelper, SQLiteDatabase db) {
            super();
            Log.d("nfs", "JobPlan.PopulateAsync()");
            this.dbHelper = dbHelper;
            this.mDb = db;
        }

        @Override
        protected Void doInBackground(JSONArray... json) {
            Log.d("nfs", "JobPlan.PopulateAsync.doInBackground()");

            JobPlan[] array = jsonToArray(json[0]);
            Log.d("nfs", "LOADING " + array.length + " JOB PLANS");
            dbHelper.onTableLoadStart(TABLE_NAME, array.length);
            mDb.execSQL("DELETE FROM " + TABLE_NAME);

            int n = 0;
            for (JobPlan item : array) {
//                    Log.d("nfs", item.toString());
                if (mDb.insertOrThrow(TABLE_NAME, null, item.getContentValues()) == -1) {
                    Log.e("nfs", "FAILED TO INSERT <" + item.toString() + ">");
                }
                dbHelper.onTableLoadProgress(TABLE_NAME, ++n);
            }

            dbHelper.onTableLoadEnd(TABLE_NAME);
            Log.d("nfs", "JobPlan.PopulateAsync() DONE");

            return null;
        }
    }
}