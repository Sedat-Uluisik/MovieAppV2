package com.sedat.movieappv2.util

import android.content.Context

class SharedPrefsLanguage(context: Context) {
    private val shared = context.getSharedPreferences("com.sedat.movieappv2", Context.MODE_PRIVATE)

    fun saveLanguage(language: String){
        shared.edit().putString("language", language).apply()
    }
    fun getLanguage(): String{
        return shared.getString("language", "en").toString()
    }
}