package com.polina.settings.ui

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {
    private val _darkTheme =
        MutableStateFlow<String?>(sharedPreferences.getString("darkTheme", "light"))
    val darkTheme: StateFlow<String?> = _darkTheme.asStateFlow()
    private val _colorTheme =
        MutableStateFlow(sharedPreferences.getString("colorTheme", "green") ?: "green")
    val colorTheme: StateFlow<String> = _colorTheme.asStateFlow()
    private val _language = MutableStateFlow(
        sharedPreferences.getString("language", null)
            ?: getSystemLanguage()
    )
    private val _sound =
        MutableStateFlow<String>(sharedPreferences.getString("sound", "true") ?: "true")
    val sound: StateFlow<String> = _sound.asStateFlow()

    val language: StateFlow<String> = _language.asStateFlow()
    var version = ""
    fun setTheme() {
        val newTheme = when (_darkTheme.value) {
            "dark" -> "light"
            else -> "dark"
        }
        _darkTheme.value = newTheme
        sharedPreferences.edit().putString("darkTheme", newTheme).apply()
    }

    fun setSound() {
        val newSound = when (sound.value) {
            "true" -> "false"
            else -> "true"
        }
        _sound.value = newSound
        sharedPreferences.edit().putString("sound", newSound).apply()
    }

    fun setColor(newColor: String) {
        _colorTheme.value = newColor
        sharedPreferences.edit().putString("colorTheme", newColor).apply()
    }

    fun setLanguage(newLanguage: String) {
        _language.value = newLanguage
        sharedPreferences.edit().putString("language", newLanguage).apply()
    }

    private fun getSystemLanguage(): String {
        return AppCompatDelegate.getApplicationLocales()[0]?.language
            ?: Locale.getDefault().language
    }

}