package com.agsimplified.android.database;

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

public class Client {
    public Client() {}

    public Client(JSONObject obj) {
        try {
            id = obj.optInt("id");
            name = obj.getString("name");
            address1 = obj.getString("address_1");
            address2 = obj.getString("address_2");
            city = obj.getString("city");
            state = obj.getString("state");
            zip = obj.getString("zip");
            clientStatus = obj.getString("client_status");
            officePhone = obj.getString("office_phone");
            officeFax = obj.getString("office_fax");
            mobilePhone = obj.getString("mobile_phone");
            email = obj.getString("email");
            website = obj.getString("website");
            farm = obj.optBoolean("farm");
            feedlot = obj.optBoolean("feedlot");
            serviceProvider = obj.optBoolean("service_provider");
            contactId = obj.optInt("contact_id");
            companyId = obj.optInt("company_id");
            notes = obj.getString("notes");
        } catch (JSONException e) {
            Log.e("nfs", "Client(): " + e.getLocalizedMessage());
            Log.e("nfs", obj.toString());
        }
    }

    public static Client[] createClients(JSONArray clients) {
        List<Client> clientList = new ArrayList<>();

        try {
            for (int i = 0; i < clients.length(); i++) {
                clientList.add(new Client(clients.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e("nfs", "Client.createClients(): " + e.getLocalizedMessage());
            Log.e("nfs", clients.toString());
        }

        Client[] clientArray = new Client[clientList.size()];
        return clientList.toArray(clientArray);
    }

    private int id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String clientStatus;
    private String officePhone;
    private String officeFax;
    private String mobilePhone;
    private String email;
    private String website;
    private boolean farm;
    private boolean feedlot;
//    private Date createdAt;
//    private Date updatedAt;
    private boolean serviceProvider;
    private int contactId;
    private int companyId;
    private String notes;
    private int serviceLevelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getOfficeFax() {
        return officeFax;
    }

    public void setOfficeFax(String officeFax) {
        this.officeFax = officeFax;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isFarm() {
        return farm;
    }

    public void setFarm(boolean farm) {
        this.farm = farm;
    }

    public boolean isFeedlot() {
        return feedlot;
    }

    public void setFeedlot(boolean feedlot) {
        this.feedlot = feedlot;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    public boolean isServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(boolean serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getServiceLevelId() {
        return serviceLevelId;
    }

    public void setServiceLevelId(int serviceLevelId) {
        this.serviceLevelId = serviceLevelId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", clientStatus='" + clientStatus + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", officeFax='" + officeFax + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", farm=" + farm +
                ", feedlot=" + feedlot +
                ", serviceProvider=" + serviceProvider +
                ", contactId=" + contactId +
                ", companyId=" + companyId +
                ", notes='" + notes + '\'' +
                ", serviceLevelId=" + serviceLevelId +
                '}';
    }

    protected static class LoadAsync extends LoadTableAsync {
        LoadAsync(SQLiteDatabase db) {
            super(db);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("nfs", "Client.LoadAsync.doInBackground()");
            String url = setUrl("clients");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("nfs", "Client.LoadAsync.onResponse()");
                            try {
//                                Log.d("nfs", response.toString(2));
                                Client[] clients = Client.createClients(response.getJSONArray("clients"));
                                new PopulateAsync(mDb).execute(clients);
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

        private static class PopulateAsync extends AsyncTask<Client, Void, Void> {
            PopulateAsync(SQLiteDatabase db) {
                Log.d("nfs", "Client.PopulateAsync()");
            }

            @Override
            protected Void doInBackground(Client... clients) {
                for (Client client : clients) {
                    Log.d("nfs", client.toString());
                    Log.d("nfs", "==================================================");
                }

                Log.d("nfs", "Client.PopulateAsync() DONE");

                return null;
            }
        }
    }
}