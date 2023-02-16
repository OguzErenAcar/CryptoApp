package com.example.crypto_news_app.VM

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.crypto_news_app.model.Coin
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.services.CoinAPIService
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

 open class BaseVM(application: Application, ):AndroidViewModel(application),CoroutineScope {

    protected val disposable = CompositeDisposable()
    protected val CoinApiServis= CoinAPIService()

    protected companion object {
        val Coinler = ArrayList<Coin>()
    }

    private val job= Job()
    override val coroutineContext: CoroutineContext
    get()= job+Dispatchers.Main //arkplanda iş yapılıp maine dönülücek


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun CoinsNametoArray(context: Context): ArrayList<String> {
        val jsonString = context.assets.open("coins.json")
            .bufferedReader()
            .use { it.readText() }
        val json = Gson().fromJson(jsonString, JsonObject::class.java)
        val array = Gson().fromJson(json["coins"].toString(), ArrayList<String>()::class.java)

        return array
    }

    protected fun CoinsNameString(Array:ArrayList<String>):String{
        val arrayString= Array.toString()
        var response =arrayString.substring(1,arrayString.length-1).filter { !it.isWhitespace() }

        return response
    }

    protected open fun verileriInternettenAl() { }

//    protected fun addCoinList(list:List<CoinInfo>){
//        for(coin in list){
//            var Coin=Coin()
//            Coin.Coin_Info=coin
//            Coinler.add(Coin)
//        }
//    }

}