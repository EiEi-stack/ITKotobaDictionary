package com.example.itkotobadictionary

import android.content.ContentValues
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
                    dictionaryClass.name =
                        queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                    dictionaryClass.hiragana = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_HIRAGANA
                        )
                    )
                    dictionaryClass.kanji = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_KANJI
                        )
                    )
                    dictionaryClass.katakana = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_KATAKANA
                        )
                    )
                    dictionaryClass.favouriteStatus = queryResult.getInt(
                        queryResult.getColumnIndex(
                            COL_FAVOURITE_STATUS
                        )
                    )
                    list.add(dictionaryClass)
                } while (queryResult.moveToNext())
                queryResult.close()
            }

            return list
        }

    val getFavouriteDictionaries: MutableList<DictionaryClass>
        get() {
            val list: MutableList<DictionaryClass> = ArrayList()
            val queryResult =
                database!!.rawQuery("SELECT * FROM dictionary WHERE $COL_FAVOURITE_STATUS=1", null)
            if (queryResult.moveToFirst()) {
                do {
                    val dictionaryClass = DictionaryClass()
                    dictionaryClass.name =
                        queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                    dictionaryClass.hiragana = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_HIRAGANA
                        )
                    )
                    dictionaryClass.katakana = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_KATAKANA
                        )
                    )
                    dictionaryClass.kanji = queryResult.getString(
                        queryResult.getColumnIndex(
                            COL_KANJI
                        )
                    )
                    dictionaryClass.favouriteStatus = queryResult.getInt(
                        queryResult.getColumnIndex(
                            COL_FAVOURITE_STATUS
                        )
                    )
                    list.add(dictionaryClass)
                } while (queryResult.moveToNext())
                queryResult.close()
            }

            return list
        }

    fun updateFavourite(itemId: Long, isFavourite: Int) {
        val queryResult = database?.rawQuery("SELECT * FROM dictionary WHERE $COL_ID=$itemId", null)
        if (queryResult != null) {
            if (queryResult.moveToFirst()) {
                do {
                    val kotoba = DictionaryClass()
                    kotoba.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                    kotoba.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                    kotoba.hiragana =
                        queryResult.getString(queryResult.getColumnIndex(COL_HIRAGANA))
                    kotoba.katakana =
                        queryResult.getString(queryResult.getColumnIndex(COL_KATAKANA))
                    kotoba.kanji = queryResult.getString(queryResult.getColumnIndex(COL_KANJI))
                    kotoba.favouriteStatus = isFavourite
                    updateKotoba(kotoba)

                } while (queryResult.moveToNext())
                queryResult?.close()
            }
        }
    }

    private fun updateKotoba(kotoba: DictionaryClass) {
        val cv = ContentValues()
        cv.put(COL_ID, kotoba.id)
        cv.put(COL_NAME, kotoba.name)
        cv.put(COL_HIRAGANA, kotoba.hiragana)
        cv.put(COL_KATAKANA, kotoba.katakana)
        cv.put(COL_KANJI, kotoba.kanji)
        cv.put(COL_FAVOURITE_STATUS, kotoba.favouriteStatus)
        database?.update(TABLE_DICTIONARY, cv, "$COL_ID=?", arrayOf((kotoba.id.toString())))
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