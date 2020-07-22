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
    val getDictionaries: MutableList<DictionaryClass>
        get() {
            val list: MutableList<DictionaryClass> = ArrayList()
            val queryResult = database!!.rawQuery("SELECT * FROM dictionary", null)
            if (queryResult.moveToFirst()) {
                do {
                    val dictionaryClass = DictionaryClass()
                    dictionaryClass.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                    dictionaryClass.hiragana = queryResult.getString(queryResult.getColumnIndex(
                        COL_HIRAGANA))
                    dictionaryClass.kanji = queryResult.getString(queryResult.getColumnIndex(
                        COL_KANJI))
                    dictionaryClass.katakana = queryResult.getString(queryResult.getColumnIndex(
                        COL_KATAKANA))
                    list.add(dictionaryClass)
                }while(queryResult.moveToNext())
                queryResult.close()
            }
//            cursor.moveToFirst()
//            while (!cursor.isAfterLast) {
//                list.add(cursor.getString(2))
//                cursor.moveToNext()
//            }

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