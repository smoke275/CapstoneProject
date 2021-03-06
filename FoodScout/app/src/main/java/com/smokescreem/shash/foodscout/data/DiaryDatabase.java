package com.smokescreem.shash.foodscout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Shash on 5/20/2017.
 */

@Database(version = DiaryDatabase.VERSION,
        packageName = "com.smokescreem.shash.foodscout.provider")
public class DiaryDatabase {
    public static final int VERSION = 1;
    @Table(DiaryColumns.class)
    public static final String MEMORY = "memory";
    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + MEMORY;

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {
    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {
    }

}