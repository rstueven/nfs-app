package com.agsimplified.android.database;

import android.content.ContentValues;
import android.database.Cursor;

public class JobPlan extends AbstractTable {
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

    @Override
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

    @Override
    void objectFromCursor(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex("_id"));
        clientId = cursor.getInt(cursor.getColumnIndex("client_id"));
        description = cursor.getString(cursor.getColumnIndex("description"));
        status = cursor.getString(cursor.getColumnIndex("status"));
        jobType = cursor.getString(cursor.getColumnIndex("job_type"));
        jobCode = cursor.getInt(cursor.getColumnIndex("job_code"));
        jobStatus = cursor.getString(cursor.getColumnIndex("job_status"));
        notes = cursor.getString(cursor.getColumnIndex("notes"));
        managerId = cursor.getInt(cursor.getColumnIndex("manager_id"));
        wizardComplete = cursor.getString(cursor.getColumnIndex("wizard_complete"));
        year = cursor.getInt(cursor.getColumnIndex("year"));
        managerEmailed = cursor.getInt(cursor.getColumnIndex("year")) == 1;
        accountingManagerId = cursor.getInt(cursor.getColumnIndex("accounting_manager_id"));
        accountingManagerEmailed = cursor.getInt(cursor.getColumnIndex("accounting_manager_emailed")) == 1;
        createdById = cursor.getInt(cursor.getColumnIndex("created_by_id"));
        clientJobCode = cursor.getInt(cursor.getColumnIndex("client_job_code"));
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
}