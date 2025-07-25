package com.example.xuehanyu.core.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "SharedPrefHelper"
        private const val PREF_NAME = "xuehanyu_preferences"
        
        // Preference keys
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_FIRST_LAUNCH = "is_first_launch"
        private const val KEY_THEME_MODE = "theme_mode"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_SEARCH_HISTORY = "search_history"
        private const val KEY_FAVORITE_COUNT = "favorite_count"
        private const val KEY_LAST_SEARCH = "last_search"
        private const val KEY_APP_VERSION = "app_version"
        private const val KEY_DICTIONARY_LAST_UPDATE = "dictionary_last_update"
        private const val KEY_LOGIN_SKIPPED = "login_skipped"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // User ID management
    fun getUserId(): String {
        return sharedPreferences.getString(KEY_USER_ID, "default_user") ?: "default_user"
    }

    fun setUserId(userId: String) {
        Log.d(TAG, "Setting user ID: $userId")
        sharedPreferences.edit {
            putString(KEY_USER_ID, userId)
        }
    }

    // First launch detection
    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_LAUNCH, true)
    }

    fun setFirstLaunchComplete() {
        Log.d(TAG, "Setting first launch as complete")
        sharedPreferences.edit {
            putBoolean(KEY_IS_FIRST_LAUNCH, false)
        }
    }

    // Theme management
    fun getThemeMode(): String {
        return sharedPreferences.getString(KEY_THEME_MODE, "system") ?: "system"
    }

    fun setThemeMode(themeMode: String) {
        Log.d(TAG, "Setting theme mode: $themeMode")
        sharedPreferences.edit {
            putString(KEY_THEME_MODE, themeMode)
        }
    }

    // Language management
    fun getLanguage(): String {
        return sharedPreferences.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    fun setLanguage(language: String) {
        Log.d(TAG, "Setting language: $language")
        sharedPreferences.edit {
            putString(KEY_LANGUAGE, language)
        }
    }

    // Search history management
    fun getSearchHistory(): Set<String> {
        return sharedPreferences.getStringSet(KEY_SEARCH_HISTORY, emptySet()) ?: emptySet()
    }

    fun addToSearchHistory(query: String) {
        if (query.isBlank()) return
        
        val currentHistory = getSearchHistory().toMutableSet()
        currentHistory.add(query.trim())
        
        // Keep only last 20 searches
        if (currentHistory.size > 20) {
            val sortedHistory = currentHistory.toList().takeLast(20)
            currentHistory.clear()
            currentHistory.addAll(sortedHistory)
        }
        
        Log.d(TAG, "Adding to search history: '$query' (total: ${currentHistory.size})")
        sharedPreferences.edit {
            putStringSet(KEY_SEARCH_HISTORY, currentHistory)
        }
    }

    fun clearSearchHistory() {
        Log.d(TAG, "Clearing search history")
        sharedPreferences.edit {
            remove(KEY_SEARCH_HISTORY)
        }
    }

    // Favorite count tracking
    fun getFavoriteCount(): Int {
        return sharedPreferences.getInt(KEY_FAVORITE_COUNT, 0)
    }

    fun setFavoriteCount(count: Int) {
        Log.d(TAG, "Setting favorite count: $count")
        sharedPreferences.edit {
            putInt(KEY_FAVORITE_COUNT, count)
        }
    }

    fun incrementFavoriteCount() {
        val currentCount = getFavoriteCount()
        setFavoriteCount(currentCount + 1)
    }

    fun decrementFavoriteCount() {
        val currentCount = getFavoriteCount()
        if (currentCount > 0) {
            setFavoriteCount(currentCount - 1)
        }
    }

    // Last search tracking
    fun getLastSearch(): String {
        return sharedPreferences.getString(KEY_LAST_SEARCH, "") ?: ""
    }

    fun setLastSearch(query: String) {
        Log.d(TAG, "Setting last search: '$query'")
        sharedPreferences.edit {
            putString(KEY_LAST_SEARCH, query)
        }
    }

    // App version tracking
    fun getAppVersion(): String {
        return sharedPreferences.getString(KEY_APP_VERSION, "") ?: ""
    }

    fun setAppVersion(version: String) {
        Log.d(TAG, "Setting app version: $version")
        sharedPreferences.edit {
            putString(KEY_APP_VERSION, version)
        }
    }

    // Dictionary update tracking
    fun getDictionaryLastUpdate(): Long {
        return sharedPreferences.getLong(KEY_DICTIONARY_LAST_UPDATE, 0L)
    }

    fun setDictionaryLastUpdate(timestamp: Long) {
        Log.d(TAG, "Setting dictionary last update: $timestamp")
        sharedPreferences.edit {
            putLong(KEY_DICTIONARY_LAST_UPDATE, timestamp)
        }
    }

    fun setLoginSkipped(skipped: Boolean) {
        Log.d(TAG, "Setting login skipped: $skipped")
        sharedPreferences.edit {
            putBoolean(KEY_LOGIN_SKIPPED, skipped)
        }
    }

    fun getLoginSkipped(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGIN_SKIPPED, false)
    }

    // Utility methods
    fun clearAllPreferences() {
        Log.d(TAG, "Clearing all preferences")
        sharedPreferences.edit {
            clear()
        }
    }

    fun removePreference(key: String) {
        Log.d(TAG, "Removing preference: $key")
        sharedPreferences.edit {
            remove(key)
        }
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    // Debug method to log all preferences
    fun logAllPreferences() {
        Log.d(TAG, "=== All Preferences ===")
        Log.d(TAG, "User ID: ${getUserId()}")
        Log.d(TAG, "First Launch: ${isFirstLaunch()}")
        Log.d(TAG, "Theme Mode: ${getThemeMode()}")
        Log.d(TAG, "Language: ${getLanguage()}")
        Log.d(TAG, "Search History: ${getSearchHistory()}")
        Log.d(TAG, "Favorite Count: ${getFavoriteCount()}")
        Log.d(TAG, "Last Search: '${getLastSearch()}'")
        Log.d(TAG, "App Version: ${getAppVersion()}")
        Log.d(TAG, "Dictionary Last Update: ${getDictionaryLastUpdate()}")
        Log.d(TAG, "Login Skipped: ${getLoginSkipped()}")
        Log.d(TAG, "=======================")
    }
}