package com.feigdev.androidserialsql;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by ejohn on 9/2/13.
 */
public class DefineDB {
    private final int version;
    private final String dbName;
    private final HashMap<String, String> tableDefinitions = new HashMap<String, String>();
    private final SparseArray<UpgradeRunnable> versionUpgrades = new SparseArray<UpgradeRunnable>();

    public DefineDB(String dbName, int version) {
        this.dbName = dbName;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public String getDbName() {
        return dbName;
    }

    public void setTableDefenition(String table, String tableDefenition) {
        tableDefinitions.put(table, tableDefenition);
    }

    public String getTableDefenition(String table) {
        return tableDefinitions.get(table);
    }

    public Set<String> getTables() {
        return tableDefinitions.keySet();
    }

    public void setVersionUpgrade(int version, UpgradeRunnable upgrade) {
        versionUpgrades.put(version, upgrade);
    }

    public UpgradeRunnable getVersionUpgrade(int version) {
        return versionUpgrades.get(version);
    }
}
