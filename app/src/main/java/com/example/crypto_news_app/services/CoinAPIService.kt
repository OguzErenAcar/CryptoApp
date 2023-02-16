package com.example.crypto_news_app.services

import com.example.crypto_news_app.model.ApiData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CoinAPIService {

//BASE_URL="https://raw.githubusercontent.com/"
    private val BASE_URL="https://pro-api.coinmarketcap.com/v2/cryptocurrency/"
    private val info_api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinApi::class.java)//burda belirtiyor api bu sınıf türünden dönüyor

    private val quotes_api=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CoinApi::class.java)


    //bunun coin listesi dönmesi gerek
   // List<Single<Coin>>
    fun getInfoData(CoinList:String):Single<ApiData>{
        return  info_api.getInfoApiData(CoinList)
    }
    fun getQuotesData(CoinList:String):Single<ApiData>{
        return  quotes_api.getQuotesData(CoinList)
    }

}