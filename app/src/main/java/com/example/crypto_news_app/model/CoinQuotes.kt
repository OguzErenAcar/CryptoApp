package com.example.crypto_news_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

@Entity
data class CoinQuotes  (
    @ColumnInfo(name="max_supply")
    @SerializedName("max_supply")
    val max_supply:Double?,
    @ColumnInfo(name="circulating_supply")
    @SerializedName("circulating_supply")
    val circulating_supply:Double?,
    @ColumnInfo(name="total_supply")
    @SerializedName("total_supply")
    val total_supply:Double?,
    @ColumnInfo(name="last_updated")
    @SerializedName("last_updated")
    val last_updated:String?,
    @ColumnInfo(name="quote")
    @SerializedName("quote")
    val quote: JsonObject?,
    @ColumnInfo(name="price")
    @SerializedName("price")
    var price: String?,
    @ColumnInfo(name="percent_1h")
    @SerializedName("percent_1h")
    var percent_1h: String?,
    @ColumnInfo(name="percent_24h")
    @SerializedName("percent_24h")
    var percent_24h: String?
) {

 @PrimaryKey
   var uuid:Int=0
}