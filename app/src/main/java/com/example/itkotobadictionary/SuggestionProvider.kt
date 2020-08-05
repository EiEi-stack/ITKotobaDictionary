package com.example.itkotobadictionary

import android.content.SearchRecentSuggestionsProvider

class SuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.example.itkotobadictionary.SuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }
}