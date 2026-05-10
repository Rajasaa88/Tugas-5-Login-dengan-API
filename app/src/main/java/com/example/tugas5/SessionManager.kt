package com.example.tugas5

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString("USER_TOKEN", token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString("USER_TOKEN", null)
    }

    fun saveUserName(name: String) {
        prefs.edit().putString("USER_NAME", name).apply()
    }

    fun fetchUserName(): String? {
        return prefs.getString("USER_NAME", "User")
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}