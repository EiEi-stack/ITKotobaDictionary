package com.example.itkotobadictionary

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null

    /**
     * Open the database connection.
     */
    fun open() {
        database = openHelper.writableDatabase
    }

    /**
     * Close the database connection.
     */
    fun close() {
        if (database != null) {
            database!!.close()
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    val getDictionaries: List<String>
        get() {
            val list: MutableList<String> = ArrayList()
            val cursor = database!!.rawQuery("SELECT * FROM dictionary", null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                list.add(cursor.getString(2))
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }

    companion object {
        private var instance: DatabaseAccess? = null

        /**
         * Return a singleton instance of DatabaseAccess.
         *
         * @param context the Context
         * @return the instance of DabaseAccess
         */
        fun getInstance(context: Context): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    init {
        openHelper = DatabaseOpenHelper(context)
    }
}