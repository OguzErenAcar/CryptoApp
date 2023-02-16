package com.example.crypto_news_app.VM

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.crypto_news_app.loginmodel.LoginDataSource
import com.example.crypto_news_app.loginmodel.LoginRepository
import com.example.crypto_news_app.model.ApiData
import com.example.crypto_news_app.model.Coin
import com.example.crypto_news_app.model.CoinQuotes
import com.example.crypto_news_app.services.RoomCoin.CoinDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class Coin_detailsVM(application: Application,val Firestore: FirebaseFirestore,val auth: FirebaseAuth): BaseVM(application){

    val CoinLiveData=MutableLiveData<Coin>()
    private lateinit var Coin:Coin
    //https://www.mobiler.dev/post/kotlin-coroutines-in-genel-tanitimi-ve-kullanimi

    private val CollectionName="Users"
    private val userId=auth.currentUser?.uid ?: "nullids"
    private val arrayName="Symbols"

    fun roomVerisiniAl(uuid:Int):Job{
     return launch {
            val infodao = CoinDatabase(getApplication()).CoinInfoDao()
            Coin=Coin(uuid)
            Coin.Coin_Info=infodao.getCoinInfo(uuid)
            CoinLiveData.value =Coin
        }
    }

    //bunun hem coinden sonra çalışması lazm hem asenkron olmaması
    fun isFav(Bool:(Boolean)->Unit){

        Firestore.collection(CollectionName).document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val symbols = documentSnapshot.get(arrayName) as ArrayList<String>
                    for(i in symbols ){
                        if(Coin.Coin_Info?.symbol==i){
                            Bool(true)
                            System.out.println("buyda")
                            break
                        }
                        else{
                            Bool(false)
                        }
                    }
                }
            }
        System.out.println("is fav ")

    }

   fun  changeFav(isAdd:Boolean){

           Firestore.collection(CollectionName).document(userId).get()
           .addOnSuccessListener { documentSnapshot ->
               if (documentSnapshot.exists()) {
                   //set data
                   val symbols = documentSnapshot.get(arrayName) as ArrayList<String>
                   if(isAdd){
                       symbols.add(Coin.Coin_Info?.symbol!!)
                   }else{
                       symbols.remove(Coin.Coin_Info?.symbol!!)
                   }

                   val postHashMap = hashMapOf<String, Any>()
                   postHashMap.put(arrayName, symbols)

                   Firestore.collection(CollectionName).document(userId).set(postHashMap, SetOptions.merge())
                       .addOnCompleteListener { task ->
                           if (task.isSuccessful) {
                               System.out.println("Success")
                           }
                       }.addOnFailureListener {
                           System.out.println(it.message.toString())
                       }
               } else {
                   System.out.println("Document does not exist")
                   //şablon olmadığı için
                   val arrayList= arrayListOf<String>()
                   arrayList.add(Coin.Coin_Info?.symbol!!)
                   val postHashMap = hashMapOf<String, Any>()

                   postHashMap.put(arrayName,arrayList)

                   Firestore.collection(CollectionName).document(userId).set(postHashMap, SetOptions.merge())
                       .addOnCompleteListener { task->
                           if(task.isSuccessful){
                               System.out.println("Sablon olustu ve eklendi   ")
                           }
                       }.addOnFailureListener {
                           System.out.println( it.message.toString())
                       }
               }
           }
           .addOnFailureListener {
               System.out.println(it.message.toString())
           }
    }


    fun addFav(){
        changeFav(true)
    }

    fun deleteFav(){
        changeFav(false)
    }


  public override fun verileriInternettenAl(){
//       var CoinsNamesArray =CoinsNameArray(getApplication())
//       var CoinNames = CoinsNameString(CoinsNamesArray)
     disposable.add(
           CoinApiServis.getQuotesData(Coin.Coin_Info?.symbol!!)
               .subscribeOn(Schedulers.newThread())//yeni threadde işlemler asenkron ve kullanıcı beklemesin programı diye
               .observeOn(AndroidSchedulers.mainThread())//mainde gözlemler
               .subscribeWith(object : DisposableSingleObserver<ApiData>(){
                   override fun onSuccess(t: ApiData) {

                        sqlitetaSakla(t.toQuotesCoin(Coin.Coin_Info?.symbol!!))
                   }
                   override fun onError(e: Throwable) {
                       System.out.println("------hata-----")
                       System.out.println( e.printStackTrace().toString())
                   }
               })
       )
    }
//gerek yok aslnda
    private fun sqlitetaSakla(CoinQuotes: CoinQuotes) {

        launch {
            val quotesdao = CoinDatabase(getApplication()).CoinQuotesDao()
            val id= quotesdao.getuuid(Coin.uuid)
            if(id!=null) {
                quotesdao.insert(CoinQuotes)
            }
            Coin.Coin_Quotes =CoinQuotes
            CoinLiveData.value=Coin
        }

    }

//      fun roomVerisiniAl(uuid:Int){
//        var i = 0
//        launch {
//            withContext(Dispatchers.IO){
//                System.out.println("withContext")
//                i=3
//            }
//            System.out.println("launch")
//              i=5
//        }
//          System.out.println("fonksion")
//          System.out.println(i)
//    }

}