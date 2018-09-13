package com.agsimplified.android.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.agsimplified.android.R;
import com.agsimplified.android.util.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.agsimplified.android.util.SharedPref.Pref.DB_IS_UPDATING;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "nfs";
    private static DbOpenHelper mInstance;
    private static List<LoadListener> loadListeners = new ArrayList<>();
    private enum ListenerAction {
        TABLE_LOAD_START,
        TABLE_LOAD_PROGRESS,
        TABLE_LOAD_END,
        DB_LOADED
    }

    public interface LoadListener {
        void onTableLoadStart(String tableName, int recordCount);
        void onTableLoadProgress(String tableName, int recordCount);
        void onTableLoadEnd(String tableName);
        void onDatabaseLoaded();
    }

    public static void registerLoadListener(LoadListener listener) {
        loadListeners.add(listener);
    }

    public static void unregisterLoadListener(LoadListener listener) {
        loadListeners.remove(listener);
    }

    private static void notifyListeners(ListenerAction action) {
        notifyListeners(action, null, 0);
    }

    private static void notifyListeners(ListenerAction action, String tableName) {
        notifyListeners(action, tableName, 0);
    }

    private static void notifyListeners(ListenerAction action, String tableName, int recordCount) {
        for (LoadListener listener : loadListeners) {
            switch (action) {
                case TABLE_LOAD_START:
                    listener.onTableLoadStart(tableName, recordCount);
                    break;
                case TABLE_LOAD_PROGRESS:
                    listener.onTableLoadProgress(tableName, recordCount);
                    break;
                case TABLE_LOAD_END:
                    listener.onTableLoadEnd(tableName);
                    break;
                case DB_LOADED:
                    listener.onDatabaseLoaded();
                    break;
                default:
                    Log.w("nfs", "UNKNOWN ACTION <" + action + ">");
            }
        }
    }

    public void onTableLoadStart(String tableName, int recordCount) {
        notifyListeners(ListenerAction.TABLE_LOAD_START, tableName, recordCount);
    }

    public void onTableLoadProgress(String tableName, int recordCount) {
        notifyListeners(ListenerAction.TABLE_LOAD_PROGRESS, tableName, recordCount);
    }

    public void onTableLoadEnd(String tableName) {
        notifyListeners(ListenerAction.TABLE_LOAD_END, tableName);
    }

    private DbOpenHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void init(Context context) {
        Log.d("nfs", "DbOpenHelper.init()");
        if (mInstance == null) {
            mInstance = new DbOpenHelper(context);
        }
    }

    public static DbOpenHelper getInstance() {
//        Log.d("nfs", "DbOpenHelper.getInstance()");
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("nfs", "DbOpenHelper.onCreate()");
        TableDefs tableDefs;
        tableDefs = TableDefs.newInstance(this, DB_VERSION);

        if (!isUpdating()) {
            try {
                setUpdating(true);
                HashMap<String, String> createStatements = tableDefs.getCreateStatements();

                for (String key : createStatements.keySet()) {
                    db.execSQL(createStatements.get(key));
                }

                HashMap<String, String[]> indexStatements = tableDefs.getIndexStatements();

                for (String key : indexStatements.keySet()) {
                    String[] idxStmts = indexStatements.get(key);
                    for (String idxStmt : idxStmts) {
                        db.execSQL(idxStmt);
                    }
                }

            } catch (SQLException e) {
                Log.e("nfs", "DbOpenHelper.onCreate(): " + R.string.Database_problem);
                Log.e("nfs", e.getLocalizedMessage());
            } finally {
                setUpdating(false);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("nfs", "DbOpenHelper.onUpgrade()");
        for (int i = ++oldVersion; i <= newVersion; i++) {
            TableDefs.newInstance(this, i).upgrade(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("nfs", "DbOpenHelper.onDowngrade()");
        // UNSUPPORTED
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.d("nfs", "DbOpenHelper.onOpen()");
        TableDefs.newInstance(this, DB_VERSION).loadData(db);

        // TODO: This needs to wait for all that other background stuff to finish.
//        notifyListeners(ListenerAction.DB_LOADED);
    }

    public boolean isUpdating() {
        Log.d("nfs", "DbOpenHelper.isUpdating()");
        return SharedPref.read(DB_IS_UPDATING, false);
    }

    public void setUpdating(boolean updating) {
        Log.d("nfs", "DbOpenHelper.setUpdating(" + updating + ")");
        SharedPref.write(DB_IS_UPDATING, updating);
    }
}