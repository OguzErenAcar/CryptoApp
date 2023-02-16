package com.example.crypto_news_app.VM

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crypto_news_app.model.ApiData
import com.example.crypto_news_app.model.Coin
import com.example.crypto_news_app.model.CoinInfo
import com.example.crypto_news_app.services.RoomCoin.CoinDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProfilVM(application: Application, val Firestore: FirebaseFirestore,val auth: FirebaseAuth): BaseVM(application) {

    val Coinler_Info =MutableLiveData<List<CoinInfo>>()

    private val CollectionName="Users"
    private val userId=auth.currentUser?.uid ?: "nullid"
    private val arrayName="Symbols"
    private var SymbolsArray=ArrayList<String>()

    fun getFavCoinInFirebase(callback:(ArrayList<String>)->Unit){

        Firestore.collection(CollectionName).document(userId).get().addOnSuccessListener { documentSnapshot->
            if(documentSnapshot.exists()){

                callback(documentSnapshot.get(arrayName) as ArrayList<String>)
            }else{
                System.out.println("firebasede  fav coin yok ")
                callback(documentSnapshot.get(arrayName) as ArrayList<String>)
            }
        }.addOnFailureListener {
            System.out.println("firebasede hata var profilVM ${it.message.toString()}")

        }
    }

    fun FavCoinleriRoomdanAl(){


        getFavCoinInFirebase {
            SymbolsArray=it
            launch {
                var Array= arrayListOf<CoinInfo>()
                val CoinInfoDatabase= CoinDatabase(getApplication()).CoinInfoDao()
                var i =0
                System.out.println("size "+ SymbolsArray.size)
                while(i<SymbolsArray.size){
                    //sembolle aratıp onu live dataya ekle
                    val symbol = SymbolsArray[i]
                    val CoinInfo= CoinInfoDatabase.getCoinInfoWithSymbol(symbol)
                    Array.add(CoinInfo)
                    i+=1
                }
                Coinler_Info.value=Array

            }

        }

        //room olmadan
//        getFavCoinInFirebase {
//            SymbolsArray=it
//            val CoinNames = CoinsNameString(SymbolsArray)
//               disposable.add(
//                CoinApiServis.getInfoData(CoinNames)
//                    .subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeWith(object :DisposableSingleObserver<ApiData>(){
//                        override fun onSuccess(t: ApiData) {
//                            val list =  t.toinfoCoinList(SymbolsArray)
//                            System.out.println(list)
//                                var i = 0
//                                while(i<list.size){
//                                    list.get(i).uuid=i
//                                    i += 1
//                                 }
//                          Coinler_Info.value=list
//                        }
//
//                        override fun onError(e: Throwable) {
//                            System.out.println("------hata-----")
//                            System.out.println( e.printStackTrace().toString())
//                        }
//
//                    })
//            )
//
//        }
    }




}