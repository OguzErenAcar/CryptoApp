package com.example.crypto_news_app.util

import androidx.room.TypeConverter
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.model.CoinQuotes
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

public class Converter {

    @TypeConverter
   public fun  jsonToString(data: JsonObject?): String = data.toString()
    @TypeConverter
   public fun stringToJson(json: String): JsonObject {
       if(json==""){
           val string_="{"+"message"+":"+"null"+"}"
           return Gson().fromJson(string_,JsonObject::class.java)

       }else{
           return Gson().fromJson(json,JsonObject::class.java)
       }
    }



}