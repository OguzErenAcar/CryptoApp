package com.example.crypto_news_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

@Entity
 data class CoinInfo (
    @ColumnInfo(name="name")//sqlite da bir kolon
    @SerializedName("name")
   val name: String?,
    @ColumnInfo(name="symbol")
    @SerializedName("symbol")
    val symbol:String?,
    @ColumnInfo(name="category")
    @SerializedName("category")
    val category:String?,
    @ColumnInfo(name="description")
    @SerializedName("description")
    val description:String?,
    @ColumnInfo(name="logo")
    @SerializedName("logo")
    val logo:String?,
    @ColumnInfo(name="urls")
    @SerializedName("urls")
    val urls: JsonObject?
)  {
   @PrimaryKey
   var uuid:Int=0
}






