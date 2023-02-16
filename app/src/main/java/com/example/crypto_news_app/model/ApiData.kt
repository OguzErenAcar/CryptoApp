package com.example.crypto_news_app.model

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import java.util.*
import kotlin.collections.ArrayList




data class ApiData (
    @SerializedName("data")//burası apideki ile aynı olmalı
    val data : LinkedTreeMap<String, Any?> ,val context: Context)
{

    //olmadı constructor
    //vm de yap



    //???
    //parametre olarak CoinsName Ver
fun toinfoCoinList( CoinsNameArray:ArrayList<String>): List<CoinInfo>{
    //for
    val array= ArrayList<CoinInfo>()
    for (coinsymbol in CoinsNameArray){
        val coin=data.get(coinsymbol) as ArrayList<*>
        val jsonstring= Gson().toJsonTree(coin.get(0)).asJsonObject.toString()
        val coinInfo_ = Gson().fromJson(jsonstring , CoinInfo::class.java)

    //    System.out.println(coinInfo_.uuid)
        array.add(coinInfo_)
    }

    return  array
}

@SuppressLint("SuspiciousIndentation")
fun toQuotesCoin(CoinNameString:String): CoinQuotes {
    //for
        lateinit var Coin:CoinQuotes
        val coin=data.get(CoinNameString) as ArrayList<*>
        val jsonstring= Gson().toJsonTree(coin.get(0)).asJsonObject.toString()
        val coinQuotes = Gson().fromJson(jsonstring,CoinQuotes::class.java)
        val quotesStr=coinQuotes.quote?.get("USD").toString()
        val USD = Gson().fromJson(quotesStr,JsonObject::class.java)

     coinQuotes.price= USD.get("price").toString()
     coinQuotes.percent_24h= USD.get("percent_change_24h").toString()
     coinQuotes.percent_1h=  USD.get("percent_change_1h").toString()

        return coinQuotes
}


}
  //  data?.getString("coins")?.length ?:nedir
    //json parse
//    fun toCoinList():List<Coin>{
//
//      val CoinList:List<Coin>
//      CoinList= ArrayList<Coin>()
//
//      val array :ArrayList<Any>
//      array = data.get("coins") as ArrayList<Any>//bura gson ile direk alınablr ve döngüye gerek kalmayablr
//
//      for (i in array){
//        val Coin:LinkedTreeMap<String,Any>
//        Coin = i as LinkedTreeMap<String,Any>
//
//        val coinname:String= Coin.get("symbol") as String
//        val coinprice:String= Coin.get("price") as String
//        val iconUrl:String=Coin.get("iconUrl") as String
//        val Coin_=Coin(coinname,coinprice,iconUrl)
//        CoinList.add(Coin_)
//
//      }
//      return CoinList
//  }

//        val CoinList:List<Coin>
//        CoinList= ArrayList<Coin>()
//
//       val Coinarray = JSONTokener(data.get("coins") as String?).nextValue() as JSONObject
//
//        for (i in 0 until Coinarray.length()) {
//            val Coin_=Coin(Coinarray.getString("symbol"),Coinarray.getString("price"))
//            CoinList.add(Coin_)
//        }
//        return Single.just(CoinList)

