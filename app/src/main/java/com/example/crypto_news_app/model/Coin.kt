package com.example.crypto_news_app.model

import androidx.room.*
import com.example.crypto_news_app.util.Converter
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import org.json.JSONObject
import java.util.UUID

//sqlite da tablolamya yarar
  class Coin(
  var uuid:Int
  )   {
    var Coin_Info: CoinInfo?=null
    var Coin_Quotes: CoinQuotes?=null


}




