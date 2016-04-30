package com.example.shibli.databse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shibli on 4/25/2016.
 */
public class DatabaseScema {

    Shiblihelper shiblihelper;

    DatabaseScema(Context context) {
        shiblihelper = new Shiblihelper(context);


    }


    // inner database helper class
    static class Shiblihelper extends SQLiteOpenHelper {

        //database information
        private static final String CONTACT_DATABSE_NAME = "contact_database";
        private static final String CONTACT_TABLE_NAME = "contact_table";
        private static final String GROUP_TABLE_NAME="group_table";
        private static final int CONTACT_DATABASE_VERSION = 1;

        //ContactFragment table columns
        private static final String CONTACT_NAME = "Name";
        private static final String CONTACT_ID = "ID";
        private static final String CONTACT_NUMBER1 = "Number1";
        private static final String CONTACT_NUMBER2 = "Number2";

        //GroupFragment table column


        private static final String CREATE_CONTACT_TABLE = "CREATE TABLE " +
                CONTACT_TABLE_NAME + "(" + CONTACT_NAME + " VARCHAR(255), " +
                CONTACT_ID + " VARCHAR(255), " + CONTACT_NUMBER1 + " VARCHAR(255), " +
                CONTACT_NUMBER2 + " VARCHAR(255);";

        private static final String CREATE_GROUP_TABLE="CREATE TABLE " +
                GROUP_TABLE_NAME + "(" + CONTACT_NAME + " VARCHAR(255), " +
                CONTACT_ID + " VARCHAR(255), " + CONTACT_NUMBER1 + " VARCHAR(255), " +
                CONTACT_NUMBER2 + " VARCHAR(255);";


        public Shiblihelper(Context context) {
            super(context, CONTACT_DATABSE_NAME, null, CONTACT_DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
