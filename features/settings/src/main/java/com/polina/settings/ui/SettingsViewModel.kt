package com.polina.settings.ui

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {
    private val _darkTheme = MutableStateFlow<String?>(sharedPreferences.getString("darkTheme", "light"))
    val darkTheme: StateFlow<String?> = _darkTheme.asStateFlow()
    private val _colorTheme = MutableStateFlow(sharedPreferences.getString("colorTheme", "green")?: "green")
    val colorTheme: StateFlow<String> = _colorTheme.asStateFlow()

    fun setTheme() {
        val newTheme = when (_darkTheme.value) {
            "dark" -> "light"
            else -> "dark"
        }
        _darkTheme.value = newTheme
        sharedPreferences.edit().putString("darkTheme", newTheme).apply()
    }
    fun setColor(newColor: String){
        _colorTheme.value = newColor
        sharedPreferences.edit().putString("colorTheme", newColor).apply()
    }

}