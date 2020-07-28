package com.example.itkotobadictionary

import android.content.SearchRecentSuggestionsProvider
import android.database.Cursor
import android.database.MergeCursor


class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.itkotobadictionary.MySuggestionProvider"
        const val MODE: Int = SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }
}