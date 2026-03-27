package com.example.colorphone.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: Long) {
        prefs.edit().putLong("USER_ID", userId).apply()
    }

    fun getUserId(): Long {
        return prefs.getLong("USER_ID", -1L)
    }

    fun isLoggedIn(): Boolean {
        return getUserId() != -1L
    }

    fun clearSession() {
        prefs.edit().remove("USER_ID").apply()
    }
}
