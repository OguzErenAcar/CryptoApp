package com.example.crypto_news_app.services

import com.example.crypto_news_app.model.ApiData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


//retrofit
//rx java obervable type -->SEARCH
interface CoinApi {

    @Headers( "X-CMC_PRO_API_KEY: f2a3eaea-7628-4b02-89b4-731c4bd1968b")
    @GET("info")//buraya nasıl list yazıcaz
    fun getInfoApiData(@Query("symbol") CoinlistString: String): Single<ApiData>

    @Headers( "X-CMC_PRO_API_KEY: f2a3eaea-7628-4b02-89b4-731c4bd1968b")
    @GET("quotes/latest")//buraya nasıl list yazıcaz
    fun getQuotesData(@Query("symbol") CoinlistString: String): Single<ApiData>


}