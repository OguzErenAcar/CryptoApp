package com.example.crypto_news_app.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import java.util.prefs.Preferences

//sharepref kucuk verileri uygula üzerinde tutmaya
//ve bunu kolay paylaşmaya yarar
class OzelSharedPreferences {


    private val ZAMAN ="zaman"
    companion object{

        private var SharedPreferences: SharedPreferences? =null

        @Volatile private  var instance:OzelSharedPreferences? =null

        private val lock =Any()
        operator  fun invoke(context: Context):OzelSharedPreferences = instance ?: synchronized(lock){
            instance ?: OzelSharedPreferencesYap(context).also{
                instance=it
            }
        }

        private fun OzelSharedPreferencesYap(context: Context):OzelSharedPreferences{
            SharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return  OzelSharedPreferences()
        }


    }

    fun zamaniKaydet(zaman:Long){
        SharedPreferences?.edit(commit = true){
            putLong(ZAMAN,zaman)
        }
    }

    fun zamaniAL()= SharedPreferences?.getLong(ZAMAN,0)
}