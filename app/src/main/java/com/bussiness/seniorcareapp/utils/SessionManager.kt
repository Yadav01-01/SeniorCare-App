package com.bussiness.seniorcareapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(private val context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_SKIP_LOGIN = "is_skip_login"
    }

    // Call this when user successfully logs in
    fun setLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, value)
            if (value) {
                putBoolean(KEY_IS_SKIP_LOGIN, false) // reset skip status
            }
            apply()
        }
    }

    // Call this when user skips login from onboarding
    fun setSkipLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(KEY_IS_SKIP_LOGIN, value)
            if (value) {
                putBoolean(KEY_IS_LOGGED_IN, false) // reset login status
            }
            apply()
        }
    }

    fun isLoggedIn(): Boolean = preferences.getBoolean(KEY_IS_LOGGED_IN, false)

    fun isSkippedLogin(): Boolean = preferences.getBoolean(KEY_IS_SKIP_LOGIN, false)

    fun clearSession() {
        preferences.edit { clear() }
    }
}
